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
package org.getspout.unchecked.api.plugin;

import java.io.File;

import org.getspout.unchecked.api.event.Event;

public abstract interface PluginManager {

	public abstract Plugin getAddon(String paramString);

	public abstract Plugin[] getAddons();

	public abstract boolean isAddonEnabled(String paramString);

	public abstract boolean isAddonEnabled(Plugin paramAddon);

	public abstract Plugin loadAddon(File paramFile) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

	public abstract Plugin[] loadAddons(File paramFile);

	public abstract void disableAddons();

	public abstract void clearAddons();

	public abstract <TEvent extends Event> TEvent callEvent(TEvent event);

	public abstract void enableAddon(Plugin paramAddon);

	public abstract void disableAddon(Plugin paramAddon);

	public abstract Plugin getOrCreateAddon(String readString);

	public abstract ThreadGroup getSecurityThreadGroup();

}
