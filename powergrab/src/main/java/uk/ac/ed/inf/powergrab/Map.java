package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;


public class Map {
	private double[][] coordinates;
	private double totalPositiveCoins;
	private double totalPositivePower;
	
	private List<Charger> chargers;
	FeatureCollection rawFeatures;
	
	{
		totalPositiveCoins = 0;
		totalPositivePower = 0;
		rawFeatures = null;
		coordinates = new double[50][2];
		chargers = new ArrayList<Charger>();
	}
	
	public Map(FeatureCollection featureMap) {
		fromFeatures(featureMap);
	}
	
	public Map(int year, int mounth, int day) throws IOException,  MalformedURLException{
		String yearS = String.valueOf(year);
		String mounthS = String.valueOf(mounth);
		String dayS = String.valueOf(day);
		
		if (mounth < 10) {
			mounthS = "0" + mounthS;
		}
		if (day < 10) {
			dayS = "0" + dayS;
		}

		
        String mapString = "http://homepages.inf.ed.ac.uk/stg/powergrab/" +
        					yearS + "/" +
        					mounthS + "/" +
        					dayS + "/" +
        					"powergrabmap.geojson";
        
        String mapSource = null;
        URL mapURL;
        try {
			mapURL = new URL(mapString);
		} catch (MalformedURLException e) {
			throw e;
		}

        HttpURLConnection conn = (HttpURLConnection) mapURL.openConnection();
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(15000); 
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		
        try {
			conn.connect();
		} catch (IOException e) {
			throw e;
		}
        
        InputStream buffer = conn.getInputStream();
		mapSource = reader(buffer);
        FeatureCollection fc = FeatureCollection.fromJson(mapSource);
        fromFeatures(fc);

	}
	
	Charger nearestPositiveCharger(Position pos) {
		double distance = 300;
		double curDistance = 0;
		int index = -1;
		for (int i = 0; i < 50; i ++) {
			if (chargers.get(i).power > 0 && chargers.get(i).power > 0) {
				curDistance = pos.distance(new Position(coordinates[i][0], coordinates[i][1]));
			} else continue;
			if (curDistance < distance) {
				distance = curDistance;
				index = i;
			}
		}
		if (index == -1) return null;
		return chargers.get(index);
	}
	
	public Charger nearestCharger(Position pos) {
		double distance = 300;
		double curDistance = 0;
		int index = 0;
		for (int i = 0; i < 50; i ++) {
			curDistance = pos.distance(new Position(coordinates[i][0], coordinates[i][1]));
			if (curDistance < distance) {
				distance = curDistance;
				index = i;
			}
		}
		return chargers.get(index);
	}
	
	public Charger connectedCharger(Position pos) {
		Charger nearest = nearestCharger(pos);
		double distance = nearest.distance(pos);
		if (distance <= 0.00025d) return nearest;
		else return null;
	}
	
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
    
    private void fromFeatures(FeatureCollection featureMap) {
    	double coins;
    	double power;
    	rawFeatures = featureMap;
		List<Feature> features = featureMap.features();
		Feature currentFeature = null;
		Point currentPoint = null;
		
		for (int i = 0; i < 50; i ++) {
			currentFeature = features.get(i);
			currentPoint = (Point) currentFeature.geometry();
			coordinates[i][0] = currentPoint.latitude();
			coordinates[i][1] = currentPoint.longitude();
			coins = currentFeature.getProperty("coins").getAsDouble();
			power = currentFeature.getProperty("power").getAsDouble();
			
			if (coins > 0) totalPositiveCoins += coins;
			if (power > 0) totalPositivePower += power;
			
			chargers.add(new Charger(currentPoint.latitude(), currentPoint.longitude(), coins, power));
		}
    }
    
    void reset() {
    	fromFeatures(rawFeatures);
    }
    
    boolean noCoinsLeft() {
    	for (int i = 0; i < 50; i++) {
    		if (chargers.get(i).coins > 0) return false;
    	}
    	return true;
    }
	
}
