package org.getspout.api.net.codec.minecraft;

import java.io.IOException;
import java.util.List;

import org.getspout.api.net.message.minecraft.SpawnMobMessage;
import org.getspout.api.util.ChannelBufferUtils;
import org.getspout.api.util.Parameter;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public final class SpawnMobCodec extends MessageCodec<SpawnMobMessage> {
	public SpawnMobCodec() {
		super(SpawnMobMessage.class, 0x18);
	}

	@Override
	public SpawnMobMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int type = buffer.readUnsignedByte();
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		int rotation = buffer.readUnsignedByte();
		int pitch = buffer.readUnsignedByte();
		List<Parameter<?>> parameters = ChannelBufferUtils.readParameters(buffer);
		return new SpawnMobMessage(id, type, x, y, z, rotation, pitch, parameters);
	}

	@Override
	public ChannelBuffer encode(SpawnMobMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getId());
		buffer.writeByte(message.getType());
		buffer.writeInt(message.getX());
		buffer.writeInt(message.getY());
		buffer.writeInt(message.getZ());
		buffer.writeByte(message.getRotation());
		buffer.writeByte(message.getPitch());
		ChannelBufferUtils.writeParameters(buffer, message.getParameters());
		return buffer;
	}
}
