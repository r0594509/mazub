package jumpingalien.model;

import java.awt.Rectangle;

import jumpingalien.util.Sprite;

public class Shark extends Actor {
	
//	private Position position;
//	
//	private Sprite[] sprites;
//	
//	private Sprite currentSprite;
	
	private double actionTime;
	
	private double startTime;
	
	private Direction direction;
	
	private World world;
	
	private double horizontalVelocity, verticalVelocity;
	
	private static final double maxHorizontalVelocity = 4.0;
	
	private double horizontalAcceleration, verticalAcceleration;
	
	private static final double BASE_HORIZONTAL_ACCELERATION = 1.5;

	public Shark(int x, int y, Sprite[] sprites) {
		super(x, y, sprites, 100);
		setStartTime(System.currentTimeMillis());
		setActionTime((Math.random() * 3) + 1);
		if (Math.random() > 0.5)
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
		setHorizontalVelocity(0.0);
		setHorizontalAcceleration(BASE_HORIZONTAL_ACCELERATION * getDirection().getMultiplier());
	}
	
	private double getStartTime() {
		return startTime;
	}

	private void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	private double getActionTime() {
		return actionTime;
	}

	private void setActionTime(double actionTime) {
		this.actionTime = actionTime;
	}
	
	@Override
	public Sprite getCurrentSprite() {
		if (getDirection() == Direction.LEFT)
			return getSprites()[0];
		else
			return getSprites()[1];
	}

	public void advanceTime(double dt) {
		
		if (isDead()) {
			getWorld().removeShark(this);
			return;
		}
		
		if ((System.currentTimeMillis() - getStartTime()) >= getActionTime() * 1000) {
			setStartTime(System.currentTimeMillis());
			setActionTime((Math.random() * 3) + 1);
			toggleDirection();
		}
		
		setHorizontalAcceleration(BASE_HORIZONTAL_ACCELERATION * getDirection().getMultiplier());
		
		double newVelocity = getHorizontalVelocity() + (getHorizontalAcceleration() * dt);
		
		if (Math.abs(newVelocity) >= getMaxHorizontalVelocity()) {
			setHorizontalAcceleration(0.0);
			newVelocity = getMaxHorizontalVelocity();
		}
						
		this.setHorizontalVelocity(Math.abs(newVelocity) * getDirection().getMultiplier());
		
		//*100 to convert everything to cm
		double newXPosition = getPosition().getCoordinateX() + ((1/2) * 100 * getHorizontalAcceleration() * Math.pow(dt, 2)) + ((getHorizontalVelocity() * 100 * dt));
		//double newYPosition = getPosition().getCoordinateY() + ((1/2) * 100 * getVerticalAcceleration() * Math.pow(dt, 2)) + ((getVerticalVelocity() * 100 * dt));	
		
		checkCollision(dt);
		
		setPosition(newXPosition, getPosition().getCoordinateY());
	}
	

	@Override
	public Rectangle advanceTime(double dt, boolean bool) {

		double hVel = getHorizontalVelocity();
		
		double hAcc = BASE_HORIZONTAL_ACCELERATION * getDirection().getMultiplier();
		
		hVel = hVel + (hAcc * dt);
		
		if (Math.abs(hVel) >= getMaxHorizontalVelocity()) {
			hAcc = 0;
			hVel = getMaxHorizontalVelocity() * getDirection().getMultiplier();
		}
		
		//*100 to convert everything to cms
		double newXPosition = getPosition().getCoordinateX() + ((1/2) * 100 * hAcc * Math.pow(dt, 2)) + ((hVel * 100 * dt));
		//double newYPosition = getPosition().getCoordinateY() + ((1/2) * 100 * vAcc * Math.pow(dt, 2)) + ((vVel * 100 * dt));	
		
		Position newPosition = new Position(newXPosition, getPosition().getCoordinateY());
		
		Rectangle myBox = updateCollisionBox(newPosition);

		return myBox;
	}

	private void toggleDirection() {
		if (getDirection() == Direction.LEFT)
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double getHorizontalVelocity() {
		return horizontalVelocity;
	}

	public void setHorizontalVelocity(double horizontalVelocity) {
		this.horizontalVelocity = horizontalVelocity;
	}

	private double getVerticalVelocity() {
		return verticalVelocity;
	}

	private void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}

	private double getHorizontalAcceleration() {
		return horizontalAcceleration;
	}

	private void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}

	private double getVerticalAcceleration() {
		return verticalAcceleration;
	}

	private void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;
	}

	private double getMaxHorizontalVelocity() {
		return maxHorizontalVelocity;
	}

	@Override
	protected void collisionHandling() {
		toggleDirection();
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	protected void checkCollision(double dt) {
		
		double newDT, nominator, denominator;
		
		double hVel = getHorizontalVelocity();
		
		nominator = 1;
		denominator = hVel * 100;
		
		newDT = nominator / denominator;
		
		if (this.collidesWith(getWorld().getMazub(), newDT)) {
			getWorld().getMazub().setNbHitPoints(getWorld().getMazub().getNbHitPoints().takeEnemyDamage());
			this.setNbHitPoints(getNbHitPoints().takeEnemyDamage());
		}
	}
	
}