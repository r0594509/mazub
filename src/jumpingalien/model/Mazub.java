package jumpingalien.model;

import java.awt.Rectangle;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.*;

/**
 * A class of Mazubs involving a starting x and y position and an array of Sprites.
 * 
 *@invar	...
 *			| isValidHorizontalVelocity(this.getHorizontalVelocity())
 *
 *@invar	...
 *			| isValidPosition(this.getLocationX(), this.getLocationY())
 *
 *@invar	...
 *			| (this.getInitialHorizontalVelocity() >= 1) && (this.getMaxHorizontalVelocity > this.getInitialHorizontalVelocity())
 *
 *@author 	Nathan Olmanst
 *@author 	Jonathan Hoebeke
 *
 */

public class Mazub extends Actor {
	
//	private int width;
//	private int height;
	
	//private Position position;
	
	private double initialHorizontalVelocity;
	private double maxHorizontalVelocity;
	private double horizontalVelocity;
	private double verticalVelocity;
	
	private double horizontalAcceleration;
	private double verticalAcceleration;
	
	//private Sprite currentSprite;
	
	//private final Sprite[] sprites;
	
	private int spriteCounter;
	
	private boolean moving;
	private boolean jumping;
	private boolean ducking;
	
	private static final double BASE_HORIZONTAL_ACCELERATION = 0.9;
	private static final double BASE_VERTICAL_ACCELERATION = -10.0;
	
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 768;
	
	private long initialIdleTime;
	private boolean idle;
	private Direction direction;
	
	private World world;

	/**
	 * Initializes this new Mazub with given initial horizontal velocity, given maximum horizontal velocity,
	 * given x starting position, given Y starting position and given array of sprites.
	 * 
	 * @param 	initialHorizontalVelocity
	 * 			Base horizontal velocity this Mazub will have when moving.
	 * 
	 * @param 	maxHorizontalVelocity
	 * 			Maximum horizontal velocity this Mazub will be able to move at.
	 * 
	 * @param 	startX
	 * 			Starting X-axis pixel coordinate for this Mazub.
	 * 
	 * @param 	startY
	 * 			Starting Y-axis pixel coordinate for this Mazub.
	 * 
	 * @param 	images
	 * 			The array of sprites that this Mazub will use for its animations.
	 * 
	 * @post	The new initial horizontal velocity of this new Mazub is equal to the given initial horizontal velocity.
	 * 			| new.getInitialHorizontalVelocity() == initialHorizontalVelocity
	 * 
	 * @post	The new maximal horizontal velocity of this new Mazub is equal to the given maximal horizontal velocity.
	 * 			| new.getMaxHorizontalVelocity() == maxHorizontalVelocity
	 * 
	 * @post	The new x location of this new Mazub is equal to the given starting x position.
	 * 			| this.getGamePosition()[0] == startX
	 * 
	 * @post	The new y location of this new Mazub is equal to the given starting y position.
	 * 			| this.getGamePosition()[1] = startY
	 * 
	 * @post	The new array of sprites of this new Mazub is equal to the given array of sprites.
	 * 			| this.sprites == images
	 */
	public Mazub(double initialHorizontalVelocity, double maxHorizontalVelocity, int startX, int startY, Sprite[] images) {
		
		super(startX, startY, images, 100);
		
		this.setInitialHorizontalVelocity(initialHorizontalVelocity);
		this.setMaxHorizontalVelocity(maxHorizontalVelocity);

		// this.setPosition(startX, startY);
		
		this.setHorizontalVelocity(0.0);
		this.setVerticalVelocity(0.0);
		
		this.setHorizontalAcceleration(0.0);
		this.setVerticalAcceleration(0.0);
		
		//sprites = images.clone();
		
		//this.setCurrentSprite(sprites[0]);
		
		//Rectangle myBox = new Rectangle(startX, startY + getCurrentSprite().getHeight(), getCurrentSprite().getWidth(), getCurrentSprite().getHeight());
		//setCollisionBox(myBox);
		
		this.setIdle(false);
		this.setJumping(false);
		this.setMoving(false);
		this.setDucking(false);
		
		setSpriteCounter(0);
	}

