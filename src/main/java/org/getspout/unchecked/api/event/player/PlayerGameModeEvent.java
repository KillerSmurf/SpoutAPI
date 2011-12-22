
package org.getspout.unchecked.api.event.player;

import org.getspout.unchecked.api.GameMode;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.HandlerList;

/**
 *
 * Called when a player changes gamemode.
 */
public class PlayerGameModeEvent extends PlayerEvent implements Cancellable {

		private static HandlerList handlers = new HandlerList();
		private GameMode gameMode;

		public GameMode getGameMode() {
				return gameMode;
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
