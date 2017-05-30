package jumpingalien.part3.facade;

import java.beans.Expression;
import java.beans.Statement;
import java.util.Collection;
import java.util.Optional;

import jumpingalien.model.Buzam;
import jumpingalien.model.Direction;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.ProgramFactory;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.program.Type;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart3 {

	@Override
	public int getNbHitPoints(Mazub alien) {
		return alien.getNbHitPoints().getNbHitPoints();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		World myWorld = new World(tileSize, nbTilesX, nbTilesY, visibleWindowWidth, visibleWindowHeight, targetTileX, targetTileY);
		return myWorld;
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {
		int[] worldSize = new int[2];
		worldSize[0] = world.getXSize();
		worldSize[1] = world.getYSize();
		return worldSize;
	}

	@Override
	public int getTileLength(World world) {
		return world.getTileSize();
	}

	@Override
	public void startGame(World world) {
		world.startGame();
	}

	@Override
	public boolean isGameOver(World world) {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) {		
		return world.didPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) throws ModelException {
		try {
			world.advanceTime(dt);
		} catch (IllegalArgumentException e) {
			throw new ModelException("Invalid position");
		}
		didPlayerWin(world);
	}

	@Override
	public int[] getVisibleWindow(World world) {
		// TODO
		
	//Change camera when mazub is less than 20units for window
		int[] visibleWindow = new int[4];
		visibleWindow[0] = world.getMazubMiddlePosition()[0] - world.getVisibleWindowWidth()/2;
		visibleWindow[1] = world.getMazubMiddlePosition()[1] - world.getVisibleWindowHeight()/2;
		visibleWindow[2] = world.getMazubMiddlePosition()[0] + world.getVisibleWindowWidth()/2;
		visibleWindow[3] = world.getMazubMiddlePosition()[1] + world.getVisibleWindowHeight();
		return visibleWindow;
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return world.getBottomLeftPixelOfTile(tileX, tileY);
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {
		return world.getGeologicalFeature(pixelX, pixelY);
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		world.setGeologicalFeature(tileX, tileY, tileType);
	}

	@Override
	public void setMazub(World world, Mazub alien) {
		world.setMazub(alien);
	}

	@Override
	public boolean isImmune(Mazub alien) {
		return false;
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		Plant myPlant = new Plant(x, y, sprites);
		return myPlant;
	}

	@Override
	public void addPlant(World world, Plant plant) {
		world.addPlant(plant);
	}

	@Override
	public Collection<Plant> getPlants(World world) {
		return world.getVegetation();
	}

	@Override
	public int[] getLocation(Plant plant) {
		int[] position = new int[2];
		position[0] = (int) plant.getPosition().getCoordinateX();
		position[1] = (int) plant.getPosition().getCoordinateY();		
		return position;
	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		return plant.getCurrentSprite();
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		Shark myShark = new Shark(x, y, sprites);
		return myShark;
	}

	@Override
	public void addShark(World world, Shark shark) {
		world.addShark(shark);
	}

	@Override
	public Collection<Shark> getSharks(World world) {
		return world.getSharks();
	}

	@Override
	public int[] getLocation(Shark shark) {
		int[] position = new int[2];
		position[0] = (int) shark.getPosition().getCoordinateX();
		position[1] = (int) shark.getPosition().getCoordinateY();		
		return position;
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		return shark.getCurrentSprite();
	}

	@Override
	public School createSchool() {
		School mySchool = new School();
		return mySchool;
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		Slime mySlime = new Slime(x, y, sprites, school);
		return mySlime;
	}

	@Override
	public void addSlime(World world, Slime slime) {
		world.addSlime(slime);
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		return world.getSlimes();
	}

	@Override
	public int[] getLocation(Slime slime) {
		int[] position = new int[2];
		position[0] = (int) slime.getPosition().getCoordinateX();
		position[1] = (int) slime.getPosition().getCoordinateY();		
		return position;
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		return slime.getCurrentSprite();
	}

	@Override
	public School getSchool(Slime slime) {
		return slime.getSchool();
	}

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

	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		// TODO Auto-generated method stub
		Buzam myBuzam = new Buzam(pixelLeftX, pixelBottomY, sprites);
		return myBuzam;
	}

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		ProgramFactory<Expression, Statement, Type, Program> factory = new ProgramFactory<>();
		ProgramParser<Expression, Statement, Type, Program> parser = new ProgramParser<>(factory);
		//ParseOutcome<Program> parseResult = parser.parse(text);
		ParseOutcome<Program> geoff = ParseOutcome.success(null);
		return geoff;
	}

	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		world.addBuzam(buzam);
	}

	@Override
	public int[] getLocation(Buzam alien) {
		int location[] = new int[2];
		location[0] = ((int) alien.getPosition().getCoordinateX());
		location[1] = ((int) alien.getPosition().getCoordinateY());
		return location;
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		double velocity[] = new double[2];
		velocity[0] = alien.getHorizontalVelocity();
		velocity[1] = alien.getVerticalVelocity();
		return velocity;
	}
	
	@Override
	public double[] getAcceleration(Buzam alien) {
		double[] acceleration = new double[2];
		acceleration[0] = alien.getHorizontalAcceleration();
		acceleration[1] = alien.getVerticalAcceleration();
		return acceleration;
	}

	@Override
	public int[] getSize(Buzam alien) {
		int[] size = new int[2];
		size[0] = alien.getWidth();
		size[1] = alien.getHeight();
		return size;
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		return alien.getCurrentSprite();
	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		return 500;
	}

}
