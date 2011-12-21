package org.getspout.api.net.codec.minecraft;

import java.io.IOException;

import org.getspout.api.net.message.minecraft.UserListItemMessage;
import org.getspout.api.util.ChannelBufferUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class UserListItemCodec extends MessageCodec<UserListItemMessage> {
	public UserListItemCodec() {
		super(UserListItemMessage.class, 0xC9);
	}

	@Override
	public ChannelBuffer encode(UserListItemMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getName());
		buffer.writeByte(message.addOrRemove() ? 1 : 0);
		buffer.writeShort(message.getPing());
		return buffer;
	}

	@Override
	public UserListItemMessage decode(ChannelBuffer buffer) throws IOException {
		String name = ChannelBufferUtils.readString(buffer);
		boolean addOrRemove = buffer.readByte() == 1;
		short ping = buffer.readShort();
		return new UserListItemMessage(name, addOrRemove, ping);
	}
}
