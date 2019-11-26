/**
 * 
 */
package uk.ac.ed.inf.powergrab;

import java.util.Random;

/**
 * @author Tai Yintao
 *
 */
public abstract class Drone implements Geography{
	//TODO add method to output logs
	Position myPosition;
	private int stepLeft = 250;
	Random rand;
	LineDrawer trace;
	private double coins = 0;
	private double power = 250;
	//TODO use method to get coins and power
	
	Map map;
	LineDrawer tracer;
	
	public double currentCoins() {
		return coins;
	}
	
	public double currentPower() {
		return power;
	}
	
	public Drone(Position startPosition, long seed, Map map, LineDrawer tracer) {
		this.map = map;
		this.tracer = tracer;
		myPosition = new Position(startPosition);
		rand = new Random(seed);
		updatePowerAndCoinsAndTrace();
	}
	
	private void updatePowerAndCoinsAndTrace() {
		Charger charger = map.connectedCharger(myPosition);
		if (charger != null) {
			coins += charger.coins;
			power += charger.power;
			charger.coins = 0;
			charger.power = 0;
			if (coins < 0) coins = 0;
			if (power < 0) power = 0;
		}
		tracer.recordDrone(this);
	}
	
	Position goNextPosition() {
		Position nextPosition = findNextPosition();
		myPosition = nextPosition;
		stepLeft--;
		power -= 1.25;
		updatePowerAndCoinsAndTrace();
		return nextPosition;
		
	};
	
	abstract Position findNextPosition();
	
	
	public boolean hasNext() {
		if (stepLeft > 0 && power >= 1.25) return true;
		else {
			return false;
		}
	}
	
	boolean dangerous(Position nextPosition) {
		Charger charger = map.connectedCharger(nextPosition);
		if (charger == null) return false;
		else if (charger.power >= 0) return false;
		return true;
	}
	
	boolean isPositive(Position position) {
		Charger charger = map.connectedCharger(position);
		if (charger == null) return false;
		if (charger.power > 0 && charger.coins > 0) return true;
		else return false;
	}
	
	boolean isPositive(Charger charger) {
		if (charger == null) return false;
		if (charger.power > 0 && charger.coins > 0) return true;
		else return false;
	}
	
	@Override
	public double latitude() {
		return myPosition.latitude();
	}

	@Override
	public double longitude() {
		return myPosition.longitude();
	}
		
}