	/**
	 * A less extended constructor, uses default initialHorizontalVelocity and maxHorizontalVelocity.
	 * 
	 * @param 	pixelX
	 * 			Starting X-axis pixel coordinate for this Mazub.
	 * 
	 * @param 	pixelY
	 * 			Starting Y-axis pixel coordinate for this Mazub.
	 * 
	 * @param 	images
	 * 			The array of sprites that this Mazub will use for its animations.
	 * 		
	 * @effect	
	 * 			This new Mazub is initialised with an initial velocity of 1.0 m/s, 
	 * 			a maximum velocity of 3.0 m/s, the given pixelX as its starting x position,
	 * 			the given pixelY as its Y position 
	 * 			and the array of sprites images as the sprites to use for its animation.
	 * 			| this(1.0, 3.0, pixelX, pixelY, images)
	 */
	public Mazub(int pixelX, int pixelY, Sprite[] images) {
		this(1.0, 3.0, pixelX, pixelY, images);
	}
	
//	/**
//	 * Return a Position object containing the x and y coordinates of this Mazub.
//	 */
//	@Basic
//	public Position getPosition() {
//		return this.position;
//	}
//	
//	/**
//	 * Store the x and y coordinates of this Mazub into a new Position object.
//	 * 
//	 * @param 	coordinateX
//	 * 			The new X coordinate of this Mazub.
//	 * 
//	 * @param 	coordinateY
//	 * 			The new Y coordinate of this Mazub.
//	 * 
//	 * @post	The new position of this Mazub is set to the given x and y coordinates.
//	 * 
//	 * @throws 	IllegalArgumentException
//	 * 			The given x or y coordinate is not valid.
//	 * 			| !isValidCoordinateX(coordinateX) || !isValidCoordinateY(coordinateY)
//	 */
//	private void setPosition(double coordinateX, double coordinateY) throws IllegalArgumentException {
//		if (!isValidCoordinateX(coordinateX) || !isValidCoordinateY(coordinateY))
//			throw new IllegalArgumentException();
//		this.position = new Position(coordinateX, coordinateY);
//	}
//	
//	/**
//	 * Check whether this Mazub can have the given coordinate as its X coordinate.
//	 * 
//	 * @param 	positionX
//	 * 			The X coordinate of this Mazub.
//	 * 						
//	 * @return	
//	 * 			Returns true if and only if the given position is valid.
//	 * 			| result == (positionX < SCREEN_WIDTH) && (positionX >= 0)
//	 */
//	private boolean isValidCoordinateX(double positionX) {
//		
//		if (hasValidWorld())
//			return (positionX < getMyWorld().getXSize() && positionX >= 0.0);
//		else
//			return (positionX < SCREEN_WIDTH && positionX >= 0.0);
//	}
//
//	/**
//	 * Check whether this Mazub can have the given coordinate as its Y coordinate.
//	 * 
//	 * @param 	coordinateY
//	 * 			The Y coordinate of this Mazub.
//	 * 						
//	 * @return	
//	 * 			Returns true if and only if the given position is valid.
//	 * 			| result == (positionY < SCREEN_HEIGHT) && (positionY >= 0)
//	 */
//	private boolean isValidCoordinateY(double positionY) {
//		
//		if (hasValidWorld())
//			return (positionY < getMyWorld().getYSize() && positionY >= 0.0);
//		else
//			return (positionY < SCREEN_HEIGHT && positionY >= 0.0);
//	}
	
