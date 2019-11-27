package uk.ac.ed.inf.powergrab;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Main procedure of the game application.
 *
 */
public class App 
{
    public static void main(String[] args) {
    	// Check the argument format
    	if (args.length != 7) {
    		System.err.println("########################");
    		System.err.println("Wrong argument format!!!");
    		System.err.println("########################");
    		return;
    	}
    	
    	// Read game parameters
    	int day = Integer.valueOf(args[0]);
    	int mounth = Integer.valueOf(args[1]);
    	int year = Integer.valueOf(args[2]);
    	
    	double latitude = Double.valueOf(args[3]);
    	double longitude = Double.valueOf(args[4]);
    	Position pos = new Position(latitude, longitude);
    	long seed = Integer.valueOf(args[5]);
    	
    	// The drone must be initizlized within the play area
    	if (!pos.inPlayArea()) {
    		System.err.println("#######################################");
    		System.err.println("Initial position is out of play area!!!");
    		System.err.println("#######################################");
    		return;
    	}
    	
    	// Download and generate the game map
		Map map = null;
		try {
			map = new Map(year, mounth, day);
		} catch (IOException e) {
			System.err.println("############################################################");
			System.err.println("Map initialization failed, please check the date or network!");
			System.err.println("############################################################");
			e.printStackTrace();
		}
    	
		// Generate the file name to store the drone records.
		String filename;
		String y, m, d;
		Drone drone;
		y = String.valueOf(year);
		if (mounth < 10)
			m = "0" + String.valueOf(mounth);
		else m = String.valueOf(mounth);
		if (day < 10)
			d = "0" + String.valueOf(day);
		else d = String.valueOf(day);
		
		// Initialize the drone and the tracer (LineDrawer) according to the input drone type
		LineDrawer ld = new LineDrawer(map.rawFeatures);
		if (args[6].equals("stateless")) {
			drone = new Stateless(pos, seed, map, ld);
			filename = "stateless" + "-" + d + "-" + m + "-" + y;
		}
		else if (args[6].equals("stateful")) {
			drone = new Stateful(pos, seed, map, ld);
			filename = "stateful" + "-" + d + "-" + m + "-" + y;
		} else {
			System.err.println("#####################################################");
			System.err.println("Please enter a valid drone type! stateless / stateful");
			System.err.println("#####################################################");
			return;
		};
		
		// Run the drone
		while (drone.hasNext()) {
        	drone.goNextPosition();
        }
		
		// Store the drone records to files.
		String json = ld.mapWithLines().toJson();
		String records = ld.flightTrace();
		try{    
			FileWriter fw = new FileWriter(filename + ".geojson");    
			fw.write(json);    
			fw.close();
			fw = new FileWriter(filename + ".txt");    
			fw.write(records);    
			fw.close();  
		}catch(Exception e){
			System.err.println("#####################");
			System.err.println("Saving file failed!!!");
			System.err.println("#####################");
			e.printStackTrace();
			return;
		}

		System.out.println("Success...");  
    }    
}
