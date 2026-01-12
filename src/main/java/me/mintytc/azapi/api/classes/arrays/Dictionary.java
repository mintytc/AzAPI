package me.mintytc.azapi.api.classes.arrays;

import com.google.gson.Gson;
import me.mintytc.azapi.api.interfaces.Mergeable;
import me.mintytc.azapi.api.interfaces.Resettable;
import me.mintytc.azapi.api.interfaces.Similar;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Custom implementation of a dictionary-like data structure using hash table principles.
 * Provides methods similar to HashMap for storing and retrieving key-value pairs.
 * Handles collisions using chaining (lists of entries in buckets).
 *
 * @param <K> the type of keys maintained by this dictionary
 * @param <V> the type of mapped values
 *
 * @since 1.0.0-R0.1
 *
 */
public class Dictionary<K, V> implements Similar<Dictionary<K, V>>, Mergeable<Dictionary<K, V>>, Resettable, ConfigurationSerializable {

    private static final int DEFAULT_CAPACITY = 16;
    private static volatile double LOAD_FACTOR = 0.8;

    @SuppressWarnings("rawtypes")
    private ArrayList[] buckets;
    private int size;

    /**
     * Constructs an empty dictionary with an initial capacity of {@code DEFAULT_CAPACITY}.
     * Initializes the array of buckets and sets the size to zero.
     */
    public Dictionary() {
        buckets = new ArrayList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;
    }

