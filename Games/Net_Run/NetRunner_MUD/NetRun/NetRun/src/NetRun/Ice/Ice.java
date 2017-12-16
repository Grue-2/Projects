package NetRun.Ice;

import java.util.Random;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Program.AdSense;
import NetRun.Program.Program;

abstract public class Ice 
{
	protected int mStrength;
	protected int mType; // 0 barrier, 1 code, 2 sentry
	protected String mSubType; // "Bioroid" , "" <-- for no subtype
	protected String mName;
	protected String mDescription;
	private static final int ICE_POOL=14;
	
	
	
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public int getStrength() {
		return mStrength;
	}

	public void setStrength(int strength) {
		mStrength = strength;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		mType = type;
	}

	public String getSubType() {
		return mSubType;
	}

	public void setSubType(String subType) {
		mSubType = subType;
	}

	public static boolean encounterIce(Game current)
	{
		Ice x=randomIce();
		System.out.println("\nYou encountered: "+x.mName+" "+x.mDescription);
		boolean broken=false;
		
		if(x instanceof DataRaven)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Data Raven will know more about you if you continue even if you break past. Continue?(1 for yes, 2 for no): "))
			{
			case 1:
				System.out.println("The raven will report this to the corp.(+2 threat)");
				current.setCorpTurns(current.getCorpTurns()+2);
				break;
			default:
				System.out.println("The raven has warded you off for now.");
				return false;
			}
		}
		
		if(current.getProgram().size()>0)
		{
			boolean notOnlyAdSense=false;
			for(Program p:current.getProgram())if(!(p instanceof AdSense))notOnlyAdSense=true;
			if(notOnlyAdSense){
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"To attempt to use a Program please enter 1, 2 to skip: "))
			{
			case 1:
				int programChoice=Demo.safeIntegerInput(current.mInputRead,1,current.getProgram().size(),"To choose which program to use please enter 1 for the first and so on: ");
				broken=current.getProgram().get(programChoice-1).programInteractionShell(current,x);
				break;
			default:
				break;
			}
			}
		}
		
		if(broken)return true;
		else return x.triggerIce(current);
	}
	
	public static Ice randomIce()
	{
		Random rng=new Random();
		switch(rng.nextInt(ICE_POOL))
		{
			case 0:
				return new Rototurret();
			case 1:
				return new IceWall();
			case 2:
				return new Ichi1_0();
			case 3:
				return new Hunter();
			case 4:
				return new DataMine();
			case 5:
				return new NeuralKatana();
			case 6:
				return new DataRaven();
			case 7:
				return new HadriansWall();
			case 8:
				return new Enigma();
			case 9:
				return new WallOfStatic();
			case 10:
				return new Heimdall1_0();
			case 11:
				return new HungryHungryHippo();
			case 12:
				if(rng.nextInt(3)!=0)return new Archer();
			default:	
				return new Viktor1_0();
		}
	}
	
	abstract public boolean triggerIce(Game current);
	
	protected void bioroidDestroy(Game current)
	{
	  if(current.getProgram().size()>0){
		if(current.getClicks()>0)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend a click to avoid ichi 1.0 destroying a program?(1 for yes, 2 for no): "))
			{
			case 1:
				current.spendClick();
				System.out.println("Good you had time to avoid that. Would of ice'd your breaker.");
				break;
			default:	
				current.destroyProgram();
			}
		}else current.destroyProgram();
	  }
	}
	protected void bioroidDamage(Game current)
	{
		if(current.getClicks()>0)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend a click to avoid taking damage?(1 for yes, 2 for no): "))
			{
			case 1:
				current.spendClick();
				System.out.println("That would of hurt.");
				break;
			default:
				System.out.println(mName+" pokes you.");
				current.takeDamage(current);
				break;
			}
		}
		else {current.takeDamage(current);System.out.println(mName+" pokes you.");}
	}
	protected boolean bioroidEnd(Game current)
	{
		if(current.getClicks()>0)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend a click to avoid ending the run?(1 for yes, 2 for no): "))
			{
			case 1:
				current.setClicks(current.getClicks()-1);
				return true;
			case 2:
				System.out.println("As they say, the early bird gets past the bioroid.");
				return false;
			}
		}
		System.out.println("Bioroid severed connection to server.");
		return false;
		
	}
	/*
	protected void bioroidDamageAndTrace(Game current)
	{
		Random rng=new Random();
		int trace=rng.nextInt(3);
		
		if(current.getClicks()>0)
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend a click to avoid taking damage and threat?(1 for yes, 2 for no): "))
			{
			case 1:
				current.spendClick();
				System.out.println("That would of hurt.");
				break;
			default:
				System.out.println(mName+" pokes you.");
				current.takeDamage(current);
				break;
			}
		}
		else {current.takeDamage(current);System.out.println(mName+" pokes you.");}
	} */
	
}
