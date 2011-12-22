
package org.getspout.unchecked.api.event.player;

import org.getspout.unchecked.api.entity.CreatureType;
import org.getspout.unchecked.api.entity.object.Egg;
import org.getspout.unchecked.api.event.Cancellable;
import org.getspout.unchecked.api.event.HandlerList;



/**
 *
 * Called when a player throws an egg.
 */
public class PlayerEggThrowEvent extends PlayerEvent implements Cancellable {

	private Egg egg;
	private int amountOfHatches;
	private boolean hatch;
	private CreatureType hatchedEntity;
	private static HandlerList handlers = new HandlerList();

	/**
	 * Gets the amount of entities that were hatched.
	 * 
	 * @return amount of hatched entities.
	 */
	public int getAmountOfHatches() {
		return amountOfHatches;
	}

	/**
	 * Sets the amount of entities that were hatched.
	 * 
	 * @param amountOfHatches
	 */
	public void setAmountOfHatches(int amountOfHatches) {
		this.amountOfHatches = amountOfHatches;
	}

	/**
	 * Gets the boolean of whether or not the egg will hatch an entity.
	 * 
	 * @return whether or not there was a hatch.
	 */
	public boolean isHatch() {
		return hatch;
	}

	/**
	 * Sets the boolean of whether or not the egg will hatch an entity.
	 * 
	 * @param hatch 
	 */
	public void setHatch(boolean hatch) {
		this.hatch = hatch;
	}

	/**
	 * Gets the type of entity that was hatched from the egg.
	 * 
	 * @return the type of entity that was hatched.
	 */
	public CreatureType getHatchedEntity() {
		return hatchedEntity;
	}

	/**
	 * Sets the type of entity that was hatched from the egg.
	 * 
	 * @param hatchedEntity 
	 */
	public void setHatchedEntity(CreatureType hatchedEntity) {
		this.hatchedEntity = hatchedEntity;
	}

	/**
	 * Gets the egg in the event.
	 * 
	 * @return The EGG.
	 */
	public Egg getEgg() {
		return egg;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
