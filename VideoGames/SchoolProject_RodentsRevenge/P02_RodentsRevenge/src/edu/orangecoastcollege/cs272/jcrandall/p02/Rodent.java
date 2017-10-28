/**
 * @author jcrandall
 * @file Rodent.java
 * @date 2/28/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;

import java.util.Random;

/**
 * Represents a Rodent Character, child of <code>Character</code> has its own movement, to string, and constructor methods.
 *
 */
public class Rodent extends Character 
{
	/**
	 * Randomly moves rodent
	 * @param board which will be moved on
	 */
	@Override public void move(Board board)
	{
		Random rng=new Random();
		while(true)
		{
		switch(rng.nextInt(8))
		{
		case 0:
			successfulMove(board,-1,-1);
			break;
		case 1:	
			successfulMove(board,0,-1);
			break;
		case 2:
			successfulMove(board,-1,0);
			break;
		case 3:
			successfulMove(board,1,1);
			break;
		case 4:	
			successfulMove(board,-1,1);
			break;
		case 5:
			successfulMove(board,1,-1);
			break;
		case 6:
			successfulMove(board,0,1);
			break;
		default:	
			successfulMove(board,1,0);
			break;
		}
		break;
		}
	}
	/**
	 * ( before knowing wrap around rule -checks to see if a move is legal, and if it is performs the move, then returns if it was succesful or not)
	 * Now moves mice in direction, and if they're not on the board wraps them around.
	 * ( adding wrap around makes this game sooo much harder. That said the movement does feel more Rodent'y.
	 * @param board to move on 
	 * @param changeInRow row direction to move
	 * @param changeInCol col direction to move
	 */
	private void successfulMove(Board board,int changeInRow,int changeInCol)
	{
		int[] x={this.getPosition()[0]+changeInRow,this.getPosition()[1]+changeInCol};
		this.setPosition(x);
		
		if(!board.isInBounds(this.getPosition()[0],this.getPosition()[1]))
		{
			if(getPosition()[0]<0)
			{
				int[] y={getPosition()[0]+board.getRows(),getPosition()[1]};
				setPosition(y);
			}
			else if(getPosition()[0]>=board.getRows())
			{
				int[] y={getPosition()[0]-board.getRows(),getPosition()[1]};
				setPosition(y);
			}
			
			if(getPosition()[1]<0)
			{
				int[] y={getPosition()[0],getPosition()[1]+board.getCols()};
				setPosition(y);
			}
			else if(getPosition()[1]>=board.getCols())
			{
				int[] y={getPosition()[0],getPosition()[1]-board.getCols()};
				setPosition(y);
			}
			
		}
	}
	
	/**
	 * returns a string representation of a rodent
	 * @return string representation of a rodent
	 */
	@Override public String toString()
	{
		return "R";
	}
	/**
	 * instantiates a new rodent character with a position
	 * @param pos of the new rodent
	 */
	public Rodent(int[] pos)
	{
		super(pos);
	}
	
	
}
