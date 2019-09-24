package uk.ac.ed.inf.powergrab;

public class Position {
	public double latitude;
	public double longitude;
		
	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Position() {
		latitude = 0;
		longitude = 0;
	}
	
	public Position(Position copyFrom) {
		latitude = copyFrom.latitude;
		longitude = copyFrom.longitude;
	}
	
	static class Movement{
		static private double[] NNE = {Math.sin(3.0 / 8 * Math.PI) * 0.0003, Math.cos(3.0 / 8 * Math.PI) * 0.0003};
		static private double[] NE = {Math.sin(2.0 / 8 * Math.PI) * 0.0003, Math.cos(2.0 / 8 * Math.PI) * 0.0003};
		static private double[] ENE = {Math.sin(1.0 / 8 * Math.PI) * 0.0003, Math.cos(1.0 / 8 * Math.PI) * 0.0003};
		static private double[] ESE = {Math.sin(- 1.0 / 8 * Math.PI) * 0.0003, Math.cos(- 1.0 / 8 * Math.PI) * 0.0003};
		static private double[] SE = {Math.sin(- 2.0 / 8 * Math.PI) * 0.0003, Math.cos(- 2.0 / 8 * Math.PI) * 0.0003};
		static private double[] SSE = {Math.sin(- 3.0 / 8 * Math.PI) * 0.0003, Math.cos(- 3.0 / 8 * Math.PI) * 0.0003};
		static private double[] SSW = {Math.sin(- 5.0 / 8 * Math.PI) * 0.0003, Math.cos(- 5.0 / 8 * Math.PI) * 0.0003};
		static private double[] SW = {Math.sin(- 6.0 / 8 * Math.PI) * 0.0003, Math.cos(- 6.0 / 8 * Math.PI) * 0.0003};
		static private double[] WSW = {Math.sin(- 7.0 / 8 * Math.PI) * 0.0003, Math.cos(- 7.0 / 8 * Math.PI) * 0.0003};
		static private double[] WNW = {Math.sin(7.0 / 8 * Math.PI) * 0.0003, Math.cos(7.0 / 8 * Math.PI) * 0.0003};
		static private double[] NW = {Math.sin(6.0 / 8 * Math.PI) * 0.0003, Math.cos(6.0 / 8 * Math.PI) * 0.0003};
		static private double[] NNW = {Math.sin(5.0 / 8 * Math.PI) * 0.0003, Math.cos(5.0 / 8 * Math.PI) * 0.0003};
	}
	
	public Position nextPosition(Direction direction) {
		Position nxtPos = new Position();
		switch(direction) {
		case N: {
			nxtPos.latitude = latitude + 0.0003;
			nxtPos.longitude = longitude;
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
			nxtPos.latitude = latitude;
			nxtPos.longitude = longitude + 0.0003;
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
			nxtPos.latitude = latitude - 0.0003;
			nxtPos.longitude = longitude;
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
			nxtPos.latitude = latitude;
			nxtPos.longitude = longitude - 0.0003;
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

	//TODO Adding change play area function
	public boolean inPlayArea() {
		if (latitude >= 55.946233) {
			return false;
		}
		if (latitude <= 55.942617) {
			return false;
		}
		if (longitude >= -3.184319) {
			return false;
		}
		if (longitude <= -3.192473) {
			return false;
		}
		return true;
	}
	
	protected void go(Direction direction) {
		Position nxtPos = nextPosition(direction);
		latitude = nxtPos.latitude;
		longitude = nxtPos.longitude;
	}
}
