package uk.ac.ed.inf.powergrab;

public class Charger implements Geography {
	public double latitude;
	public double longitude;
	public double coins;
	public double power;
	private Position position;
	
	public Charger(double latitude, double longitude, double coins, double power) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.coins = coins;
		this.power = power;
		this.position = new Position(latitude, longitude);
	}

	@Override
	public double latitude() {
		return position.latitude();
	}

	@Override
	public double longitude() {
		return position.longitude();
	}
}
