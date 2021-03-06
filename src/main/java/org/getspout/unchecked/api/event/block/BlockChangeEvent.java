/*
 * This file is part of SpoutAPI (http://www.getspout.org/).
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.unchecked.api.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.getspout.unchecked.api.event.EventSource;

/**
 * Called when a block changes its state.
 */
public abstract class BlockChangeEvent extends BlockEvent {
	public BlockChangeEvent(Block block, EventSource source) {
		super(block, source);
	}

	private BlockState newState;

	/**
	 * Gets the final block state that the block will change to after this event
	 * executes.
	 *
	 * @return final block state
	 */
	public BlockState getNewState() {
		return newState;
	}

	/**
	 * Sets the final block state.
	 *
	 * @param state to set
	 */
	public void setNewState(BlockState state) {
		newState = state;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

}
