package NetRun.Operation;

import NetRun.Game;

public class ClosedAccounts extends Operation
{
	public ClosedAccounts()
	{
	mOpName="Closed Accounts";
	mOpText="That shouldn't be there $ rm -rf";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCorpTurns()>5){current.setCredits(current.getCredits()-5);System.out.println(("You lose 5 credits"));}
		else System.out.println("They don't know enough about who you are to do anything yet.");
	}
	
	
}
