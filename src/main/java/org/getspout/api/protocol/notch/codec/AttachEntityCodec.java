package org.getspout.api.protocol.notch.codec;

import org.getspout.api.protocol.MessageCodec;
import org.getspout.api.protocol.notch.msg.AttachEntityMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.IOException;

public final class AttachEntityCodec extends MessageCodec<AttachEntityMessage> {
	public AttachEntityCodec() {
		super(AttachEntityMessage.class, 0x27);
	}

	@Override
	public AttachEntityMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		int vehicle = buffer.readInt();
		return new AttachEntityMessage(id, vehicle);
	}

	@Override
	public ChannelBuffer encode(AttachEntityMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(8);
		buffer.writeInt(message.getId());
		buffer.writeInt(message.getVehicle());
		return buffer;
	}
}
