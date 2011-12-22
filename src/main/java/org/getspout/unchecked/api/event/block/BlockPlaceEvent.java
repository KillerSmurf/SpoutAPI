
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.getspout.unchecked.api.entity.Player;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.event.HandlerList;
import org.getspout.unchecked.api.inventory.ItemStack;

/**
 *
 * Called when a block is placed.
 */
public class BlockPlaceEvent extends BlockEvent implements Cancellable {

		public BlockPlaceEvent(Block block, EventSource source) {
				super(block, source);
		}
		private static HandlerList handlers;
		private Player player;
		private Block againstBlock;
		private ItemStack itemInHand;
		private BlockState replacedBlockState;

		public Block getAgainstBlock() {
				return againstBlock;
		}

		public Player getPlayer() {
				return player;
		}

		public ItemStack getItemInHand() {
				return itemInHand;
		}

		public BlockState getReplacedBlockState() {
				return replacedBlockState;
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
