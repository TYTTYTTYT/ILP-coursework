package uk.ac.ed.inf.powergrab;

import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;


public class Map {
	private double[][] coordinates;
	private double[] coins;
	private double[] power;
	
	{
		coordinates = new double[50][2];
		coins = new double[50];
		power = new double[50];
	}
	
	public Map(FeatureCollection featureMap) {
		List<Feature> features = featureMap.features();
		Feature currentFeature = null;
		Point currentPoint = null;
		
		for (int i = 0; i < 50; i ++) {
			currentFeature = features.get(i);
			currentPoint = (Point) currentFeature.geometry();
			coordinates[i][0] = currentPoint.latitude();
			coordinates[i][1] = currentPoint.longitude();
			coins[i] = currentFeature.getProperty("coins").getAsDouble();
			power[i] = currentFeature.getProperty("power").getAsDouble();
		}
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
		Charger charger = new Charger(coordinates[index][0], coordinates[index][1], coins[index], power[index]);
		return charger;
	}
	
	private double calDistance(Position pos1, Position pos2) {
		double distance = Math.sqrt(Math.pow(pos1.latitude - pos2.latitude, 2) + Math.pow(pos1.longitude - pos2.longitude, 2));
		return distance;
	}
	
	public Charger availableCharger(Position pos) {
		Charger nearest = nearestCharger(pos);
		double distance = calDistance(nearest.position, pos);
		if (distance <= 0.00025) return nearest;
		else return null;
	}
	
}
