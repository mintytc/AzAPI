package me.mintytc.azapi.core.storage;

import java.util.Map;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface StorageProvider {

    /**
     * Saves the given key-value pair into storage.
     */
    void save(String key, Object value) throws StorageException;

    /**
     * Loads the value for the given key from storage.
     * Returns null if it doesn't exist.
     */
    Object load(String key) throws StorageException;

    /**
     * Deletes the value associated with the key.
     */
    void delete(String key) throws StorageException;

    /**
     * Returns all stored entries as an immutable map.
     */
    Map<String, Object> all() throws StorageException;

    /**
     * Checks if a key exists.
     */
    boolean contains(String key) throws StorageException;

    /**
     * Clears all data.
     */
    void clear() throws StorageException;
}
