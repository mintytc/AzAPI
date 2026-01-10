package me.mintytc.azapi.core.storage.sql;

import me.mintytc.azapi.core.storage.StorageException;
import me.mintytc.azapi.core.storage.StorageProvider;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class SqlStorage implements StorageProvider {

    private final Connection connection;
    private final String table;

    public SqlStorage(Connection connection, String table) {
        this.connection = connection;
        this.table = table;
        createTableIfMissing();
    }

    private void createTableIfMissing() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (key_name VARCHAR(255) PRIMARY KEY, value TEXT)");
        } catch (SQLException e) {
            throw new StorageException("Failed to create table " + table, e);
        }
    }

    @Override
    public void save(String key, Object value) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "REPLACE INTO " + table + " (key_name, value) VALUES (?, ?)")) {
            stmt.setString(1, key);
            stmt.setString(2, value.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Failed to save value for key " + key, e);
        }
    }

    @Override
    public Object load(String key) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT value FROM " + table + " WHERE key_name=?")) {
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("value");
            return null;
        } catch (SQLException e) {
            throw new StorageException("Failed to load value for key " + key, e);
        }
    }

    @Override
    public void delete(String key) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM " + table + " WHERE key_name=?")) {
            stmt.setString(1, key);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException("Failed to delete key " + key, e);
        }
    }

    @Override
    public Map<String, Object> all() {
        Map<String, Object> map = new HashMap<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {
            while (rs.next()) {
                map.put(rs.getString("key_name"), rs.getString("value"));
            }
        } catch (SQLException e) {
            throw new StorageException("Failed to fetch all values", e);
        }
        return map;
    }

    @Override
    public boolean contains(String key) {
        return load(key) != null;
    }

    @Override
    public void clear() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + table);
        } catch (SQLException e) {
            throw new StorageException("Failed to clear table " + table, e);
        }
    }
}
