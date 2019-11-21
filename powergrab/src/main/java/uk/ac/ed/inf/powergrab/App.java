package uk.ac.ed.inf.powergrab;

import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] qweqwe ) {

	    int allMaps = 0;
	    int clearMaps = 0;
	    for (int y = 2019; y <= 2020; y++) {
	    	for (int m = 1; m <= 12; m++) {
	        	for (int d = 1; d <= 28; d++) {
	        		allMaps++;
	        		Map map = new Map(y, m, d);
	        		LineDrawer ld = new LineDrawer(map.rawFeatures);
	       			Stateless drone = new Statefull(new Position(55.9462022, -3.1924501), 123L, map, ld);
	       			while (drone.hasNext()) {
	       	        	drone.goNextPosition();
	       	        }
	       			if (map.noCoinsLeft()) clearMaps++;
	       			else {
	       				System.out.println("map " + String.valueOf(y) + " " + String.valueOf(m) + " " + String.valueOf(d));
	       				System.out.println(String.valueOf(drone.stepsLeft) + " steps left");
	       			}
	
	       	  	}
	    	}
	    }
	    System.out.println("all maps: " + String.valueOf(allMaps));
	    System.out.println("cleared maps: " + String.valueOf(clearMaps));
    
        
//		Map map = new Map(2019, 12, 9);
//		LineDrawer ld = new LineDrawer(map.rawFeatures);
//		Stateless drone = new Statefull(new Position(55.9462022, -3.1924501), 123L, map, ld);
//		while (drone.hasNext()) {
//        	drone.goNextPosition();
//		}
//		
//		System.out.println(ld.mapWithLines().toJson());
//		System.out.println(drone.coins);
//		System.out.println(drone.stepsLeft);
    
        
    }
    
}
