package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;

public class AccountSiphon extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AccountSiphon()
	{
		mCardName="Account Siphon";
		mCardText="Make a run on HQ. If successful gain 15 credits. Takes two clicks to do and increases threat slightly.";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>1)
		{
		current.hitServer(2);
		current.spendClick();
		current.spendClick();

		boolean victory=true;
		int i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][2])victory=Ice.encounterIce(current);
		if(victory)
		{
			System.out.println("Good. that'll keep things fluid for a while.");
			current.setCredits(current.getCredits()+15);
			current.setCorpTurns(current.getCorpTurns()+2);
			return true;
		}
		else 
		{
			System.out.println("Run ended.");
			return true;
		}
		}
		else 
		{
			System.out.println("Need 2 clicks to use this.");
			return false;
		}
	}
}
