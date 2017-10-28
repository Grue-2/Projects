/**
 * @author jcrandall
 * @file Character.java
 * @date 2/27/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;

import java.util.Arrays;

/**
 * Parent class of Player, Rodent, and Pellet. Has a position array, because it felt
 * reasonable at the time. Mistakes were made.
 */
public abstract class Character implements GameActions{
	/**
	 * Represents the position of the character, { row, col }
	 */
	private int[] mPosition;
	/**
	 * gets the position of the character
	 * @return position of the character {row,col}
	 */
	public int[] getPosition() {
		return mPosition;
	}
	/**
 	*sets the position of the character 
 	* @param position of the character {row,col}
 	*/
	public void setPosition(int[] position) {
		mPosition = position;
	}
	/**
	 * Generates hash code for the field
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(mPosition);
		return result;
	}
	/**
	 * compares two objects for equality by comparing fields
	 * @return true for the same false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (!Arrays.equals(mPosition, other.mPosition))
			return false;
		return true;
	}
	/**
	 * parameterized constructor for children to inherit
	 * @param position int[] { row, col } to set newly instantiated object to
	 */
	protected Character(int[] position){mPosition=position;}
}
