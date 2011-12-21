package org.getspout.api.net.codec.minecraft;

import java.io.IOException;

import org.getspout.api.net.message.minecraft.StatisticMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public final class StatisticCodec extends MessageCodec<StatisticMessage> {
	public StatisticCodec() {
		super(StatisticMessage.class, 0xC8);
	}

	@Override
	public StatisticMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		byte amount = buffer.readByte();
		return new StatisticMessage(id, amount);
	}

	@Override
	public ChannelBuffer encode(StatisticMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.buffer(1);
		buffer.writeInt(message.getId());
		buffer.writeByte(message.getAmount());
		return buffer;
	}
}
