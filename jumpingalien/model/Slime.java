package jumpingalien.model;

import java.awt.Rectangle;

import jumpingalien.util.Sprite;

public class Slime extends Mazub {
	
	private long startTime;
	private double actionTime;
	
	private double horizontalVelocity;
	private double horizontalAcceleration;
	
	private static final double maxHorizontalVelocity = 2.5;
	
	private static final double BASE_HORIZONTAL_ACCELERATION = 0.7;
	private static final double BASE_VERTICAL_ACCELERATION = -10.0;
	
	private School school;

	public Slime(int x, int y, Sprite[] sprites, School school) {
		super(x, y, sprites);	
		setStartTime(System.currentTimeMillis());
		setActionTime((Math.random() * 4) + 2);
		if (Math.random() > 0.5)
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
		setSchool(school);
	}
	
	private void setHorizontalVelocity(double velocity) {
		this.horizontalVelocity = velocity;
	}
	
	
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}
	
	private void toggleDirection() {
		if (getDirection() == Direction.LEFT)
			setDirection(Direction.RIGHT);
		else
			setDirection(Direction.LEFT);
	}
	
	@Override
	public void advanceTime(double dt) {
		
		if (isDead()) {
			getWorld().removeSlime(this);
			return;
		}
		
		if ((System.currentTimeMillis() - getStartTime()) > getActionTime() * 10000) {
			setStartTime(System.currentTimeMillis());
			setActionTime((Math.random() * 4) + 2);
			toggleDirection();
		}
		
		double newVelocity = getHorizontalVelocity() + (BASE_HORIZONTAL_ACCELERATION * getDirection().getMultiplier() * dt);
		
		if (Math.abs(newVelocity) >= getMaxHorizontalVelocity()) {
			setHorizontalAcceleration(0.0);
			newVelocity = getMaxHorizontalVelocity() * getDirection().getMultiplier();
		}
		
		setHorizontalVelocity(Math.abs(newVelocity) * getDirection().getMultiplier());
				
		double newXPosition = getPosition().getCoordinateX() + ((1/2) * 100 * BASE_HORIZONTAL_ACCELERATION * Math.pow(dt, 2)) + ((getHorizontalVelocity() * 100 * dt));
		
		checkCollision(dt);
		
		setPosition(newXPosition, getPosition().getCoordinateY());
	}
	
	private void setActionTime(double actionTime) {
		this.actionTime = actionTime;
	}
	
	private double getActionTime() {
		return this.actionTime;
	}

	private void setStartTime(long currentTimeMillis) {
		this.startTime = currentTimeMillis;
	}

	private long getStartTime() {
		return this.startTime;
	}

	private void setSchool(School school) {
		this.school = school;
	}
	
	public School getSchool() {
		return this.school;
	}
	
	@Override
	protected void collisionHandling() {
		
		setActionTime(System.currentTimeMillis());
		toggleDirection();
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

	public static double getMaxHorizontalVelocity() {
		return maxHorizontalVelocity;
	}

	private void setHorizontalAcceleration(double acc) {
		this.horizontalAcceleration = acc;
	}
	
	@Override
	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}
	
	@Override
	public Sprite getCurrentSprite() {
		if (getDirection() == Direction.LEFT)
			return getSprites()[0];
		else
			return getSprites()[1];
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
