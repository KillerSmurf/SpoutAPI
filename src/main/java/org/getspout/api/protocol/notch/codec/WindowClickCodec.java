package org.getspout.api.protocol.notch.codec;

import org.getspout.api.protocol.MessageCodec;
import org.getspout.api.protocol.notch.ChannelBufferUtils;
import org.getspout.api.protocol.notch.msg.WindowClickMessage;
import org.getspout.api.util.nbt.Tag;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.IOException;
import java.util.Map;

public final class WindowClickCodec extends MessageCodec<WindowClickMessage> {
	public WindowClickCodec() {
		super(WindowClickMessage.class, 0x66);
	}

	@Override
	public WindowClickMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readUnsignedByte();
		int slot = buffer.readUnsignedShort();
		boolean rightClick = buffer.readUnsignedByte() != 0;
		int transaction = buffer.readUnsignedShort();
		boolean shift = buffer.readUnsignedByte() != 0;
		int item = buffer.readUnsignedShort();
		if (item == 0xFFFF) {
			return new WindowClickMessage(id, slot, rightClick, transaction, shift);
		} else {
			int count = buffer.readUnsignedByte();
			int damage = buffer.readUnsignedShort();
			Map<String, Tag> nbtData = null;
			if (ChannelBufferUtils.hasNbtData(item)) {
				nbtData = ChannelBufferUtils.readCompound(buffer);
			}
			return new WindowClickMessage(id, slot, rightClick, transaction, shift, item, count, damage, nbtData);
		}
	}

	@Override
	public ChannelBuffer encode(WindowClickMessage message) throws IOException {
		int item = message.getItem();

		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeByte(message.getId());
		buffer.writeShort(message.getSlot());
		buffer.writeByte(message.isRightClick() ? 1 : 0);
		buffer.writeShort(message.getTransaction());
		buffer.writeByte(message.isShift() ? 1 : 0);
		buffer.writeShort(item);
		if (item != -1) {
			buffer.writeByte(message.getCount());
			buffer.writeShort(message.getDamage());
			if (ChannelBufferUtils.hasNbtData(message.getId())) {
				ChannelBufferUtils.writeCompound(buffer, message.getNbtData());
			}
		}
		return buffer;
	}
}
