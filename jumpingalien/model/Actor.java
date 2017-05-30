package jumpingalien.model;

import java.awt.Rectangle;

import jumpingalien.util.Sprite;

public abstract class Actor {

	private Position position;
	
	private Rectangle collisionBox;
	
	private Sprite[] sprites;
	
	private Sprite currentSprite;
	
	private int width;
	private int height;
	
	private HitPoints nbHitPoints;
	
//	private World world;
	
	public Actor(int x, int y, Sprite[] sprites, int hitPoints) {
		setPosition(x, y);
		setSprites(sprites);
		setCurrentSprite(getSprites()[0]);
		Rectangle myBox = new Rectangle(x, y + getHeight(), getWidth(), getHeight());
		setCollisionBox(myBox);
		setNbHitPoints(new HitPoints(hitPoints));
	}
	
	public HitPoints getNbHitPoints() {
		return nbHitPoints;
	}
	
	public void setNbHitPoints(HitPoints nbHitPoints) {
		this.nbHitPoints = nbHitPoints;
	}

	protected void setPosition(double coordinateX, double coordinateY) {
		this.position = new Position(coordinateX, coordinateY);
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	private void setSprites(Sprite[] sprites) {
		this.sprites = sprites.clone();
	}
	
	//watch out for people manipulating the pointer
	protected Sprite[] getSprites() {
		return this.sprites;
	}
	
	protected void setCurrentSprite(Sprite sprite) {
		this.currentSprite = sprite;
		this.setHeight(currentSprite.getHeight());
		this.setWidth(currentSprite.getWidth());
	}
	
	public Sprite getCurrentSprite() {
		return this.currentSprite;
	}
	
	private void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	private void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	protected void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}
	
	protected void updateCollisionBox() {
		Position currentPosition = getPosition();
		
		int xCoord = (int) currentPosition.getCoordinateX();
		int yCoord = ((int) currentPosition.getCoordinateY() + getHeight());
		
		Rectangle myBox = new Rectangle(xCoord, yCoord, getWidth(), getHeight());
		
		setCollisionBox(myBox);
	}
	
	protected Rectangle updateCollisionBox(Position newPosition) {
		
		int xCoord = (int) newPosition.getCoordinateX();
		int yCoord = ((int) newPosition.getCoordinateY() + getHeight());
		
		Rectangle myBox = new Rectangle(xCoord, yCoord, getWidth(), getHeight());
		
		return myBox;
	}

	protected boolean collidesWith(Actor other, double dt) {
		Rectangle actorCollisionBox = this.advanceTime(dt, true);
		Rectangle otherCollisionBox = other.advanceTime(dt, true);
		return (actorCollisionBox.intersects(otherCollisionBox));		
	}
	
	protected boolean collidesWith(Tile tile, double dt) {
		Rectangle actorCollisionBox = this.advanceTime(dt, true);
		Rectangle tileCollisionBox = tile.getCollisionBox();
		return (actorCollisionBox.intersects(tileCollisionBox));		
	}
	
	protected boolean isDead() {
		return getNbHitPoints().getNbHitPoints() <= 0;
	}
	
	abstract Rectangle advanceTime(double dt, boolean bool);
	
	abstract void collisionHandling();

	abstract void checkCollision(double dt);
	
//	public World getWorld() {
//		return world;
//	}
//
//	public void setWorld(World world) {
//		this.world = world;
//	}

}
