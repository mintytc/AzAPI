package me.mintytc.azapi.output.format.replacer;

import static me.mintytc.azapi.output.format.replacer.PlaceholderDefaults.*;

/**
 * @since 1.0.0-R0.1
 *
 */
public enum PlaceholderCategory {
    VARIABLE(
            VARIABLE_GETTER.FReplacement,
            VARIABLE_SETTER.FReplacement
    ),

    INVENTORY(
            INV_ARMOR_COUNT.FReplacement,
            INV_BOOTS.FReplacement,
            INV_CHESTPLATE.FReplacement,
            INV_CONTAINS_DIAMOND.FReplacement,
            INV_CONTAINS_TOTEM.FReplacement,
            INV_EMPTY.FReplacement,
            INV_EMPTY_SLOTS.FReplacement,
            INV_FIRST_EMPTY.FReplacement,
            INV_HAND.FReplacement,
            INV_HELMET.FReplacement,
            INV_HOTBAR_COUNT.FReplacement,
            INV_LEGGINGS.FReplacement,
            INV_OFFHAND.FReplacement,
            INV_SHIELD.FReplacement,
            INV_SIZE.FReplacement,
            INV_STORAGE_COUNT.FReplacement,
            INV_TOTAL_ITEMS.FReplacement,
            INV_TOTAL_STACKS.FReplacement
    ),

    LOCATION(
            LOC_X.FReplacement,
            LOC_Y.FReplacement,
            LOC_Z.FReplacement,
            LOC_YAW.FReplacement,
            LOC_PITCH.FReplacement,
            LOC_WORLD.FReplacement,
            LOC_COORDS.FReplacement,
            LOC_CHUNK_X.FReplacement,
            LOC_CHUNK_Z.FReplacement,
            LOC_BIOME.FReplacement,
            LOC_BLOCK.FReplacement,
            LOC_BLOCK_LIGHT.FReplacement,
            LOC_REGION_X.FReplacement,
            LOC_REGION_Z.FReplacement,
            // LOC_DIRECTION.FReplacement,
            LOC_DISTANCE_SPAWN.FReplacement,
            LOC_SPAWN_X.FReplacement,
            LOC_SPAWN_Y.FReplacement,
            LOC_SPAWN_Z.FReplacement
    ),

    MISC(
            MISC_SERVER_NAME.FReplacement,
            MISC_SERVER_ID.FReplacement,
            MISC_PLUGIN_COUNT.FReplacement,
            MISC_PLUGIN_NAMES.FReplacement,
            MISC_DEFAULT_GAMEMODE.FReplacement,
            MISC_ALLOW_NETHER.FReplacement,
            MISC_ALLOW_END.FReplacement,
            MISC_ALLOW_FLIGHT.FReplacement,
            MISC_MAX_PLAYERS.FReplacement,
            MISC_SHUTDOWN_MSG.FReplacement,
            MISC_JAVA_VERSION.FReplacement,
            MISC_JAVA_VENDOR.FReplacement,
            MISC_JAVA_HOME.FReplacement,
            MISC_OS_NAME.FReplacement,
            MISC_OS_ARCH.FReplacement,
            MISC_OS_VERSION.FReplacement,
            MISC_USER_NAME.FReplacement,
            MISC_USER_DIR.FReplacement,
            MISC_RANDOM_INT.FReplacement,
            MISC_RANDOM_UUID.FReplacement
    ),

    NETWORK(
            NET_PLAYER_IP.FReplacement,
            NET_PLAYER_PORT.FReplacement,
            NET_PLAYER_HOST.FReplacement,
            NET_PLAYER_UUID.FReplacement,
            NET_SERVER_IP.FReplacement,
            NET_SERVER_PORT.FReplacement,
            NET_SERVER_VERSION.FReplacement,
            NET_SERVER_MOTD.FReplacement,
            NET_SERVER_VIEWDIST.FReplacement,
            NET_PLAYER_ONLINE.FReplacement,
            NET_PLAYER_LOCALE.FReplacement,
            NET_PLAYER_GAMEMODE.FReplacement,
            NET_ALLOW_FLIGHT.FReplacement,
            NET_FLYING.FReplacement,
            NET_INVULNERABLE.FReplacement,
            NET_OP.FReplacement,
            NET_WHITELISTED.FReplacement,
            NET_BANNED.FReplacement
    ),

