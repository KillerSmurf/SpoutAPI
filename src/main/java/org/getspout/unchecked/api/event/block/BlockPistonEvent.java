/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.event.HandlerList;

/**
 *
 * Called when a piston event.
 */
public class BlockPistonEvent extends BlockEvent implements Cancellable {

		public BlockPistonEvent(Block block, EventSource source) {
				super(block, source);
		}
		private BlockFace direction;
		private static HandlerList handlers;

		/**
		 * Checks if it is a sticky piston.
		 * 
		 * @return true if it is a sticky piston.
		 */
		public boolean isSticky() {
				return getBlock().getTypeId() == 29;
		}

		/**
		 * Gets the direction that the piston will extend.
		 * 
		 * @return the direction the piston is facing.
		 */
		public BlockFace getDirection() {
				return direction;
		}

		@Override
		public void setCancelled(boolean cancelled) {
				super.setCancelled(cancelled);
		}

		@Override
		public HandlerList getHandlers() {
				return handlers;
		}

		public HandlerList getHandlerList() {
				return handlers;
		}
}
