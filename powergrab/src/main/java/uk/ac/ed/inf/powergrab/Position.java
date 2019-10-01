package uk.ac.ed.inf.powergrab;
/**
* The Position class used to indicate position on GeoMaps.
*
* @author  Tai Yintao
* @version 0.1
* @since   2019-09-24
*/
public class Position {
	public double latitude;		// The latitude of the position
	public double longitude;	// The longitude of the position
	
	/**
	   * This constructor is used to initiate Position instance with provided coordinate.
	   * 
	   * @param latitude The latitude of the initial position
	   * @param longitude  The longitude of the initial position
	   */
	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	   * This constructor is used to initiate Position instance without provided position.
	   * 
	   */
	public Position() {
		latitude = 0;
		longitude = 0;
	}
	
	
	/**
	   * This constructor is used to initiate Position instance with another
	   * Position instance.
	   * 
	   * @param copyFrom The another instance
	   */
	public Position(Position copyFrom) {
		latitude = copyFrom.latitude;
		longitude = copyFrom.longitude;
	}
	
	/* This static class calculate movement length of all directions first, used to calculate
	 * next position.
	 */
	static class Movement{
		static private double[] N = {0.0003d, 0d};
		static private double[] NNE = {Math.sin(3.0d / 8 * Math.PI) * 0.0003d, Math.cos(3.0d / 8 * Math.PI) * 0.0003d};
		static private double[] NE = {Math.sin(2.0d / 8 * Math.PI) * 0.0003d, Math.cos(2.0d / 8 * Math.PI) * 0.0003d};
		static private double[] ENE = {Math.sin(1.0d / 8 * Math.PI) * 0.0003d, Math.cos(1.0d / 8 * Math.PI) * 0.0003d};
		static private double[] E = {0, 0.0003d};
		static private double[] ESE = {Math.sin(- 1.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 1.0d / 8 * Math.PI) * 0.0003d};
		static private double[] SE = {Math.sin(- 2.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 2.0d / 8 * Math.PI) * 0.0003d};
		static private double[] SSE = {Math.sin(- 3.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 3.0d / 8 * Math.PI) * 0.0003d};
		static private double[] S = {- 0.0003d, 0d};
		static private double[] SSW = {Math.sin(- 5.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 5.0d / 8 * Math.PI) * 0.0003d};
		static private double[] SW = {Math.sin(- 6.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 6.0d / 8 * Math.PI) * 0.0003d};
		static private double[] WSW = {Math.sin(- 7.0d / 8 * Math.PI) * 0.0003d, Math.cos(- 7.0d / 8 * Math.PI) * 0.0003d};
		static private double[] W = {0d, - 0.0003d};
		static private double[] WNW = {Math.sin(7.0d / 8 * Math.PI) * 0.0003d, Math.cos(7.0d / 8 * Math.PI) * 0.0003d};
		static private double[] NW = {Math.sin(6.0d / 8 * Math.PI) * 0.0003d, Math.cos(6.0d / 8 * Math.PI) * 0.0003d};
		static private double[] NNW = {Math.sin(5.0d / 8 * Math.PI) * 0.0003d, Math.cos(5.0d / 8 * Math.PI) * 0.0003d};
	}
	
	/**
	   * This method is used to generate a new Position instance with a given direction.
	   * 
	   * @param direction The new positon's direction of current position
	   * @return nxtPos The instance of next position
	   */
	public Position nextPosition(Direction direction) {
		Position nxtPos = new Position();
		switch(direction) {
		case N: {
			// Change the latitude and longitude with movement length in Movement class
			nxtPos.latitude = latitude + Movement.N[0];
			nxtPos.longitude = longitude + Movement.N[1];
			return nxtPos;
		}
		case NNE: {
			nxtPos.latitude = latitude + Movement.NNE[0];
			nxtPos.longitude = longitude + Movement.NNE[1];
			return nxtPos;
		}
		case NE: {
			nxtPos.latitude = latitude + Movement.NE[0];
			nxtPos.longitude = longitude + Movement.NE[1];
			return nxtPos;
		}
		case ENE: {
			nxtPos.latitude = latitude + Movement.ENE[0];
			nxtPos.longitude = longitude + Movement.ENE[1];
			return nxtPos;
		}
		case E: {
			nxtPos.latitude = latitude + Movement.E[0];
			nxtPos.longitude = longitude + Movement.E[1];
			return nxtPos;
		}
		case ESE: {
			nxtPos.latitude = latitude + Movement.ESE[0];
			nxtPos.longitude = longitude + Movement.ESE[1];
			return nxtPos;
		}
		case SE: {
			nxtPos.latitude = latitude + Movement.SE[0];
			nxtPos.longitude = longitude + Movement.SE[1];
			return nxtPos;
		}
		case SSE: {
			nxtPos.latitude = latitude + Movement.SSE[0];
			nxtPos.longitude = longitude + Movement.SSE[1];
			return nxtPos;
		}
		case S: {
			nxtPos.latitude = latitude + Movement.S[0];
			nxtPos.longitude = longitude + Movement.S[1];
			return nxtPos;
		}
		case SSW: {
			nxtPos.latitude = latitude + Movement.SSW[0];
			nxtPos.longitude = longitude + Movement.SSW[1];
			return nxtPos;
		}
		case SW: {
			nxtPos.latitude = latitude + Movement.SW[0];
			nxtPos.longitude = longitude + Movement.SW[1];
			return nxtPos;
		}
		case WSW: {
			nxtPos.latitude = latitude + Movement.WSW[0];
			nxtPos.longitude = longitude + Movement.WSW[1];
			return nxtPos;
		}
		case W: {
			nxtPos.latitude = latitude + Movement.W[0];
			nxtPos.longitude = longitude + Movement.W[1];
			return nxtPos;
		}
		case WNW: {
			nxtPos.latitude = latitude + Movement.WNW[0];
			nxtPos.longitude = longitude + Movement.WNW[1];
			return nxtPos;
		}
		case NW: {
			nxtPos.latitude = latitude + Movement.NW[0];
			nxtPos.longitude = longitude + Movement.NW[1];
			return nxtPos;
		}
		case NNW: {
			nxtPos.latitude = latitude + Movement.NNW[0];
			nxtPos.longitude = longitude + Movement.NNW[1];
			return nxtPos;
		}
		default:
			return nxtPos;
		}
	}

	/**
	   * Check whether the current coordinate is within the play area
	   * 
	   * @return If it's in the area, return true, otherwise return false.
	   */
	public boolean inPlayArea() {
		if (latitude >= 55.946233d) {
			return false;
		}
		if (latitude <= 55.942617d) {
			return false;
		}
		if (longitude >= -3.184319d) {
			return false;
		}
		if (longitude <= -3.192473d) {
			return false;
		}
		return true;
	}
	
	/**
	   * Change the current coordinate of this instance to one of 16 directions
	   * 
	   * @param direction  The direction of where to go
	   */
	protected void go(Direction direction) {
		// Use nextPosition method to calculate the new coordinate。
		Position nxtPos = nextPosition(direction);
		latitude = nxtPos.latitude;
		longitude = nxtPos.longitude;
	}
}
