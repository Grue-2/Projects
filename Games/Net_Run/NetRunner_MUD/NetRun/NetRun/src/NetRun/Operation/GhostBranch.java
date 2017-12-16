package NetRun.Operation;

import NetRun.Game;

public class GhostBranch extends Operation {
	public GhostBranch()
	{
	mOpName="Ghost Branch";
	mOpText="It diddn't look like the headquarters of a multi-billion dollar company. Probably becuase it wasn't.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCorpTurns()>15){current.loseAgenda();System.out.println("Lose last agenda scored.");}
		else System.out.println("If the corps had a better grip of who you were you'd lose the last agenda you scored. Your fine for now though.");
	}
	
}
