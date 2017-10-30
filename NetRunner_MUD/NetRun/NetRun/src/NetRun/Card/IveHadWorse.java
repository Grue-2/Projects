package NetRun.Card;

import NetRun.Game;

public class IveHadWorse extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IveHadWorse()
	{
		mCardName="I've Had Worse";
		mCardText="I once walked 5 miles to school. Uphill. In the snow. Both ways.(using it just throws it away, but if its discarded you draw 3 cards.)";
	}
	public boolean useCard(Game current)
	{
		System.out.println("Feh, I don't even need this card.(card used)");
		return true;
	}
}
