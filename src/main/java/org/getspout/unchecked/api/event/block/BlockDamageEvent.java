/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.getspout.unchecked.api.entity.Player;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.event.HandlerList;
import org.getspout.unchecked.api.inventory.ItemStack;

/**
 *
 * Called when a block is damaged.
 */
public class BlockDamageEvent extends BlockEvent implements Cancellable {

		public BlockDamageEvent(Block block, EventSource source) {
				super(block, source);
		}
		private static HandlerList handlers;
		private Player player;
		private ItemStack itemInHand;
		private boolean instantBreak;

		/**
		 * Gets if the block instantly breaks when a player damages it.
		 * 
		 * @return true if it broke instantly when a player damaged it.
		 */
		public boolean isInstantBreak() {
				return instantBreak;
		}

		/**
		 * Gets the item in hand he/she is damaging the block with.
		 * 
		 * @return the ItemStack the player currently has in his/her hand while damaging the block.
		 */
		public ItemStack getItemInHand() {
				return itemInHand;
		}

		/**
		 * Gets the player who is damaging the block.
		 * 
		 * @return the player involved.
		 */
		public Player getPlayer() {
				return player;
		}

		/**
		 * Sets it to whether it should break instantly or not.
		 * 
		 * @param instantBreak 
		 */
		public void setInstantBreak(boolean instantBreak) {
				this.instantBreak = instantBreak;
		}

		/**
		 * Sets the event canceled.
		 * 
		 * @param cancelled 
		 */
		@Override
		public void setCancelled(boolean cancelled) {
				super.setCancelled(cancelled);
		}

		@Override
		public HandlerList getHandlers() {
				return handlers;
		}

		public static HandlerList getHandlerList() {
				return handlers;
		}
}
