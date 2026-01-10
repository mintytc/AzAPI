package me.mintytc.azapi.core.storage.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.mintytc.azapi.core.storage.StorageException;
import me.mintytc.azapi.core.storage.StorageProvider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class JsonStorage implements StorageProvider {

    private final File file;
    private final Gson gson = new Gson();
    private Map<String, Object> data = new HashMap<>();

    public JsonStorage(File file) {
        this.file = file;
        loadFile();
    }

    @SuppressWarnings("unchecked")
    private void loadFile() {
        if (!file.exists()) {
            saveFile();
            return;
        }

        try (Reader reader = new FileReader(file)) {
            data = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {
            }.getType());
            if (data == null) data = new HashMap<>();
        } catch (IOException e) {
            throw new StorageException("Failed to load JSON storage", e);
        }
    }

    private void saveFile() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new StorageException("Failed to save JSON storage", e);
        }
    }

    @Override
    public void save(String key, Object value) {
        data.put(key, value);
        saveFile();
    }

    @Override
    public Object load(String key) {
        return data.get(key);
    }

    @Override
    public void delete(String key) {
        data.remove(key);
        saveFile();
    }

    @Override
    public Map<String, Object> all() {
        return new HashMap<>(data);
    }

    @Override
    public boolean contains(String key) {
        return data.containsKey(key);
    }

    @Override
    public void clear() {
        data.clear();
        saveFile();
    }
}
