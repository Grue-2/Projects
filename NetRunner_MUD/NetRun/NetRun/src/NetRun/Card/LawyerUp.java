package NetRun.Card;

import NetRun.Game;

public class LawyerUp extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LawyerUp()
	{
		mCardName="LawyerUp";
		mCardText="I know my rights.( draw 2 cards , lose some threat, takes 2 clicks and 2 credits )";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>1&&current.getCredits()>1)
		{
		current.spendClick();
		current.spendClick();
		current.setCredits(current.getCredits()-2);
		System.out.println("Yee haw! (draw 2, some threat reduction)");
		current.drawCard(current,true);
		current.drawCard(current,true);
		current.setCorpTurns(current.getCorpTurns()-1);
		
		return true;
		}else {System.out.println("Need 2 clicks to use this, and 2 credits");return false;}
	}
}
