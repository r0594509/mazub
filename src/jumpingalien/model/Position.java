package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of positions involving an x position and a y position.
 * 
 * @invar	The x coordinate of each position must be a valid x position.
 * 			| isValidPositionX()
 * @invar	The y coordinate of each position must be a valid y position.
 * 			| isValidPositionY()
 *
 */
@Value
public class Position {
	
	private final double coordinateX;
	private final double coordinateY;

	public Position(double coordinateX, double coordinateY) {
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}
	
	public double getCoordinateX() {
		return this.coordinateX;
	}
	
	public double getCoordinateY() {
		return this.coordinateY;
	}
	
	private boolean isValidCoordinateX() {
		return true;
	}
	
	private boolean isValidCoordinateY() {
		return true;
	}
	
	/**
	 * Check whether this position is equal to the given object.
	 * 
	 * @return	True if and only if the given object is an effective position,
	 * 			whose values for the x and y coordinate are equal to those of this position.
	 * 			| result == (other instanceof Position) &&
				| (this.getPositionX() == ((Position) other).getPositionX() && 
				| this.getPositionY() == ((Position) other).getPositionY());	
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Position) &&
				(this.getCoordinateX() == ((Position) other).getCoordinateX() && this.getCoordinateY() == ((Position) other).getCoordinateY());	
	}
	
	/**
	 * Return the hash code for this position.
	 */
	@Override
	public int hashCode() {
		return (int) (getCoordinateX() + getCoordinateY());
	}
	
	/**
	 * Return a textual representation of this position.
	 * 
	 * @return	A string consisting of the textual representation of the x and y coordinates of this position,
	 * 			separated by a space and enclosed in square brackets.
	 * 			| result.equals("[" + getPositionX() + " " + getPositionY() + "]")
	 */
	@Override
	public String toString() {
		return "[" + getCoordinateX() + " " + getCoordinateY() + "]";
	}
	
}
