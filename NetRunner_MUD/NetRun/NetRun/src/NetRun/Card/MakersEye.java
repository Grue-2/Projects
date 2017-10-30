package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;

public class MakersEye extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MakersEye()
	{
		mCardName="Makers Eye";
		mCardText="Make a run on RND. If successful get 3 attempts at finding agendas, costs one click and two credits.";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0&&current.getCredits()>1)
		{
		current.hitServer(1);	
		current.spendClick();
		current.setCredits(current.getCredits()-2);	
		boolean victory=true;
		int i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][1])victory=Ice.encounterIce(current);
		if(victory)
		{
			System.out.println("Ooo lookie here.");
			current.accessRND(current);
			current.RNDsingleHit(current);
			current.RNDsingleHit(current);
			return true;
		}
		else {System.out.println("Run ended.");return true;}
		}else {System.out.println("Need a click and 2 credits to use this.");return false;}
	}
}
