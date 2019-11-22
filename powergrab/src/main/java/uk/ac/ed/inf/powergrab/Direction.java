package uk.ac.ed.inf.powergrab;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
* The Direction class stores 16 direction from compass, used to indicate the direction 
* in Position class
*
* @author  Tai Yintao
* @version 0.1
* @since   2019-09-24 
*/
public enum Direction {
	// 16 directions
	N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW;
	
	static public Direction angleToDirection(double angle) {
		angle = angle % (Math.PI * 2);
		int index = 0;
		if (angle < 0) angle = angle + 2 * Math.PI;
		index = (int)(angle / (Math.PI / 8) + 0.5);
		if (index == 16) index = 0;
		return Direction.values()[index];
	}
	
	static public Iterator<Direction> randomDirections() {
		List<Direction> directions = new LinkedList<Direction>(Arrays.asList(Direction.values()));
		Collections.shuffle(directions);
		return directions.iterator();
	}
}
