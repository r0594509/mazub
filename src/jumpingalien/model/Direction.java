package jumpingalien.model;

public enum Direction {

	NONE ("none", 0),
	
	LEFT ("left", -1),
	
	RIGHT ("right", 1);
	
	private final String direction;
	private final int multiplier;
	
	Direction (String direction, int multiplier) {
		this.direction = direction;
		this.multiplier = multiplier;
	}
	
	protected String getDirection() {
		return direction;
	}
	
	protected int getMultiplier() {
		return multiplier;
	}	
}