	/**
	 * Return the maximum horizontal velocity of this Mazub.
	 */
	@Basic
	private double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}

	/**
	 * Set the maximum horizontal velocity of this Mazub to the given velocity.
	 * 
	 * @param 	maxHorizontalVelocity
	 * 			The new maximum horizontal velocity of this Mazub.
	 * 
	 * @post	
	 *  		The new maximum horizontal velocity of this Mazub is equal to the given horizontal velocity.
	 *  		| new.getMaxHorizontalVelocity() == maxHorizontalVelocity
	 */
	private void setMaxHorizontalVelocity(double maxHorizontalVelocity) {
		this.maxHorizontalVelocity = maxHorizontalVelocity;
	}
	
	/**
	 * Return the initial horizontal velocity of this Mazub.
	 */
	@Basic @Immutable
	public double getInitialHorizontalVelocity() {
		return this.initialHorizontalVelocity;
	}
	
	/**
	 * Set the initial horizontal velocity of this Mazub.
	 * @param initialHorizontalVelocity
	 */
	private void setInitialHorizontalVelocity(double initialHorizontalVelocity) {
		this.initialHorizontalVelocity = initialHorizontalVelocity;
	}
	
	/**
	 * Return the horizontal velocity of this Mazub.
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}
	
	/**
	 * Set the horizontal velocity of this Mazub to the given velocity.
	 * 
	 * @param 	velocity
	 * 			The new horizontal velocity of this Mazub.
	 * 
	 * @post
	 * 			The new horizontal velocity of this Mazub is equal to the given horizontal velocity.
	 * 			| new.getHorizontalVelocity() == horizontalVelocity
	 * 
	 * @throws	IllegalArgumentException
	 * 			The given horizontal velocity is not valid.
	 * 			| ! isValidHorizontalVelocity(getHorizontalVelocity())
	 */
	private void setHorizontalVelocity(double velocity) throws IllegalArgumentException{
		if (! isValidHorizontalVelocity(getHorizontalVelocity()))
			throw new IllegalArgumentException();
		this.horizontalVelocity = velocity;
	}
	
	/**
	 * Check whether this Mazub can have the given velocity as its horizontal velocity.
	 * 
	 * @param 	horizontalVelocity
	 * 			Velocity to check.
	 * 
	 * @return
	 * 			Returns true if and only if the given horizontal velocity is valid
	 * 			| result == ( Math.abs(horizontalVelocity) >= getInitialHorizontalVelocity()) && ( Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity())) || horizontalVelocity == 0.0)
	 */
	private boolean isValidHorizontalVelocity(double horizontalVelocity) {
		return ( (( Math.abs(horizontalVelocity) >= getInitialHorizontalVelocity()) && ( Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity())) || horizontalVelocity == 0.0);
	}
	
	/**
	 * Return the vertical acceleration of this Mazub.
	 */
	@Basic
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}
	
	/**
	 * Set the vertical acceleration of this Mazub to the given vertical acceleration
	 * 
	 * @param verticalAcceleration
	 * 			The new vertical acceleration for this Mazub.
	 * 
	 * @post
	 * 			The new vertical acceleration for this Mazub is equal to the given vertical acceleration.
	 * 			| if (verticalAcceleration == 0.0 || verticalAcceleration == BASE_VERTICAL_ACCELERATION)
	 * 			| 	then new.getVerticalAcceleration() == verticalAcceleration
	 * @post 	
	 *			If the given vertical acceleration is not equal to either 0.0 or BASE_VERTICAL_ACCELERATION, 
	 *			set the new vertical acceleration to its current value.
	 * 			| if (verticalAcceleration != 0.0 && verticalAcceleration != BASE_VERTICAL_ACCELERATION)
	 * 			| 	then new.getVerticalAcceleration() == this.getVerticalAcceleration()
	 */
	private void setVerticalAcceleration(double verticalAcceleration) {
		if (verticalAcceleration == 0.0 || verticalAcceleration == BASE_VERTICAL_ACCELERATION)
			this.verticalAcceleration = verticalAcceleration;
		else
			this.verticalAcceleration = this.getVerticalAcceleration();
	}
	
	/**
	 * Return the horizontal acceleration of this Mazub.
	 */
	@Basic
	public double getHorizontalAcceleration() {
		return horizontalAcceleration;
	}
	
	/**
	 * Set the horizontal acceleration of this Mazub to the given horizontal acceleration.
	 * 
	 * @param 	horizontalAcceleration
	 * 			The new horizontal acceleration for this Mazub.
	 * @post
	 * 			The new horizontal acceleration for this Mazub is equal to the given horizontal acceleration.
	 * 			| if ((horizontalAcceleration == 0.0 && horizontalAcceleration == BASE_HORIZONTAL_ACCELERATION)
	 * 			|	then new.getHorizontalAcceleration() == horizontalAcceleration
	 * 
	 * @post 	
	 *			If the absolute value of the given horizontal acceleration is not equal to either 0.0 or BASE_HORIZONTAL_ACCELERATION, 
	 *			set the new horizontal acceleration to its current value.
	 * 			| if (horizontalAcceleration != 0.0 && horizontalAcceleration != BASE_HORIZONTAL_ACCELERATION)
	 * 			| 	then new.getHorizontalAcceleration() == this.getHorizontalAcceleration()
	 */
	private void setHorizontalAcceleration(double horizontalAcceleration) {
		if (this.horizontalAcceleration == 0 || Math.abs(this.horizontalAcceleration) == BASE_HORIZONTAL_ACCELERATION)
			this.horizontalAcceleration = horizontalAcceleration;
		else
			this.horizontalAcceleration = this.getHorizontalAcceleration();
	}
	
	/**
	 * Return the vertical velocity of this Mazub.
	 */
	@Basic
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}

	/**
	 * Set the vertical velocity of this Mazub to the given vertical velocity.
	 * 
	 * @param 	verticalVelocity
	 * 			The new vertical velocity for this Mazub.
	 * 
	 * @post
	 * 			The new vertical velocity of this Mazub is equal to the given vertical velocity.
	 * 			| new.getVerticalVelocity() == verticalVelocity
	 */
	private void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}
	
	
//	/**
//	 * Return the current sprite of this Mazub.
//	 */
//	@Basic @Override
//	public Sprite getCurrentSprite() {
//		assert(isValidSprite());
//		return this.currentSprite;
//	}
//
//	/**
//	 * Check whether Mazub's current sprite is valid.
//	 * 
//	 * @return	
//	 * 			Returns true if and only if Mazub's currentSprite is valid.
//	 * 			| result == (this.currentSprite.getHeight() == this.getHeight() && this.currentSprite.getWidth() == this.getWidth())
//	 */
//	private boolean isValidSprite() {
//		for (Sprite sprite: sprites) {
//			if (sprite.equals(currentSprite)) {
//				if (this.currentSprite.getHeight() == this.getHeight() && this.currentSprite.getWidth() == this.getWidth()){
//					return true;
//				}
//			}
//		}		
//		return false;
//	}
//
//	/**
//	 * Set the Sprite of this Mazub to the given Sprite
//	 * 
//	 * @param 	currentSprite
//	 * 			The new Sprite to give to this Mazub
//	 * 
//	 * @post	The new sprite of this Mazub will be changed to currentSprite.
//	 * 			| new.getCurrentSprite() = currentSprite
//	 * 
//	 * @post	The new height of this Mazub is equal to the height of the given sprite.
//	 * 			| new.getHeight() == currentSprite.getHeight()
//	 * 
//	 * @post	The new width of this Mazub is equal to the width of the given sprite.
//	 * 			| new.getWidth() == currentSprite.getWidth()
//	 */
//	public void setCurrentSprite(Sprite currentSprite) {
//		this.currentSprite = currentSprite;
//		this.setHeight(currentSprite.getHeight());
//		this.setWidth(currentSprite.getWidth());
//	}

//	/**
//	 * Return the width of this Mazub's current sprite.
//	 */
//	@Basic
//	public int getWidth() {
//		return this.width;
//	}
//	
//	/**
//	 * Return the height of this Mazub's current sprite.
//	 */
//	@Basic
//	public int getHeight() {
//		return this.height;
//	}
	
