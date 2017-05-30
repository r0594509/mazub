package jumpingalien.model;

import java.awt.Rectangle;

import jumpingalien.util.Sprite;

public class Plant extends Actor {
	
	private World world;
	
	private long initialTime;
	
	private Direction direction;
	
	private double velocity;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Plant(int x, int y, Sprite[] sprites) {
		super(x, y, sprites, 100);
		setDirection(Direction.LEFT);
	}
	
	@Override
	public Sprite getCurrentSprite() {
		if (getDirection() == Direction.LEFT)
			return getSprites()[0];
		else
			return getSprites()[1];
	}

	public void advanceTime(double dt) {
		
		if (System.currentTimeMillis() - getInitialTime() > 500) {
			toggleDirection();
			setInitialTime(System.currentTimeMillis());
		}

		setVelocity(0.5 * getDirection().getMultiplier());
		
		double newXPosition = getPosition().getCoordinateX() + ((getVelocity() * 100 * dt));
		
		checkCollision(dt);
		
		setPosition(newXPosition, getPosition().getCoordinateY());
		
		updateCollisionBox();
	}
	
	@Override
	public Rectangle advanceTime(double dt, boolean bool) {

		setVelocity(0.5 * getDirection().getMultiplier());
		
		double newXPosition = getPosition().getCoordinateX() + ((getVelocity() * 100 * dt));
			
		Position newPosition = new Position(newXPosition, getPosition().getCoordinateY());
		
		Rectangle myBox = updateCollisionBox(newPosition);
		
		return myBox;
	}
	
	@Override
	protected void checkCollision(double dt) {
		
		double newDT, nominator, denominator;
		
		double hVel = getVelocity();
		
		nominator = 1;
		denominator = hVel * 100;
		
		newDT = nominator / denominator;
		
		if (this.collidesWith(getWorld().getMazub(), newDT)) {
			if (getNbHitPoints().getNbHitPoints() < getWorld().getMazub().getNbHitPoints().getMaxHitPoints()) {
				getWorld().getMazub().setNbHitPoints(getWorld().getMazub().getNbHitPoints().heal());
				getWorld().removePlant(this);
			}
		}
		//}
	}

	private void toggleDirection() {
		if (getDirection() == Direction.LEFT)
			setDirection(Direction.RIGHT);
		else if (getDirection() == Direction.RIGHT)
			setDirection(Direction.LEFT);
	}

	public void startGame() {
		setInitialTime(System.currentTimeMillis());
	}
	public long getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}

	//DOCUMENTATION: say that people need to add getMultiplier in order to get something else than the absolute value
	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	@Override
	void collisionHandling() {
		
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}



