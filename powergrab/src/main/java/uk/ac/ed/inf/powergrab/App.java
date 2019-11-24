package uk.ac.ed.inf.powergrab;

import java.io.IOException;

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
	        	for (int d = 1; d <= 31; d++) {
	    			Map map = null;
	    			try {
	    				map = new Map(y, m, d);
	    			} catch (IOException e) {
	    				System.err.println("Map initialization failed, please check the date or network!");
	    				continue;
	    			}
	    			
	        		allMaps++;
	        		LineDrawer ld = new LineDrawer(map.rawFeatures);
	       			Stateless drone = new Statefull(new Position(55.944425, -3.188396), 333, map, ld);
	       			while (drone.hasNext()) {
	       	        	drone.goNextPosition();
	       	        }
	       			if (map.noCoinsLeft()) clearMaps++;
	       			else {
	       				System.out.println("map " + String.valueOf(y) + " " + String.valueOf(m) + " " + String.valueOf(d));
	       				System.out.println(String.valueOf(drone.stepLeft) + " steps left");
	       			}
	       			if (!approxEq(map.totalPositiveCoins, drone.coins)) {
	       				System.out.println("map " + String.valueOf(y) + " " + String.valueOf(m) + " " + String.valueOf(d));
	       				System.out.println(String.valueOf(map.totalPositiveCoins) + " in total, " + String.valueOf(drone.coins) + " got.");
	       			}
	       	  	}
	    	}
	    }
	    System.out.println("all maps: " + String.valueOf(allMaps));
	    System.out.println("cleared maps: " + String.valueOf(clearMaps));


//			Map map = null;
//			try {
//				map = new Map(2019, 10, 14);
//			} catch (IOException e) {
//				System.err.println("Map initialize failed, please check the input date and network!");
//				e.printStackTrace();
//			}
//			
//			LineDrawer ld = new LineDrawer(map.rawFeatures);
//			Stateless drone = new Statefull(new Position(55.944425, -3.188396), 5678, map, ld);
//			while (drone.hasNext()) {
//	        	drone.goNextPosition();
//			}
//			
//			System.out.println(ld.mapWithLines().toJson());
//			System.out.println(drone.coins);
//			System.out.println(drone.stepLeft);
    }
    
	static boolean approxEq(double d0, double d1) {
		final double epsilon = 1.0E-10d;
		return Math.abs(d0 - d1) < epsilon;
	}
    
}
