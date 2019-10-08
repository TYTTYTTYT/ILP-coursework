package uk.ac.ed.inf.powergrab;

import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

public class LineDrawer {
	private double[][] lineTrace;
	List<Feature> features;
	int tracePointer;
	
	static private String jsonHead = "{\"type\": \"Feature\",\"properties\": {}, \"geometry\": {\"type\":\"LineString\",\"coordinates\": [";
	static private String jsonTail = "]}}";
	
	{
		lineTrace = new double[250][2];
		tracePointer = 0;
		for (int i = 0; i < 250; i ++) {
			lineTrace[i][0] = -100;
			lineTrace[i][1] = -100;
		}
	}
	public LineDrawer(FeatureCollection fc) {
		features = fc.features();
	}
	
	public void addNextPoint(double latitude, double longitude) {
		lineTrace[tracePointer][0] = latitude;
		lineTrace[tracePointer][1] = longitude;
		tracePointer++;
	}
	
	public void addNextPoint(Position pos) {
		lineTrace[tracePointer][0] = pos.latitude;
		lineTrace[tracePointer][1] = pos.longitude;
		tracePointer++;
	}
	
	public FeatureCollection mapWithLines() {
		Feature lines = Feature.fromJson(genTraceJson());
		features.add(lines);
		FeatureCollection map = FeatureCollection.fromFeatures(features);
		return map;
	}
	
	public String genTraceJson() {
		String traceJson = new String();
		for (int i = 0; i < tracePointer; i++) {
			traceJson = traceJson + "[ " + Double.toString(lineTrace[i][1]) + ", " + Double.toString(lineTrace[i][0]) + " ]";
			if (i != tracePointer - 1) traceJson += ",";
		}
		traceJson = jsonHead + traceJson + jsonTail;
		return traceJson;
	}
}
