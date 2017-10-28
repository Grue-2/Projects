/**
 * @author jcrandall
 * @file GameActions.java
 * @date 2/27/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;
/**
 * Interface enforces the <code>move</code> action in classes which implement it
 */
public interface GameActions {
	/**
	 * moves the object on the board
	 * @param board on which to move
	 */
	public abstract void move(Board board);
}
