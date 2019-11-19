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
    public static void main(String[] fff ) {;

        for (int y = 2019; y <= 2020; y++) {
        	for (int m = 1; m <= 12; m++) {
        		for (int d = 1; d <= 28; d++) {
        			Map map = new Map(y, m, d);
        			LineDrawer ld = new LineDrawer(map.rawFeatures);
        			Stateless drone = new Statefull(new Position(55.94404781601724, -3.1917158579021225), 123L, map, ld);
        			while (drone.hasNext()) {
        	        	drone.goNextPosition();
        	        }
        			System.out.println(ld.mapWithLines().toJson());
        			System.out.println(drone.coins);
        	        System.out.println(drone.power);
        		}
        	}
        }
    
    	
        System.out.println("finished");
        System.out.println(Direction.angleToDirection(Math.PI / 16 - 0.0001));    
        Position pos1 = new Position(400, -321);
        Position pos2 = new Position(0, 0);
        
        double angle = pos2.angle(pos1);
        System.out.println(Direction.angleToDirection(angle));
    }
    
}
