package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;
import NetRun.Resource.RabbitHole;
import NetRun.Resource.Resource;

public class Vamp extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vamp()
	{
		mCardName="Vamp";
		mCardText="Make a run on HQ. If successful spend 50 credits. Corp loses all trace of you and skips its next turn. Takes one click and 0 credits to start.";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>1)
		{
		current.hitServer(2);
		current.spendClick();

		boolean victory=true;
		int i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][2])victory=Ice.encounterIce(current);
		if(victory)
		{
			System.out.println("Good. that'll keep things fluid for a while.");
			current.setCredits(current.getCredits()-50);
			current.setCorpTurns(0);
			int sumHole=0;
			for(Resource r:current.getResource())if(r instanceof RabbitHole);
			if(sumHole>3)sumHole=3;
			current.setCorpTurns(current.getCorpTurns()-2*sumHole);
			if(current.getConsole().getValue()==1)current.setCorpTurns(current.getCorpTurns()-5);
			current.vamped();
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
			System.out.println("Need 1 clicks to use this.");
			return false;
		}
	}
}
