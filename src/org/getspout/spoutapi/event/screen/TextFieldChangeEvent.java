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
package org.getspout.spoutapi.event.screen;

import org.getspout.spoutapi.event.EventType;
import org.getspout.spoutapi.event.SpoutEvent;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.gui.TextField;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TextFieldChangeEvent extends ScreenEvent implements SpoutEvent {
	
	private static final long serialVersionUID = -287243193163686657L;
	protected TextField field;
	protected String oldVal;
	protected String newVal;
	private static final EventType type = EventType.TextField_Change;
	
	public TextFieldChangeEvent(SpoutPlayer player, Screen screen, TextField field, String newVal) {
		super("TextFieldChangeEvent", player, screen, ScreenType.CUSTOM_SCREEN);
		this.field = field;
		this.oldVal = field.getText();
		this.newVal = newVal;
	}
	
	public TextField getTextField() {
		return field;
	}
	
	public String getOldText() {
		return oldVal;
	}
	
	public String getNewText() {
		return newVal;
	}
	
	public void setNewText(String newVal) {
		if (newVal == null) newVal = "";
		this.newVal = newVal;
	}

	@Override
	public EventType getEventType() {
		return type;
	}

}