package me.mintytc.azapi.api.registry;

import java.util.Objects;

/**
 * @since 1.0.0-R0.1
 *
 */
public class NamespacedKey {

    String namespace;
    String key;

    public NamespacedKey(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;
    }

    public static org.bukkit.NamespacedKey of(String namespace, String key) {
        return new org.bukkit.NamespacedKey(namespace.toLowerCase(), key.toLowerCase());
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof org.bukkit.NamespacedKey) {
            org.bukkit.NamespacedKey n = (org.bukkit.NamespacedKey) o;
            return n.getNamespace().equals(namespace) &&
                    n.getKey().equals(key);
        }
        return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, key);
    }
}
