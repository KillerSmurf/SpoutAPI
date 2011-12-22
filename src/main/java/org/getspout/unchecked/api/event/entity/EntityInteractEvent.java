package org.getspout.unchecked.api.event.entity;

import org.bukkit.block.Block;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.HandlerList;

/**
 * Called when an entity interacts with an object
 */
public class EntityInteractEvent extends EntityEvent implements Cancellable {

		private static HandlerList handlers = new HandlerList();
		private Block block;

		/**
		 * Returns the block
		 *
		 * @return the block that was clicked with the item.
		 */
		public Block getBlock() {
				return block;
		}

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
