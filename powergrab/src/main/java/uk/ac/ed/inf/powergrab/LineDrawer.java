package uk.ac.ed.inf.powergrab;

import java.util.List;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

/**
 * Provides methods to record the drone and add the trace to map FeatureCollection 
 *
 * @author      Tai Yintao s1891075@ed.ac.uk
 * @version     1.0
 * @since       0.1
 */
public class LineDrawer {
	
	/**
	 * Latitude records.
	 */
	private double[] latitudeTrace;
	
	/**
	 * Longitude records.
	 */
	private double[] longitudeTrace;
	
	/**
	 * Coins records.
	 */
	private double[] coinsTrace;
	
	/**
	 * Power records.
	 */
	private double[] powerTrace;
	
	/**
	 * Map features.
	 */
	private List<Feature> features;
	
	/**
	 * The pointer indicate how many records it has.
	 */
	private int tracePointer;
	
	/**
	 * The Json head used to generate the Json element
	 */
	static final private String jsonHead = "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[";
	
	/**
	 * The Json head tail to generate the Json element
	 */
	static final private String jsonTail = "]}}";
	
	{
		latitudeTrace = new double[500];
		longitudeTrace = new double[500];
		coinsTrace = new double[500];
		powerTrace = new double[500];
		tracePointer = 0;
	}
	
	/**
	 * Constructs a LineDrawer with given map features
	 *
	 * @param fc a map FeatureCollection
	 */
	public LineDrawer(FeatureCollection fc) {
		features = fc.features();
	}
	
	/**
	 * Record the drone's states and position
	 *
	 * @param d the drone to be recorded
	 */
	public void recordDrone(Drone d) {
		latitudeTrace[tracePointer] = d.latitude();
		longitudeTrace[tracePointer] = d.longitude();
		coinsTrace[tracePointer] = d.currentCoins();
		powerTrace[tracePointer] = d.currentPower();
		tracePointer++;
	}
	
	/**
	 * Add the drones trace as lines to the map FeatureCollection
	 *
	 * @return the map FeatureCollection with recorded trace.
	 */
	public FeatureCollection mapWithLines() {
		Feature lines = Feature.fromJson(genTraceJson());
		features.add(lines);
		FeatureCollection map = FeatureCollection.fromFeatures(features);
		return map;
	}
	
	/**
	 * Write the drone movement records to a string.
	 * <p>
	 * Each line in the string represents one record/move of the drone, in the format of
	 * <latitude before move>,<longitude before move>,<direction of this move>,<latitude after move>,<longitude after move>,<coins after move>,<power after move>
	 * 
	 * @return the movement records.
	 */
	public String flightTrace() {
		String records = "";
		for (int stepFinished = 1; stepFinished < tracePointer; stepFinished++) {
			// Calculate the direction of this movement
			Position pos1 = new Position(latitudeTrace[stepFinished - 1], longitudeTrace[stepFinished - 1]);
			Position pos2 = new Position(latitudeTrace[stepFinished], longitudeTrace[stepFinished]);
			Direction d = Direction.angleToDirection(pos1.angle(pos2));

			records += Double.toString(latitudeTrace[stepFinished - 1]) + "," +
					Double.toString(longitudeTrace[stepFinished - 1]) + "," +
					String.valueOf(d) + "," +
					Double.toString(latitudeTrace[stepFinished]) + "," +
					Double.toString(longitudeTrace[stepFinished]) + "," +
					Double.toString(coinsTrace[stepFinished]) + "," +
					Double.toString(powerTrace[stepFinished]) + "\n";	// Change line after each record
		}
		return records;
	}
	
	/**
	 * Generate a Json element with flight trace records
	 * <p>
	 * 
	 * @return the Json element
	 */
	private String genTraceJson() {
		String traceJson = new String();
		for (int i = 0; i < tracePointer; i++) {
			traceJson = traceJson + "[ " + Double.toString(longitudeTrace[i]) + ", " + Double.toString(latitudeTrace[i]) + " ]";
			if (i != tracePointer - 1) traceJson += ",";
		}
		traceJson = jsonHead + traceJson + jsonTail;
		return traceJson;
	}
}
