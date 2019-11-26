package uk.ac.ed.inf.powergrab;

import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

public class LineDrawer {
	private double[] latitudeTrace;
	private double[] longitudeTrace;
	private double[] coinsTrace;
	private double[] powerTrace;
	List<Feature> features;
	private int tracePointer;
	
	static private String jsonHead = "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[";
	static private String jsonTail = "]}}";
	
	{
		latitudeTrace = new double[500];
		longitudeTrace = new double[500];
		coinsTrace = new double[500];
		powerTrace = new double[500];
		tracePointer = 0;
	}
	public LineDrawer(FeatureCollection fc) {
		features = fc.features();
	}
	
	public void recordDrone(Drone d) {
		latitudeTrace[tracePointer] = d.latitude();
		longitudeTrace[tracePointer] = d.longitude();
		coinsTrace[tracePointer] = d.currentCoins();
		powerTrace[tracePointer] = d.currentPower();
		tracePointer++;
	}
	
	public FeatureCollection mapWithLines() {
		Feature lines = Feature.fromJson(genTraceJson());
		features.add(lines);
		FeatureCollection map = FeatureCollection.fromFeatures(features);
		return map;
	}
	
	public String flightTrace() {
		String records = "";
		for (int stepFinished = 1; stepFinished < tracePointer; stepFinished++) {
			Position pos1 = new Position(latitudeTrace[stepFinished - 1], longitudeTrace[stepFinished - 1]);
			Position pos2 = new Position(latitudeTrace[stepFinished], longitudeTrace[stepFinished]);
			Direction d = Direction.angleToDirection(pos1.angle(pos2));
			
			records += Double.toString(latitudeTrace[stepFinished - 1]) + "," +
					Double.toString(longitudeTrace[stepFinished - 1]) + "," +
					String.valueOf(d) + "," +
					Double.toString(latitudeTrace[stepFinished]) + "," +
					Double.toString(longitudeTrace[stepFinished]) + "," +
					Double.toString(coinsTrace[stepFinished]) + "," +
					Double.toString(powerTrace[stepFinished]) + "\n";
		}
		
		return records;
	}
	
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
