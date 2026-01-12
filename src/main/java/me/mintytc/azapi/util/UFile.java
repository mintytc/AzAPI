package me.mintytc.azapi.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @since 1.0.0-R0.1
 *
 */
public class UFile {

    private UFile() {
    }

    // ========== BASIC CREATION ==========

    public static java.io.File createFile(java.io.File file) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static java.io.File createFolder(java.io.File folder) {
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    // ========== FILE READING/WRITING ==========

    public static List<String> readLines(java.io.File file) {
        try {
            return java.nio.file.Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeFile(java.io.File file, java.lang.String content) {
        createFile(file);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(java.io.File file, java.lang.String content) {
        createFile(file);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(java.io.File from, java.io.File to) {
        try {
            createFile(to);
            java.nio.file.Files.copy(from.toPath(), to.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(java.io.File file) {
        if (file.isDirectory()) {
            for (java.io.File f : file.listFiles())
                delete(f);
        }
        file.delete();
    }

    public static java.io.File saveResource(JavaPlugin plugin, java.lang.String resourcePath, boolean replace) {
        java.io.File outFile = new java.io.File(plugin.getDataFolder(), resourcePath);
        if (!outFile.exists() || replace) {
            plugin.saveResource(resourcePath, replace);
        }
        return outFile;
    }

    public static java.lang.String getNameWithoutExt(java.io.File file) {
        java.lang.String name = file.getName();
        int dot = name.lastIndexOf('.');
        return (dot == -1) ? name : name.substring(0, dot);
    }

    // ========== FILE LISTING SECTION ==========

    /**
     * List all files directly in plugin data folder (non-recursive)
     */
    public static List<java.io.File> listFiles(JavaPlugin plugin) {
        return listFiles(plugin.getDataFolder());
    }

    /**
     * List all files directly in a directory (non-recursive)
     */
    public static List<java.io.File> listFiles(java.io.File dir) {
        List<java.io.File> list = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            java.io.File[] files = dir.listFiles(java.io.File::isFile);
            if (files != null) Collections.addAll(list, files);
        }
        return list;
    }

    /**
     * List all files recursively (includes files in subdirectories)
     */
    public static List<java.io.File> listFilesRecursive(java.io.File dir) {
        List<java.io.File> list = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) return list;
        java.io.File[] files = dir.listFiles();
        if (files == null) return list;
        for (java.io.File f : files) {
            if (f.isFile()) list.add(f);
            else list.addAll(listFilesRecursive(f));
        }
        return list;
    }

    /**
     * List all files recursively from plugin data folder
     */
    public static List<java.io.File> listFilesRecursive(JavaPlugin plugin) {
        return listFilesRecursive(plugin.getDataFolder());
    }

    /**
     * List files matching a condition (non-recursive)
     */
    public static List<java.io.File> listFiles(java.io.File dir, Predicate<File> filter) {
        List<java.io.File> list = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) return list;
        java.io.File[] files = dir.listFiles();
        if (files == null) return list;
        for (java.io.File f : files)
            if (filter.test(f)) list.add(f);
        return list;
    }

    /**
     * List files recursively matching a condition
     */
    public static List<java.io.File> listFilesRecursive(java.io.File dir, Predicate<java.io.File> filter) {
        List<java.io.File> list = new ArrayList<>();
        if (!dir.exists() || !dir.isDirectory()) return list;
        java.io.File[] files = dir.listFiles();
        if (files == null) return list;
        for (java.io.File f : files) {
            if (filter.test(f)) list.add(f);
            if (f.isDirectory()) list.addAll(listFilesRecursive(f, filter));
        }
        return list;
    }

    /**
     * List from a relative path in the plugin data folder
     */
    public static List<java.io.File> listFrom(JavaPlugin plugin, java.lang.String relativePath, boolean recursive) {
        java.io.File target = new java.io.File(plugin.getDataFolder(), relativePath);
        return recursive ? listFilesRecursive(target) : listFiles(target);
    }

    /**
     * List recursively with filter and relative path
     */
    public static List<java.io.File> listFrom(JavaPlugin plugin, java.lang.String relativePath, boolean recursive, Predicate<java.io.File> filter) {
        java.io.File target = new java.io.File(plugin.getDataFolder(), relativePath);
        return recursive ? listFilesRecursive(target, filter) : listFiles(target, filter);
    }
}
