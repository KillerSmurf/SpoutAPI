/*
 * This file is part of Spout API (http://wiki.getspout.org/).
 *
 * Spout API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Spout API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.unchecked.api.event.player;

import org.getspout.unchecked.api.event.HandlerList;
import org.getspout.unchecked.api.event.Result;
import org.getspout.unchecked.api.inventory.InventorySlotType;
import org.getspout.unchecked.api.inventory.ItemStack;
import org.getspout.unchecked.api.inventory.PlayerInventory;

public class PlayerInventoryClickEvent extends PlayerInventoryEvent {
	private static HandlerList handlers = new HandlerList();

	protected InventorySlotType type;

	protected ItemStack item;

	protected ItemStack cursor;

	protected int slot;

	protected int convertedSlot;

	protected Result result = Result.DEFAULT;

	protected boolean leftClick;

	protected boolean shift;

	@Override
	public void setCancelled(boolean cancel) {
		if (cancel) {
			result = Result.DENY;
		}
		super.setCancelled(cancel);
	}

	/**
	 * Gets the result of this event. Default: Allow for Minecraft to handle the
	 * inventory click normally Allow: Allow the inventory click to continue,
	 * regardless of the consequences Deny: Block the inventory click from
	 * occuring, reset the inventory state to the pre-click state
	 *
	 * @return result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * Sets the result of this event. Default: Allow for Minecraft to handle the
	 * inventory click normally Allow: Allow the inventory click to continue,
	 * regardless of the consequences Deny: Block the inventory click from
	 * occuring, reset the inventory state to the pre-click state
	 */
	public void setResult(Result result) {
		this.result = result;
		if (result == Result.DENY) {
			setCancelled(true);
		}
	}

	/**
	 * Gets the type of slot that is being interacted with
	 *
	 * @return slot type
	 */
	public InventorySlotType getSlotType() {
		return type;
	}

	public void setSlotType(InventorySlotType type) {
		this.type = type;
	}

	/**
	 * Gets the item at the slow being interacted with, or null if empty
	 *
	 * @return item
	 */
	public ItemStack getItem() {
		return item;
	}

	/**
	 * Sets the slot being interacted with. Use null for an empty slot. Note:
	 * The inventory slot can not be changed unless the result has been set to
	 * Allow.
	 *
	 * @param item to set
	 */
	public void setItem(ItemStack item) {
		if (result != Result.ALLOW) {
			throw new UnsupportedOperationException("Can not alter stack contents without allowing any result");
		}
		this.item = item;
	}

	/**
	 * Gets the cursor being interacted with, or null if empty.
	 *
	 * @return cursor
	 */
	public ItemStack getCursor() {
		return cursor;
	}

	/**
	 * Sets the cursor being interacted with. Use null for an empty slot. Note:
	 * The cursor can not be changed unless the result has been set to Allow.
	 *
	 * @param cursor to set
	 */
	public void setCursor(ItemStack cursor) {
		if (result != Result.ALLOW) {
			throw new UnsupportedOperationException("Can not alter cursor stack contents without allowing any result");
		}
		this.cursor = cursor;
	}

	/**
	 * Gets the slot index being interacted with If the slot is -999, the
	 * clicked region is outside of the inventory
	 *
	 * @return slot index
	 */
	public int getSlot() {
		return convertedSlot;
	}

	public void setSlot(int slot) {
		convertedSlot = slot;
	}

	/**
	 * Gets the raw slot index that the packet sent If the slot is -999, the
	 * clicked region is outside of the inventory
	 *
	 * @return raw slot
	 */
	public int getRawSlot() {
		return slot;
	}

	public void setRawSlot(int slot) {
		this.slot = slot;
	}

	/**
	 * Returns true if the click on the inventory window was a left click. If
	 * false, it was a right click.
	 *
	 * @return true if left click
	 */
	public boolean isLeftClick() {
		return leftClick;
	}

	public void setLeftClick(boolean leftClick) {
		this.leftClick = leftClick;
	}

	/**
	 * Returns true if the click on the inventory crafting slow was a shift
	 * click.
	 *
	 * @return true if shift click
	 */
	public boolean isShiftClick() {
		return shift;
	}

	public void setShiftClick(boolean shift) {
		this.shift = shift;
	}

	protected int convertSlot(int slot) {
		if (getInventory() instanceof PlayerInventory) {
			int size = getInventory().getSize();
			//Armour slot
			switch (slot) {
				case 5:
					return 39;
				case 6:
					return 38;
				case 7:
					return 37;
				case 8:
					return 36;
			}
			//Quickslots
			if (slot >= size) {
				slot -= size;
			}

			return slot;
		}
		return slot;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
