/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.getspout.unchecked.api.event.player;

import org.getspout.unchecked.api.entity.object.Item;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.HandlerList;

/**
 * Called when a player picks an item up from the ground
 */
public class PlayerPickUpItemEvent extends PlayerEvent implements Cancellable {
	private static HandlerList handlers = new HandlerList();
	private final Item pickedUpItem;
	private int remainingItems;

	public PlayerPickUpItemEvent(Item item) {
		this.pickedUpItem = item;
	}

	/**
	 * Gets the item that was picked up.
	 * 
	 * @return the item that was picked up.
	 */
	public Item getPickedUpItem() {
		return pickedUpItem;
	}

	/**
	 * Gets the remaining items on the ground.
	 * 
	 * @return the remaining items
	 */
	public int getRemainingItems() {
		return remainingItems;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
