package me.mintytc.azapi.core.storage.yaml;

import me.mintytc.azapi.core.storage.StorageException;
import me.mintytc.azapi.core.storage.StorageProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class YamlStorage implements StorageProvider {

    private final File file;
    private final FileConfiguration config;

    public YamlStorage(File file) {
        this.file = file;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new StorageException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save(String key, Object value) throws StorageException {
        config.set(key, value);
        saveFile();
    }

    @Override
    public Object load(String key) throws StorageException {
        return config.get(key);
    }

    @Override
    public void delete(String key) throws StorageException {
        config.set(key, null);
        saveFile();
    }

    @Override
    public Map<String, Object> all() throws StorageException {
        Map<String, Object> map = new HashMap<>();
        for (String key : config.getKeys(true)) {
            map.put(key, config.get(key));
        }
        return map;
    }

    @Override
    public boolean contains(String key) throws StorageException {
        return config.contains(key);
    }

    @Override
    public void clear() throws StorageException {
        for (String key : config.getKeys(true)) {
            config.set(key, null);
        }
        saveFile();
    }

    private void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new StorageException("Failed to save YAML storage", e);
        }
    }
}
