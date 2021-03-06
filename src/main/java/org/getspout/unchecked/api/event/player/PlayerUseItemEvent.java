/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.getspout.unchecked.api.event.player;

import org.bukkit.block.Block;
import org.getspout.api.material.Material;
import org.getspout.unchecked.api.event.HandlerList;
import org.getspout.unchecked.api.inventory.ItemStack;

/**
 * Called when a player uses an item.
 */
public class PlayerUseItemEvent extends PlayerInteractEvent {
	private static HandlerList handlers = new HandlerList();

	private ItemStack item;

	/**
	 * Returns the item in hand represented by this event
	 *
	 * @return ItemStack the item used
	 */
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	/**
	 * Convenience method. Returns the material of the item represented by this
	 * event
	 *
	 * @return Material the material of the item used
	 */
	public Material getMaterial() {
		return item.getType();
	}

	/**
	 * Convenience method to inform the user whether this was a block placement
	 * event.
	 *
	 * @return boolean true if the item in hand was a block
	 */
	public boolean isBlockPlace() {
		return item.getType() instanceof Block;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
