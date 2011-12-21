package org.getspout.api.net.codec.minecraft;

import java.io.IOException;

import org.getspout.api.net.message.minecraft.EntityRotationMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public final class EntityRotationCodec extends MessageCodec<EntityRotationMessage> {
	public EntityRotationCodec() {
		super(EntityRotationMessage.class, 0x20);
	}

	@Override
	public EntityRotationMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int rotation = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		return new EntityRotationMessage(id, rotation, pitch);
	}

	@Override
	public ChannelBuffer encode(EntityRotationMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(6);
		buffer.writeInt(message.getId());
		buffer.writeByte(message.getRotation());
		buffer.writeByte(message.getPitch());
		return buffer;
	}
}
