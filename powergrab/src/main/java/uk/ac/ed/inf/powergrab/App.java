package uk.ac.ed.inf.powergrab;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
    	System.out.println(args[6]);
    	int year, mounth, day;
    	long seed;
    	double latitude, longitude;
    	Stateless drone;
    	Position pos;
    	
    	day = Integer.valueOf(args[0]);
    	mounth = Integer.valueOf(args[1]);
    	year = Integer.valueOf(args[2]);
    	
    	latitude = Double.valueOf(args[3]);
    	longitude = Double.valueOf(args[4]);
    	pos = new Position(latitude, longitude);
    	
    	if (!pos.inPlayArea()) {
    		System.err.println("#######################################");
    		System.err.println("Initial position is out of play area!!!");
    		System.err.println("#######################################");
    		return;
    	}
    	
    	seed = Integer.valueOf(args[5]);
		Map map = null;
		try {
			map = new Map(year, mounth, day);
		} catch (IOException e) {
			System.err.println("############################################################");
			System.err.println("Map initialization failed, please check the date or network!");
			System.err.println("############################################################");
			e.printStackTrace();
		}
    	
		String filename;
		String y, m, d;
		y = String.valueOf(year);
		if (mounth < 10)
			m = "0" + String.valueOf(mounth);
		else m = String.valueOf(mounth);
		if (day < 10)
			d = "0" + String.valueOf(day);
		else d = String.valueOf(day);
		
		
		LineDrawer ld = new LineDrawer(map.rawFeatures);
		if (args[6].equals("stateless")) {
			drone = new Stateless(pos, seed, map, ld);
			filename = "stateless" + "-" + d + "-" + m + "-" + y;
		}
		else if (args[6].equals("stateful")) {
			drone = new Stateful(pos, seed, map, ld);
			filename = "stateful" + "-" + d + "-" + m + "-" + y;
		}
		else {
			System.err.println("#####################################################");
			System.err.println("Please enter a valid drone type! stateless / stateful");
			System.err.println("#####################################################");
			return;
		};
		
		while (drone.hasNext()) {
        	drone.goNextPosition();
        }
		
		String json = ld.mapWithLines().toJson();
		String records = ld.flightTrace();
		
		try{    
			FileWriter fw = new FileWriter(filename + ".geojson");    
			fw.write(json);    
			fw.close();    
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		try{    
			FileWriter fw = new FileWriter(filename + ".txt");    
			fw.write(records);    
			fw.close();    
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		System.out.println("Success...");  

//	    int allMaps = 0;
//	    int clearMaps = 0;
//	    for (int y = 2019; y <= 2020; y++) {
//	    	for (int m = 1; m <= 12; m++) {
//	        	for (int d = 1; d <= 31; d++) {
//	    			Map map = null;
//	    			try {
//	    				map = new Map(y, m, d);
//	    			} catch (IOException e) {
//	    				System.err.println("############################################################");
//	    				System.err.println("Map initialization failed, please check the date or network!");
//	    				System.err.println("############################################################");
//	    				e.printStackTrace();
//	    				continue;
//	    			}
//	    			
//	        		allMaps++;
//	        		LineDrawer ld = new LineDrawer(map.rawFeatures);
//	       			Stateless drone = new Statefull(new Position(55.944425, -3.188396), 333, map, ld);
//	       			while (drone.hasNext()) {
//	       	        	drone.goNextPosition();
//	       	        }
//	       			if (map.noCoinsLeft()) clearMaps++;
//	       			else {
//	       				System.out.println("map " + String.valueOf(y) + " " + String.valueOf(m) + " " + String.valueOf(d));
//	       				System.out.println(String.valueOf(drone.stepLeft) + " steps left");
//	       			}
//	       			if (!approxEq(map.totalPositiveCoins, drone.coins)) {
//	       				System.out.println("map " + String.valueOf(y) + " " + String.valueOf(m) + " " + String.valueOf(d));
//	       				System.out.println(String.valueOf(map.totalPositiveCoins) + " in total, " + String.valueOf(drone.coins) + " got.");
//	       			}
//	       	  	}
//	    	}
//	    }
//	    System.out.println("all maps: " + String.valueOf(allMaps));
//	    System.out.println("cleared maps: " + String.valueOf(clearMaps));


//			Map map = null;
//			try {
//				map = new Map(2019, 3, 16);
//			} catch (IOException e) {
//				System.err.println("Map initialize failed, please check the input date and network!");
//				e.printStackTrace();
//			}
//			
//			LineDrawer ld = new LineDrawer(map.rawFeatures);
//			Stateless drone = new Stateful(new Position(55.944425, -3.188396), 5678, map, ld);
//			while (drone.hasNext()) {
//	        	drone.goNextPosition();
//			}
//			
//			System.out.println(ld.flightTrace());
//			System.out.println(ld.mapWithLines().toJson());
//			System.out.println(drone.currentCoins());
			
    }
    
	static boolean approxEq(double d0, double d1) {
		final double epsilon = 1.0E-10d;
		return Math.abs(d0 - d1) < epsilon;
	}
    
}
