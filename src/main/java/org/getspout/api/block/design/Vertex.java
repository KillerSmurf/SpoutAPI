package org.getspout.api.block.design;

public class Vertex {

	private SubTexture texture;
	private int index;
	private int quad;
	private float x;
	private float y;
	private float z;
	private int tx;
	private int ty;

	private Vertex(int index, int quad, float x, float y, float z) {
		if (index < 0 || index > 3) {
			throw new IllegalArgumentException("Invalid vertex index: " + index);
		}
		this.index = index;
		this.quad = quad;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vertex(int index, int quad, float x, float y, float z, SubTexture texture) {
		this(index, quad, x, y, z);

		setSubTexture(texture);
	}

	public Vertex(int index, int quad, float x, float y, float z, int tx, int ty) {
		this(index, quad, x, y, z);
		this.tx = tx;
		this.ty = ty;
	}

	public Vertex setSubTexture(SubTexture texture) {
		this.texture = texture;

		switch (index) {
			case 0:
				tx = texture.getXLoc();
				ty = texture.getYLoc();
				break;
			case 1:
				tx = texture.getXLoc();
				ty = texture.getYTopLoc();
				break;
			case 2:
				tx = texture.getXTopLoc();
				ty = texture.getYTopLoc();
				break;
			case 3:
				tx = texture.getXTopLoc();
				ty = texture.getYLoc();
		}

		return this;
	}

	public int getIndex() {
		return index;
	}

	public SubTexture getSubTexture() {
		return texture;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public int getTextureX() {
		return tx;
	}

	public int getTextureY() {
		return ty;
	}

	public int getTextureWidth() {
		return texture.getParent().getWidth();
	}

	public int getTextureHeight() {
		return texture.getParent().getHeight();
	}

	public int getQuadNum() {
		return quad;
	}
}