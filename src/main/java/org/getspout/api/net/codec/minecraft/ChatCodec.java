package org.getspout.api.net.codec.minecraft;

import org.getspout.api.net.message.minecraft.ChatMessage;
import org.getspout.api.util.ChannelBufferUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public final class ChatCodec extends MessageCodec<ChatMessage> {
	public ChatCodec() {
		super(ChatMessage.class, 0x03);
	}

	@Override
	public ChatMessage decode(ChannelBuffer buffer) {
		String message = ChannelBufferUtils.readString(buffer);
		return new ChatMessage(message);
	}

	@Override
	public ChannelBuffer encode(ChatMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getMessage());
		return buffer;
	}
}
