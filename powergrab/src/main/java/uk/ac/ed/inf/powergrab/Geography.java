package uk.ac.ed.inf.powergrab;

/**
 * Provides methods to get or calculate geographic information.
 * <p>
 * Any class implemented this interface means its object has geographic information.
 * Geographic information includes latitude, longitude, the distance to another object
 * and the angle to another object.
 *  
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.1
 * @since       0.3
 */
interface Geography {
	
	/**
	 * Return the latitude of this geographic object.
	 *
	 * @return the latitude.
	 */
	public double latitude();
	
	/**
	 * Return the longitude of this geographic object.
	 *
	 * @return the longitude.
	 */
	public double longitude();
	
	/**
	 * Calculate the distance to another geographic object.
	 * <p>
	 * The distance is the Pythagorean distance calculated from latitude and longitude.
	 * It's an approximated distance, assuming the earth as a plane and latitude and longitude
	 * are equally distributed.
	 *
	 * @param g the geographic object to calculate the distance of.
	 * @return the distance.
	 */
	default double distance(Geography g) {
		// Calculate the Pythagorean distance, regards latitude and longitude as two coordinates.
		double distance = Math.sqrt(Math.pow(this.latitude() - g.latitude(), 2) + 
				Math.pow(this.longitude() - g.longitude(), 2));
		return distance;
	}
	
	/**
	 * Calculate the distance to a position with given latitude and longitude
	 * <p>
	 * The distance is the Pythagorean distance calculated from latitude and longitude.
	 * It's an approximated distance, assuming the earth as a plane and latitude and longitude
	 * are equally distributed.
	 *
	 * @param latitude the given latitude
	 * @param longitude the given longitude
	 * @return the distance.
	 */
	default double distance(double latitude, double longitude) {
		// Calculate the Pythagorean distance, regards latitude and longitude as two coordinates.
		double distance = Math.sqrt(Math.pow(this.latitude() - latitude, 2) + 
						Math.pow(this.longitude() - longitude, 2));
		return distance;
	}
	
	/**
	 * Calculate the angle to another geographic object.
	 * <p>
	 * The angle from North to some compass point in clockwise direction,
	 * expressed in radinans.
	 * 
	 * @param g the geographic object to calculate the angle of.
	 * @return the angle expressed in radians.s
	 */
	default double angle(Geography g) {
		// Calculate the cosine value of this angle.
		double cos = (g.latitude() - latitude()) / Math.sqrt(Math.pow(g.latitude() - latitude(), 2)
				+ Math.pow(g.longitude() - longitude(), 2));
		
		// Use the inverse cosine function to calculate angle from the cosine value.
		double acos = Math.acos(cos);
		
		// Determine which quadrant the angle locates.
		if (longitude() > g.longitude())
			acos = - acos;
		return acos;
	}
	
}
