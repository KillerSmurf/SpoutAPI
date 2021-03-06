package org.getspout.api.geo.discrete;

import org.getspout.api.geo.World;
import org.getspout.api.math.Vector3;
import org.getspout.api.math.Vector3m;

public class Transform extends Eyeline {

	protected final Vector3m scale;

	public Transform(Point position, Vector3 direction, Vector3 scale) {
		super(position, direction);
		this.scale = new Vector3m(scale);
	}

	public Transform(World world, double px, double py, double pz, double dx, double dy, double dz, double sx, double sy, double sz) {
		this(new Pointm(world, px, py, pz), new Vector3m(dx, dy, dz), new Vector3m(sx, sy, sz));
	}

	/**
	 * Gets the forward scaling component
	 *
	 * @return the forward scaling component
	 */
	public double getScaleForward() {
		return scale.getZ();
	}

	/**
	 * Gets the left scaling component
	 *
	 * @return the left scaling component
	 */
	public double getScaleLeft() {
		return scale.getY();
	}

	/**
	 * Gets the vertical scaling component
	 *
	 * @return the vertical scaling component
	 */
	public double getScaleUp() {
		return scale.getX();
	}

}
