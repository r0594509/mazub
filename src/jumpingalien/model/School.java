package jumpingalien.model;

import java.util.ArrayList;
import java.util.Collection;

public class School {

	//not sure if needed as there appear to be no arguments passed along when creating a school
	public School() {
		
	}
	
	private final Collection<Slime> schoolSlimes = new ArrayList<Slime>();
	
	public void addToSchool(Slime slime) {
		schoolSlimes.add(slime);
	}
	
}
