package uk.ac.ed.inf.powergrab;

import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;

public class LineDrawer {
	double[][] lineTrace;
	List<Feature> features;
	int tracePointer;
	
	static private String jsonHead = "{\n\"type\": \LineString\",\n\"coordinates\": [\n";
	static private String jsonTail = "]\n}";
	
	{
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
}
