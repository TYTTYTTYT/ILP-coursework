package uk.ac.ed.inf.powergrab;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
        
    }
}
