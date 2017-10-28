/**
 * @author jcrandall
 * @file Player.java
 * @date 2/27/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;


/**
 * Represents a Player Character, child of <code>Character</code> has its own movement, to string, and constructor methods.
 *
 */
public class Player extends Character 
{
	/**
	 * Prompts the player for movement and moves on the board.
	 * @param board which will be moved on
	 */
	@Override public void move(Board board)
	{
		System.out.println("What action would you like the player to take?\n"
				+ "U: Move Up\n"
				+ "D: Move Down\n"
				+ "L: Move Left\n"
				+ "R: Move Right\n"
				+ "P: Pellet");
		switch(board.readToLowerCase())
		{
		case "u":
			attemptMove(board,-1,0,"UP");
			break;
		case "d":
			attemptMove(board,1,0,"DOWN");
			break;
		case "l":
			attemptMove(board,0,-1,"LEFT");
			break;
		case "r":
			attemptMove(board,0,1,"RIGHT");
			break;
		case "p":
			board.addPellet(this.getPosition()[0],this.getPosition()[1]);
			break;
		default:
			System.out.println("Unrecognized action, please enter U, D, L, R, or P. Player loses Turn.");
			break;
		}	
	}
	/**
	 * Attempts to move player. If player can't move prints out message that player has lost a turn and hasn't moved.
	 * @param board on which player is moving
	 * @param changeInRow change in players row
	 * @param changeInCol change in players col
	 * @param direction String of direction to print to the player if they can't move
	 */
	private void attemptMove(Board board,int changeInRow,int changeInCol,String direction)
	{
		if(board.isInBounds(this.getPosition()[0]+changeInRow,this.getPosition()[1]+changeInCol))
		{
			int[] x={this.getPosition()[0]+changeInRow,this.getPosition()[1]+changeInCol};
			this.setPosition(x);
		}else System.out.println("Player current cannot move "+direction+" and loses turn.");
	}
	
	
	/**
	 * returns a string representation of the player
	 * @return string representation of the player
	 */
	@Override public String toString()
	{
		return "P";
	}
	/**
	 * instantiates a new player character with a position
	 * @param pos of the new player
	 */
	public Player(int[] pos)
	{
		super(pos);
	}
	
	
}
