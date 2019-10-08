package uk.ac.ed.inf.powergrab;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Stateless {
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
	private Map map;
	private LineDrawer tracer;
	
	public Stateless(Position startPosition, long seed, Map map, LineDrawer tracer) {
		this.map = map;
		myPosition = new Position(startPosition);
		randomGenerator = new Random(seed);
		this.tracer = tracer;
		updatePowerAndCoinsAndTrace();
	}
	
	public boolean hasNext() {
		if (stepsLeft > 0 && power >= 1.25) return true;
		else {
			System.out.println(String.valueOf(250 - stepsLeft) + " steps in totall");
			return false;
		}
	}
	
	public Position goNextPosition() {
		if (!hasNext()) return null;
		Position nextPosition = findNextPosition();
		myPosition = nextPosition;
		stepsLeft--;
		power -= 1.25;
		updatePowerAndCoinsAndTrace();
		return nextPosition;
	}
	
	private void updatePowerAndCoinsAndTrace() {
		Charger charger = map.availableCharger(myPosition);
		tracer.addNextPoint(myPosition);
		if (charger != null) {
			coins += charger.coins;
			power += charger.power;
			charger.coins = 0;
			charger.power = 0;
			if (coins < 0) coins = 0;
			tracer.addNextPoint(charger.position);
			tracer.addNextPoint(myPosition);
		}
	}
	
	private Position findNextPosition() {
		//TODO situation when surrounded by red charger
		Direction direction;
		int index;
		Charger nextCharger;
		
		List<Direction> directions = new LinkedList<Direction>();
		for(Direction d : Direction.values()) {
			directions.add(d);
		}
		while(!directions.isEmpty()) {
			index = randomGenerator.nextInt(directions.size());
			direction = directions.get(index);
			directions.remove(index);
			nextCharger = map.availableCharger(myPosition.nextPosition(direction));
			if (nextCharger != null && nextCharger.power > 0 && myPosition.nextPosition(direction).inPlayArea())
				return myPosition.nextPosition(direction);
		}
		for(Direction d : Direction.values()) {
			directions.add(d);
		}
		while(!directions.isEmpty()) {
			index = randomGenerator.nextInt(directions.size());
			direction = directions.get(index);
			directions.remove(index);
			nextCharger = map.availableCharger(myPosition.nextPosition(direction));
			if (nextCharger == null && myPosition.nextPosition(direction).inPlayArea())
				return myPosition.nextPosition(direction);
		}
		return null;
	}
	
}
