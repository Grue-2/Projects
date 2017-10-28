package NetRun.Ice;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

public class DataRaven extends Ice{
	public DataRaven()
	{
		mStrength=4;
		mType=2;
		mSubType="Tracer";
		mName="Data Raven";
		mDescription="Caw! caw!(type sentry)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		Random rng=new Random();
		int trace=rng.nextInt(5);
		if(current.getResource().size()>0){
		if(current.getCredits()>trace){
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to pay "+trace+" credits to avoid the raven destroying a Resource?(1 for yes, 2 for no): "))
			{
			case 1:
				System.out.println("Least your stuff won't get pecked at.");
				current.setCredits(current.getCredits()-trace);
				return true;
			default:
				current.destroyResource();
				break;
			}
			}else {System.out.println("Caw!");current.destroyResource();}}
		trace=rng.nextInt(5);
		
		if(current.getCredits()>trace){
		switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to pay "+trace+" credits to avoid the corp knowing more about you?(1 for yes, 2 for no): "))
		{
		case 1:
			System.out.println("The raven diddn't find anything more on you.");
			current.setCredits(current.getCredits()-trace);
			return true;
		default:
			break;
		}
		}
		System.out.println("The raven has more to report. Probably for crackers.(threat +4)");
		current.setCorpTurns(current.getCorpTurns()+4);
		return true;
	}
}
