package uk.ac.ed.inf.powergrab;

import java.util.Iterator;

public class Statefull extends Stateless {
	
	private String strategy = "clockwise";
	private double pointer;
	private double lastBestAngle;


	public Statefull(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
	}
	
	private void changeStrategy() {
		if (strategy == "clockwise") {
			strategy = "anti-clockwise";
		} else {
			strategy = "clockwise";
		}
	}

	@Override
	Position findNextPosition() {
		Charger nextPositive = map.nearestPositiveCharger(myPosition);
		Position nextPosition;
		
		if (nextPositive == null) {
			return super.findNextPosition();
		}
		
		double bestAngle = myPosition.angle(nextPositive.position);
		double angle = bestAngle;
		
		nextPosition = super.findNextPosition();
		if (isPositivePosition(nextPosition) || dangerous(nextPosition))
			return nextPosition;
		
		
		
		while (true) {
			nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
			if (!nextPosition.inPlayArea()) {
				changeStrategy();
				angle = bestAngle;
				continue;
			}
			if (dangerous(nextPosition)) {
				if (strategy == "clockwise")
					angle -= Math.PI / 8;
				else angle += Math.PI / 8;			
			} else return nextPosition;
		}

	}

}
