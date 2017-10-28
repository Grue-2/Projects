package NetRun.Operation;

import NetRun.Game;

public class BioticLabor extends Operation
{
	public BioticLabor()
	{
	mOpName="Biotic Labor";
	mOpText="Corperate workers have been given mandatory upgrades. (Corp takes two extra actions this turn)";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		current.cClick(2);
	}
	
	
}
