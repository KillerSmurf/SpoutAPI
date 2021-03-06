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

import org.getspout.unchecked.api.util.Location;

public interface TravelAgent {
	/**
	 * Set the Block radius to search in for available portals.
	 *
	 * @param radius The radius in which to search for a portal from the
	 *            location.
	 * @return This travel agent.
	 */
	public TravelAgent setSearchRadius(int radius);

	/**
	 * Gets the search radius value for finding an available portal.
	 *
	 * @return Returns the currently set search radius.
	 */
	public int getSearchRadius();

	/**
	 * Sets the maximum radius from the given location to create a portal.
	 *
	 * @param radius The radius in which to create a portal from the location.
	 * @return This travel agent.
	 */
	public TravelAgent setCreationRadius(int radius);

	/**
	 * Gets the maximum radius from the given location to create a portal.
	 *
	 * @return Returns the currently set creation radius.
	 */
	public int getCreationRadius();

	/**
	 * Returns whether the TravelAgent will attempt to create a destination
	 * portal or not.
	 *
	 * @return Return whether the TravelAgent should create a destination portal
	 *         or not.
	 */
	public boolean getCanCreatePortal();

	/**
	 * Sets whether the TravelAgent should attempt to create a destination
	 * portal or not.
	 *
	 * @param create Sets whether the TravelAgent should create a destination
	 *            portal or not.
	 */
	public void setCanCreatePortal(boolean create);

	/**
	 * Attempt to find a portal near the given location, if a portal is not
	 * found it will attempt to create one.
	 *
	 * @param location The location where the search for a portal should begin.
	 * @return Returns the location of a portal which has been found or returns
	 *         the location passed to the method if unsuccessful.
	 */
	public Location findOrCreate(Location location);

	/**
	 * Attempt to find a portal near the given location.
	 *
	 * @param location The desired location of the portal.
	 * @return Returns the location of the nearest portal to the location.
	 */
	public Location findPortal(Location location);

	/**
	 * Attempt to create a portal near the given location.
	 *
	 * @param location The desired location of the portal.
	 * @return True if a nether portal was successfully created.
	 */
	public boolean createPortal(Location location);

}