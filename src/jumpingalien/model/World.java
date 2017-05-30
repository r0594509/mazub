package jumpingalien.model;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import jumpingalien.util.ModelException;

public class World {
	
	private Mazub mazub;
	
	private Buzam buzam;
	
	// Static define world boundaries == check them in Position class
	
	private int tileSize;
	private int nbTilesX;
	private int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	
	
	private Collection<Plant> worldVegetation = new ArrayList<Plant>();
	private Collection<Shark> worldSharks = new ArrayList<Shark>();
	private Collection<Slime> worldSlimes = new ArrayList<Slime>();
	private final Collection<Tile> worldTiles = new ArrayList<Tile>();
	
	
	public int getXSize() {
		return this.getTileSize() * this.getNbTilesX();
	}

	public int getYSize() {
		return this.getTileSize() * this.getNbTilesY();
	}

	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		this.tileSize = tileSize;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		this.visibleWindowWidth = visibleWindowWidth;
		this.visibleWindowHeight = visibleWindowHeight;
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
		
		for (int i = 0; i < getXSize(); i += tileSize){ 
			for (int j = 0; j < getYSize(); j += tileSize) {
				Tile newTile = new Tile(i, j, 3, tileSize);
				addTile(newTile);
			}
		}
		
	}

	public int getNbTilesX() {
		return nbTilesX;
	}

	public void setNbTilesX(int nbTilesX) {
		this.nbTilesX = nbTilesX;
	}

	public int getNbTilesY() {
		return nbTilesY;
	}

	public void setNbTilesY(int nbTilesY) {
		this.nbTilesY = nbTilesY;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public int getGeologicalFeature(int pixelX, int pixelY)
			throws ModelException {

		for (Tile tile : worldTiles) {
			if (tile.getXPosition() == pixelX && tile.getYPosition() == pixelY) {
				return tile.getTileType();
			}
		}
		
		//THROW ERROR
		return -1;
	}

	public void setGeologicalFeature(int tileX, int tileY,
			int tileType) {
		
		for (Tile tile : worldTiles) {
			if (tile.getXPosition() == tileX*tileSize && tile.getYPosition() == tileY*tileSize) {
				tile.setTileType(tileType);
			}
		}
	
	}
	
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY) {
		
		int bottomX = tileX * tileSize;
		int bottomY = tileY * tileSize;
		
		int[] bottomLeftPixel = {bottomX, bottomY};
		return bottomLeftPixel;
	}
	
	public int[] getBottomLeftPixelOfTile(double xPosition, double yPosition) {
		
		int positionX = (int) xPosition;
		int positionY = (int) yPosition;
		
		int x = Math.floorDiv(positionX, getTileSize());
		int y = Math.floorDiv(positionY, getTileSize());
		
		int pixelX = x * getTileSize();
		int pixelY = y * getTileSize();
		
		int[] tileLocation = {pixelX, pixelY};
		
		return tileLocation;
	}
	
	public int[][] getTilePositionsIn(int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {

		//int amountTilesX = pixelRight/tileSize - pixelLeft/tileSize;
		
		//int amountTilesY = pixelTop/tileSize - pixelBottom/tileSize;
		
		int[][] tiles = new int[worldTiles.size()][2];	
					
		int i = 0;
		for (Tile tile : worldTiles) {
			int[] newTile = {tile.getXPosition()/tileSize, tile.getYPosition()/tileSize};
			tiles[i] = newTile;
			i++;
		}
	
		return tiles;
	} 	
	
	public void startGame() {
		
		for (Plant plant : getVegetation()) {
			plant.setWorld(this);
			plant.startGame();
		}
		
		for (Shark shark : getSharks()) {
			shark.setWorld(this);
			//shark.startGame();
		}
		
		for (Slime slime : getSlimes()) {
			slime.setWorld(this);
		//	slime.startGame();
		}
		
	}
	
	public void advanceTime(double dt) {

		checkCollision(mazub, dt);
		mazub.advanceTime(dt);
		
		for (Plant plant : getVegetation()) {		
			plant.advanceTime(dt);
		}
		
		for (Shark shark : getSharks()) {
			checkCollision(shark, dt);
			shark.advanceTime(dt);
		}
		
		for (Slime slime : getSlimes()) {
			checkCollision(slime, dt);
			slime.advanceTime(dt);
		}
		
	}
	
	private void checkCollision(Actor actor, double dt) {
		
		for (Tile tile : getTiles()) {
			if (tile.getTileType() == 1 && tile.collidesWith(actor, dt)) {
				actor.collisionHandling();
			}
		}
			
	}

	public void setMazub(Mazub mazub) {
		this.mazub = mazub;
		mazub.setWorld(this);
	}
	
	public int getVisibleWindowWidth() {
		return visibleWindowWidth;
	}
	
	public int getVisibleWindowHeight() {
		return visibleWindowHeight;
	}
	
	public int[] getMazubMiddlePosition() {
		
		int[] position = new int[2];
		position[0] = (int) mazub.getPosition().getCoordinateX();
		position[1] = (int) mazub.getPosition().getCoordinateY();
		
		return position;
	}
	
	public boolean didPlayerWin() {	
		
		int[] btmlft = getBottomLeftPixelOfTile(mazub.getPosition().getCoordinateX(), mazub.getPosition().getCoordinateY()); 
		
		if ((btmlft[0] == targetTileX * tileSize && btmlft[1] == targetTileY * tileSize) 
				|| (btmlft[0] + mazub.getWidth() == targetTileX * tileSize && btmlft[1] == targetTileY * tileSize)) {
			return true;
		}
		return false;
	}
	
	public void addPlant(Plant plant) {
		worldVegetation.add(plant);
	}

	public Collection<Plant> getVegetation() {
		return worldVegetation;
	}
	
	//private list<Plant> worldVegetation = new ArrayList<Plant>(); 
	
	public void addShark(Shark shark) {
		worldSharks.add(shark);
	}
	
	public Collection<Shark> getSharks() {
		return worldSharks;
	}
	
	public void addSlime(Slime slime) {
		worldSlimes.add(slime);
	}
	
	public Collection<Slime> getSlimes() {
		return worldSlimes;
	}	

	
	private void addTile(Tile tile) {
		worldTiles.add(tile);
	}
	


	public boolean isGameOver() {
		return mazub.isDead();
	}
	
	public Collection<Tile> getTiles() {
		return this.worldTiles;
	}
	
//	public Iterator iterator(Collection collection) {
//		return new Iterator() {
//
//			public boolean hasNext() {
//				return currentIterator.hasNext();
//			}
//
//			public Object next() throws NoSuchElementException {
//				if (!hasNext()) 
//					throw new NoSuchElementException();
//				else
//					return currentIterator.next();
//			}
//
//			private Iterator currentIterator = collection.iterator();
//
//		};
//	}


//	public void removePlant(Actor actor, Collection<? extends Actor> collection) {
//		
//		Collection<? extends Actor> newCollection;
//		if (actor instanceof Plant) {
//			newCollection = new ArrayList<Plant>();
//		} else if (actor instanceof Shark) {
//			newCollection = new ArrayList<Shark>();
//		} else if (actor instanceof Slime) {
//			newCollection = new ArrayList<Slime>();
//		}
//		
//		if (Plant.class.isInstance(actor)) {
//			newCollection = new ArrayList<Plant>();
//			((Plant) actor).setWorld(null);
//		}
//		else if (Plant.class.isInstance(actor)) {
//			newCollection = new ArrayList<Plant>();
//			((Plant) actor).setWorld(null);
//		}
//		
//		
//		for (Actor anObject : collection) {
//			if (anObject.hashCode() != actor.hashCode()) {
//				newCollection.add(anObject);
//			} else
//				anObject.setWorld(null);
//		}
//		
//		collection = newCollection;
	
	public void removePlant(Plant plant) {
 		
		Collection<Plant> newVegetation = new ArrayList<Plant>();
		
		for (Plant anObject : worldVegetation) {
			if (anObject.hashCode() != plant.hashCode()) {
				newVegetation.add(anObject);
			} else
				anObject.setWorld(null);
		}

		worldVegetation = newVegetation;
		
	}
	
	public void removeShark(Shark shark) {
 		
		Collection<Shark> newSharks = new ArrayList<Shark>();
		
		for (Shark anObject : worldSharks) {
			if (anObject.hashCode() != shark.hashCode()) {
				newSharks.add(anObject);
			} else
				anObject.setWorld(null);
		}

		worldSharks = newSharks;
		
	}
	
	public void removeSlime(Slime slime) {
 		
		Collection<Slime> newSlimes = new ArrayList<Slime>();
		
		for (Slime anObject : worldSlimes) {
			if (anObject.hashCode() != slime.hashCode()) {
				newSlimes.add(anObject);
			} else
				anObject.setWorld(null);
		}

		worldSlimes = newSlimes;
		
	}

	public void addBuzam(Buzam buzam) {
		this.buzam = buzam;
	}

	protected Mazub getMazub() {
		return this.mazub;
	}		
	
	protected Buzam getBuzam() {
		return this.buzam;
	}

}


