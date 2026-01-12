package me.mintytc.azapi.api.registry;

import me.mintytc.azapi.api.interfaces.Loadable;
import me.mintytc.azapi.api.registry.impl.FreezableKeyedRegistry;

/**
 * Central hub for all registries in AzAPI.
 *
 * @since 1.0.0-R0.1
 */
public final class AzRegistries {

    /**
     * Meta-registry holding all other registries.
     * Typed as KeyedRegistry<String, ?> because FreezableKeyedRegistry implements KeyedRegistry
     */
    public static final FreezableKeyedRegistry<String, Object> REGISTRIES =
            new FreezableKeyedRegistry<>();

    // -----------------------------
    // Individual registries
    // -----------------------------

    public static final FreezableKeyedRegistry<String, Object> COMMANDS =
            register("commands", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Object> GUIS =
            register("guis", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Object> ITEMS =
            register("items", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Object> EFFECTS =
            register("effects", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Object> PLACEHOLDERS =
            register("placeholders", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Object> PLAYER_DATA =
            register("player_data", new FreezableKeyedRegistry<>());

    public static final FreezableKeyedRegistry<String, Loadable> LOADABLES =
            register("loadables", new FreezableKeyedRegistry<>());

    // -----------------------------
    // Helper registration
    // -----------------------------

    private AzRegistries() {
        // Prevent instantiation
    }

    // -----------------------------
    // Freeze all registries
    // -----------------------------

    /**
     * Registers a registry into the meta-registry.
     *
     * @param key      The name of the registry
     * @param registry The registry instance
     * @param <T>      Type of the registry
     *
     * @return The registry instance for assignment
     */
    @SuppressWarnings("unchecked")
    private static <T> T register(String key, T registry) {
        REGISTRIES.register(key, registry);
        return registry;
    }

    /**
     * Freezes all registries and the meta-registry to prevent further modifications.
     */
    public static void freezeAll() {
        for (Object registry : REGISTRIES.values()) {
            if (registry instanceof Freezable) {
                ((Freezable) registry).freeze();
            }
        }
        REGISTRIES.freeze();
    }
}
