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
package org.getspout.unchecked.api.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.getspout.unchecked.api.plugin.Plugin;

public abstract class MetadataStoreBase<TSubject> {

	private Map<String, List<MetadataValue>> metadataMap = new HashMap<String, List<MetadataValue>>();

	/**
	 * Adds a metadata value to an object. Each metadata value is owned by a
	 * specific{@link Plugin}. If a plugin has already added a metadata value to
	 * an object, that value will be replaced with the value of
	 * {@code newMetadataValue}. Multiple plugins can set independent values for
	 * the same {@code metadataKey} without conflict.
	 *
	 * Implementation note: I considered using a
	 * {@link java.util.concurrent.locks.ReadWriteLock} for controlling access
	 * to {@code metadataMap}, but decided that the added overhead wasn't worth
	 * the finer grained access control. Bukkit is almost entirely single
	 * threaded so locking overhead shouldn't pose a problem.
	 *
	 * @param subject The object receiving the metadata.
	 * @param metadataKey A unique key to identify this metadata.
	 * @param newMetadataValue
	 */
	public synchronized void setMetadata(TSubject subject, String metadataKey, MetadataValue newMetadataValue) {
		String key = disambiguate(subject, metadataKey);
		if (!metadataMap.containsKey(key)) {
			metadataMap.put(key, new ArrayList<MetadataValue>());
		}
		// we now have a list of subject's metadata for the given metadata key. If newMetadataValue's owningPlugin
		// is found in this list, replace the value rather than add a new one.
		List<MetadataValue> metadataList = metadataMap.get(key);
		for (int i = 0; i < metadataList.size(); i++) {
			if (metadataList.get(i).getOwningPlugin() == newMetadataValue.getOwningPlugin()) {
				metadataList.set(i, newMetadataValue);
				return;
			}
		}
		// we didn't find a duplicate...add the new metadata value
		metadataList.add(newMetadataValue);
	}

	/**
	 * Returns all metadata values attached to an object. If multiple plugins
	 * have attached metadata, each will value will be included.
	 *
	 * @param subject
	 * @param metadataKey
	 * @return
	 */
	public synchronized List<MetadataValue> getMetadata(TSubject subject, String metadataKey) {
		String key = disambiguate(subject, metadataKey);
		if (metadataMap.containsKey(key)) {
			return metadataMap.get(key);
		} else {
			return new ArrayList<MetadataValue>();
		}
	}

	/**
	 * Tests to see if a metadata attribute has been set on an object.
	 *
	 * @param subject
	 * @param metadataKey
	 * @return
	 */
	public synchronized boolean hasMetadata(TSubject subject, String metadataKey) {
		String key = disambiguate(subject, metadataKey);
		return metadataMap.containsKey(key);
	}

	/**
	 * Removes a metadata item owned by a plugin from a subject.
	 *
	 * @param subject
	 * @param metadataKey
	 * @param owningPlugin
	 */
	public synchronized void removeMetadata(TSubject subject, String metadataKey, Plugin owningPlugin) {
		String key = disambiguate(subject, metadataKey);
		List<MetadataValue> metadataList = metadataMap.get(key);
		for (int i = 0; i < metadataList.size(); i++) {
			if (metadataList.get(i).getOwningPlugin() == owningPlugin) {
				metadataList.remove(i);
			}
		}
	}

	/**
	 * Invalidates all metadata in the metadata store that originates from the
	 * given plugin. Doing this will force each invalidated metadata item to be
	 * recalculated the next time it is accessed.
	 *
	 * @param owningPlugin
	 */
	public synchronized void invalidateAll(Plugin owningPlugin) {
		for (List<MetadataValue> values : metadataMap.values()) {
			for (MetadataValue value : values) {
				if (value.getOwningPlugin() == owningPlugin) {
					value.invalidate();
				}
			}
		}
	}

	/**
	 * Creates a unique name for the object receiving metadata by combining
	 * unique data from the subject with a metadataKey. The name created must be
	 * globally unique for the given object and any two equivalent objects must
	 * generate the same unique name. For example, two Player objects must
	 * generate the same string if they represent the same player, even if the
	 * objects would fail a reference equality test.
	 *
	 * @param subject The object for which this key is being generated
	 * @param metadataKey The name identifying the metadata value
	 * @return
	 */
	protected abstract String disambiguate(TSubject subject, String metadataKey);

}
