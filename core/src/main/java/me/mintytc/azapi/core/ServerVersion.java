package me.mintytc.azapi.core;

import org.bukkit.Bukkit;

public final class ServerVersion {
    private static final int[] version;

    static {
        String ver = Bukkit.getBukkitVersion().split("-")[0]; // e.g. 1.19.4
        String[] parts = ver.split("\\.");
        int major = parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
        int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        int patch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        version = new int[]{major, minor, patch};
    }

    private ServerVersion() {
    }

    public static boolean atLeast(int major, int minor) {
        if (version[0] > major) return true;
        if (version[0] < major) return false;
        return version[1] >= minor;
    }

    public static int major() {
        return version[0];
    }

    public static int minor() {
        return version[1];
    }

    public static int patch() {
        return version[2];
    }
}
