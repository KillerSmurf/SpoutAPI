package org.getspout.api.protocol.notch.msg;

import org.getspout.api.protocol.Message;

public final class GroundMessage extends Message {
	private final boolean onGround;

	public GroundMessage(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public String toString() {
		return "GroundMessage{onGround=" + onGround + "}";
	}
}
