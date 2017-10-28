package AI;

import java.util.Random;

public class Trainer {
	/*
	 * Apperntly poor practice can deeply reinforce horrible moves? , maxs / mins before statistical gains matter ??
	 * 
	 * TODO: funny the debug code utterly destroys its speed
	 * 
	 */
	
	
	/*
	 * Under negative only learning , learns only to draw games going second
	 * TODO: maybe I have to weigh draws ??
	 */
	
//	private static final boolean printing=false;
//	private static final boolean forcedTurn=false;
//	private static final boolean forcedTurnToFirst=false;
	
	private static final boolean aiGame=true;
	private static boolean notEnded;
	private static int [][] state;
	private static boolean aiIsX;
	private static AI ai=AI.getInstance();//,ai2=AI.getInstance();
	private static boolean xTurn;
	private static Random rng=new Random();
	private static Trainer trainer=new Trainer();
	private static int wins=0,wins2=0,draws=0,draws2=0;
	
	public static void main(String...args){
		// start training at turn 8 for a million goes
		// start raining at turn 7 for a million goes...
		
		//train(8,1);
		for(int i=8,k=(int)1.5e7;i>=0;i--,k*=1){train(i,k);System.out.println("step "+i+" done");}
	}
	// 0 is turn 1
	private static void train(int startingTurn,int iterations){
		//int iterations=(int)1e7;
//		if(printing)iterations=1;
//		else iterations=100000000;
		int ithold=iterations;
		while(iterations-->0){
			if(iterations%1000000==0)System.out.println(iterations);
			notEnded=true;
			state=new int[3][3];
			aiIsX=false;
			if(rng.nextInt(2)==0)aiIsX=true;
			xTurn=true;
			for(int i=0;i<startingTurn;i++){
				trainer.takeTurnRandom();
				if(!notEnded)break;
			}
//			if(forcedTurn)aiIsX=forcedTurnToFirst;
			
			while(notEnded){
				
			if(aiIsX){
				trainer.aiTakeTurn();
				//System.out.println("working");
//				if(printing){System.out.println("ai move");trainer.printGame();}
			}
			else trainer.takeTurnRandom();
//				if(printing)trainer.printGame();
				//System.out.println("reached");
				if(notEnded){
					trainer.aiTakeTurn();
//					if(printing){System.out.println("ai move");trainer.printGame();}
				}
			}
			
			//if(iterations%10000==0){System.out.println("winrate= "+(wins2/(10000-draws2/100))+"%"+" iterations left: "+iterations);wins2=0;draws2=0;ai.save();}
			//System.out.println("Iterations left: "+(iterations+1));
		}
			ai.save();
			System.out.println("done.");
			System.out.println(wins+" "+(ithold-draws));
			//System.out.println("winrate= "+(((wins)/(ithold-draws))*100)+"%");
	}
	
	
	
	private static void printGame(){
		for(int i=0;i<3;i++){
				for(int k=0;k<3;k++)System.out.print(state[i][k]!=-1?state[i][k]==0?" 0":" 1":"-1");
				System.out.println();
			}
		System.out.println();
	}
	
	private void aiTakeTurn(){
		int[] move=ai.makeMove(state);
		takeTurn(move[0],move[1]);
	}
	private void takeTurnRandom(){
		int i,k;
		do{i=rng.nextInt(3);k=rng.nextInt(3);testVictory();//System.out.println("i: "+i+" K: "+k);
		}
		while(state[i][k]!=0);
		//System.out.println("random move: "+i+" "+k);
		takeTurn(i,k);
	}
	
	public void takeTurn(int i,int k){
		if(notEnded){
			if(xTurn){
				state[i][k]=1;
			}
			else{
				state[i][k]=-1;
			}
			xTurn=!xTurn;
			testVictory();
		}
	}
	
	public void testVictory(){
		int sumDiag=0,sumDiag2=0;
		boolean noneEmpty=true;
		for(int i=0;i<3;i++){
			int sumRow=0,sumCol=0;
			for(int k=0;k<3;k++){
				sumRow+=state[i][k];
				sumCol+=state[k][i];
				if(state[i][k]==0)noneEmpty=false;
			}
			if(sumRow==3){win("X");if(aiGame)return;}
			else if(sumRow==-3){win("O");return;}
			else if(sumCol==3){win("X");return;}
			else if(sumCol==-3){win("O");return;}
			sumDiag+=state[i][i];
			sumDiag2+=state[2-i][i];
		}
			if(sumDiag==3){win("X");return;}
			else if(sumDiag==-3){win("O");return;}
			else if(sumDiag2==3){win("X");return;}
			else if(sumDiag2==-3){win("O");return;}
			else if(noneEmpty)win("Nobody");
	}
	
	private void win(String winner){
		notEnded=false;
		if(aiGame){
			if(winner.equals("X")){
				if(aiIsX){ai.aiEndGame(1,false);wins++;wins2++;}
				else {ai.aiEndGame(-1,false);}
			}
			else if((winner.equals("X"))){
				if(aiIsX){ai.aiEndGame(-1,false);}
				else {ai.aiEndGame(1,false);wins++;wins2++;}
			}
			else
				{ai.aiEndGame(0,false);draws++;draws2++;}
		}
	}
	
}
