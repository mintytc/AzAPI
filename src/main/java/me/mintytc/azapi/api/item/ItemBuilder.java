package me.mintytc.azapi.api.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import me.mintytc.azapi.api.output.OutputStream;
import me.mintytc.azapi.api.util.UString;

/**
 * @since 1.0.0-R0.1
 *
 */
@Getter
@SuppressWarnings("unchecked")
public abstract class ItemBuilder<T extends ItemBuilder<T>> implements Listener {

	protected final List<ItemStack> stacks = new ArrayList<>();
	protected ItemAction itemAction;

	protected String display;
	protected String[] lore = new String[]{};
	protected int amount = 1;
	protected boolean visibleEnchant = false;
	protected boolean unbreakable = false;
	protected Map<Enchantment, Integer> enchants = new HashMap<>();
	protected List<ItemFlag> itemFlags = new ArrayList<>();

	public ItemBuilder(JavaPlugin plugin) {
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Contract("_, _ -> new")
	public static @NotNull Stack stack(JavaPlugin plugin, Material material) {
		return new Stack(plugin, material);
	}

	@Contract("_, _ -> new")
	public static @NotNull SkullStack skull(JavaPlugin plugin, OfflinePlayer owner) {
		return new SkullStack(plugin, owner);
	}

	@Contract("_, _, _ -> new")
	public static @NotNull PotionStack potion(JavaPlugin plugin, PotionEffect effect, PotionStack.PotionStackType potionType) {
		return new PotionStack(plugin, effect, potionType);
	}

	@Contract("_, _ -> new")
	public static @NotNull LeatherArmorStack leatherArmor(JavaPlugin plugin, LeatherArmorStack.LeatherArmorType type) {
		return new LeatherArmorStack(plugin, type);
	}

	public T action(ItemAction action) {
		this.itemAction = action;
		return (T) this;
	}

	public T display(String display) {
		this.display = display;
		return (T) this;
	}

	public T lore(String... lore) {
		this.lore = lore;
		return (T) this;
	}

	public T amount(int amount) {
		this.amount = amount;
		return (T) this;
	}

	public T visibleEnchanted(boolean b) {
		this.visibleEnchant = b;
		return (T) this;
	}

	public T unbreakable(boolean b) {
		this.unbreakable = b;
		return (T) this;
	}

	public T addEnchant(Enchantment enchantment, int i) {
		this.enchants.put(enchantment, i);
		return (T) this;
	}

	public T removeEnchant(Enchantment enchantment) {
		enchants.remove(enchantment);
		return (T) this;
	}

	public T addItemFlag(ItemFlag enchantment) {
		this.itemFlags.add(enchantment);
		return (T) this;
	}

	public T removeItemFlag(ItemFlag enchantment) {
		this.itemFlags.remove(enchantment);
		return (T) this;
	}

	protected ItemStack applyDefaults(@NotNull ItemStack stack, Player player) {

		ItemMeta meta = stack.getItemMeta();
		if (meta == null)
			return stack;

		// display name
		if (this.display == null)
			this.display = UString.capitalise(stack.getType().name().toLowerCase());

		meta.setDisplayName(OutputStream.f("&f" + this.display, player));

		// amount
		stack.setAmount(Math.max(amount, 1));

		// lore
		List<String> finalLore = new ArrayList<>();
		if (lore != null) {
			for (String l1 : lore) {
				if (l1 == null)
					continue;
				for (String l2 : l1.split("%nl%")) {
					finalLore.add(OutputStream.f("&f" + l2, player));
				}
			}
		}
		if (!finalLore.isEmpty())
			meta.setLore(finalLore);

		// enchants
		if (!enchants.isEmpty()) {
			for (Enchantment ench : enchants.keySet()) {
				meta.addEnchant(ench, enchants.get(ench), true);
			}
		}

		// item flags
		if (!itemFlags.isEmpty()) {
			for (ItemFlag flag : itemFlags) {
				meta.addItemFlags(flag);
			}
		}

		// visibly enchanted.
		if (visibleEnchant) {
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		// unbreakable
		meta.setUnbreakable(unbreakable);

		stack.setItemMeta(meta);
		return stack;
	}

	public abstract ItemStack build(Player player);

	@EventHandler
	public void onClick(@NotNull InventoryClickEvent event) {
		if (this.stacks.contains(event.getCurrentItem()))
			if (itemAction != null)
				itemAction.onClick(event);
	}

	@EventHandler
	public void onDrag(@NotNull InventoryDragEvent event) {
		if (this.stacks.contains(event.getCursor()))
			if (itemAction != null)
				itemAction.onDrag(event);
	}

	@EventHandler
	public void onInteract(@NotNull PlayerInteractEvent event) {
		if (this.stacks.contains(event.getItem()))
			if (itemAction != null)
				itemAction.onInteract(event);
	}

	@EventHandler
	public void onHeld(@NotNull PlayerItemHeldEvent event) {
		if (this.stacks.contains(event.getPlayer().getItemOnCursor()))
			if (itemAction != null)
				itemAction.onHeld(event);
	}

	@EventHandler
	public void onConsume(@NotNull PlayerItemConsumeEvent event) {
		if (this.stacks.contains(event.getItem()))
			if (itemAction != null)
				itemAction.onConsume(event);
	}

	@EventHandler
	public void onMend(@NotNull PlayerItemMendEvent event) {
		if (this.stacks.contains(event.getItem()))
			if (itemAction != null)
				itemAction.onMend(event);
	}

	@EventHandler
	public void onBreak(@NotNull PlayerItemBreakEvent event) {
		if (this.stacks.contains(event.getBrokenItem()))
			if (itemAction != null)
				itemAction.onBreak(event);
	}

	@EventHandler
	public void onDamage(@NotNull PlayerItemDamageEvent event) {
		if (this.stacks.contains(event.getItem()))
			if (itemAction != null)
				itemAction.onDamage(event);
	}

	@EventHandler
	public void onDrop(@NotNull PlayerDropItemEvent event) {
		if (this.stacks.contains(event.getItemDrop().getItemStack()))
			if (itemAction != null)
				itemAction.onDrop(event);
	}

	@EventHandler
	public void onSwapHand(@NotNull PlayerSwapHandItemsEvent event) {
		if (this.stacks.contains(event.getMainHandItem()) || this.stacks.contains(event.getOffHandItem()))
			if (itemAction != null)
				itemAction.onSwapHand(event);
	}

}