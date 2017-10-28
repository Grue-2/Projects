/**
 * @author jcrandall
 * @file Board.java
 * @date 3/9/2017
 * @version OCC CS272 Spring 2017 Tuesday evening
 */

package edu.orangecoastcollege.cs272.jcrandall.p02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Represents board where game is played. Has a player, a list of rodent objects, a list of pellet objects
 * and the number of rows/cols in ghe game.
 */
public class Board {
	/*
	 * represents the player character
	 */
	private Player mPlayer;
	/**
	 * List holding the Rodents in the game
	 */
	private List<Character> mLstRodent;
	/**
	 * List holding the pellets in the game
	 */
	private List<Character> mLstPellet;
	/**
	 * number of rows in the game as an int
	 */
	private int mRows;
	/**
	 * number of columns in the game as an int
	 */
	private int mCols;
	/**
	 * Scanner to use for player input, closes on game over method finding that the game is indeed over
	 */
	private Scanner mCS;// package private
	/**
	 * Parameterized constructor instantiating a new Board.
	 * @param rows number of rows in the game
	 * @param cols number of cols int he game
	 * @param numRodents starting numbmer of rodents
	 */
	public Board(int rows,int cols,int numRodents)
	{		
		mPlayer=new Player(new int[]{rows-1,cols-1});
		mLstPellet=new ArrayList<>();
		mLstRodent=new ArrayList<>();
		mCS=new Scanner(System.in);
		mRows=rows;
		mCols=cols;
		OUTER_LOOP:for(int i=0;i<rows;i++)for(int k=0;k<cols;k++)
		{
			mLstRodent.add(new Rodent(new int[]{i,k}));
			numRodents--;
			if(numRodents==0)break OUTER_LOOP;
		}
		
	}
	/**
	 * Uses the scanner object to read a line in , and returns the string read to lower case.
	 * @return String read from player input as lower case
	 */
	String readToLowerCase()
	{
		return this.mCS.nextLine().toLowerCase();
	}
	/**
	 * Adds a pellet to the board, pellets can overlap.
	 * @param row location of pellet
	 * @param col location of pellet
	 */
	public void addPellet(int row,int col)
	{
		mLstPellet.add(new Pellet(new int[]{row,col}));
	}
	/**
	 * Checks to see if location is outside of the boards range.
	 * @param row to check to see if position is within
	 * @param col to see if position is within
	 * @return true if lcoation given is inside of the board, false otherwies
	 */
	public boolean isInBounds(int row,int col)
	{
		if((row<mRows&&row>=0)&&(col<mCols&&col>=0))return true;
		else return false;
	}
	/**
	 * Checks if player is alive.
	 * @return true if player is alive, false if player is "dead" ( null player object )
	 */
	public boolean isPlayerAlive()
	{
		if(mPlayer==null)return false;
		return true;
	}
	/**
	 * Checks to see if player is dead, or if there's no Rodents left.
	 * @return true if player is dead or no rodents left, false otherwise
	 */
	public boolean isGameOver()
	{
		if((!isPlayerAlive())||mLstRodent.size()==0){mCS.close();return true;}
		else return false;
	}
	/**
	 * 
	 * This was the function I choose to use lambdas for
	 * 
	 * Moves the game along a turn, with the player moving first, and then the rodents moving
	 * then checking to see if rodent is on player to kill them, then if the rodent is on a
	 * pellet to kill the pellet and the rodent.
	 * 
	 * This ordering disallows the player to camp on top of pellets.
	 */
	public void moveAllCharacters()
	{
		mPlayer.move(this);
		int posRow=mPlayer.getPosition()[0], posCol=mPlayer.getPosition()[1];
		RODENT_LOOP:for(int i=0;i<mLstRodent.size();i++)
		{	
			mLstRodent.get(i).move(this);
			int row=mLstRodent.get(i).getPosition()[0],col=mLstRodent.get(i).getPosition()[1];
			if(posRow==row&&posCol==col)mPlayer=null;
			
			for(int k=0;k<mLstPellet.size();k++){	
				if(mLstPellet.get(k).getPosition()[0]==row&&mLstPellet.get(k).getPosition()[1]==col)
				{
					mLstPellet.remove(k);
					mLstRodent.remove(i);i--;
					continue RODENT_LOOP;
				}	
			}	
		}
	}
	/**
	 * Gets a String representing the  current state of the game.
	 * @return string representation of the game
	 */
	public String toString()
	{
		StringBuilder result=new StringBuilder();
		for(int i=0;i<mRows;i++){
			for(int k=0;k<mCols;k++)
		{	
			int numberRodents=numSamePosition(mLstRodent,i,k);
			boolean hasPellet=numSamePosition(mLstPellet,i,k)>0;	
			// have to check if player is alive or risk bombing because position calls on null are not valid
			if(isPlayerAlive()&&mPlayer.getPosition()[0]==i&&mPlayer.getPosition()[1]==k)result.append(mPlayer.toString());
			else if(hasPellet)result.append("*");
			else if(numberRodents>0)
			{
				if(numberRodents==1)result.append("R");
				else result.append(numberRodents);
			}
			else result.append(".");
		}
		result.append("\n");
	}
		
		result.append(mLstRodent.size()+" rodents left to evict.");
		return result.toString();
	}
	/**
	 * Method takes a list of characters and returns those which had the position i , k 
	 * @param c list of characters to filter
	 * @param i row position of to filter for
	 * @param k col position to filter for
	 * @return character list with the position {i.k} from the original list
	 */
	private int numSamePosition(List<Character> c,int i,int k)
	{
		return c.stream().filter(d->(d.getPosition()[0]==i&&d.getPosition()[1]==k)).collect(Collectors.toList()).size();
	}
	/**
	 * gets number of rows
	 * @return number of rows
	 */
	public int getRows() {
		return mRows;
	}
	/**
	 * gets number of columns
	 * @return number of columns
	 */
	public int getCols() {
		return mCols;
	}
	
	
	
	
}