    PERFORMANCE(
            PERF_MEMORY_MAX.FReplacement,
            PERF_MEMORY_TOTAL.FReplacement,
            PERF_MEMORY_USED.FReplacement,
            PERF_MEMORY_FREE.FReplacement,
            PERF_CPU_CORES.FReplacement,
            PERF_UPTIME.FReplacement,
            PERF_ENTITY_COUNT.FReplacement,
            PERF_CHUNK_COUNT.FReplacement,
            PERF_LOADED_WORLDS.FReplacement,
            PERF_PLAYER_COUNT.FReplacement,
            PERF_THREAD_COUNT.FReplacement,
            PERF_GC_COUNT.FReplacement,
            PERF_GC_TIME.FReplacement,
            PERF_CLASS_COUNT.FReplacement,
            PERF_TOTAL_THREADS.FReplacement,
            PERF_PEAK_THREADS.FReplacement
    ),

    PLAYER(
            PLAYER_DISPLAY.FReplacement,
            PLAYER_EXP.FReplacement,
            PLAYER_FLYING.FReplacement,
            PLAYER_GAMEMODE.FReplacement,
            PLAYER_FOOD.FReplacement,
            PLAYER_HEALTH.FReplacement,
            PLAYER_IP.FReplacement,
            PLAYER_ITEM_HAND.FReplacement,
            PLAYER_ITEM_OFFHAND.FReplacement,
            PLAYER_LEVEL.FReplacement,
            PLAYER_MAX_HEALTH.FReplacement,
            PLAYER_NAME.FReplacement,
            PLAYER_OP.FReplacement,
            PLAYER_PORT.FReplacement,
            PLAYER_SATURATION.FReplacement,
            PLAYER_SNEAKING.FReplacement,
            PLAYER_SPRINTING.FReplacement,
            PLAYER_TOTAL_EXP.FReplacement,
            PLAYER_UUID.FReplacement,
            PLAYER_WORLD.FReplacement,
            PLAYER_XP_BAR.FReplacement
    ),

    TIME(
            TIME_YEAR.FReplacement,
            TIME_MONTH.FReplacement,
            TIME_MONTH_NAME.FReplacement,
            TIME_DAY.FReplacement,
            TIME_DAY_NAME.FReplacement,
            TIME_HOUR.FReplacement,
            TIME_MINUTE.FReplacement,
            TIME_SECOND.FReplacement,
            TIME_MILLI.FReplacement,
            TIME_AMPM.FReplacement,
            TIME_ISO.FReplacement,
            TIME_UNIX.FReplacement,
            TIME_WEEK.FReplacement,
            TIME_DAYOFYEAR.FReplacement,
            TIME_ZONE.FReplacement,
            TIME_OFFSET.FReplacement,
            TIME_24H.FReplacement,
            TIME_12H.FReplacement,
            TIME_DATE.FReplacement
    ),

    WORLD(
            WORLD_NAME.FReplacement,
            WORLD_SEED.FReplacement,
            WORLD_TIME.FReplacement,
            WORLD_FULLTIME.FReplacement,
            WORLD_PLAYERS.FReplacement,
            WORLD_ENV.FReplacement,
            WORLD_DIFFICULTY.FReplacement,
            WORLD_MAX_HEIGHT.FReplacement,
            WORLD_SEALEVEL.FReplacement,
            WORLD_PVP.FReplacement,
            WORLD_STORM.FReplacement,
            WORLD_THUNDER.FReplacement,
            WORLD_ANIMALS.FReplacement,
            WORLD_MONSTERS.FReplacement,
            WORLD_CHUNK_COUNT.FReplacement,
            WORLD_WEATHER_DURATION.FReplacement
    );

    public final FReplacement[] FReplacements;

    PlaceholderCategory(FReplacement... FReplacements) {
        this.FReplacements = FReplacements;
    }
}