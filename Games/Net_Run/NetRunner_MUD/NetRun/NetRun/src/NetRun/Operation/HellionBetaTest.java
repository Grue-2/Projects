package NetRun.Operation;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

public class HellionBetaTest extends Operation{
	public HellionBetaTest()
	{
	mOpName="Hellion beta test";
	mOpText="Um.. Meow?";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		
	   if(current.getResource().size()!=0){
		if(current.getCorpTurns()>3)
		{
			Random rng=new Random();
			int trace=rng.nextInt(10);
			if(current.getCredits()>=trace)
			{
				switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to pay "+trace+" credits to avoid your resources getting eaten?(1 for yes, 2 for no): "))
				{
				case 1:
					System.out.println("Well your stuff is safe for now.");
					current.setCredits(current.getCredits()-trace);
					return;
				default:
					break;
				}
			}
			if(current.getResource().size()>0)System.out.println("Hey that not free you know...(Resource clawed.)");
			current.destroyResource();
		}else System.out.println("They don't know enough about who you are to do anything yet.");
	   }else System.out.println("You heard some cats out last night. Nothing important though.");
	}
	
	
}
