package uk.ac.ed.inf.powergrab;

import java.util.Iterator;

/**
 * The stateless drone class,
 * inherits the abstract drone,
 * and defines strategy of the stateless drone. 
 * <p>
 * The stateless drone extends the abstract drone class and implemented Geography interface.
 * Strategy of the stateless drone is go to next position randomly,
 * but if it detected a nearby positive charger,
 * fly to that charger.
 * If it detected a negative charger, 
 * avoid it unless no other positions to go.
 *  
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.1
 * @since       0.2
 */
public class Stateless extends Drone {
	// No more states needed for stateless drone
	
	/**
	 * Constructs a stateless drone with given position, seed, map and tracer.
	 *
	 * @param startPosition the initial position of this drone.
	 * @param seed the seed used to initialize the pseudorandom number generator.
	 * @param map the game map
	 * @param tracer used to record the flight trace and states.
	 */
	public Stateless(Position startPosition, long seed, Map map, LineDrawer tracer) {
		// Using the abstract drone's constructor
		super(startPosition, seed, map, tracer);
	}
	
	/**
	 * Decide which position to go next using random strategy.
	 * <p>
	 * The stateless drone randomly iterates the positions of 16 directions.
	 * Once find a position connecting to a positive charger, 
	 * return this position.
	 * If no position connects to positive chargers,
	 * return the first safe position.
	 * If all positions are dangerous,
	 * return the position with least damage.
	 * Any return position will within the play area.
	 * 
	 * @return position the drone decides to go.
	 */
	@Override
	Position findNextPosition() {
		Position position;
		Position nextPosition = null;
		
		// A random shuffled direction list iterator
		Iterator<Direction> iterator = Direction.randomDirections(rand);
		
		// Iterates 16 directions
		while (iterator.hasNext()) {
			position = myPosition.nextPosition(iterator.next());
			if (!position.inPlayArea()) {
				continue;	// Not in the play area
			}
			if (isPositive(position)) {
				return position;	// a positive position found, return it!
			}
			if (!dangerous(position)) {
				nextPosition = position;// a safe position found, record it 
				continue;
			} 
			if (nextPosition == null) {// no safe position for now, record this whatever
				nextPosition = position;
				continue;
			}
			// If both the recorded position and the current position are dangerous,
			// record the one has less damage to coins.
			if (dangerous(position) && dangerous(nextPosition)) {
				if (map.connectedCharger(nextPosition).coins < map.connectedCharger(position).coins) {
					nextPosition = position;
				}
			}
		}
		// return the recorded position
		return nextPosition;
	}

	
}
