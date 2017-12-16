package NetRun.Operation;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

public class CerebralCast extends Operation {
	public CerebralCast()
	{
	mOpName="Cerebral cast";
	mOpText="Corps looking for you.";
	}
	public void operate(Game current)
	{
		Random rng=new Random();
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCorpTurns()>1){
		int corpChoice=rng.nextInt(2);
		int playerChoice=0;
		if(current.getCredits()>1)playerChoice=(Demo.safeIntegerInput(current.mInputRead,0,2,"Corps feelers are out there.(corp has picked a number between 0-2\n"+
							 "On a 0 corp is just trying to find more about you, on a 1 corps just trying to mess up your things\n on a 2 corps gunning for you.\n"
							 +"Spend credits equal to a guess at that number.\n If you guess correctly you reduce your threat by that much and gain twice the investment"+
							 "\nGuess wrong and you get consequences."));
		switch(corpChoice){
		case 0:
			if(corpChoice==playerChoice)System.out.println("You guessed right. No gains however.");
			else {System.out.println("Corp sees all and knows all. Within a slightly less bounded range than before.");current.setCorpTurns(current.getCorpTurns()+4);}
			break;
		case 1:
			if(corpChoice==playerChoice){System.out.println("You guessed right. Lose a bit of threat and gain a couple credits.");current.setCorpTurns(current.getCorpTurns()-1);current.setCredits(current.getCredits()+1);}
			else {
				System.out.println("Corp finds something of yours.");
				current.setCredits(current.getCredits()-1);
				if(current.getProgram().size()>0)current.destroyProgram();
				else if(current.getResource().size()>0)current.destroyResource();
				}
			break;
		default:
				if(corpChoice==playerChoice){System.out.println("You guessed right. Lose some threat and gain 4 credits.");current.setCorpTurns(current.getCorpTurns()-2);current.setCredits(current.getCredits()+2);}
				else
				{
					System.out.println("Corp finds somepart of you.(Take 3 damage)");
					current.takeDamage(current);
					current.takeDamage(current);
					current.takeDamage(current);
					current.setCredits(current.getCredits()-1);
				}
				break;
		}}else System.out.println("Corp doesn't know enough about you to even begin feeling you out.");
		
	}
	
	
}
