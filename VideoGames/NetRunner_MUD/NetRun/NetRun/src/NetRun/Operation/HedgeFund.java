package NetRun.Operation;

import NetRun.Game;

public class HedgeFund extends Operation {
	public HedgeFund()
	{
	mOpName="Hedge Fund";
	mOpText="Hedge Fund. Noun. An ingenious device by which the rich get richer even while every other poor SOB is losing his shirt.-anrchR";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		System.out.println("Corps can do just a little bit more and are just a little bit stronger.");
		current.cClick(1);
	}
	
	
}
