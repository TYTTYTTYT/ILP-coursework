package uk.ac.ed.inf.powergrab;

/**
 * The statefull drone class,
 * extends the stateless drone, 
 * and defines strategy of the stateless drone. 
 * <p>
 * The stateful drone adapts a greedy strategy.
 * It always try to get the nearest positive charger.
 * If there are any negative charger block the drone's way,
 * the stateful drone will bypass negative chargers in clockwise or anti-clockwise direction.
 * If one direction is not useful or the drone meet the border,
 * it will try another direction. 
 *  
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.4
 * @since       0.3
 */
public class Stateful extends Stateless {
	// TODO add reset method to the stateful drone
	/**
	 * the current bypassing strategy, "clockwise" or "anti-clockwise"
	 */
	private String strategy;
	
	/**
	 * the last position of this drone
	 */
	private Position lastPosition;

	/**
	 * Constructs a stateful drone with given position, seed, map and tracer.
	 *
	 * @param startPosition the initial position of this drone.
	 * @param seed the seed used to initialize the pseudorandom number generator.
	 * @param map the game map
	 * @param tracer used to record the flight trace and states.
	 */
	public Stateful(Position startPosition, long seed, Map map, LineDrawer tracer) {
		super(startPosition, seed, map, tracer);
		lastPosition = new Position(myPosition);
		// Randomly choose the initial bypassing strategy
		if (rand.nextBoolean()) {
			strategy = "clockwise";
		} else {
			strategy = "anti-clockwise";
		}
	}

	/**
	 * Change the bypassing strategy
	 *
	 */
	private void changeStrategy() {
		if (strategy == "clockwise") {
			strategy = "anti-clockwise";
		} else {
			strategy = "clockwise";
		}
	}

	/**
	 * Move this drone to the next position.
	 * The drone decides which direction to go.
	 * <p>
	 * Change the drone's position,
	 * reduce the power consumption and remaining steps and update the last position.
	 * Each step consumes 1.25 unit power.
	 * 
	 * @return the position after move.
	 */
	@Override
	public Position goNextPosition() {
		Position pos = new Position(myPosition);
		super.goNextPosition();
		// Because the method findNextPosition invoked in goNextPosition will make use
		// of the lastPositoin, so update it after goNextPosition.
		lastPosition = pos;
		return myPosition;
	}

	/**
	 * Decide which position to go next using greedy strategy.
	 * <p>
	 * If the drone can connect to a positive charger within 1 step,
	 * return that position connects to the charger.
	 * Otherwise the drone will go with the direction to the nearest positive charger.
	 * If the direction lead to a negative charger,
	 * rotate the direction until it lead to a safe or positive position.
	 * 
	 * @return position the drone decides to go.
	 */
	@Override
	Position findNextPosition() {
		// In the cases of no safe way to go or only one step can connect a positive charger,
		// using the stateless strategy.
		Position statelessNext = super.findNextPosition();
		if (isPositive(statelessNext) || dangerous(statelessNext)) return statelessNext;
		
		// Find the nearest positive charger
		Charger nextPositive = map.nearestPositiveCharger(myPosition);
		Position nextPosition;
		
		// If no positive charger anymore, return to the last position for safe.
		if (nextPositive == null) {
			return lastPosition;
		}
		
		// Calculate the angle to the nearest positive charger.
		double bestAngle = myPosition.angle(nextPositive);
		double angle = bestAngle;
		
		for (int i = 0; i < 17; i++) {	// Try all possible direction by different strategy
			nextPosition = myPosition.nextPosition(Direction.angleToDirection(angle));
			if (!nextPosition.inPlayArea() || nextPosition.equals(lastPosition)) {
				// The next position is not in play area or will go back to last position,
				// try another bypassing strategy
				changeStrategy();
				angle = bestAngle;
				continue;
			}
			// Next position is dangerous, rotate the angle.
			if (dangerous(nextPosition)) {
				if (strategy == "clockwise")
					angle -= Math.PI / 8;
				else angle += Math.PI / 8;
			} else return nextPosition;
		}
		
		return statelessNext;

	}

}
