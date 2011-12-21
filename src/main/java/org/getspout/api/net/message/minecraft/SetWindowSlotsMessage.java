package org.getspout.api.net.message.minecraft;

import java.util.Arrays;

import org.getspout.api.inventory.ItemStack;


public final class SetWindowSlotsMessage extends Message {
	private final int id;
	private final ItemStack[] items;

	public SetWindowSlotsMessage(int id, ItemStack[] items) {
		this.id = id;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public ItemStack[] getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "SetWindowSlotsMessage{id=" + id + ",slots=" + Arrays.toString(items) + "}";
	}
}
