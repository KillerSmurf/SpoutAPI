
package org.getspout.unchecked.api.event.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.block.Block;
import org.getspout.unchecked.api.event.EventSource;



/**
 *
 * Called when a piston extends.
 */
public class BlockPistonExtendEvent extends BlockPistonEvent {

		public BlockPistonExtendEvent(Block block, EventSource source) {
				super(block, source);
		}
		private List<Block> blocks;
		private int blockAmount;

		/**
		 * Gets the amount blocks that were moved.
		 * 
		 * @return the amount of blocks.
		 */
		public int getBlockAmount() {
				return blockAmount;
		}

		/**
		 * Gets the UnModifiablelist of blocks that were moved
		 * 
		 * @return The blocks that were moved.
		 */
		public List<Block> getBlocks() {
				if (blocks == null) {
						ArrayList<Block> tempBlocks = new ArrayList<Block>();
						for (int i = 0; i < getBlockAmount(); i++) {
								tempBlocks.add(getBlock().getRelative(getDirection(), i + 1));
						}
						blocks = Collections.unmodifiableList(tempBlocks);
				}
				return blocks;
		}
}
