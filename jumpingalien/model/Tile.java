package jumpingalien.model;

import java.awt.Rectangle;

public class Tile {
	
	private final int x;
	private final int y;
	private int tileSize;
	private int tileType;
	private Rectangle collisionBox;

	public Tile (int xPos, int yPos, int tileType, int tileSize) {
		this.x = xPos;
		this.y = yPos;
		setTileSize(tileSize);
		this.tileType = tileType;
		Rectangle rekt = new Rectangle(xPos, yPos + getTileSize(), getTileSize() - 1, getTileSize() - 1);
		setCollisionBox(rekt);
	}
	
	public int getXPosition() {
		return this.x;
	}
	
	public int getYPosition() {
		return this.y;
	}
	
	public int getTileType() {
		return this.tileType;
	}
	
	public void setTileType(int tileType) {
		this.tileType = tileType;
	}
	
	public int getTileSize() {
		return tileSize;
	}

	private void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	protected void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
	
	protected void updateCollisionBox() {
		
		int xCoord = getXPosition();
		int yCoord = getYPosition() + getTileSize();
		
		Rectangle myBox = new Rectangle(xCoord, yCoord, getTileSize() - 1, getTileSize() - 1);
		
		setCollisionBox(myBox);
	}
	
	protected Rectangle updateCollisionBox(Position newPosition) {
		
		int xCoord = getXPosition();
		int yCoord = getYPosition() + getTileSize();
		
		Rectangle myBox = new Rectangle(xCoord, yCoord, getTileSize() - 1, getTileSize() - 1);
		
		return myBox;
	}

	protected boolean collidesWith(Actor actor, double dt) {
		
		Rectangle tileCollisionBox = getCollisionBox();
		Rectangle actorCollisionBox = actor.advanceTime(dt, true);
		return (tileCollisionBox.intersects(actorCollisionBox));
	}


}
