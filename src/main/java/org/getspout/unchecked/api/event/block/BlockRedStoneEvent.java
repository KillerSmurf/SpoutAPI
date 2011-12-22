
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.event.HandlerList;

/**
 *
 * Called when a redstone changes current.
 */
public class BlockRedStoneEvent extends BlockEvent implements Cancellable {

		public BlockRedStoneEvent(Block block, EventSource source) {
				super(block, source);
		}
		private static HandlerList handlers;
		private int current;

		/**
		 * Gets the current of the redstone.
		 * 
		 * @return the current
		 */
		public int getCurrent() {
				return current;
		}

		/**
		 * Sets the current of the redstone.
		 * 
		 * @param current 
		 */
		public void setCurrent(int current) {
				this.current = current;
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
