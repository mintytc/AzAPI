package me.mintytc.azapi.core.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class CacheLayer implements StorageProvider {

    private final StorageProvider backend;
    private final Map<String, Object> cache = new HashMap<>();

    public CacheLayer(StorageProvider backend) {
        this.backend = backend;
    }

    @Override
    public void save(String key, Object value) throws StorageException {
        cache.put(key, value);
        backend.save(key, value);
    }

    @Override
    public Object load(String key) throws StorageException {
        if (cache.containsKey(key)) return cache.get(key);
        Object value = backend.load(key);
        if (value != null) cache.put(key, value);
        return value;
    }

    @Override
    public void delete(String key) throws StorageException {
        cache.remove(key);
        backend.delete(key);
    }

    @Override
    public Map<String, Object> all() throws StorageException {
        return backend.all(); // Could merge cache if needed
    }

    @Override
    public boolean contains(String key) throws StorageException {
        return cache.containsKey(key) || backend.contains(key);
    }

    @Override
    public void clear() throws StorageException {
        cache.clear();
        backend.clear();
    }
}
