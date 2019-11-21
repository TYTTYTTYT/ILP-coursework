package uk.ac.ed.inf.powergrab;

public class Statefull extends Stateless {
	
	String strategy = "clockwise";


	public Statefull(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
	}

	@Override
	Position goNextPosition() {
		if (map.nearestPositiveCharger(myPosition) == null) {
			lastPosition = myPosition;
			return super.goNextPosition();
		}
		if (!hasNext()) return null;
		Position nextPosition = findNextPosition();
		if (nextPosition.same(lastPosition)) {
			return super.goNextPosition();
		}
		lastPosition = myPosition;
		myPosition = nextPosition;
		stepsLeft--;
		power -= 1.25;
		updatePowerAndCoinsAndTrace();
		return nextPosition;
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
		
		for (int i = 0; i < 16; i ++) {
			if (nextPositive(myPosition.nextPosition(Direction.values()[i])) && myPosition.nextPosition(Direction.values()[i]).inPlayArea())
				return myPosition.nextPosition(Direction.values()[i]);
		}
		
		if (strategy == "clockwise") {
			for (int i = 0; i < 15; i++) {
				nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
				if (!nextPosition.inPlayArea() || nextPosition.same(lastPosition)) {
					strategy = "anti-clockwise";
					break;
				}
				if (dangerous(nextPosition)) {
					angle += Math.PI / 8;
				} else return nextPosition;
				// TODO solve trap
			}
		}
		
		angle = bestAngle;
		if (strategy == "anti-clockwise") {
			for (int i = 0; i < 15; i++) {
				nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
				if (!nextPosition.inPlayArea() || nextPosition.same(lastPosition)) {
					strategy = "clockwise";
					break;
				}
				if (dangerous(nextPosition) || !nextPosition.inPlayArea()) {
					angle -= Math.PI / 8;
				} else return nextPosition;
				// TODO solve trap
			}
		}
		return super.findNextPosition();
	}

}