//	/**
//	 * Set the width of this Mazub.
//	 * 
//	 * @param 	width
//	 * 			The new width of this Mazub. 
//	 * 
//	 * @post	The new width of this Mazub is equal to the given width.
//	 * 			| new.getWidth() == width
//	 * 
//	 * @throws	IllegalArgumentException
//	 * 			The given width is not valid.
//	 * 			| !isValidWidth(width)
//	 */
//	private void setWidth(int width) throws IllegalArgumentException {
//		if (!isValidWidth(width))
//			throw new IllegalArgumentException();
//		this.width = width;
//	}
//
//	/**
//	 * Set the height of this Mazub.
//	 * 
//	 * @param 	height
//	 * 			The new height of this Mazub. 
//	 * 
//	 * @post	The new height of this Mazub is equal to the given height.
//	 * 			| new.getHeight() == height
//	 * 
//	 * @throws	IllegalArgumentException
//	 * 			The given height is not valid.
//	 * 			| (!isValidHeight(height))
//	 */
//	private void setHeight(int height) throws IllegalArgumentException {
//		if (!isValidHeight(height))
//			throw new IllegalArgumentException();
//		this.height = height;
//	}
//	
//	/**
//	 * Check whether this Mazub can have the given width as its width.
//	 * .
//	 * @param 	width
//	 * 			The width to check.
//	 * 
//	 * @return
//	 * 			Returns true if and only if the given width is valid
//	 * 			| result == (width <= SCREEN_WIDTH)
//	 */
//	private boolean isValidWidth(int width) {
//		return ((width <= SCREEN_WIDTH));
//	}
//
//	/**
//	 * Check whether this Mazub can have the given height as its height.
//	 * 
//	 * @param 	height
//	 * 			The height to check.
//	 * 
//	 * @return	
//	 * 			Returns true if and only if the given height is valid
//	 * 			| result == (height <= SCREEN_HEIGHT)
//	 */
//	private boolean isValidHeight(int height) {
//		return ((height <= SCREEN_HEIGHT));
//	}
//	
	/**
	 * Determine the Sprite that should be given to this Mazub depending on his current state.
	 * 
	 * @effect	If this mazub has not been moving for the past second, then set its idle state to true and change its direction to "NONE"
	 * 			|if (System.currentTimeMillis() > getInitialIdleTime() + 1000)
	 * 			|	then setIdle(true) && setDirection(Direction.NONE)
	 * 
	 * @effect	The current sprite of this Mazub is set to the correct sprite from its sprites array
	 * 			based on the boolean inspectors isMoving(), isDucking(), isJumping(), isFacingRight(), isFacingLeft(), 
	 * 			isIdle(), isMovingRight() and isMovingLeft().
	 */
	private void determineNextSprite() {
		
		if (! isMoving()) {
			if (System.currentTimeMillis() > getInitialIdleTime() + 1000) {
				setIdle(true);
				setDirection(Direction.NONE);
			}
		}
		
		if (isMovingRight())
			findNextSpriteInLoop(8);
		if (isMovingLeft())
			findNextSpriteInLoop(19);
		if (isIdle() && isDucking() && !isMoving())
			setCurrentSprite(getSprites()[1]);
		else if (isIdle() && !isDucking())
			setCurrentSprite(getSprites()[0]);
		if (isMovingRight() && isDucking())
			setCurrentSprite(getSprites()[6]);
		if (isMovingLeft() && isDucking())
			setCurrentSprite(getSprites()[7]);
		if (isJumping() && isMovingRight())
			setCurrentSprite(getSprites()[4]);
		if (isJumping() && isMovingLeft())
			setCurrentSprite(getSprites()[5]);
		if (isDucking() && isFacingRight())
			setCurrentSprite(getSprites()[6]);
		if (isDucking() && isFacingLeft())
			setCurrentSprite(getSprites()[7]);
		if (!isMoving() && !isDucking() && isFacingRight())
			setCurrentSprite(getSprites()[2]);
		else if (!isMoving() && !isDucking() && isFacingLeft())
			setCurrentSprite(getSprites()[3]);
	}
	
	/**
	 * Return the value for Mazub's sprite counter.
	 * This variable is used to cycle through this Mazub's walking animation.
	 */
	private int getSpriteCounter() {
		return this.spriteCounter;
	}

	/**
	 * Set the value for this Mazub's sprite counter to the parameter spriteCounter.
	 * 
	 * @param 	spriteCounter
	 * 			Value to set Mazub's sprite counter variable to.
	 * 
	 * @post	Set the new value of this Mazub's spriteCounter to the value of the parameter spriteCounter.
	 * 			| new.spriteCounter = spriteCounter
	 */
	private void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}
	
	/**
	 * Set the current sprite to the correct sprite in the walking loop.
	 *  
	 * @param 	initialSprite
	 * 			The initial sprite of the walking loop currently executing
	 * @post	
	 * 			The current sprite is set to the sprite with index of the initial sprite
	 * 			of the walking loop in execution + the value of the sprite counter
	 * 			| getCurrentSprite() == sprites[initialSprite + getSpriteCounter()]
	 */
	private void findNextSpriteInLoop(int initialSprite) {
		setSpriteCounter(getSpriteCounter() + 1);
		if (getSpriteCounter() > 11)
			setSpriteCounter(0);
		else
			setCurrentSprite(getSprites()[(initialSprite - 1) + getSpriteCounter()]);
	}
	
	/**
	 * Make this Mazub move in the given direction.
	 * 
	 * @param 	direction
	 * 			The direction this Mazub should move towards.
	 * 
	 * @pre		This Mazub will only start moving if it is not out of bound.
	 * 			| isValidCoordinateX(this.getPosition().getCoordinateX()) && isValidCoordinateY(this.getPosition().getCoordinateY())
	 * 
	 * @pre		This Mazub will only start moving to the opposite direction if it is moving at a valid speed.
	 * 			| isValidHorizontalVelocity(this.getHorizontalVelocity())
	 * 
	 * @post	...
	 * 			| setMoving(true)
	 * 
	 * @post	...
	 * 			| setIdle(false)
	 * 
	 * @post	...
	 * 			| setFacing(direction)
	 * 
	 * @post	...
	 * 			| setHorizontalVelocity(getInitialHorizontalVelocity() * direction.getMultiplier())
	 * 			| setHorizontalAcceleration(BASE_HORIZONTAL_ACCELERATION * direction.getMultiplier())
	 * 
	 */
	public void startMove(Direction direction) {
		
		assert (isValidHorizontalVelocity(this.getHorizontalVelocity()));
		
		assert(isValidDirection(direction));
				
		setMoving(true);
		setIdle(false);
		
		setDirection(direction);
		
		
		
		setHorizontalVelocity(getInitialHorizontalVelocity() * direction.getMultiplier());
		setHorizontalAcceleration(BASE_HORIZONTAL_ACCELERATION * direction.getMultiplier());
	}
	
	/**
	 * Check whether the direction is correct
	 * 
	 * @param	direction
	 * 			The direction to check.
	 * 
	 * @return	True if and only if the direction is either LEFT, RIGHT or NONE
	 * 			result == (direction == Direction.RIGHT || direction == Direction.LEFT || direction == Direction.NONE)
	 */
	private boolean isValidDirection(Direction direction) {
		return direction == Direction.RIGHT || direction == Direction.LEFT || direction == Direction.NONE;
	}
	


	/**
	 * End this Mazub's movement in the given direction.
	 * 
	 * @pre		This Mazub can only stop moving if it was already moving.
	 * 			| isMoving()
	 * 		
	 * @post	...
	 * 			| setHorizontalVelocity(0.0);
	 * 			| setHorizontalAcceleration(0.0);
	 * 			| setSpriteCounter(0);
	 * 			| setMoving(false);
	 */
	public void endMove(Direction direction) {	
		//assert (isMoving());
		assert (isValidDirection(direction));
		
		if ((direction == Direction.LEFT && isMovingLeft()) || (direction == Direction.RIGHT && isMovingRight())) {	
			setHorizontalVelocity(0.0);
			setHorizontalAcceleration(0.0);
			setSpriteCounter(0);
			setMoving(false);
		}			
				
		setInitialIdleTime(System.currentTimeMillis());		
	}

	/**
	 * Make this Mazub duck.
	 * 
	 * @throws	IllegalStateException
	 * 			This Mazub cannot duck if he is already ducking.
	 * 			| if ( isDucking())
	 * 			| 	then throw new exception
	 */
	public void startDuck() throws IllegalStateException {
		
		//if (isDucking())
		//	throw new IllegalStateException();
		
		setDucking(true);
		
		if (this.isMoving())
			setHorizontalVelocity(1.0 * getDirection().getMultiplier());
		
		setMaxHorizontalVelocity(1.0);
	}
	
	/**
	 * End this Mazub's ducking.
	 * 
	 * @throws	IllegalStateException
	 * 			This Mazub cannot stand up if he is already standing up.
	 * 			| if (! isDucking())
	 * 			| 	then throw new exception
	 */
	public void endDuck() throws IllegalStateException {
		
		//if( !isDucking())
		//	throw new IllegalStateException();
		
		if (!isImpassableTerrain((int) getPosition().getCoordinateX() + 1, (int) getPosition().getCoordinateY() + getHeight() + 1) && !isImpassableTerrain((int) getPosition().getCoordinateX() + getWidth() - 1, (int) getPosition().getCoordinateY() + getHeight() + 1)) {
			setDucking(false);
			setMaxHorizontalVelocity(3.0);

			if (isMoving() && Math.abs(getHorizontalVelocity()) < getMaxHorizontalVelocity()) {
			setHorizontalAcceleration(BASE_HORIZONTAL_ACCELERATION * getDirection().getMultiplier());
			}
		}		
	}
	
	/**
	 * Make this Mazub jump.
	 * 
	 * @throws 	IllegalStateException
	 * 			This Mazub cannot jump if he is moving upwards (no double jump).
	 * 			| if (isJumping())
	 * 			| 	then throw new exception
	 * 
	 * @effect	
	 * 			| setJumping(true)
	 * @effect	
	 * 			| setVerticalAcceleration(BASE_VERTICAL_ACCELERATION)
	 */
	public void startJump() throws IllegalStateException {
			
		if (isJumping())
			throw new IllegalStateException();
		
		setJumping(true);
		
		//no double jump
		if (getVerticalAcceleration() == 0.0)
			setVerticalVelocity(8.0);
			
		setVerticalAcceleration(BASE_VERTICAL_ACCELERATION);
	}

	/**
	 * Make this Mazub stop jumping.
	 * 
	 * @throws 	IllegalStateException
	 * 			This Mazub cannot stop jumping if he is not currently jumping, or if he is not in the air.
	 * 			| if( !isJumping() && getPosition().getCoordinateY() != Position.POSITION_0)
	 * 			| 	then throw new exception.
	 * @effect	
	 * 			| setJumping(false)
	 */
	public void endJump() throws IllegalStateException {

//		if (!isJumping() && getPosition().getCoordinateY() != 0.0)
	//		throw new IllegalStateException();
		
		setJumping(false);
		
		if (getVerticalVelocity() > 0.0)
			setVerticalVelocity(0.0);
	}
	
	/**
	 * Compute the new position of this Mazub based on his current position, acceleration and velocity and set its corresponding sprite.
	 * 
	 * @param 	dt
	 *          The time interval (in seconds) by which to advance this Mazub's time.
	 *        
	 * @post	Create a new Position with the newly computed position coordinates of this Mazub.
	 * 			|new.getPosition() == (newXPosition, newYPosition)
	 * 
	 * @post	Set the new sprite of this Mazub based on it's newly computed state.
	 */
	public void advanceTime(double dt) {
		
		determineNextSprite();		
	
		double newVelocity = getHorizontalVelocity() + (getHorizontalAcceleration() * dt);
		
		if (Math.abs(newVelocity) >= getMaxHorizontalVelocity()) {
			setHorizontalAcceleration(0.0);
			newVelocity = getMaxHorizontalVelocity() * getDirection().getMultiplier();
		}
						
		this.setHorizontalVelocity(newVelocity);
		this.setVerticalVelocity(getVerticalVelocity() + (getVerticalAcceleration() * dt));
		
		//*100 to convert everything to cm
		double newXPosition = getPosition().getCoordinateX() + ((1/2) * 100 * getHorizontalAcceleration() * Math.pow(dt, 2)) + ((getHorizontalVelocity() * 100 * dt));
		double newYPosition = getPosition().getCoordinateY() + ((1/2) * 100 * getVerticalAcceleration() * Math.pow(dt, 2)) + ((getVerticalVelocity() * 100 * dt));	
		
		//checkCollision(dt);
		
		inWaterHandling(getPosition().getCoordinateX(), getPosition().getCoordinateY());
		
		inMagmaHandling(getPosition().getCoordinateX(), getPosition().getCoordinateY());
		
		inTheAirHandling(getPosition().getCoordinateX(), getPosition().getCoordinateY());
		
		//double[] position = impassableTerrainHandling(newXPosition, newYPosition);
		
		setPosition(newXPosition, newYPosition);
		
		updateCollisionBox();
	}
	
	@Override
	public Rectangle advanceTime(double dt, boolean bool) {
		
		double hVel = this.getHorizontalVelocity();
		double vVel = this.getVerticalVelocity();
		
		double hAcc = this.getHorizontalAcceleration();
		double vAcc = this.getVerticalAcceleration();
		
		hVel = hVel + (hAcc * dt);
		
		if (Math.abs(hVel) >= getMaxHorizontalVelocity()) {
			hAcc = 0;
			hVel = getMaxHorizontalVelocity() * getDirection().getMultiplier();
		}
						
		vVel = vVel + (vAcc * dt);
		
		//*100 to convert everything to cms
		double newXPosition = getPosition().getCoordinateX() + ((1/2) * 100 * hAcc * Math.pow(dt, 2)) + ((hVel * 100 * dt));
		double newYPosition = getPosition().getCoordinateY() + ((1/2) * 100 * vAcc * Math.pow(dt, 2)) + ((vVel * 100 * dt));	
		
		Position newPosition = new Position(newXPosition, newYPosition);
		
		Rectangle myBox = updateCollisionBox(newPosition);

		return myBox;
	}
	

