package AI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AI {
	/*
	 * Failed AI - don't get how to do first turn movement really
	 * - can learn to avoid losses going second, not first
	 * - hasn't learned to try to win going second
	 */
	
	
	//private static final int AI_WIGGLE_FACTOR=2;
	private static final boolean printOdds=true;
	private int[][][] [][][] [][][] [][] movePrefs;
	private List<String> movesMade;
	public AI ai;
	private AI(){}
	private static synchronized AI generateAI(){
		return new AI();
	}
	public static void main(String args[]){
		AI ai=getInstance();int count=0,total=0;
		for(int a1=0;a1<3;a1++)
			for(int a2=0;a2<3;a2++)
				for(int a3=0;a3<3;a3++)
					for(int a4=0;a4<3;a4++)
						for(int a5=0;a5<3;a5++)
							for(int a6=0;a6<3;a6++)
								for(int a7=0;a7<3;a7++)
									for(int a8=0;a8<3;a8++)
										for(int a9=0;a9<3;a9++)
											for(int a10=0;a10<3;a10++)
												for(int a11=0;a11<3;a11++){
														int x=ai.movePrefs[a1][a2][a3][a4][a5][a6][a7][a8][a9][a10][a11];
														if(x==0)count++;
														total++;
														if(x!=0)System.out.println(x);
														//if(x!=2147483647)System.out.println(x);
													}
		System.out.println("\n0 entries: "+count);
		System.out.println("non0 entries: "+(total-count));
	}
	
	public static AI getInstance(){
		AI ai=generateAI();
		ai.aiStartGame();
		return ai;
	}
	private void copier(int[][] choices,int[][] _state,int i,int k){
		choices[i][k]=movePrefs[_state[0][0]+1]
				 [_state[0][1]+1]
				 [_state[0][2]+1]
				 [_state[1][0]+1]
			     [_state[1][1]+1]
				 [_state[1][2]+1]
			     [_state[2][0]+1]
			     [_state[2][1]+1]
				 [_state[2][2]+1]
				 [i][k];
	}
	public int[] makeMove(int[][] _state){
		//TODO: parse game state into move preferences
		int[][] choiceArray=new int[3][3];
		for(int i=0;i<3;i++){
			for(int k=0;k<3;k++){copier(choiceArray,_state,i,k);}	
		}
			
		//removing impossible options
		for(int i=0;i<3;i++)for(int k=0;k<3;k++){
			//System.out.println(i+" "+k+": "+choiceArray[i][k]);
			if(_state[i][k]!=0){
				choiceArray[i][k]=Integer.MIN_VALUE;
				//System.out.println("shouldnt proc");
			}
		}
		
		//int printMe=choiceArray[k][i],sum=1;
		//boolean neg=false;
		//if(printMe<0){sum=-1;printMe=-printMe;neg=true;}
		//while((printMe/=10)>0){if(neg)sum--;else sum++;}
		//System.out.print(sum+" ");
		
		
		if(printOdds){
			for(int i=0;i<3;i++){
				for(int k=0;k<3;k++){
					System.out.print(choiceArray[k][i]+"/");
				}	
				System.out.println("\n");
			}
		}
		
		//find max value and second max value
		int[] bestMoves=maxAndSecondMax(choiceArray),move=new int[2];
		//TODO: select best move + wiggle room		
		
		
		//if(new Random().nextInt(AI_WIGGLE_FACTOR)!=0)
		{move[0]=bestMoves[0];move[1]=bestMoves[1];}
		//else{move[0]=bestMoves[2];move[1]=bestMoves[3];}
		
		
		//TODO:add move to list of moves made this game
		//System.out.println(move[0]+" "+move[1]);
		if(move[0]<0){System.out.println("move error");System.exit(0);}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<3;i++)for(int k=0;k<3;k++){
			switch(_state[i][k]){
				case -1:
					sb.append("0");
					break;
				case 0:
					sb.append("1");
					break;
				default:
					sb.append("2");
			}
		}
		//System.out.println(""+move[0]+" "+move[1]);
		sb.append(""+move[0]+move[1]);
		movesMade.add(0,sb.toString());
		//movesMade.add(sb.toString());
		
		return move;
	}
	private int[] maxAndSecondMax(int[][] choices){
		int[] firstMax=findMax(choices,new int[]{-2,-2}); // -1,-1 should never proc
		int[] secondMax=findMax(choices,firstMax);
		//System.out.println("move: "+firstMax[0]+" "+firstMax[1]+" "+secondMax[0]+" "+secondMax[1]);
		return new int[]{firstMax[0],firstMax[1],secondMax[0],secondMax[1]};
	}
	private int[] findMax(int[][] choices,int[] exception){
		
		int max=Integer.MIN_VALUE,maxX=-1,maxY=-1;
		
		List<Integer> levelOptionsX=new ArrayList<Integer>();
		List<Integer> levelOptionsY=new ArrayList<Integer>();
		
		for(int i=0;i<3;i++)for(int k=0;k<3;k++){
			//System.out.println("max: "+max+" <? choices["+i+"]["+k+"]: "+choices[i][k]);
			
			//novelty clause ?? <-- wtf is this
			//if(choices[i][k]==0)return new int[]{i,k}; 
			if(!(i==exception[0]&&k==exception[1])&&max<choices[i][k]){
				max=choices[i][k];
				//maxX=i;maxY=k;
			}
		}
		for(int i=0;i<3;i++)for(int k=0;k<3;k++){
			if(!(i==exception[0]&&k==exception[1])&&max==choices[i][k]){
				levelOptionsX.add(i);
				levelOptionsY.add(k);
			}
		}
		//System.out.println(levelOptionsX.size());
		int randomChoiceOfEqualOptions=new Random().nextInt(levelOptionsX.size());
		maxX=levelOptionsX.get(randomChoiceOfEqualOptions);
		maxY=levelOptionsY.get(randomChoiceOfEqualOptions);
		//System.out.println();
		if(maxX<0||maxY<0)return exception;
		return new int[]{maxX,maxY};
	}
	
	public void aiStartGame(){
		movesMade=new ArrayList<>();
		try(ObjectInputStream in=new ObjectInputStream(new FileInputStream("preferences.ser"))){
			movePrefs=(int[][][] [][][] [][][] [][])in.readObject();
		} catch (Exception e) {
			System.out.println("Save file could not be loaded. Defaulting knowledge base");
			movePrefs=new int[3][3][3] [3][3][3] [3][3][3] [3][3];// auto defaulted to 0's	
			for(int a1=0;a1<3;a1++)
				for(int a2=0;a2<3;a2++)
					for(int a3=0;a3<3;a3++)
						for(int a4=0;a4<3;a4++)
							for(int a5=0;a5<3;a5++)
								for(int a6=0;a6<3;a6++)
									for(int a7=0;a7<3;a7++)
										for(int a8=0;a8<3;a8++)
											for(int a9=0;a9<3;a9++)
												for(int a10=0;a10<3;a10++)
													for(int a11=0;a11<3;a11++)
														movePrefs[a1][a2][a3][a4][a5][a6][a7][a8][a9][a10][a11]=0;
		} 

	}
	public synchronized void aiEndGame(Integer didIWin){aiEndGame(didIWin,true);}
	//1 for win, -1 for loss
	public synchronized void aiEndGame(Integer didIWin,boolean save){
		//TODO: use list of moves, adjust based on win or lose preferences
		//System.out.println("updating");

		//System.out.println(movesMade.size());
		//tag:if(didIWin!=0
				// testing negative only learning
			   //&&didIWin<0
		//	   )
		//	{
			
			//System.out.println("updating");
			
			//if(didIWin<0)System.out.println("working"+" "+didIWin);
			//if(didIWin<0)didIWin*=2;
			//if(didIWin>0)break tag;
			//System.out.println(movePrefs.length);
			//int update=2;
			
			/*
			 * TODO: solve winrate equilibrium learning problem
			 * once winrate in equilibrium with lose rate doesn't change ai.
			 * 
			 * interestingly only 2 step distance AI plays worse dunno why.
			 * 
			 * if I set it to carry heavily onto other turns it goes down to 89% winrate v random
			 * 
			 */
			int distance=1000; // disabled all jumps for now
			
			//System.out.println("Moves made: "+movesMade.size());
			for(String move:movesMade){ // ** Currently edited first two moves made 
				
				//if(update<0)break;
				//System.out.println(move);
			//String move=movesMade.get(0);
				if(overflowCheck(movePrefs[Integer.parseInt(move.substring(0,1))][Integer.parseInt(move.substring(1,2))][Integer.parseInt(move.substring(2,3))] 
						[Integer.parseInt(move.substring(3,4))][Integer.parseInt(move.substring(4,5))][Integer.parseInt(move.substring(5,6))]
								[Integer.parseInt(move.substring(6,7))][Integer.parseInt(move.substring(7,8))][Integer.parseInt(move.substring(8,9))]
						 [Integer.parseInt(move.substring(9,10))][Integer.parseInt(move.substring(10))],distance*didIWin))
					{movePrefs[Integer.parseInt(move.substring(0,1))][Integer.parseInt(move.substring(1,2))][Integer.parseInt(move.substring(2,3))] 
							[Integer.parseInt(move.substring(3,4))][Integer.parseInt(move.substring(4,5))][Integer.parseInt(move.substring(5,6))]
									[Integer.parseInt(move.substring(6,7))][Integer.parseInt(move.substring(7,8))][Integer.parseInt(move.substring(8,9))]
							 [Integer.parseInt(move.substring(9,10))][Integer.parseInt(move.substring(10))]+=distance*didIWin;}
				distance/=1000;
			}
			if(save){
				save();
			}
			else{
				movesMade=new ArrayList<>();
			}
	//	}
	}
	public void save(){
		try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("preferences.ser"));){
			out.writeObject(movePrefs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean overflowCheck(int fore,int add){
		//System.out.println("Add is: "+add);
		//System.out.println("fore+add<fore: "+(fore+add)+"<"+fore);
		if(add>0){
			if(fore+add>fore){
					//System.out.println("check+");
					return true;
				}
		}
		else
			if(fore+add<fore){
					//System.out.println("check--");
					return true;
				}
		return false;
	}
	
	
}
