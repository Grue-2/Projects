package NetRun.Card;

import NetRun.Game;

public class Empty extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Empty()
	{
		mCardName="";
		mCardText="~~empty~~";
	}
	
	public boolean useCard(Game current){return true;}
}
