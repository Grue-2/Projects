package NetRun.Program;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Ice.Ice;

public class Corroder extends Program{
	
	private static final long serialVersionUID = 1L;
	public Corroder()
	{
		mProgramName="Corroder";
		mProgramText="Breaks walls at extremely low cost";
	}
	public boolean programInteract(Game current,Ice ice)
	{
		int cost=ice.getStrength();
		if(ice.getType()==0&&(current.getCredits()>=cost))
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend "+cost+" credits to break the ice?(1 for yes, 2 for no): "))
			{
			case 1:				
				current.setCredits(current.getCredits()-cost);
				System.out.println("If at first you don't succeed, boost its strength and try again.-corroder instruction manual");
				return true;
			default:	
				return false;
			}
			
		}
		else 
		{
			System.out.println("Corroder only works on barriers or you don't have enough credits to break the ice.");
			return false;
		}
	}
	
}