    /**
     * Constructs an empty dictionary with an initial capacity of {@code CAPACITY}.
     * Initializes the array of buckets and sets the size to zero.
     */
    public Dictionary(final int CAPACITY) {
        buckets = new ArrayList[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;
    }

    /**
     * Constructs an empty dictionary with an initial capacity of {@code CAPACITY}.
     * Initializes the array of buckets and sets the size to zero.
     */
    public Dictionary(final int CAPACITY, final double LOAD_FACTOR) {
        buckets = new ArrayList[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;
        Dictionary.LOAD_FACTOR = LOAD_FACTOR;
    }

    /**
     * Constructs an empty dictionary with an initial capacity of {@code DEFAULT_CAPACITY}.
     * Initializes the array of buckets and sets the size to zero.
     */
    public Dictionary(final double LOAD_FACTOR) {
        buckets = new ArrayList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;
        Dictionary.LOAD_FACTOR = LOAD_FACTOR;
    }

    /**
     * DESERIALIZATION FOR YamlConfiguration's:
     * <p>
     * Use {@code serialize()} to input the dictionary and into the file {@code config.set(path, serialize())} and use
     * {@code deserialize(map)} to read dictionaries from inside a file {@code deserialize(config.getMap(path))}.
     */
    public static @NotNull Dictionary<String, String> deserializeYaml(@NotNull Map<String, Object> map) {
        Dictionary<String, String> dictionary = new Dictionary<>();
        String json = (String) map.get("json");

        // Remove the curly braces and split by comma
        String[] entries = json.substring(1, json.length() - 1).split(", ");
        for (String entry : entries) {
            String[] keyValue = entry.split(":( )?");
            if (keyValue.length == 2) {
                String key = keyValue[0].replace("\"", "");
                String value = keyValue[1].replace("\"", "");
                dictionary.put(key, value);
            }
        }
        return dictionary;
    }

    /**
     * Adds a key-value pair to the dictionary. If the key already exists, updates the value.
     *
     * @param k the key with which the specified value is to be associated
     * @param v the value to be associated with the specified key
     *
     * @return if the object got put into the dictionary
     */
    @SuppressWarnings("unchecked")
    public boolean put(K k, V v) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k)) {
                entry.value = v; // Update value if key exists
                return true;
            }
        }
        bucket.add(new Entry<>(k, v));
        size++;

        // Resize if load factor exceeds threshold
        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
        return true;
    }

    /**
     * Adds a key-value pair to the dictionary if the key does not already exist.
     *
     * @param k the key with which the specified value is to be associated
     * @param v the value to be associated with the specified key
     */
    public Dictionary<K, V> putIfAbsent(K k, V v) {
        if (!containsKey(k)) {
            put(k, v);
        }
        return this;
    }

    /**
     * Adds a key-value pair to the dictionary. If the key already exists, update the value. Then returns the dictionary.
     *
     * @param k the key with which the specified value is to be associated
     * @param v the value to be associated with the specified key
     */
    public Dictionary<K, V> putAndReturn(K k, V v) {
        put(k, v);
        return this;
    }

    /**
     * Retrieves the value associated with the specified key from the dictionary.
     *
     * @param k the key whose associated value is to be returned
     *
     * @return the value to which the specified key is mapped, or {@code null} if this dictionary contains no mapping for the key
     */
    @SuppressWarnings("unchecked")
    public V get(K k) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k)) {
                return entry.value;
            }
        }
        return null; // Key not found
    }

    /**
     * Retrieves the value associated with the specified key from the dictionary,
     * or returns a default value if the key is not found.
     *
     * @param k  the key whose associated value is to be returned
     * @param dv the default value to return if the key is not found
     *
     * @return the value to which the specified key is mapped, or {@code defaultValue} if the key is not found
     */
    public V getOrDefault(K k, V dv) {
        V value = get(k);
        return (value != null) ? value : dv;
    }

    /**
     * Retrieves the value associated with the specified key from the dictionary,
     * or returns and puts a default value in the dictionary if the key is not found.
     *
     * @param k  the key whose associated value is to be returned
     * @param dv the default value to return if the key is not found
     *
     * @return the value to which the specified key is mapped, or {@code defaultValue} if the key is not found
     */
    public V getOrDefaultAndPut(K k, V dv) {
        V value = get(k);
        if (value != null) return value;
        else {
            put(k, dv);
            return getOrDefault(k, dv);
        }
    }

    /**
     * Retrieves the value associated with the specified key from the dictionary
     * if the key exists, otherwise returns {@code null}.
     *
     * @param k the key whose associated value is to be returned if present
     *
     * @return the value to which the specified key is mapped, or {@code null} if this dictionary contains no mapping for the key
     */
    public V returnIfContains(K k) {
        return containsKey(k) ? get(k) : null;
    }

    /**
     * Checks if the dictionary contains the specified key.
     *
     * @param k the key whose presence in this dictionary is to be tested
     *
     * @return {@code true} if this dictionary contains a mapping for the specified key, {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean containsKey(K k) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces the value associated with the specified key in the dictionary.
     *
     * @param k the key whose associated value is to be replaced
     * @param v the new value to be associated with the specified key
     */
    @SuppressWarnings("unchecked")
    public Dictionary<K, V> replace(K k, V v) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k)) {
                entry.value = v;
                return this;
            }
        }
        return this;
    }

    /**
     * Replaces the old value with the new value for the specified key in the dictionary.
     *
     * @param k  the key whose associated value is to be replaced
     * @param ov the expected old value associated with the specified key
     * @param nv the new value to be associated with the specified key
     *
     * @return {@code true} if the value was replaced, {@code false} if the key was not found or the old value did not match
     */
    @SuppressWarnings("unchecked")
    public boolean replace(K k, V ov, V nv) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k) && entry.value.equals(ov)) {
                entry.value = nv;
                return true;
            }
        }
        return false;
    }

    /**
     * Merges a key with a new value using a remapping function if the key already exists,
     * or adds the key-value pair if the key does not exist.
     *
     * @param k                 the key with which the specified value is to be associated
     * @param v                 the value to be associated with the specified key
     * @param remappingFunction function to merge the old value and new value if the key exists
     *
     * @return The merged dictionary.
     */
    public Dictionary<K, V> merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (containsKey(k)) {
            V oldValue = get(k);
            V newValue = remappingFunction.apply(oldValue, v);
            replace(k, newValue);
        } else {
            put(k, v);
        }
        return this;
    }

    /**
     * Merges all entries from another dictionary into this dictionary.
     * Entries are only added if the key does not already exist in this dictionary.
     *
     * @param other the dictionary whose entries are to be merged into this dictionary
     *
     * @return The merged dictionary.
     */
    @Override
    public Dictionary<K, V> merge(@NotNull Dictionary<K, V> other) {
        for (K key : other.keySet()) {
            if (!containsKey(key)) {
                put(key, other.get(key));
            }
        }
        return this;
    }

    /**
     * Checks if the dictionary is empty (contains no key-value pairs).
     *
     * @return {@code true} if this dictionary contains no key-value pairs, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a list of all keys present in the dictionary.
     *
     * @return a list of all keys present in this dictionary
     */
    @SuppressWarnings("unchecked")
    public List<K> keySet() {
        List<K> keyList = new ArrayList<>();
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keyList.add(entry.key);
            }
        }
        return keyList;
    }

    /**
     * Performs the given action for each key in the dictionary.
     *
     * @param action the action to be performed for each key
     */
    @SuppressWarnings("unchecked")
    public Dictionary<K, V> forEach(Consumer<? super K> action) {
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                action.accept(entry.key);
            }
        }
        return this;
    }

    /**
     * Checks if the dictionary contains at least one key-value pair with the specified value.
     *
     * @param v the value whose presence in this dictionary is to be tested
     *
     * @return {@code true} if this dictionary contains at least one key-value pair with the specified value, {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean containsValue(V v) {
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                if (entry.value.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes the key-value pair associated with the specified key from the dictionary.
     *
     * @param k the key whose mapping is to be removed from the dictionary
     *
     * @return the previous value associated with the specified key, or {@code null} if there was no mapping for the key
     */
    @SuppressWarnings("unchecked")
    public boolean remove(K k) {
        int bucketIndex = getBucketIndex(k);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(k)) {
                bucket.remove(entry);
                size--;
                return true;
            }
        }
        return false; // Key not found
    }

    /**
     * Removes all key-value pairs from the dictionary.
     * The dictionary will be empty after this call returns.
     */
    public Dictionary<K, V> clear() {
        for (ArrayList bucket : buckets) {
            bucket.clear();
        }
        size = 0;
        return this;
    }

    /**
     * Returns the number of key-value pairs in the dictionary.
     *
     * @return the number of key-value pairs in this dictionary
     */
    public int size() {
        return size;
    }

    /**
     * Returns the capacity of the dictionary (number of buckets).
     *
     * @return the current capacity (number of buckets) of this dictionary
     */
    public int capacity() {
        return buckets.length;
    }

    /**
     * Returns the load factor threshold for resizing the dictionary.
     *
     * @return the load factor threshold for resizing
     */
    public double loadFactor() {
        return LOAD_FACTOR;
    }

    /**
     * Returns a string representation of the dictionary.
     *
     * @return a string representation of the dictionary
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                sb.append(entry.key).append("=").append(entry.value).append(", ");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last ", "
        }
        sb.append("}");
        return sb.toString();
    }

    // Private helper methods

    /**
     * Calculates the bucket index for the given key.
     *
     * @param k the key for which the bucket index is calculated
     *
     * @return the bucket index
     */
    private int getBucketIndex(@NotNull K k) {
        return Math.abs(k.hashCode() % buckets.length);
    }

    /**
     * Resizes the array of buckets when the load factor threshold is exceeded.
     * Increases the capacity by doubling the number of buckets.
     */
    @Contract(" -> this")
    @SuppressWarnings("unchecked")
    private Dictionary<K, V> resize() {
        List<Entry<K, V>>[] oldBuckets = buckets;
        buckets = new ArrayList[oldBuckets.length * 2];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (List<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                int bucketIndex = getBucketIndex(entry.key);
                buckets[bucketIndex].add(entry);
            }
        }
        return this;
    }

    /**
     * SERIALIZATION FOR YamlConfiguration's:
     * <p>
     * Use {@code serialize()} to input the dictionary and into the file {@code config.set(path, serialize())} and use
     * {@code deserialize(map)} to read dictionaries from inside of a file {@code deserialize(config.getMap(path))}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (List<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                sb.append("\"").append(entry.key).append("\": \"").append(entry.value).append("\", ");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");

        Map<String, Object> serializedMap = new HashMap<>();
        serializedMap.put("json", sb.toString());
        return serializedMap;
    }

    @SuppressWarnings("unchecked")
    public String toJson(@NotNull Dictionary<K, V> dictionary) {
        Map<K, V> map = new HashMap<>();
        for (K s : dictionary.keySet()) {
            map.put(s, dictionary.getOrDefault(s, (V) ""));
        }
        return new Gson().toJson(map);
    }

    // JSON IMPLEMENTATION

    @SuppressWarnings("unchecked")
    public Dictionary<K, V> fromJson(String str) {
        Dictionary<K, V> dictionary = new Dictionary<K, V>();
        Map gson = new Gson().fromJson(str, HashMap.class);
        for (int i = 0; i < gson.size(); i++) {
            dictionary.put((K) gson.keySet().toArray()[i], (V) gson.get(gson.keySet().toArray()[i]));
        }
        return dictionary;
    }

    @SuppressWarnings("unchecked")
    public Dictionary<K, V> createDictionary(Object @NotNull ... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Entries must be in key-value pairs.");
        }

        Dictionary<K, V> dictionary = new Dictionary<>();
        for (int i = 0; i < entries.length; i += 2) {
            K key = (K) entries[i];
            V value = (V) entries[i + 1];
            dictionary.put(key, value);
        }

        return dictionary;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Dictionary<?, ?> that = (Dictionary<?, ?>) o;
        return size == that.size && Objects.deepEquals(buckets, that.buckets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(buckets), size);
    }

    /**
     * Returns a score from 0.0 - 1.0 of how similar this dictionary is to {@code other}.
     *
     * @param other The dictionary to compare.
     *
     * @return The similarity score.
     */
    @Override
    public double similarity(Dictionary<K, V> other) {
        return 0;
    }

    /**
     *
     *
     */
    @Override
    public void reset() {

    }

    // Simple class to hold Entries for values into the array
    private static class Entry<K, V> {
        K key;
        V value;

        @Contract(pure = true)
        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }
}