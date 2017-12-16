package NetRun.Operation;

import NetRun.Demo;
import NetRun.Game;

// should be changed to shakedown
public class Investigation extends Operation {
	public Investigation()
	{
	mOpName="\"Investigation\"";
	mOpText="We've been looking for you.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCredits()>3)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Wouldn't you like to pay 4 credits to make this go away?(1 for yes 2 for no): "))
			{
			case 1:
				System.out.println("We should really do this again sometime.");
				current.setCredits(current.getCredits()-4);
				return;
			case 2:
				System.out.print("Thats not the right awnswer. ");
				break;
			}
		}	
			System.out.println("THUMP. Be seeing you. (Take 3 damage, corp knows more about who you are)");
			int i=3;while(i--!=0)current.takeDamage(current);
			current.setCorpTurns((current.getCorpTurns()+10));		
	}
	
}
