package jumpingalien.model;

public enum Month {
	
	JULY (7, "july"),
	AOÛT (8, "aout");
	
	private int number;
	private String name;
	
	Month(int number, String name) {
		this.number = number;
		this.name = name;
	}
}
