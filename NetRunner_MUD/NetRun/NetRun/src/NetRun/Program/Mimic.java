package NetRun.Program;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Ice.Ice;

public class Mimic extends Program {
	private static final long serialVersionUID = 1L;
	public Mimic()
	{
		mProgramName="Mimic";
		mProgramText="cheaply breaks weak sentries. But can't handle really observant ones.";
		
	}
	public boolean programInteract(Game current,Ice ice)
	{
		int mimicPower=4;
		//if(personal touch )++
		int cost=ice.getStrength();
		if(ice.getType()==2&&(current.getCredits()>=cost)&&(ice.getStrength()<mimicPower))
		{
			switch(Demo.safeIntegerInput(current.mInputRead,1,2,"Would you like to spend "+cost+" credits to break the ice?(1 for yes, 2 for no): "))
			{
			case 1:				
				current.setCredits(current.getCredits()-cost);
				System.out.println("Mimic can't slice through the thickest of knots. But it looks like it can");
				return true;
			default:	
				return false;
			}
		}
		else 
		{
			System.out.println("Mimic only works on weak Sentries or you don't have enough credits to break the ice.");
			return false;
		}
	}
}
