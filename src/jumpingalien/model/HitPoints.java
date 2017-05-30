package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class HitPoints {
	
	private final int nbHitPoints;
	
	private final int healPoints = 50;
	
	private final int enemyDamagePoints = 50;
	private final int waterDamagePoints = 2;
	private final int magmaDamagePoints = 50;
	
	private static HitPoints maxHitPoints = new HitPoints(500);
	
	public HitPoints(int nbHitPoints) {
		this.nbHitPoints = nbHitPoints;
	}
	
	public int getNbHitPoints() {
		return this.nbHitPoints;
	}
	
	public int getMaxHitPoints() {
		return maxHitPoints.getNbHitPoints();
	}
	
	public HitPoints heal() {
		if (! hasMaximumHitPoints(getNbHitPoints() + healPoints)) {
			HitPoints newNbHitPoints = new HitPoints(getNbHitPoints() + healPoints);
			return newNbHitPoints;
		} else
			return HitPoints.maxHitPoints;
	}
	
	private boolean hasMaximumHitPoints(int hitPoints) {
		return hitPoints >= 500;
	}
	
	public HitPoints takeEnemyDamage() {
		HitPoints newNbHitPoints = new HitPoints(getNbHitPoints() - enemyDamagePoints);
		return newNbHitPoints;
	}
	
	public HitPoints takeWaterDamage() {
		HitPoints newNbHitPoints = new HitPoints(getNbHitPoints() - waterDamagePoints);
		return newNbHitPoints;
	}
	
	public HitPoints takeMagmaDamage() {
		HitPoints newNbHitPoints = new HitPoints(getNbHitPoints() - magmaDamagePoints);
		return newNbHitPoints;
	}

}
