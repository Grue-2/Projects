package NetRun.Program;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Ice.Ice;

public class GordianBlade extends Program{
	private static final long serialVersionUID = 1L;
	public GordianBlade()
	{
		mProgramName="Gordian Blade";
		mProgramText="Breaks codegates cheaply.";
		
	}
	public boolean programInteract(Game current,Ice ice)
	{
		int cost=ice.getStrength();
		if(ice.getType()==1&&(current.getCredits()>=cost))
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,1,"Would you like to spend "+cost+" credits to break the ice?(1 for yes, 2 for no): "))
			{
			case 1:				
				current.setCredits(current.getCredits()-cost);
				System.out.println("It can slice through the thickest of knots.(Success)");
				return true;
			default:	
				return false;
			}
			
		}
		else 
		{
			System.out.println("Gordian blade only works on codegates or you don't have enough credits to break the ice.");
			return false;
		}
	}
}
