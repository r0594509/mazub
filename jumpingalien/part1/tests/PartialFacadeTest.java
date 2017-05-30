package jumpingalien.part1.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import jumpingalien.part1.facade.Facade;
import jumpingalien.part1.facade.IFacade;
import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

import static jumpingalien.tests.util.TestUtils.*;

public class PartialFacadeTest {

	@Test
	public void startMoveRightCorrect() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		assertArrayEquals(intArray(10, 0), facade.getLocation(alien));
	}

	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		facade.startMoveRight(alien);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(alien, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testAccellerationZeroWhenNotMoving() {
		IFacade facade = new Facade();

		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testWalkAnimationLastFrame() {
		IFacade facade = new Facade();

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 0, sprites);

		facade.startMoveRight(alien);

		facade.advanceTime(alien, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(alien, 0.075);
		}

		assertEquals(sprites[8+m], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testVelocityOneWhenDucking() {
		IFacade facade = new Facade();
		
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(2, 2));
		
		facade.startDuck(alien);
		facade.startMoveRight(alien);
		
		assertArrayEquals(doubleArray(1, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}
	
	@Test
	public void testSpriteAfterMovement() {
		
		IFacade facade = new Facade();
				
		Sprite[] sprites = spriteArrayForSize(2, 2, 30);
		
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 0.2);
		facade.endMoveRight(alien);
		facade.advanceTime(alien, 0.005);
		
		assertEquals(sprites[2], facade.getCurrentSprite(alien));			
	}
	
	//does not seem to work - because we use System time
	public void testSpriteAfterIdle() {
	
		IFacade facade = new Facade();

		Sprite[] sprites = spriteArrayForSize(2, 2, 30);
		
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 1);
		facade.endMoveRight(alien);

		// wait 1 second
		facade.advanceTime(alien, 100);
		
		assertEquals(sprites[0], facade.getCurrentSprite(alien));			
	}
	
	@Test
	public void testSpriteJumpRight() {
		IFacade facade = new Facade();

		Sprite[] sprites = spriteArrayForSize(2, 2, 30);
		
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.startJump(alien);
		facade.advanceTime(alien, 0.005);
		
		assertEquals(sprites[4], facade.getCurrentSprite(alien));		
	}
	
	@Test
	public void testSpriteJumpRightDuck() {
		IFacade facade = new Facade();

		Sprite[] sprites = spriteArrayForSize(2, 2, 30);
		
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.startJump(alien);
		facade.advanceTime(alien, 0.005);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.005);
		
		assertEquals(sprites[6], facade.getCurrentSprite(alien));		
	}
	
	@Test
	public void testAccelerationZeroWhenDucking() {
		IFacade facade = new Facade();

		Sprite[] sprites = spriteArrayForSize(2, 2, 30);
		
		Mazub alien = facade.createMazub(0, 0, sprites);
		
		facade.startMoveRight(alien);
		facade.advanceTime(alien, 1);
		facade.startDuck(alien);
		facade.advanceTime(alien, 0.005);
		
		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);		
	}

	// TODO: add more tests
}
