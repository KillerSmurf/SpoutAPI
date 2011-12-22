
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.getspout.unchecked.api.event.EventSource;

/**
 *
 * Called when a block spreads. 
 * Examples: Water, and Lava.
 * User BlockFormEvent for random forming of blocks.
 * Example: Fire, and Mushrooms.
 * 
 */
public class BlockSpreadEvent extends BlockFormEvent {

		public BlockSpreadEvent(Block block, EventSource source) {
				super(block, source);
		}
		private Block sourceBlock;

		/**
		 * Gets the sourceBlock of the spread.
		 * 
		 * @return the source block.
		 */
		public Block getSourceBlock() {
				return sourceBlock;
		}
}
