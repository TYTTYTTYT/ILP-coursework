package uk.ac.ed.inf.powergrab;
// TODO add method to reset drone, so when drone have bad performance it can begin with different seed
public class Statefull extends Stateless {
	
	private String strategy = "clockwise";

	public Statefull(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
		if (rand.nextBoolean()) {
			strategy = "clockwise";
		}else {
			strategy = "anti-clockwise";
		}
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
			return lastPosition;
		}
		
		double bestAngle = myPosition.angle(nextPositive.position);
		double angle = bestAngle;
		
		for (int i = 0; i < 16; i++) {
			nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
			if (!nextPosition.inPlayArea() || nextPosition.same(lastPosition)) {
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
		
		return super.findNextPosition();

	}

}
