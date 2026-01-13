package me.mintytc.azapi.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @since 1.0.0-R0.1
 *
 */
public abstract class Evt extends Event {

	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

}
