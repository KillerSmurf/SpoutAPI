/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.getspout.unchecked.api.event.player;

import org.getspout.unchecked.api.event.HandlerList;
import org.getspout.unchecked.api.util.Location;

/**
 *
 * Called when a player respawns.
 */
public class PlayerRespawnEvent extends PlayerEvent {

		private static HandlerList handlers = new HandlerList();
		private Location respawnLocation;
		private boolean isBedRespawn;

		/**
		 * Gets whether or not the player respawned at a bed.
		 * 
		 * @return true if it is at a bed.
		 */
		public boolean isBedRespawn() {
				return isBedRespawn;
		}

		/**
		 * Gets the location of the respawn.
		 * 
		 * @return 
		 */
		public Location getRespawnLocation() {
				return respawnLocation;
		}

		/**
		 * Sets the location of the respawn.
		 * 
		 * @param respawnLocation 
		 */
		public void setRespawnLocation(Location respawnLocation) {
				this.respawnLocation = respawnLocation;
		}

		@Override
		public HandlerList getHandlers() {
				return handlers;
		}

		public static HandlerList getHandlerList() {
				return handlers;
		}
}
