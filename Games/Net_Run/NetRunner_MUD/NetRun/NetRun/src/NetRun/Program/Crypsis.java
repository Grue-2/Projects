package NetRun.Program;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Ice.Ice;

public class Crypsis extends Program {

	private static final long serialVersionUID = 1L;
	public Crypsis()
	{
		mProgramName="Crypsis";
		mProgramText="Can break anything, but costs alot of time and credits.";
		
	}
	public boolean programInteract(Game current,Ice ice)
	{
		int cost=ice.getStrength()*3;
		if((current.getCredits()>=cost)&&(current.getClicks()>0))
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend "+cost+" credits and a click to break the ice?(1 for yes, 2 for no): "))
			{
			case 1:				
				current.spendClick();
				current.setCredits(current.getCredits()-cost);
				System.out.println("Crypsis worked."+" "+ice.getName()+" is broken.");
				return true;
			default:	
				return false;
			}
			
		}
		else if(current.getClicks()==0)
		{
			System.out.println("You don't have the clicks left to use crypsis.");
			return false;
		}
		else
		{
			System.out.println("You don't have the resources to use crypsis right now.");
			return false;
		}
	}
}
