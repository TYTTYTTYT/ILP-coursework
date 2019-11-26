package uk.ac.ed.inf.powergrab;

/**
 * The charger class, an implementation of Geography interface with coins and power attributes.
 * <p>
 * A charger object is a geographic object with coins and power attributes.
 * It represents a charger on the map. Once it initialized,
 * the location of it will not change.
 *  
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.0
 * @since       0.3
 */
public class Charger implements Geography {
	/**
	 * Coins of this charger.
	 */
	double coins;
	
	/**
	 * Power of this charger.
	 */
	double power;
	
	/**
	 * the position of this charger.
	 */
	private Position myPosition;
	
	/**
	 * Constructs a charger with given latitude, longitude, coins and power.
	 * <p>
	 * This is the only constructor of a charger, the latitude and longitude are given
	 * by parameters and stored in a Position object. The charger also have coins and 
	 * power attributes. 
	 *
	 * @param latitude the latitude of the new charger
	 * @param longitude the longitude of the new charger
	 * @param coins the initial coins of the new charger
	 * @param power the initial power of the new charger
	 */
	public Charger(double latitude, double longitude, double coins, double power) {
		this.coins = coins;
		this.power = power;
		this.myPosition = new Position(latitude, longitude);
	}

	/**
	 * Return the latitude of this charger.
	 *
	 * @return the latitude.
	 */
	@Override
	public double latitude() {
		return myPosition.latitude();
	}

	/**
	 * Return the longitude of this charger.
	 *
	 * @return the longitude.
	 */
	@Override
	public double longitude() {
		return myPosition.longitude();
	}
}
