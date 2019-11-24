package uk.ac.ed.inf.powergrab;

interface Geography {
	
	double latitude();
	double longitude();
	
	default double distance(Geography g) {
		return calDistance(this, g);
	}
	
	static double calDistance(Geography pos1, Geography pos2) {
		double distance = Math.sqrt(Math.pow(pos1.latitude() - pos2.latitude(), 2) + Math.pow(pos1.longitude() - pos2.longitude(), 2));
		return distance;
	}
	
	default double angle(Geography g) {
		double cos = (g.latitude() - latitude()) / Math.sqrt(Math.pow(g.latitude() - latitude(), 2) + Math.pow(g.longitude() - longitude(), 2));
		double acos = Math.acos(cos);
		if (longitude() > g.longitude())
			acos = - acos;
		return acos;
	}
	
}
