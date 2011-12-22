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

/**
 *
 * Called when a sign is changed.
 */
public class BlockSignEvent extends BlockEvent implements Cancellable {

		public BlockSignEvent(Block block, EventSource source) {
				super(block, source);
		}
		private static HandlerList handlers;
		private Player player;
		private String[] lines;

		/**
		 * Gets all the lines of text.
		 * 
		 * @return all the text from the lines.
		 */
		public String[] getLines() {
				return lines;
		}

		/**
		 * Gets the player involved.
		 * 
		 * @return the player involved in the event.
		 */
		public Player getPlayer() {
				return player;
		}

		/**
		 * Gets the text on that line.
		 * 
		 * @param lineNumber, the line number its on.
		 * @return the text on lineNumber.
		 * @throws IndexOutOfBoundsException 
		 */
		public String getLine(int lineNumber) throws IndexOutOfBoundsException {
				return lines[lineNumber];
		}

		/**
		 * Sets the line of the sign involved in this event.
		 * 
		 * @param lineNumber, the line number it sets to.
		 * @param text, the text to be set to.
		 * @throws IndexOutOfBoundsException, happens when a lineNumber is > 4, or < 0. 
		 */
		public void setLine(int lineNumber, String text) throws IndexOutOfBoundsException {
				lines[lineNumber] = text;

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
