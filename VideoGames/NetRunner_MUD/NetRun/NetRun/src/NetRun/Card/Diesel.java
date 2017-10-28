package NetRun.Card;

import NetRun.Game;

public class Diesel extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Diesel()
	{
		mCardName="Diesel";
		mCardText="Diesel give syou flames. ( draw 3 cards [can't go over 5] )";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0)
		{
		current.spendClick();
		System.out.println("I'd main line it if I could.");
		current.drawCard(current,true);
		current.drawCard(current,true);
		current.drawCard(current,true);
		return true;
		}else {System.out.println("Need a click to use this.");return false;}
	}
}
