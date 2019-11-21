/**
 * 
 */
package uk.ac.ed.inf.powergrab;

import java.util.Random;

/**
 * @author Tai Yintao
 *
 */
public abstract class Drone {
	//TODO add method to output logs
	Position myPosition;
	int stepsLeft = 250;
	//TODO change stepsleft to stepleft
	Random randomGenerator;
	LineDrawer trace;
	//TODO Determin if private
	double coins = 0;
	double power = 250;
	//TODO use method to get coins and power
	Map map;
	LineDrawer tracer;
	Position lastPosition;
	
	public Drone(Position startPosition, long seed, Map map, LineDrawer tracer) {
		this.map = map;
		this.tracer = tracer;
		myPosition = new Position(startPosition);
		lastPosition = myPosition;
		randomGenerator = new Random(seed);
		updatePowerAndCoinsAndTrace();
	}
	
	void updatePowerAndCoinsAndTrace() {
		Charger charger = map.availableCharger(myPosition);
		tracer.addNextPoint(myPosition);
		if (charger != null) {
			coins += charger.coins;
			power += charger.power;
			charger.coins = 0;
			charger.power = 0;
			if (coins < 0) coins = 0;
		}
	}
	
	abstract Position goNextPosition();
	abstract Position findNextPosition();
	
	
	boolean hasNext() {
		if (stepsLeft > 0 && power >= 1.25) return true;
		else {
			return false;
		}
	}
	
	boolean dangerous(Position nextPosition) {
		Charger charger = map.availableCharger(nextPosition);
		if (charger == null) return false;
		else if (charger.power >= 0) return false;
		return true;
	}
	
	boolean nextPositive(Position nextPosition) {
		Charger charger = map.availableCharger(nextPosition);
		if (charger == null) return false;
		if (charger.power > 0 && charger.coins > 0) return true;
		else return false;
	}
	
}
