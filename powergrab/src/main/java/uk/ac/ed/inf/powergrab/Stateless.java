package uk.ac.ed.inf.powergrab;

import java.util.Iterator;

public class Stateless extends Drone {
	
	
	public Stateless(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
	}
	
	@Override
	Position findNextPosition() {
		Position position;
		Position nextPosition = null;
		
		Iterator<Direction> iterator = Direction.randomDirections(rand);
		
		while (iterator.hasNext()) {
			position = myPosition.nextPosition(iterator.next());
			if (!position.inPlayArea()) {
				continue;
			}
			if (isPositive(position)) {
				return position;
			}
			if (!dangerous(position)) {
				nextPosition = position;
				continue;
			} 
			if (nextPosition == null) {
				nextPosition = position;
				continue;
			}
			if (dangerous(position) && dangerous(nextPosition)) {
				if (map.connectedCharger(nextPosition).coins < map.connectedCharger(position).coins) {
					nextPosition = position;
				}
			}
		}
		
		return nextPosition;
	}

	
}
