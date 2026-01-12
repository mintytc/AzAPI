package me.mintytc.azapi.classes;

import lombok.Getter;

import java.util.UUID;

/**
 * Structured metadata container for any registered object.
 * Clean, grouped, and expandable.
 *
 * @apiNote 1.0.0-R0.1 -> Full documentation coming soon.
 *
 * @since 1.0.0-R0.1
 */
public enum Metadata {
    // A
    ACCESSED_AT(new MetadataKey<>("accessedAt", Long.class, null, false)),
    AI_PROFILE(new MetadataKey<>("aiProfile", String.class, null, false)),
    ARCHIVED(new MetadataKey<>("archived", Boolean.class, null, false)),
    ATTEMPTS(new MetadataKey<>("attempts", Integer.class, null, false)),
    AUDIT_TRAIL(new MetadataKey<>("auditTrail", String.class, null, false)),
    AUTHOR(new MetadataKey<>("author", String.class, null, false)),
    AUTOPLAY(new MetadataKey<>("autoplay", Boolean.class, null, false)),

    // B
    BLUEPRINT(new MetadataKey<>("blueprint", String.class, null, false)),

    // C
    CHILDREN(new MetadataKey<>("children", UUID.class, null, true)),
    COMPLEXITY_SCORE(new MetadataKey<>("complexityScore", Double.class, null, false)),
    COMPLIANCE(new MetadataKey<>("compliance", String.class, null, false)),
    COOLDOWN(new MetadataKey<>("cooldown", Double.class, null, false)),
    CREATED_AT(new MetadataKey<>("createdAt", Long.class, null, false)),

    // D
    DESCRIPTION(new MetadataKey<>("description", String.class, null, true)),
    DISPLAY(new MetadataKey<>("display", String.class, null, false)),

    // E
    ENTROPY(new MetadataKey<>("entropy", Integer.class, null, false)),
    EPHEMERAL(new MetadataKey<>("ephemeral", Boolean.class, null, false)),
    EXPERIMENTAL(new MetadataKey<>("experimental", Boolean.class, null, false)),
    EXPIRES_AT(new MetadataKey<>("expiresAt", Long.class, null, false)),

    // F
    FILE_TYPE(new MetadataKey<>("fileType", String.class, null, false)),
    // G
    GHOST(new MetadataKey<>("ghost", Boolean.class, null, false)),

    // H
    // I
    INSTABILITY(new MetadataKey<>("instability", Double.class, null, false)),
    INTERACTIVE(new MetadataKey<>("interactive", Boolean.class, null, false)),

    // J
    // K
    // L
    LAST_MODIFIED(new MetadataKey<>("lastModified", Long.class, null, false)),
    LINK(new MetadataKey<>("link", Class.class, null, false)),
    LOCALE(new MetadataKey<>("locale", String.class, null, false)),
    LOCKED(new MetadataKey<>("locked", Boolean.class, null, false)),

    // M
    MIGRATION_NOTE(new MetadataKey<>("migrationNode", String.class, null, false)),
    MODIFIER(new MetadataKey<>("modifier", Double.class, null, false)),

    // N
    // O
    OTHERS(new MetadataKey<>("others", Object.class, null, true)),
    OTHER_REFERENCES(new MetadataKey<>("otherReferences", Class.class, null, true)),

    // P
    PARENT(new MetadataKey<>("parent", UUID.class, null, false)),
    PERFORMANCE_COST(new MetadataKey<>("performanceCost", Double.class, null, false)),
    PHYSICS_PROFILE(new MetadataKey<>("physicsProfile", String.class, null, false)),
    PRIORITY_SCORE(new MetadataKey<>("priorityScore", Integer.class, null, false)),

    // Q
    // R
    RATING(new MetadataKey<>("rating", Double.class, null, false)),
    READABLE(new MetadataKey<>("readable", Boolean.class, null, false)),
    REFERENCE(new MetadataKey<>("reference", Class.class, null, false)),
    RENDERER(new MetadataKey<>("renderer", String.class, null, false)),
    REVISIONS(new MetadataKey<>("revisions", Integer.class, null, false)),

    // S,
    SCHEMA_VERSION(new MetadataKey<>("schemaVersion", String.class, null, false)),
    SECURITY_LEVEL(new MetadataKey<>("securityLevel", String.class, null, false)),
    SEE(new MetadataKey<>("see", String.class, null, false)),
    SIGNATURE(new MetadataKey<>("signature", String.class, null, false)),
    SINCE(new MetadataKey<>("since", String.class, null, false)),
    SPAWN_WEIGHT(new MetadataKey<>("spawnWeight", Integer.class, null, false)),
    SUPPRESS_WARNING(new MetadataKey<>("suppressWarning", Boolean.class, null, false)),
    SYSTEM(new MetadataKey<>("system", Boolean.class, null, false)),

    // T
    // U
    UUID(new MetadataKey<>("uuid", UUID.class, null, false)),

    // V
    VERSION(new MetadataKey<>("version", String.class, null, false)),
    VISIBILITY_RANK(new MetadataKey<>("visibilityRank", Integer.class, null, false)),

    // W
    WEIGHT(new MetadataKey<>("weight", Integer.class, null, false)),
    WRITABLE(new MetadataKey<>("writable", Boolean.class, null, false));

    private final MetadataKey<?> key;

    Metadata(MetadataKey<?> key) {
        this.key = key;
    }

    public MetadataKey<?> getKey() {
        return this.key;
    }

    @Getter
    public static class MetadataKey<T> {

        private final String id;
        private final Class<T> type;
        private final T defaultValue;

        private final boolean list;

        private T value;

        public MetadataKey(String id, Class<T> type, T defaultValue, boolean list) {
            this.id = id;
            this.type = type;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
            this.list = list;
        }

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return this.value;
        }
    }
}