//	private void checkCollision(double dt) {
//		
//		double newDT, nominator, denominator;
//		
//		double hAcc = getHorizontalAcceleration();
//		double vAcc = getVerticalAcceleration();
//		
//		double hVel = getHorizontalVelocity();
//		double vVel = getVerticalVelocity();
//		
//		if (hAcc != 0.0) {
//			nominator = Math.sqrt(((2 * Math.abs(hAcc * 100)) + Math.pow((hVel * 100), 2))) - Math.abs(hVel * 100);
//			denominator = Math.abs(hAcc * 100);
//		} else {
//			nominator = 1;
//			denominator = hVel * 100;
//		}
//		
//		double xFormula = nominator / denominator;
//		
//		if (vAcc != 0.0) {
//			nominator = Math.sqrt(((2 * Math.abs(vAcc * 100)) + Math.pow((vVel * 100), 2))) - Math.abs(vVel * 100);
//			denominator = Math.abs(vAcc * 100);
//		} else {
//			nominator = 1;
//			denominator = vVel * 100;
//		}
//		
//		double yFormula = nominator / denominator;
//		
//		newDT = Math.min(xFormula, yFormula);
//		
//		for (Tile plant : myWorld.getVegetation()) {
//			if (this.collidesWith(plant, newDT)) {
//			//	if (getNbHitPoints().getNbHitPoints() < 500) {
//				setNbHitPoints(getNbHitPoints().heal());
//				myWorld.removePlant(plant);
//			//	}
//			}
//		}
//		//}
//	}
	
	@Override
	protected void collisionHandling() {
		
//		double newXPosition = getPosition().getCoordinateX();
//		double newYPosition = getPosition().getCoordinateY();
//		
//		if (getVerticalAcceleration() == BASE_VERTICAL_ACCELERATION) {
//			newYPosition = getTopLeftPixelOfTile(newXPosition, newYPosition);
//			setJumping(false);
//			this.setVerticalAcceleration(0.0);
//			this.setVerticalVelocity(0.0);
//		}
//		
//		if (isMovingRight()) {
//			int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//			newXPosition = btmlft[0];
//			this.setHorizontalAcceleration(0.0);
//			this.setHorizontalVelocity(0.0);
//		}
//
//		if (isMovingLeft()) {
//			int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//			newXPosition = btmlft[0] + myWorld.getTileSize();
//			this.setHorizontalAcceleration(0.0);
//			this.setHorizontalVelocity(0.0);
//		}
//		
//		if (isJumping()) {
//			int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//			newYPosition = btmlft[1] - 1;
//			this.setVerticalVelocity(0.0);
//		}
		
		
		double newXPosition = getPosition().getCoordinateX();
		double newYPosition = getPosition().getCoordinateY();
		
		if (getVerticalAcceleration() == BASE_VERTICAL_ACCELERATION) {
			newYPosition = getTopLeftPixelOfTile(newXPosition, newYPosition);
			setJumping(false);
			this.setVerticalAcceleration(0.0);
			this.setVerticalVelocity(0.0);
		} 
		else if (isMoving()) {
			this.setHorizontalAcceleration(0.0);
			this.setHorizontalVelocity(0.0);
		}
		
		setPosition(newXPosition, newYPosition);
	}	
	
