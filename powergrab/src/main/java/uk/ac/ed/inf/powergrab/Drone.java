package uk.ac.ed.inf.powergrab;

import java.util.Random;

/**
 * The abstract drone class, provides common operations and defines common attributes of a drone. 
 * <p>
 * A Drone object is a geographic object implements the geography interface.
 * The drone will fly on a map and interactive with chargers on this map automatically.
 * Users can command this drone to fly to next position,
 * the drone decides which position to go by itself.
 * Classes who inheriting this class should define strategy in the findNextPositioin method.
 * If provides the same seed and map to a drone, the drone should have the same operations.
 *  
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.3
 * @since       0.2
 */
public abstract class Drone implements Geography{
	/**
	 * The position of this drone.
	 */
	Position myPosition;
	
	/**
	 * The left steps of this drone.
	 */
	private int stepLeft;
	
	/**
	 * The pseudorandom number generator of this drone.
	 */
	Random rand;
	
	/**
	 * Coins of this drone.
	 */
	private float coins;
	
	/**
	 * Power of this drone.
	 */
	private float power;
	
	/**
	 * The game map the drone is flying within.
	 */
	Map map;
	
	/**
	 * The LineDrawer tracer used to record the trace of this drone.
	 */
	private LineDrawer tracer;
	
	{
		// The drone carries no coins at beginning.
		coins = 0f;
		// Initial power is 250.
		power = 250f;
		// The drone can only fly 250 steps in total.
		stepLeft = 250;
	}
	
	/**
	 * Constructs a drone with given position, seed, map and tracer.
	 *
	 * @param startPosition the initial position of this drone.
	 * @param seed the seed used to initialize the pseudorandom number generator.
	 * @param map the game map
	 * @param tracer used to record the flight trace and states.
	 */
	public Drone(Position startPosition, long seed, Map map, LineDrawer tracer) {
		this.map = map;
		this.tracer = tracer;
		myPosition = new Position(startPosition);
		rand = new Random(seed);
		tracer.recordDrone(this);
	}
	
	/**
	 * Update the map, drone and the trace respect to the current position.
	 * <p>
	 * If the drone is connected to a charger,
	 * all coins and power in that charger will be transfered to the drone.
	 * In the meantime set the charger coins and power to 0;
	 * The drone can not carry negative coins or power,
	 * if they are negative set them to 0.
	 * Each time this method is invoked,
	 * the tracer will record the current position and states of this drone.
	 */
	private void updatePowerAndCoinsAndTrace() {
		Charger charger = map.connectedCharger(myPosition);
		// If the drone connected to a charger.
		if (charger != null) {
			coins += charger.coins;
			power += charger.power;
			charger.coins = 0;
			charger.power = 0;
			// Drone can not carry negative coins and power.
			if (coins < 0) coins = 0;
			if (power < 0) power = 0;
		}
		// Record the position and states of the drone.
		tracer.recordDrone(this);
	}
	
	/**
	 * Move this drone to the next position.
	 * The drone decides which direction to go.
	 * <p>
	 * Change the drone's position,
	 * reduce the power consumption and remaining steps.
	 * Each step consumes 1.25 unit power.
	 * 
	 * @return the position after move.
	 */
	public Position goNextPosition() {
		// Change the drone's position.
		Position nextPosition = findNextPosition();
		myPosition = nextPosition;
		// Reduce remaining steps and power
		stepLeft--;
		power -= 1.25f;
		// The drone may connected to a charger, check this after each move. 
		updatePowerAndCoinsAndTrace();
		return nextPosition;
	};
	
	/**
	 * Decide which position to go next.
	 * <p>
	 * This abstract function should be implemented by inheriting classes depending on what strategy it adopt.
	 * 
	 * @return the position the drone decides to go.
	 */
	abstract Position findNextPosition();
	
	/**
	 * Determine whether the drone has next step to go.
	 * <p>
	 * If the drone has enough power and has steps remaining return true,
	 * otherwise return false 
	 * 
	 * @return true means has next step, false means no next step.
	 */
	public boolean hasNext() {
		if (stepLeft > 0 && power >= 1.25) return true;
		else {
			return false;
		}
	}
	
	/**
	 * Determine whether the given position will connect to a negative charger.
	 * 
	 * @param pos the position
	 * @return true means dangerous position, false means safe position.
	 */
	boolean dangerous(Position pos) {
		Charger charger = map.connectedCharger(pos);
		// No connected charger of this position, also safe.
		if (charger == null) return false;
		else if (charger.power >= 0) return false;
		return true;
	}
	
	/**
	 * Determine whether the given position will connect to a positive charger.
	 * 
	 * @param position the position
	 * @return true means positive position, false means no positive charger connected.
	 */
	boolean isPositive(Position position) {
		Charger charger = map.connectedCharger(position);
		// No connected charger
		if (charger == null) return false;
		if (charger.power > 0 && charger.coins > 0) return true;
		else return false;
	}
	
	/**
	 * Determine whether the charger has positive coins and power.
	 * 
	 * @param charger the charger
	 * @return true means positive charger, false means negative or empty charger.
	 */
	boolean isPositive(Charger charger) {
		if (charger == null) return false;
		// both of the coins and power should greater than 0.
		if (charger.power > 0 && charger.coins > 0) return true;
		else return false;
	}
	
	/**
	 * Return the latitude of this drone.
	 *
	 * @return the latitude.
	 */
	@Override
	public double latitude() {
		return myPosition.latitude();
	}

	/**
	 * Return the longitude of this drone.
	 *
	 * @return the longitude.
	 */
	@Override
	public double longitude() {
		return myPosition.longitude();
	}
	
	/**
	 * Return the coins of this drone.
	 *
	 * @return the coins.
	 */
	public float currentCoins() {
		return coins;
	}
	
	/**
	 * Return the power of this drone.
	 *
	 * @return the power.
	 */
	public float currentPower() {
		return power;
	}
}
