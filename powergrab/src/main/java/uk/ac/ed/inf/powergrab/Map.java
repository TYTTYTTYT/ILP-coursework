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
		rawFeatures = null;
		coordinates = new double[50][2];
		chargers = new ArrayList<Charger>();
	}
	
	public Map(FeatureCollection featureMap) {
		fromFeatures(featureMap);
	}
	
	public Map(int year, int mounth, int day) {
		String yearS = String.valueOf(year);
		String mounthS = String.valueOf(mounth);
		String dayS = String.valueOf(day);
		if (day < 10) {
			dayS = "0" + dayS;
		}
		if (mounth < 10) {
			mounthS = "0" + mounthS;
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
			e.printStackTrace();
			System.err.println("Not a well-formed URL: " + e.getMessage());
			return;
		}

        
        try {
        	HttpURLConnection conn = (HttpURLConnection) mapURL.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000); 
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			InputStream buffer = conn.getInputStream();
			mapSource = reader(buffer);
//			in = new Scanner(buffer);
//			mapSource = in.next();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("**********************************************************************");
			System.err.println("Network problem occurred, can not download map: " + e.getMessage());
			System.err.println("**********************************************************************");
			return;
		}
        
        FeatureCollection fc = FeatureCollection.fromJson(mapSource);
        fromFeatures(fc);

	}
	
	Charger nearestPositiveCharger(Position pos) {
		double distance = 300;
		double curDistance = 0;
		int index = -1;
		for (int i = 0; i < 50; i ++) {
			if (chargers.get(i).power > 0 && chargers.get(i).power > 0) {
				curDistance = calDistance(pos, new Position(coordinates[i][0], coordinates[i][1]));
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
			curDistance = calDistance(pos, new Position(coordinates[i][0], coordinates[i][1]));
			if (curDistance < distance) {
				distance = curDistance;
				index = i;
			}
		}
		return chargers.get(index);
	}
	
	double calDistance(Position pos1, Position pos2) {
		double distance = Math.sqrt(Math.pow(pos1.latitude - pos2.latitude, 2) + Math.pow(pos1.longitude - pos2.longitude, 2));
		return distance;
	}
	
	public Charger connectedCharger(Position pos) {
		Charger nearest = nearestCharger(pos);
		double distance = calDistance(nearest.position, pos);
		if (distance <= 0.00025) return nearest;
		else return null;
	}
	
    private static String reader(InputStream in) {
    	byte current;
    	String result = new String();
    	while(true) {
    		try {
				current = (byte) in.read();
			} catch (IOException e) {
				e.printStackTrace();
				return result;
			}
    		if (current == -1) return result;
    		result += (char) current;
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
    
    boolean noCoinsLeft() {
    	for (int i = 0; i < 50; i++) {
    		if (chargers.get(i).coins > 0) return false;
    	}
    	return true;
    }
	
}
