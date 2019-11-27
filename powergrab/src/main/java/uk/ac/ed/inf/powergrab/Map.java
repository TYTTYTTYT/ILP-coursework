package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;

/**
* The game map stores all charger information and provides methods to query them efficiently.
* <p>
* A map can be constructed from a GeoJson FeatureCollection.
* users can provides a featureCollection or a date,
* the map can automatically download it according the date.
* Different query method also defined in this class,
* drones can express their strategies intuitively by them. 
*
* @author  Tai Yintao s1891075@ed.ac.uk
* @version 1.1
* @since   0.1
*/
public class Map {
	
	/**
	 * The GeoJson FeatureCollection used to generate or reset a map.
	 */
	FeatureCollection rawFeatures;
	
	/**
	 * A list stores all charger objects within this map.
	 */
	private List<Charger> chargers;

	/**
	 * For convenience to calculate distance, storing all chargers coordinates here.
	 */
	private double[][] coordinates;
	
	{
		rawFeatures = null;
		coordinates = new double[50][2];
		chargers = new ArrayList<Charger>();
	}
	
	/**
	   * Constructs a map from a GeoJson FeatureCollection.
	   * 
	   * @param featureMap a GeoJson FeatureCollection with 50 chargers information.
	   */
	public Map(FeatureCollection featureMap) {
		fromFeatures(featureMap);
	}
	
	/**
	   * Constructs a map with a given date.
	   * <p>
	   * The constructor download the map GeoJson file from http://homepages.inf.ed.ac.uk/stg/powergrab,
	   * then analyze the file and initialize chargers.
	   * 
	   * @param year the year of the map.
	   * @param month the month of the map.
	   * @param day the date of the map in a month.
	   * @throws java.io.IOException throw the error if anything wrong.
	   */
	public Map(int year, int month, int day) throws IOException{
		// Covert the integer date to strings
		String yearS = String.valueOf(year);
		String mounthS = String.valueOf(month);
		String dayS = String.valueOf(day);
		if (month < 10) {
			mounthS = "0" + mounthS;
		}
		if (day < 10) {
			dayS = "0" + dayS;
		}
		// Generate the URL string used to download the map features.
        String mapURLString = "http://homepages.inf.ed.ac.uk/stg/powergrab/" +
        					yearS + "/" +
        					mounthS + "/" +
        					dayS + "/" +
        					"powergrabmap.geojson";
        
        // Establish the http connection and download the map
        try {
        	URL mapURL = new URL(mapURLString);
        	HttpURLConnection conn = (HttpURLConnection) mapURL.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000); 
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			InputStream buffer = conn.getInputStream();
			// Read the input stream and store in a string
			String mapSource = reader(buffer);
			// Convert the string to a GeoJson FeatureCollection
	        FeatureCollection fc = FeatureCollection.fromJson(mapSource);
	        // Initialize the map from the FeatureCollection.
	        fromFeatures(fc);
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	   * Find the nearest positive charger of a position.
	   * 
	   * @param pos the position we are currently considering.
	   * @return return the nearest positive charger, if no positive charger, return null.
	   */
	public Charger nearestPositiveCharger(Position pos) {
		double distance = 300;
		double curDistance = 0;
		int index = -1;
		// iterate all 50 chargers
		for (int i = 0; i < 50; i ++) {
			if (chargers.get(i).power > 0 && chargers.get(i).power > 0) {
				curDistance = pos.distance(coordinates[i][0], coordinates[i][1]);
			} else continue;
			if (curDistance < distance) {
				distance = curDistance;
				index = i;
			}
		}
		if (index == -1) return null;	// No positive chargers now
		return chargers.get(index);
	}
	
	/**
	   * Find the nearest charger of a position.
	   * 
	   * @param pos the position we are currently considering.
	   * @return return the nearest charger.
	   */
	public Charger nearestCharger(Position pos) {
		double distance = 300;
		double curDistance = 0;
		int index = 0;
		for (int i = 0; i < 50; i ++) {
			curDistance = pos.distance(coordinates[i][0], coordinates[i][1]);
			if (curDistance < distance) {
				distance = curDistance;
				index = i;
			}
		}
		return chargers.get(index);
	}
	
	/**
	   * Find the connected charger of a position if it has.
	   * 
	   * @param pos the position we are currently considering.
	   * @return return the connected charger. If no charger connected, return false
	   */
	public Charger connectedCharger(Position pos) {
		Charger nearest = nearestCharger(pos);
		if (nearest.distance(pos) <= 0.00025d) return nearest;
		else return null;
	}
	
	/**
	   * Read the contents from a input stream, and return it as a string.
	   * 
	   * @param in the input stream.
	   * @return return the contents as a string.
	   * @throws java.io.IOException throw the error if anything wrong
	   */
    private static String reader(InputStream in) throws IOException {
    	byte current;
    	String result = new String();
    	try {
	    	while(true) {
				current = (byte) in.read();
	    		if (current == -1) return result;
	    		result += (char) current;
	    	}
	    } catch (IOException e) {
			throw e;
		}
    }
    
	/**
	   * Extract the charger information from a GeoJson FeatureCollection.
	   * <p>
	   * Extract latitude, longitude, coin and power information of chargers,
	   * and initialize chargers then add to the chargers list.
	   * 
	   * @param featureMap a GeoJson FeatureCollection with 50 charger information
	   */
    private void fromFeatures(FeatureCollection featureMap) {
    	double coins;
    	double power;
    	rawFeatures = featureMap;
		List<Feature> features = featureMap.features();
		Feature currentFeature = null;
		Point currentPoint = null;
		
		for (int i = 0; i < 50; i++) {
			currentFeature = features.get(i);
			currentPoint = (Point) currentFeature.geometry();
			coordinates[i][0] = currentPoint.latitude();
			coordinates[i][1] = currentPoint.longitude();
			coins = currentFeature.getProperty("coins").getAsDouble();
			power = currentFeature.getProperty("power").getAsDouble();
			
			chargers.add(new Charger(currentPoint.latitude(), currentPoint.longitude(), coins, power));
		}
    }
	
}
