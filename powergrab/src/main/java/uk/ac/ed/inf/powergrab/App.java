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
    public static void main(String[] fff ) {
    	Direction drct;
    	drct = Direction.N;
    	
    	switch (drct) {
    	case N:
    		System.out.println("north");
    		break;
    	case SSE:
    		System.out.println("South South East");
    	default:
    		break;
    	}
        System.out.println( "Hello World!" );
        Position A = new Position(10, 10);
        Position B = new Position(123, 123);
        B.latitude = 123;
        
        System.out.println(A.latitude);
        A.go(Direction.NNE);
        System.out.println(A.latitude);
        
        String mapString = "http://homepages.inf.ed.ac.uk/stg/powergrab/2019/01/01/powergrabmap.geojson";
        URL mapURL = null;
        
        try {
			mapURL = new URL(mapString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.err.println("Not a well-formed URL: " + e.getMessage());
		}
        HttpURLConnection conn = null;
        String mapSource = null;
//        Scanner in = null;

        try {
			conn = (HttpURLConnection)mapURL.openConnection();
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
		}
        
        
        if (mapSource != null) System.out.println(mapSource.length());
        else return;
        System.out.println(-1);
        FeatureCollection fc = FeatureCollection.fromJson(mapSource);
        System.out.println(0);
        List<Feature> features = fc.features();
        System.out.println(1);
        Feature f = features.get(0);
        Geometry g = f.geometry();
        System.out.println(3);
        Point p = (Point) g;
        System.out.println(4);
        System.out.println(p.coordinates().toString());
        System.out.println(mapSource.length());
        System.out.println(f.getProperty("coins").getAsFloat());
        System.out.println(f.getProperty("power").getAsFloat());
        
        Position start = new Position(p.latitude(), p.longitude());
        LineDrawer ld = new LineDrawer(fc);
        ld.addNextPoint(start);
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.NE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        ld.addNextPoint(start.go(Direction.ESE));
        for (int i = 0; i < 220; i++) {
        	ld.addNextPoint(start.go(Direction.ENE));
        }
        
        FeatureCollection lineMap = ld.mapWithLines();
        System.out.print(lineMap.toJson());
        
                
        
//        Double[] nums = {Double(123.123123), Double(-123.23132)};
//        System.out.println(nums[1]);
        
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
}
