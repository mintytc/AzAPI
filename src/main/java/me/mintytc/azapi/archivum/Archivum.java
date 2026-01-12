package me.mintytc.azapi.archivum;

import lombok.Getter;
import me.mintytc.azapi.classes.Metadata;
import me.mintytc.plugin.AzAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 1.0.0-R0.1
 *
 */
public class Archivum {

    public static final Map<String, Archivum> cachedFiles = new ConcurrentHashMap<>();
    private static final String DOT_REPLACEMENT = "$__DOT__$";
    private static final Map<String, ArchivumFileType> exactNameRegistry = new HashMap<>();
    private static final Map<String, ArchivumFileType> extensionRegistry = new HashMap<>();
    private static final ArchivumFileType DEFAULT_TYPE = new DefaultArchivumFileType();
    @Getter
    private final JavaPlugin plugin;
    @Getter
    private final String filename;
    @Getter
    private final File file;
    @Getter
    private final YamlConfiguration config;
    private final DataHandler handler;
    private final MetadataHandler metadata;
    private final TempHandler temp;
    private final Map<String, SectionHandler> sections = new HashMap<>();
    private final ConfigurationSection dataSection;
    private final ConfigurationSection codexSection;
    private final ConfigurationSection metadataSection;
    private final ArchivumFileType fileType;
    @Getter
    private List<String> rawLines;
    private boolean dirty = false;
    private boolean autoFlush = false;
    private long autoFlushDelay = 20L;
    private int autoFlushTaskId = -1;

    public Archivum(@NotNull JavaPlugin plugin, String filename) {
        this(plugin, filename, null);
    }

    public Archivum(@NotNull JavaPlugin plugin, String filename, ArchivumFileType type) {
        this.plugin = plugin;
        this.filename = filename;
        this.file = new File(plugin.getDataFolder(), filename);

        ensureFileExists();
        this.config = YamlConfiguration.loadConfiguration(file);
        ensureBaseSections();

        this.metadataSection = config.getConfigurationSection("_metadata");
        this.codexSection = config.getConfigurationSection("_codex");
        this.dataSection = config.getConfigurationSection("_data");

        ensureMetadataDefaults();

        this.handler = new DataHandler(this);
        this.metadata = new MetadataHandler(this);
        this.temp = new TempHandler();

        this.fileType = determineFileType(type);
        this.fileType.start();
        this.fileType.load(this);
    }

    public static Archivum get(@NotNull JavaPlugin plugin, String filename) {
        File f = new File(plugin.getDataFolder(), filename);
        return cachedFiles.computeIfAbsent(f.getAbsolutePath(), p -> new Archivum(plugin, filename));
    }

    public static Archivum get(String filename) {
        return get(AzAPI.INSTANCE, filename);
    }

    public static void registerFileType(ArchivumFileType type) {
        if (type.isExactMatch()) exactNameRegistry.put(type.match(), type);
        else extensionRegistry.put(type.match(), type);
    }

    private ArchivumFileType determineFileType(ArchivumFileType specifiedType) {
        if (specifiedType != null) return specifiedType;

        String lowerFile = filename.toLowerCase();
        if (exactNameRegistry.containsKey(lowerFile)) return exactNameRegistry.get(lowerFile);

        String ext = lowerFile.contains(".") ? lowerFile.substring(lowerFile.lastIndexOf('.') + 1) : "";
        if (extensionRegistry.containsKey(ext)) return extensionRegistry.get(ext);

        return DEFAULT_TYPE;
    }

    private void ensureFileExists() {
        try {
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create config file: " + filename, e);
        }
    }

    private void ensureBaseSections() {
        List<String> baseSections = Arrays.asList("_metadata", "_codex", "_data");
        for (String section : baseSections) if (!config.isConfigurationSection(section)) config.createSection(section);
    }

