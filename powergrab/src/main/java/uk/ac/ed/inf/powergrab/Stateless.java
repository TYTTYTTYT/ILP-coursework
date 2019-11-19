package uk.ac.ed.inf.powergrab;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Stateless extends Drone {
	
	
	public Stateless(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
	}
	
	@Override
	Position goNextPosition() {
		if (!hasNext()) return null;
		Position nextPosition = findNextPosition();
		myPosition = nextPosition;
		stepsLeft--;
		power -= 1.25;
		updatePowerAndCoinsAndTrace();
		return nextPosition;
	}
	
	@Override
	Position findNextPosition() {
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
			if (nextCharger != null && nextCharger.power == 0 && myPosition.nextPosition(direction).inPlayArea()) {
				return myPosition.nextPosition(direction);
			}
		}
		for(Direction d : Direction.values()) {
			directions.add(d);
		}
		while(!directions.isEmpty()) {
			index = randomGenerator.nextInt(directions.size());
			direction = directions.get(index);
			directions.remove(index);
			nextCharger = map.availableCharger(myPosition.nextPosition(direction));
			if (nextCharger.position.inPlayArea())
				return myPosition.nextPosition(direction);
		}
		return null;
	}
	
}
