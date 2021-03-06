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
package org.getspout.unchecked.api;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.getspout.api.geo.World;
import org.getspout.unchecked.api.command.AddonCommand;
import org.getspout.unchecked.api.command.Command;
import org.getspout.unchecked.api.command.CommandSource;
import org.getspout.unchecked.api.entity.Player;
import org.getspout.unchecked.api.event.EventManager;
import org.getspout.unchecked.api.event.EventSource;
import org.getspout.unchecked.api.inventory.Recipe;
import org.getspout.unchecked.api.plugin.PluginManager;
import org.getspout.unchecked.api.util.Named;

/**
 * Represents the abstract, non-specific implementation of Minecraft.
 */
public interface Game extends Named, EventSource {

	/**
	 * Gets the name of this game's implementation
	 *
	 * @return name of the implementation
	 */

	public String getName();

	/**
	 * Gets the build version of this game's implementation
	 *
	 * @return build version
	 */
	public long getVersion();

	/**
	 * Gets all players currently active
	 *
	 * @return array of all active players
	 */
	public Player[] getPlayers();

	/**
	 * Gets the maximum number of players this game can host, or -1 if infinite
	 *
	 * @return max players
	 */
	public int getMaxPlayers();

	/**
	 * Returns the current IP address.
	 *
	 * If this game is a server, this is the address being broadcasted.
	 *
	 * If this game is a client, and connected to a server, this is the address
	 * connected to.
	 *
	 * If neither, this is null.
	 *
	 * Address may be in "x.x.x.x:port", "x.x.x.x", or null format.
	 *
	 * @return address
	 */
	public String getAddress();

	/**
	 * True if this game allows the Nether environment to exist.
	 *
	 * @return whether the Nether exists
	 */
	public boolean hasNether();

	/**
	 * True if this game allows 'The End' environment to exist.
	 *
	 * @return whether the Nether exists
	 */
	public boolean hasTheEnd();

	/**
	 * Broadcasts the given message to all players
	 *
	 * The implementation of broadcast is identical to iterating over
	 * {@link #getPlayers()} and invoking {@link Player#sendMessage(String)} for
	 * each player.
	 *
	 * @param message to send
	 */
	public void broadcastMessage(String message);

	/**
	 * Gets singleton instance of the plugin manager, used to interact with
	 * other plugins and register events.
	 *
	 * @return plugin manager instance.
	 */
	public PluginManager getPluginManager();

	/**
	 * Gets the logger instance that is used to write to the console.
	 *
	 * It should be identical to Logger.getLogger("minecraft");
	 *
	 * @return logger
	 */
	public Logger getLogger();

	public AddonCommand getAddonCommand(String name);

	/**
	 * Sends a command from the given command sender. The command will be
	 * handled as if the sender has sent it itself.
	 *
	 * @param sender that is responsible for the command
	 * @param commandLine text
	 * @return true if dispatched
	 */
	public boolean processCommand(CommandSource sender, String commandLine);

	/**
	 * Gets the update folder. The update folder is used to safely update
	 * plugins at the right moment on a plugin load.
	 *
	 * The update folder name is relative to the plugins folder.
	 *
	 * @return The name of the update folder
	 */
	public File getUpdateFolder();

	/**
	 * Gets a player by the given username. <br/>
	 * <br/>
	 * This method will iterate over over all players and find the closest match
	 * to the given name, by comparing the length of other player names that
	 * start with the given parameter. <br/>
	 * <br/>
	 * This method is case-insensitive.
	 *
	 * @param name to look up
	 * @return Player if found, else null
	 */
	public Player getPlayer(String name);

	/**
	 * Gets the player bythe given username. <br/>
	 * <br/>
	 * If searching for the exact name, this method will iterate and check for
	 * exact matches, ignoring case. <br/>
	 * <br/>
	 * Otherwise, this method's implementation is described by
	 * {@link #getPlayer(String)}
	 *
	 * @param name to look up
	 * @return Player if found, else null
	 */
	public Player getPlayer(String name, boolean exact);

	/**
	 * Matches the given username to all players that contain it in their name.
	 *
	 * If no matches are found, an empty collection will be returned. The return
	 * will always be non-null.
	 *
	 * @param name to match
	 * @return Collection of all possible matches
	 */
	public Collection<Player> matchPlayer(String name);

	/**
	 * Searches for an actively loaded world that exactly matches the given
	 * name. <br/>
	 * <br/>
	 * The implementation is identical to iterating over {@link #getWorlds()}
	 * and checking for a world that matches {@link World#getName()}. <br/>
	 * <br/>
	 *
	 * @param name of the world to search for
	 * @return world if found, else null
	 */
	public World getWorld(String name);

	/**
	 * Searches for an actively loaded world has the given {@link UUID}. <br/>
	 * <br/>
	 * The implementation is identical to iterating over {@link #getWorlds()}
	 * and checking for a world that matches {@link World#getUID()}. <br/>
	 * <br/>
	 *
	 * @param uid of the world to search for
	 * @return world if found, else null
	 */
	public World getWorld(UUID uid);

	/**
	 * Gets a List of actively loaded worlds
	 *
	 * @return a {@link List} of actively loaded worlds
	 */
	public List<World> getWorlds();

	/**
	 * Initiates a save of the server state, including configuration files.
	 *
	 * It will save the state of the world, if specificed, and the state of
	 * players, if specified.
	 *
	 * @param worlds whether or not to save the state of all active worlds
	 * @param players whether or not to save the state of all active players
	 */
	public void save(boolean worlds, boolean players);

	/**
	 * Registers the recipe with the recipe database.
	 *
	 * @param recipe to register
	 * @return true if the recipe was registered, false if there was a conflict
	 *         with an existing recipe.
	 */
	public boolean registerRecipe(Recipe recipe);

	/**
	 * Gets the radius of the area around the spawn that is protected, in
	 * blocks.
	 *
	 * @return spawn protect radius
	 */
	public int getSpawnProtectRadius();

	/**
	 * Sets the radius of the area around the spawn that is protected, in
	 * blocks.
	 *
	 * @param radius to protect
	 */
	public void setSpawnProtectRadius(int radius);

	/**
	 * True if this server does not check if players are flying or not.
	 *
	 * If disabled, the server will attempt to verify that players are not
	 * flying, and kick any players that are flying.
	 *
	 * @return allow flight
	 */
	public boolean allowFlight();

	/**
	 * Ends this game instance safely. All worlds, players, and configuration
	 * data is saved, and all threads are ended cleanly.
	 */
	public void stop();

	/**
	 * Gets the default {@link GameMode} that is applied to all new players that
	 * join.
	 *
	 * @return default game mode
	 */
	public GameMode getDefaultGameMode();

	/**
	 * Sets the default {@link GameMode} that is applied to all new players that
	 * join.
	 *
	 * @param mode to set
	 */
	public void setDefaultGameMode(GameMode mode);

	/**
	 * Gets the folder that contains the world save data.
	 *
	 * If the folder is unusued, the file path will be '.'
	 *
	 * @return world folder
	 */
	public File getWorldFolder();

	/**
	 * Returns the game's root {@link Command}.
	 *
	 * All command registration and execution is performed through here.
	 *
	 * @return the {@link Game}'s root {@link Command}
	 */
	public Command getRootCommand();

	/**
	 * Returns the game's {@link EventManager} Event listener registration and
	 * calling is handled through this. ß
	 *
	 * @return Our EventManager instance
	 */
	public EventManager getEventManager();
}