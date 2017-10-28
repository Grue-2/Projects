/*  
 * Crandall, Josue
 * CS A170
 * November 16, 2016
 * 
 * IC #21 - Super Hacker Challenge
 */


package edu.orangecoastcollege.jcrandall.ic21;

import java.util.Arrays;
import java.util.Scanner;

public class SuperHackerChallenge {

	 public static final int ROWS=9;
	 public static final int COLS=9;
	 
	 public static int[][] initialPuzzle = new int[ROWS][COLS];
	 public static int[][] solvedPuzzle = new int[ROWS][COLS];	    
	 public static int[][] workingPuzzle=new int[ROWS][COLS];
	 

	    
	 public static void main(String[] args)
	    {
		 // generate completed puzzle ( hopefully in a strange way )
		
		 while(solvedPuzzle[ROWS-1][COLS-1]==0){
		   for(int i=0;i<ROWS;i++)for(int i2=0;i2<COLS;i2++)
		   {   
			 int count=0;
			 while(true)
			 {
				 solvedPuzzle[i][i2]=(int)(Math.random()*ROWS+1);
				 int[] a=new int[ROWS+COLS-2];
				 
				 for(int k=0;k<ROWS-1;k++)
				 {
					 if(k!=i2)a[k]=solvedPuzzle[i][k];
				 }
				 for(int k=0;k<COLS-1;k++)
				 {
					 if(k!=i)a[k+ROWS-1]=solvedPuzzle[k][i2];
				 }
				 
				 int breakInt=0;
				 
				 for(int k=0;k<a.length;k++)
				 {
					 if(solvedPuzzle[i][i2]==a[k])breakInt++;
				 }
				 count++;
				 if(count==1000)break; // stuck fixer
				 if(breakInt!=0)continue;
				 else break;		 
			 }
		   }
		 }
		 //setup initial puzzle ( hopefully in a strange way )
		 // non scaling though, I don't know the equations for this to scale, although I guess with enough time I could figure it out ( I did solve it for range 1-5, and guessed for 9x9, generalized though I'd really have to sit on it for a while.
		 for(int i=0;i<ROWS;i++)for(int k=0;k<COLS;k++)initialPuzzle[i][k]=solvedPuzzle[i][k];
		 int coin=(int)(Math.random()*2+1);
		 switch(coin)
		 {
		 case 1:
			 int[][] x={{1,0,0,1,0,1,0,0,1},{1,0,1,0,0,0,1,0,1},{0,1,0,0,1,0,0,1,0},
					    {0,0,1,1,0,1,1,0,0},{1,0,0,0,0,0,0,0,1},{0,0,1,1,0,1,1,0,0},
					    {0,1,0,0,1,0,0,1,0},{1,0,1,0,0,0,1,0,1},{1,0,0,1,0,1,0,0,1}};
			 keepOnes(x);
			 break;
		 case 2:
			 int[][]x2={{1,1,0,0,1,0,0,1,1},{0,0,1,0,0,0,1,0,0},{0,1,0,1,0,1,0,1,0},
					    {1,0,0,1,0,1,0,0,1},{0,0,1,0,0,0,1,0,0},{1,0,0,1,0,1,0,0,1},
					    {0,1,0,1,0,1,0,1,0},{0,0,1,0,0,0,1,0,0},{1,1,0,0,1,0,0,1,1}};
			 keepOnes(x2);
			 break;
		 }
		 
		 
		 
		 // game starts
	    	System.out.println("Sudoku Game:\nThe puzzle is:");
	        resetPuzzle();
	        printPuzzle();
	        int userRow,userCol,value;
	        Scanner console=new Scanner(System.in);
	        while(!gameIsWon()){
	        System.out.println("What would you like to do?\nSet a Square (S), Reset the Puzzle (R) or Quit(Q)");
	        String action=console.next().toUpperCase();
	        switch(action)
	        {
	            case "S":
	                System.out.println("Please enter row and column:");
	                userRow=console.nextInt()-1;
	                userCol=console.nextInt()-1;
	                System.out.println("What should the value (1-9) be?");
	                value=console.nextInt();
	                if(initialPuzzle[userRow][userCol]==0)workingPuzzle[userRow][userCol]=value;
	                else System.out.println("Sorry that space can't be changed, please enter another space.");
	                break;
	            case "R":
	                resetPuzzle();
	                break;
	            case "Q":
	                System.out.println("Thank you for playing.");
	                console.close();
	                System.exit(0);
	                break;
	            case "C":
	            	cheat();
	            	break;
	        }
	        printPuzzle();
	        }
	        console.close();
	        System.out.println("\n\n\nC*O*N*G*R*A*T*U*L*A*T*I*O*N*S, you WON Sudoku!!!");
	    }
	 	public static void keepOnes(int[][] a)
	 	{
	 		for(int i=0;i<ROWS;i++)for(int k=0;k<COLS;k++)if(a[i][k]==0)initialPuzzle[i][k]=0;
	 	}
	    public static void resetPuzzle()
	    {
	        for(int i=0;i<ROWS;i++)for(int k=0;k<COLS;k++)workingPuzzle[i][k]=initialPuzzle[i][k];
	    }
	    
	    public static void printPuzzle()
	    {
	        System.out.println(" C  1  2  3  4  5  6  7  8  9\n"+"R  --------------------------");
	        for(int i=0;i<ROWS;i++)
	        {
	            System.out.print((i+1)+"  |");
	            for(int k=0;k<COLS;k++)
	                System.out.print(((workingPuzzle[i][k])==0?".":workingPuzzle[i][k])+"  "); // sadly neat characters that work on the school computers don't seem to on my home computer.
	            {
	            }
	            System.out.println("");
	        }
	        System.out.println("");
	    }
	    
	    public static boolean gameIsWon()
	    {
	    			int sum=0;
	    			for(int i=0;i<ROWS;i++)if(Arrays.equals(workingPuzzle[i],solvedPuzzle[i]))sum++;
	    			return sum==ROWS;
	    }
	    private static void cheat()
	    {
	    	for(int i=0;i<ROWS;i++)for(int k=0;k<COLS;k++)workingPuzzle[i][k]=solvedPuzzle[i][k];
	    }
	    
	}
