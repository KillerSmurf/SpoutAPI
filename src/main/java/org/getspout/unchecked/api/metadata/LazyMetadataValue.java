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

import java.util.concurrent.Callable;

import org.getspout.unchecked.api.plugin.Plugin;

/**
 * The LazyMetadataValue class implements a type of metadata that is not
 * computed until another plugin asks for it. By making metadata values lazy, no
 * computation is done by the providing plugin until absolutely necessary (if
 * ever). Additionally, LazyMetadataValue objects cache their values internally
 * unless overridden by a {@link CacheStrategy} or invalidated at the individual
 * or plugin level. Once invalidated, the LazyMetadataValue will recompute its
 * value when asked.
 */
public class LazyMetadataValue implements MetadataValue {

	private Callable<Object> lazyValue;

	private CacheStrategy cacheStrategy;

	private String internalValue;

	private boolean internalValueEvaluated;

	private Plugin owningPlugin;

	/**
	 * Initialized a LazyMetadataValue object with the default
	 * CACHE_AFTER_FIRST_EVAL cache strategy.
	 *
	 * @param owningPlugin
	 * @param lazyValue
	 */
	public LazyMetadataValue(Plugin owningPlugin, Callable<Object> lazyValue) {
		this(owningPlugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
	}

	/**
	 * Initializes a LazyMetadataValue object with a specific cache strategy.
	 *
	 * @param owningPlugin
	 * @param cacheStrategy
	 * @param lazyValue
	 */
	public LazyMetadataValue(Plugin owningPlugin, CacheStrategy cacheStrategy, Callable<Object> lazyValue) {
		this.lazyValue = lazyValue;
		this.owningPlugin = owningPlugin;
		this.cacheStrategy = cacheStrategy;
		internalValueEvaluated = false;
	}

	/**
	 * Converts the metadata value into an int and returns it.
	 *
	 * @return
	 * @throws MetadataConversionException Thrown if the value cannot be
	 *             converted to an int. Ex: String => int
	 */

	public int asInt() throws MetadataConversionException {
		try {
			eval();
			return Integer.parseInt(internalValue);
		} catch (NumberFormatException e) {
			throw new MetadataConversionException("Could not convert metadata value of " + internalValue + " to type int.");
		}
	}

	/**
	 * Converts the metadata value into a double and returns it.
	 *
	 * @return
	 * @throws MetadataConversionException Thrown if the value cannot be
	 *             converted to a double. Ex: String => double
	 */

	public double asDouble() throws MetadataConversionException {
		try {
			eval();
			return Double.parseDouble(internalValue);
		} catch (NumberFormatException e) {
			throw new MetadataConversionException("Could not convert metadata value of " + internalValue + " to type double.");
		}
	}

	/**
	 * Converts the metadata value into a boolean and returns it.
	 *
	 * @return
	 * @throws MetadataConversionException Thrown if the value cannot be
	 *             converted to a double. Ex: String => double
	 */

	public boolean asBoolean() throws MetadataConversionException {
		try {
			eval();
			return internalValue != null && (internalValue.equalsIgnoreCase("true") || internalValue.equalsIgnoreCase("1") || internalValue.equalsIgnoreCase("1.0"));
		} catch (NumberFormatException e) {
			throw new MetadataConversionException("Could not convert metadata value of " + internalValue + " to type double.");
		}
	}

	/**
	 * Returns the metadata value as a string. This method will always succeed.
	 *
	 * @return
	 */

	public String asString() {
		eval();
		return internalValue;
	}

	/**
	 * Returns the {@link Plugin} that created this metadata item.
	 *
	 * @return
	 */

	public Plugin getOwningPlugin() {
		return owningPlugin;
	}

	/**
	 * Lazially evaluates the value of this metadata item.
	 *
	 * @throws MetadataEvaluationException
	 */
	private synchronized void eval() throws MetadataEvaluationException {
		if (cacheStrategy == CacheStrategy.NEVER_CACHE || !internalValueEvaluated) {
			try {
				internalValue = lazyValue.call().toString();
				internalValueEvaluated = true;
			} catch (Exception e) {
				throw new MetadataEvaluationException(e);
			}
		}
	}

	/**
	 * Invalidates this metadata item's value. The next time the value is
	 * requested it will be recomputed.
	 */

	public synchronized void invalidate() {
		if (cacheStrategy != CacheStrategy.CACHE_ETERNALLY) {
			internalValueEvaluated = false;
		}
	}

	/**
	 * Describes possible caching strategies for metadata.
	 */
	public enum CacheStrategy {

		/**
		 * Once the metadata value has been evaluated, do not re-evaluate the
		 * value until it is manually invalidated.
		 */
		CACHE_AFTER_FIRST_EVAL,
		/**
		 * Re-evaluate the metadata item every time it is requested
		 */
		NEVER_CACHE,
		/**
		 * Once the metadata value has been evaluated, do not re-evaluate the
		 * value in spite of manual invalidation.
		 */
		CACHE_ETERNALLY

	}

}
