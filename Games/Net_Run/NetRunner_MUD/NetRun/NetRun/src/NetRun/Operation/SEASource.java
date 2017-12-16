package NetRun.Operation;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

public class SEASource extends Operation {
	public SEASource()
	{
	mOpName="SEA Source";
	mOpText="The SEA tipped us off to some suspicious activity.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		Random rng=new Random();
		int trace=rng.nextInt(10);
		switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend "+trace+" credits to avoid the corp knowing more about you?(1 for yes, 2 for no): "))
		{
		case 1:
			System.out.println("Well least nobodies the wiser.");
			current.setCredits(current.getCredits()-trace);
			break;
		default:	
			current.setCorpTurns((current.getCorpTurns()+3));
			System.out.println("Corp has a better sense of who/where you are.");
			break;
		}
	}
	
}
