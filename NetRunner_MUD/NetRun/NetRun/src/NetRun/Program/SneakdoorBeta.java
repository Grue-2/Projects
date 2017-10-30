package NetRun.Program;

import java.util.Random;

import NetRun.Game;
import NetRun.Ice.Ice;

public class SneakdoorBeta extends Program{
	private static final long serialVersionUID = 1L;
	public SneakdoorBeta()
	{
		mProgramName="SneakdoorBeta";
		mProgramText="50% chance to avoid ice alltogether, but if you lose the gamble you can't use aonther breaker.";
		
	}
	public boolean programInteract(Game current,Ice ice)
	{
		Random rng=new Random();
		if(rng.nextInt(2)==0){System.out.println("Like a ghost.(success)");return true;}
		else{System.out.println("Whoops.(failed)");return false;}
	}
}
