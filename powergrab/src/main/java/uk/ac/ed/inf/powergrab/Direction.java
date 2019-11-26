package uk.ac.ed.inf.powergrab;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
* Defines 16 major compass directions and some related methods.
* <p>
* A Direction represents a major compass point.
* They are 16 enumeration constants from N to NNW and ordered in clockwise direction.
* This class also provides two methods, one for transfer an angle to a direction, one for iterating
* 16 directions in pseudorandom order. 
*	
* @author  Tai Yintao s1891075@ed.ac.uk
* @version 1.1
* @since   0.0
*/
public enum Direction {
	// 16 directions
	/**
	 * N direction
	 */
	N,
	
	/**
	 * NNE direction
	 */
	NNE,
	
	/**
	 * NE direction
	 */
	NE,
	
	/**
	 * ENE direction
	 */
	ENE,
	
	/**
	 * ENE direction
	 */
	E,
	
	/**
	 * ESE direction
	 */
	ESE,
	
	/**
	 * SE direction
	 */
	SE,
	
	/**
	 * SSE direction
	 */
	SSE,
	
	/**
	 * S direction
	 */
	S,
	
	/**
	 * SSW direction
	 */
	SSW,
	
	/**
	 * SW direction
	 */
	SW,
	
	/**
	 * WSW direction
	 */
	WSW,
	
	/**
	 * W direction
	 */
	W,
	
	/**
	 * WNW direction
	 */
	WNW,
	
	/**
	 * NW direction
	 */
	NW,
	
	/**
	 * NNW direction
	 */
	NNW;
	
	/**
	 * Find the closest direction of an angle.
	 * <p>
	 * Return the direction corresponding to the input angle.
	 * The angle is from North to some compass point in clockwise direction,
	 * expressed in radians. 
	 *
	 * @param  angle the angle from North in radians.
	 * @return the direction closest to the input angle.
	 */
	static public Direction angleToDirection(double angle) {
		int index = 0;
		
		// Transfer the angle into 0 ~ 2*PI, a unit circle
		angle = angle % (Math.PI * 2);
		if (angle < 0) angle = angle + 2 * Math.PI;
		
		// Each direction has a duration of PI / 8,
		// map the angle to a direction index
		index = (int)(angle / (Math.PI / 8) + 0.5);
		
		// The 16th direction is the 0th direction
		if (index == 16) index = 0;
		return Direction.values()[index];
	}
	
	/**
	 * Generate a iterator with 16 randomly sorted directions.
	 * <p>
	 * Return an iterator which iterates a randomly sorted direction list.
	 * The list has 16 directions from the Direction class with no repeats.
	 *
	 * @param  rand the pseudorandom generator used to shuffle the direction list.
	 * @return an iterator which can iterates the direction list.
	 */
	static public Iterator<Direction> randomDirections(Random rand) {
		// Create a linked list with 16 directions
		List<Direction> directions = new LinkedList<Direction>(Arrays.asList(Direction.values()));
		
		// Shuffle the list by the input Random object
		Collections.shuffle(directions, rand);
		return directions.iterator();
	}
}