    private void ensureMetadataDefaults() {
        long now = System.currentTimeMillis();
        for (Metadata m : Metadata.values()) {
            String key = m.getKey().getId();
            if (!metadataSection.contains(key)) {
                if (m == Metadata.FILE_TYPE) {
                    metadataSection.set(key, fileType != null ? fileType.name() : "yml");
                } else if (m == Metadata.DESCRIPTION) {
                    metadataSection.set(key, ""); // default empty
                } else if (m == Metadata.AUTHOR) {
                    metadataSection.set(key, ""); // default empty
                } else if (m == Metadata.READABLE) {
                    metadataSection.set(key, true);
                } else if (m == Metadata.WRITABLE) {
                    metadataSection.set(key, true);
                } else if (m == Metadata.VERSION) {
                    metadataSection.set(key, m.getKey().getDefaultValue()); // keep default version
                } else if (m == Metadata.CREATED_AT) {
                    metadataSection.set(key, now);
                } else if (m == Metadata.LAST_MODIFIED) {
                    metadataSection.set(key, now);
                }
            }
        }
    }

    public Archivum save() {
        try {
            if (!dirty) return this;

            long now = System.currentTimeMillis();
            metadataSection.set(Metadata.LAST_MODIFIED.getKey().getId(), now);
            if (!metadataSection.contains(Metadata.CREATED_AT.getKey().getId()))
                metadataSection.set(Metadata.CREATED_AT.getKey().getId(), now);

            handler.flushQueued();
            handler.flushCodex();

            config.save(file);
            dirty = false;

            fileType.onSave(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CompletableFuture<Archivum> saveAsync() {
        CompletableFuture<Archivum> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            save();
            future.complete(this);
        });
        return future;
    }

    public void reload() {
        try {
            config.load(file);
            fileType.load(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unload() {
        fileType.unload(this);
    }

    public List<String> getRawLines() {
        if (rawLines == null) {
            try {
                rawLines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                rawLines = Collections.emptyList();
            }
        }
        return rawLines;
    }

    public ArchivumSnapshot readOnly() {
        return new ArchivumSnapshot(this);
    }

    public Archivum enableAutoFlush(long delayTicks) {
        this.autoFlush = true;
        this.autoFlushDelay = delayTicks;

        if (autoFlushTaskId != -1) Bukkit.getScheduler().cancelTask(autoFlushTaskId);

        autoFlushTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (dirty) saveAsync();
        }, autoFlushDelay, autoFlushDelay).getTaskId();

        return this;
    }

    public void disableAutoFlush() {
        this.autoFlush = false;
        if (autoFlushTaskId != -1) Bukkit.getScheduler().cancelTask(autoFlushTaskId);
        autoFlushTaskId = -1;
    }

    public DataHandler handler() {
        return handler;
    }

    public MetadataHandler metadata() {
        return metadata;
    }

    public TempHandler temp() {
        return temp;
    }

    public SectionHandler section(String key) {
        return sections.computeIfAbsent(key, k -> new SectionHandler(this, k));
    }

    private static class DefaultArchivumFileType extends ArchivumFileType {
        public DefaultArchivumFileType() {
            super("yml", false);
        }

        @Override
        public String name() {
            return "default";
        }

        @Override
        public void start() {
        }

        @Override
        public void load(Archivum archivum) {
        }

        @Override
        public void unload(Archivum archivum) {
        }

        @Override
        public void onSave(Archivum archivum) {
        }
    }

    public static final class DataHandler {
        private final Archivum archivum;
        private final Map<String, String> codex = new HashMap<>();
        private final Map<String, Object> queued = new HashMap<>();

        public DataHandler(Archivum archivum) {
            this.archivum = archivum;
        }

        public DataHandler setQueued(String path, Object value) {
            queued.put(path, value);
            archivum.dirty = true;
            return this;
        }

        public DataHandler setQueued(String path, Object value, String docComment) {
            queued.put(path, value);
            if (docComment != null) codex.put(path, docComment);
            archivum.dirty = true;
            return this;
        }

        public Object get(String path) {
            return queued.getOrDefault(path, archivum.dataSection.get(path));
        }

        @SuppressWarnings("unchecked")
        public <R> R get(String path, Class<R> type, R defaultValue) {
            Object val = get(path);
            return type.isInstance(val) ? (R) val : defaultValue;
        }

        public void codex(String path, String comment) {
            codex.put(path, comment);
            archivum.dirty = true;
        }

        public String codex(@NotNull String path) {
            return codex.getOrDefault(path.replace(".", DOT_REPLACEMENT), null);
        }

        public void flushQueued() {
            queued.forEach(archivum.dataSection::set);
            queued.clear();
        }

        public void flushCodex() {
            codex.forEach((k, v) -> archivum.codexSection.set(k.replace(".", DOT_REPLACEMENT), v));
        }
    }

    public static final class SectionHandler {
        public final String key;
        private final Archivum archivum;
        private final Map<String, Object> queued = new HashMap<>();

        public SectionHandler(Archivum archivum, String key) {
            this.archivum = archivum;
            this.key = key;
        }

        public SectionHandler set(String path, Object value) {
            queued.put(path, value);
            archivum.dirty = true;
            return this;
        }

        public SectionHandler flush(boolean saveNow) {
            ConfigurationSection section = getSection();
            queued.forEach(section::set);
            queued.clear();
            if (saveNow) archivum.save();
            return this;
        }

        public Object get(String path) {
            return queued.getOrDefault(path, getSection().get(path));
        }

        @SuppressWarnings("unchecked")
        public <R> R get(String path, Class<R> type, R defaultValue) {
            Object val = get(path);
            return type.isInstance(val) ? (R) val : defaultValue;
        }

        public ConfigurationSection getSection() {
            ConfigurationSection section = archivum.config.getConfigurationSection(key);
            if (section == null) section = archivum.config.createSection(key);
            return section;
        }

        public Batch batch() {
            return new Batch();
        }

        public final class Batch {
            private final Map<String, Object> batchQueue = new HashMap<>();

            public Batch set(String path, Object value) {
                batchQueue.put(path, value);
                return this;
            }

            public Batch remove(String path) {
                batchQueue.put(path, null);
                return this;
            }

            public SectionHandler flush(boolean saveNow) {
                ConfigurationSection section = getSection();
                batchQueue.forEach(section::set);
                batchQueue.clear();
                if (saveNow) archivum.save();
                return SectionHandler.this;
            }
        }
    }

    public static final class MetadataHandler {
        private final Archivum archivum;

        public MetadataHandler(Archivum archivum) {
            this.archivum = archivum;
        }

        public MetadataHandler set(@NotNull Metadata meta, Object value) {
            if (meta.getKey().isList()) {
                if (!(value instanceof List<?>))
                    throw new IllegalArgumentException("Invalid type for " + meta.getKey().getId());
                List<?> list = (List<?>) value;
                if (list.stream().anyMatch(e -> !meta.getKey().getType().isInstance(e)))
                    throw new IllegalArgumentException("Invalid type for " + meta.getKey().getId());
            } else if (!meta.getKey().getType().isInstance(value))
                throw new IllegalArgumentException("Invalid type for " + meta.getKey().getId());
            archivum.metadataSection.set(meta.getKey().getId(), value);
            archivum.dirty = true;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(@NotNull Metadata meta) {
            return (T) archivum.metadataSection.get(meta.getKey().getId());
        }
    }

    public static final class TempHandler {
        private final Map<String, Object> values = new HashMap<>();

        public TempHandler set(String key, Object val) {
            values.put(key, val);
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) values.get(key);
        }

        public boolean has(String key) {
            return values.containsKey(key);
        }

        public TempHandler clear() {
            values.clear();
            return this;
        }
    }

    public static final class ArchivumSnapshot {
        private final YamlConfiguration snapshot;

        public ArchivumSnapshot(Archivum orig) {
            snapshot = YamlConfiguration.loadConfiguration(orig.file);
        }

        public Object get(String path) {
            return snapshot.get(path);
        }

        @SuppressWarnings("unchecked")
        public <T> T get(String path, Class<T> type, T defaultValue) {
            Object val = snapshot.get(path);
            return type.isInstance(val) ? (T) val : defaultValue;
        }

        public ConfigurationSection getSection(String key) {
            return snapshot.getConfigurationSection(key);
        }
    }
}