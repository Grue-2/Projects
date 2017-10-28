package NetRun.Ice;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

public class Hunter extends Ice{
	public Hunter()
	{
		mStrength=4;
		mType=2;
		mSubType="Tracer";
		mName="Hunter";
		mDescription=".//run/hunter-tr/return=trueclient/sec2561IPv7(sentry)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		Random rng=new Random();
		int trace=rng.nextInt(6);
		switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to pay "+trace+" to avoid gaining attention?(1 for yes, 2 for no): "))
		{
		case 1:
			System.out.println("They were thrown off the trail for now.");
			current.setCredits(current.getCredits()-trace);
			break;
		default:
			System.out.println("Corp knows a bit more about you.");
			current.setCorpTurns(current.getCorpTurns()+4);
			break;
		}
		

		return true;
	}
}
