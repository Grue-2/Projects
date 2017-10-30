package NetRun.Operation;

import NetRun.Game;

public class CorporateRetreat extends Operation {
	public CorporateRetreat()
	{
	mOpName="Corporate Retreat";
	mOpText="This job is too stressful.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		System.out.println("Corp loses a turn if able. And is slightly more laid back.");
		current.cClick(-1);
	}
	
}
