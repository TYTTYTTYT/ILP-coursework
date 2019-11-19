package uk.ac.ed.inf.powergrab;

public class Statefull extends Stateless {

	public Statefull(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
	}

	@Override
	Position goNextPosition() {
		if (map.nearestPositiveCharger(myPosition) == null) {
			return super.goNextPosition();
		}
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
		Charger nextPositive = map.nearestCharger(myPosition);
		Position nextPosition;
		if (nextPositive == null) {
			return super.findNextPosition();
		}
		double angle = myPosition.angle(nextPositive.position);
		while(true) {
			nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
			if (dangerous(nextPosition)) {
				angle -= Math.PI / 8;
			} else return nextPosition;
			// TODO solve trap
		}
	}

}