//	private double[] impassableTerrainHandling(double newXPosition, double newYPosition) {
//		
//		if (getVerticalAcceleration() == BASE_VERTICAL_ACCELERATION) {
//			if (isImpassableTerrain((int) newXPosition, (int) newYPosition) || isImpassableTerrain((int) newXPosition + this.getWidth() - 1, (int) newYPosition)) {
//				newYPosition = getTopLeftPixelOfTile(newXPosition, newYPosition);
//				setJumping(false);
//				this.setVerticalAcceleration(0.0);
//				this.setVerticalVelocity(0.0);
//			}
//		}
//		
//		if (isMovingRight()) {
//			if (isImpassableTerrain((int) newXPosition + getWidth() + 1, (int) newYPosition + getHeight() - 1) || isImpassableTerrain((int) newXPosition + getWidth() + 1, (int) newYPosition + 1)) {
//				int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//				newXPosition = btmlft[0];
//				this.setHorizontalAcceleration(0.0);
//				this.setHorizontalVelocity(0.0);
//			}
//		}
//
//		if (isMovingLeft()) {
//			if (isImpassableTerrain((int) newXPosition, (int) newYPosition + getHeight()) || isImpassableTerrain((int) newXPosition + 1, (int) newYPosition + 1)) {
//				int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//				newXPosition = btmlft[0] + myWorld.getTileSize();
//				this.setHorizontalAcceleration(0.0);
//				this.setHorizontalVelocity(0.0);
//			}
//		}
//		
//		if (isJumping()) {
//			if (isImpassableTerrain((int) newXPosition, (int) newYPosition + myWorld.getTileSize()/2) || (isImpassableTerrain((int) newXPosition + getWidth() - 1, (int) newYPosition + myWorld.getTileSize()/2))) {
//				int[] btmlft = findNearestTile((int) newXPosition, (int) newYPosition);
//				newYPosition = btmlft[1] - 1;
//				this.setVerticalVelocity(0.0);
//			}
//		}
//		
//		double[] lösung = {newXPosition, newYPosition}; 
//		return lösung;
//	}
		
	private void inTheAirHandling(double newXPosition, double newYPosition) {
		
		if (isInTheAir((int) getPosition().getCoordinateX(), (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1) && isInTheAir((int) getPosition().getCoordinateX() + getWidth() - 1, (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1)) {
			this.setVerticalAcceleration(-10.0);
		}
	}

	private boolean isInTheAir(int xPosition, int yPosition) {
		
		int[] btmlft = findNearestTile(xPosition, yPosition);
		
		if (getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) == 0 
				|| getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) == 2
				|| getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) == 3)
			return true;
		else
			return false;
	}
	
	private void inWaterHandling(double newXPosition, double newYPosition) {
		
		if (isInWater((int) getPosition().getCoordinateX(), (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1) && isInTheAir((int) getPosition().getCoordinateX() + getWidth() - 1, (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1)) {
			setNbHitPoints(getNbHitPoints().takeWaterDamage());
		}
	}

	private boolean isInWater(int xPosition, int yPosition) {
		
		int[] btmlft = findNearestTile(xPosition, yPosition);
		
		if (getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) == 2)
			return true;
		else
			return false;
	}
	
	private void inMagmaHandling(double newXPosition, double newYPosition) {
		
		if (isInMagma((int) getPosition().getCoordinateX(), (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1) && isInTheAir((int) getPosition().getCoordinateX() + getWidth() - 1, (int) getPosition().getCoordinateY() - getWorld().getTileSize() + 1)) {
			setNbHitPoints(getNbHitPoints().takeMagmaDamage());
		}
	}

	private boolean isInMagma(int xPosition, int yPosition) {
		
		int[] btmlft = findNearestTile(xPosition, yPosition);
		
		if (getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) == 3)
			return true;
		else
			return false;
	}

	private boolean isImpassableTerrain(int xPosition, int yPosition) {
		
		int[] btmlft = findNearestTile(xPosition, yPosition);
		
		if (getWorld().getGeologicalFeature(btmlft[0], btmlft[1]) != 1)
			return false;
		else
			return true;
		
	}
		
	private double getTopLeftPixelOfTile(double xPosition, double yPosition) {
		int[] btmlft = findNearestTile((int) xPosition, (int) yPosition);
		return btmlft[1] + getWorld().getTileSize() - 1;
	}
		
	private int[] findNearestTile(int xPosition, int yPosition) {
		int x = Math.floorDiv(xPosition, getWorld().getTileSize());
		int y = Math.floorDiv(yPosition, getWorld().getTileSize());
		
		int pixelX = x * getWorld().getTileSize();
		int pixelY = y * getWorld().getTileSize();
		
		int[] tileLocation = {pixelX, pixelY};
		
		return tileLocation;
	}
	
	/**
	 * Set this Mazub's initial idle time to the given time (in ms).
	 * This timer is used to define when this Mazub reaches its idle state.
	 * It starts once this Mazub has stopped moving, and sets its idle state to true if no motion has been detected during the following second.
	 * 
	 * @param 	time
	 * 			Variable to set Mazub's idle timer to.
	 * 			| this.idleTimer = time
	 */
	private void setInitialIdleTime(long time) {
		this.initialIdleTime = time;		
	}
	
	/**
	 * Get this Mazub's initial idle time (in ms).
	 * This timer is used to define when this Mazub reaches its idle state.
	 * It starts once this Mazub has stopped moving, and sets its idle state to true if no motion has been detected during the following second.
	 */
	private long getInitialIdleTime() {		
		return this.initialIdleTime;
	}
	
	/**
	 * 	Set the direction this Mazub is facing to.
	 * @param 	direction
	 * 			The new direction this Mazub is facing to.
	 * 
	 * @post	The new direction this Mazub is facing to is equal to the given direction.
	 * 			| this.facing = direction
	 */
	protected void setDirection(Direction direction) {
		this.direction = direction;	
	}
	
	/**
	 * Return the direction this Mazub is facing to.
	 */
	protected Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Check whether this Mazub is currently facing left.
	 */
	private boolean isFacingLeft() {
		return getDirection() == Direction.LEFT;
	}

	/**
	 * Check whether this Mazub is currently facing right.
	 */
	private boolean isFacingRight() {
		return getDirection() == Direction.RIGHT;
	}
		
	/**
	 * Check whether this Mazub is currently moving.
	 */
	private boolean isMoving() {
		return this.moving;
	}
	
	/**
	 * Set this Mazub's moving state to the parameter moving.
	 * 
	 * @param	moving
	 * 			Boolean value to set this Mazub's moving state to.
	 * 
	 * @post	...
	 * 			| new.isMoving() = moving
	 * 
	 */
	private void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	/**
	 * Check whether this Mazub is currently moving to the right.
	 * 
	 * @return
	 * 			True if and only if this Mazub is moving and facing to the right.
	 * 			| result == (isMoving() && isFacingRight())
	 */
	private boolean isMovingRight() {
		if (isMoving() && isFacingRight()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check whether this Mazub is currently moving to the left.
	 * 
	 * @return
	 * 			True if and only if this Mazub is moving and facing to the left.
	 * 			| result == (isMoving() && isFacingLeft())
	 */
	private boolean isMovingLeft() {
		if (isMoving() && isFacingLeft()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check whether this Mazub is currently jumping.
	 */
	private boolean isJumping() {		
		return this.jumping;
	}
	
	/**
	 * Set this Mazub's jumping state to the given boolean jumping.
	 * 
	 * @param 	jumping
	 * 			Boolean value to change this Mazub's jumping state to.
	 * 
	 * @post	...
	 * 			| new.isJumping() == jumping
	 */
	private void setJumping(boolean jumping) {
		this.jumping = jumping;		
	}
	
	/**
	 * Check whether this Mazub is currently ducking.
	 */
	private boolean isDucking() {
		return this.ducking;
	}
	
	/**
	 * Set this Mazub's ducking state to the parameter ducking.
	 * 
	 * @param 	ducking
	 * 			Boolean value to set this Mazub's ducking state to.
	 * 
	 * @post	...
	 * 			| new.isDucking() == ducking
	 */
	private void setDucking(boolean ducking) {
		this.ducking = ducking;
	}

	/**
	 * Check whether this Mazub has moved in the past second or not.
	 */
	private boolean isIdle() {
		return this.idle;
	}

	/**
	 * Change the boolean variable for this Mazub to the given parameter idle.
	 * 
	 * @param idle
	 * 			...
	 * 			Boolean to change the variable idle to.
	 * 
	 * @post	...
	 * 			The new value of the variable idle will be set to the given parameter idle.
	 * 			| new.isIdle() = idle
	 */
	private void setIdle(boolean idle) {
		this.idle = idle;
	}
	
	private boolean hasValidWorld() {
		return this.getWorld() != null;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	void checkCollision(double dt) {
		
	}
	
}


