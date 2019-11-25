package uk.ac.ed.inf.powergrab;

import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

public class LineDrawer {
	private double[][] lineTrace;
	private double[] coinsTrace;
	private double[] powerTrace;
	List<Feature> features;
	int tracePointer;
	
	static private String jsonHead = "{\"type\": \"Feature\",\"properties\": {}, \"geometry\": {\"type\":\"LineString\",\"coordinates\": [";
	static private String jsonTail = "]}}";
	
	{
		lineTrace = new double[500][2];
		coinsTrace = new double[500];
		powerTrace = new double[500];
		tracePointer = 0;
		for (int i = 0; i < 500; i ++) {
			lineTrace[i][0] = -100;
			lineTrace[i][1] = -100;
		}
	}
	public LineDrawer(FeatureCollection fc) {
		features = fc.features();
	}
	
	public void recordDrone(Drone d) {
		lineTrace[tracePointer][0] = d.latitude();
		lineTrace[tracePointer][1] = d.longitude();
		coinsTrace[tracePointer] = d.coins;
		powerTrace[tracePointer] = d.power;
		
		tracePointer++;
	}
	
//	public void addNextPoint(double latitude, double longitude) {
//		lineTrace[tracePointer][0] = latitude;
//		lineTrace[tracePointer][1] = longitude;
//		tracePointer++;
//	}
//	
//	public void addNextPoint(Position pos) {
//		lineTrace[tracePointer][0] = pos.latitude;
//		lineTrace[tracePointer][1] = pos.longitude;
//		tracePointer++;
//	}
	
	public FeatureCollection mapWithLines() {
		Feature lines = Feature.fromJson(genTraceJson());
		features.add(lines);
		FeatureCollection map = FeatureCollection.fromFeatures(features);
		return map;
	}
	
	public String flightTrace() {
		String records = "";
		for (int stepFinished = 1; stepFinished < tracePointer; stepFinished++) {
			Position pos1 = new Position(lineTrace[stepFinished - 1][0], lineTrace[stepFinished - 1][1]);
			Position pos2 = new Position(lineTrace[stepFinished][0], lineTrace[stepFinished][1]);
			Direction d = Direction.angleToDirection(pos1.angle(pos2));
			
			records += Double.toString(lineTrace[stepFinished - 1][0]) + "," +
					Double.toString(lineTrace[stepFinished - 1][1]) + "," +
					String.valueOf(d) + "," +
					Double.toString(lineTrace[stepFinished][0]) + "," +
					Double.toString(lineTrace[stepFinished][1]) + "," +
					Double.toString(coinsTrace[stepFinished]) + "," +
					Double.toString(powerTrace[stepFinished]) + "\n";
		}
		
		return records;
	}
	
	private String genTraceJson() {
		String traceJson = new String();
		for (int i = 0; i < tracePointer; i++) {
			traceJson = traceJson + "[ " + Double.toString(lineTrace[i][1]) + ", " + Double.toString(lineTrace[i][0]) + " ]";
			if (i != tracePointer - 1) traceJson += ",";
		}
		traceJson = jsonHead + traceJson + jsonTail;
		return traceJson;
	}
}
