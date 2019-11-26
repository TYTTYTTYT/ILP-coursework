package uk.ac.ed.inf.powergrab;

public class Charger implements Geography {
	double coins;
	double power;
	private Position myPosition;
	
	public Charger(double latitude, double longitude, double coins, double power) {
		this.coins = coins;
		this.power = power;
		this.myPosition = new Position(latitude, longitude);
	}

	@Override
	public double latitude() {
		return myPosition.latitude();
	}

	@Override
	public double longitude() {
		return myPosition.longitude();
	}
}
