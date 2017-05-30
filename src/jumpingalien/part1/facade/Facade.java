package jumpingalien.part1.facade;

import jumpingalien.model.Direction;
import jumpingalien.model.Mazub;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade{

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Mazub myMazub = new Mazub(pixelLeftX, pixelBottomY, sprites);
		return myMazub;
	}

	@Override
	public int[] getLocation(Mazub alien) throws ModelException {
		int[] position = new int[2];
		position[0] = (int) alien.getPosition().getCoordinateX();
		position[1] = (int) alien.getPosition().getCoordinateY();		
		return position;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		double velocity[] = new double[2];
		velocity[0] = alien.getHorizontalVelocity();
		velocity[1] = alien.getVerticalVelocity();
		return velocity;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		double[] acceleration = new double[2];
		acceleration[0] = alien.getHorizontalAcceleration();
		acceleration[1] = alien.getVerticalAcceleration();
		return acceleration;
	}

	@Override
	public int[] getSize(Mazub alien) {
		int[] size = new int[2];
		size[0] = alien.getWidth();
		size[1] = alien.getHeight();
		return size;
	}
	
	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		alien.startJump();
	}

	@Override
	public void endJump(Mazub alien) throws ModelException {
		try {
			alien.endJump();
		} catch (IllegalStateException e) {
			throw new ModelException("Exception when ending the jumping state");
		}
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		alien.startMove(Direction.LEFT);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		alien.endMove(Direction.LEFT);
	}

	@Override
	public void startMoveRight(Mazub alien) {
		alien.startMove(Direction.RIGHT);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		alien.endMove(Direction.RIGHT);
	}

	@Override
	public void startDuck(Mazub alien) {
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) throws ModelException {
		try {
			alien.endDuck();
		} catch (IllegalStateException e) {
			throw new ModelException("Exception when ending the ducking state");
		}
	}

	@Override
	public void advanceTime(Mazub alien, double dt) throws ModelException {
		try { 
			alien.advanceTime(dt);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Invalid position");
		}
	}
}
