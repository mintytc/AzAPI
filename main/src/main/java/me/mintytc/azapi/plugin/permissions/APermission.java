package me.mintytc.azapi.plugin.permissions;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.*;
import java.util.function.Consumer;

/**
 * Full-featured Azalium Permissions API.
 * Handles player permissions, groups, registration, child nodes, and fluent building.
 *
 * @since 1.0.0-R0.1-BASE
 */
public class APermission {

    private final Permission basePermission;
    private final Set<Player> attachedPlayers = new HashSet<>();
    private final Set<String> groups = new HashSet<>();
    private final Consumer<Player> onAttach;
    private final Consumer<Player> onDetach;

    private APermission(Builder builder) {
        // Create or get existing Bukkit permission
        Permission perm = Bukkit.getPluginManager().getPermission(builder.name);
        if (perm == null) {
            perm = new Permission(builder.name, builder.description, builder.defaultValue);
            builder.children.forEach(perm::addParent);
            Bukkit.getPluginManager().addPermission(perm);
        }
        this.basePermission = perm;

        // Store callbacks
        this.onAttach = builder.onAttach;
        this.onDetach = builder.onDetach;
        this.groups.addAll(builder.groups);

        // Pre-fill attachedPlayers with any online players who already have this permission
        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(builder.name)).forEach(attachedPlayers::add);
    }

    /* -----------------------------
       Fluent Builder
       ----------------------------- */
    public static Builder create(String name) {
        return new Builder(name);
    }

    /* -----------------------------
       Core Accessors
       ----------------------------- */
    public String getName() {
        return basePermission.getName();
    }

    public String getDescription() {
        return basePermission.getDescription();
    }

    public PermissionDefault getDefault() {
        return basePermission.getDefault();
    }

    public Map<String, Boolean> getChildren() {
        return Collections.unmodifiableMap(basePermission.getChildren());
    }

    /* -----------------------------
       Player Management
       ----------------------------- */
    public void attach(Player player) {
        if (player == null) return;
        if (!player.hasPermission(getName())) {
            player.addAttachment(Bukkit.getPluginManager().getPlugin("YourPlugin"), getName(), true);
        }
        attachedPlayers.add(player);
        if (onAttach != null) onAttach.accept(player);
    }

    public void detach(Player player) {
        if (player == null) return;
        attachedPlayers.remove(player);
        player.getEffectivePermissions().stream().filter(perm -> perm.getPermission().equalsIgnoreCase(getName())).forEach(perm -> player.addAttachment(Bukkit.getPluginManager().getPlugin("YourPlugin"), getName(), false));
        if (onDetach != null) onDetach.accept(player);
    }

    public boolean has(Player player) {
        return player != null && player.hasPermission(getName());
    }

    /* -----------------------------
       Child Management
       ----------------------------- */
    public APermission addChild(String childName, boolean value) {
        basePermission.addParent(childName, value);
        attachedPlayers.forEach(this::attach);
        return this;
    }

    /* -----------------------------
       Group Support
       ----------------------------- */
    public void attachToGroup(String groupName) {
        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(groupName)).forEach(this::attach);
    }

    public void detachFromGroup(String groupName) {
        Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(groupName)).forEach(this::detach);
    }

    public Set<Player> getAttachedPlayers() {
        return Collections.unmodifiableSet(attachedPlayers);
    }

    public static class Builder {
        private final String name;
        private final Map<String, Boolean> children = new HashMap<>();
        private final Set<String> groups = new HashSet<>();
        private String description = "";
        private PermissionDefault defaultValue = PermissionDefault.OP;
        private Consumer<Player> onAttach;
        private Consumer<Player> onDetach;

        public Builder(String name) {
            this.name = name;
        }

        public Builder description(String desc) {
            this.description = desc;
            return this;
        }

        public Builder defaultValue(PermissionDefault def) {
            this.defaultValue = def;
            return this;
        }

        public Builder child(String childName, boolean value) {
            children.put(childName, value);
            return this;
        }

        public Builder group(String groupName) {
            groups.add(groupName);
            return this;
        }

        public Builder onAttach(Consumer<Player> callback) {
            this.onAttach = callback;
            return this;
        }

        public Builder onDetach(Consumer<Player> callback) {
            this.onDetach = callback;
            return this;
        }

        public APermission build() {
            return new APermission(this);
        }
    }
}