package NetRun.Card;

import NetRun.Game;

public class EasyMark extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EasyMark()
	{
		mCardName="Easy Mark";
		mCardText="Microsoft warning, virus detected. Confirm new installation to protect your PC. ( gain 4 credits )";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0)
		{
		current.spendClick();
		current.setCredits(current.getCredits()+4);
		System.out.println("Well, that was easy.");
		return true;
		}else {System.out.println("Need a click to use this.");return false;}
	}

}
