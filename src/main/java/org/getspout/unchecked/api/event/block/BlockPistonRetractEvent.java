
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.util.Location;

/**
 *
 * Called when a piston retracts.
 */
public class BlockPistonRetractEvent extends BlockPistonEvent {

		public BlockPistonRetractEvent(Block block, EventSource source) {
				super(block, source);
		}

		/**
		 * Gets the position of the retract.
		 * 
		 * @return the position of the retract.
		 */
		public Location getRetractPosition() {
				return (Location) getBlock().getRelative(getDirection(), 2).getPosition();
		}
}
