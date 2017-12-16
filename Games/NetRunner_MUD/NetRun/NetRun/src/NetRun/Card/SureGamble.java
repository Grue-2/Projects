package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;

public class SureGamble extends Card
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SureGamble()
	{
		mCardName="Sure Gamble";
		mCardText="Make a run anywhere and bet 5 credits.\nIf successful you get access and 8 credits more than you bet.";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0)
		{
		current.spendClick();
		int laneInt=targetRun(current);
		
		current.setCredits(current.getCredits()-5);
		boolean victory=true;
		int i=5;while(i--!=0&&victory){
			if(current.getCorpIce()[i][laneInt])victory=Ice.encounterIce(current);
		}
		if(victory)
		{
			System.out.println("Sure thing.(+13 credits)");
			current.setCredits(current.getCredits()+13);
			switch(laneInt)
			{
			case 0:
				current.accessArchives(current);
			case 1:
				current.accessRND(current);
			case 2:	
				current.accessHQ(current);
			default:
				current.accessRemote(current);
			}
			return true;
			
		}
		else {System.out.println("Run ended.");return true;}
		}
		else {System.out.println("Takes 1 click and 2 credits to use.");return false;}
	}
}
