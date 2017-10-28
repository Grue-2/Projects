package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;

public class Stimhack extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Stimhack()
	{
		mCardName="Stimhack";
		mCardText="Gain 9 temporary credits and make a run anywhere. Then take 1 damage\n(If you discard without any cards left you die)";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0)
		{
		current.spendClick();
		int laneInt=targetRun(current);
		int priorCredits=current.getCredits();
		current.setCredits(priorCredits+9);
		boolean victory=true;
		int i=5;while(i--!=0&&victory){
			if(current.getCorpIce()[i][laneInt])victory=Ice.encounterIce(current);
		}
		if(current.getCredits()>priorCredits)current.setCredits(priorCredits);
		if(victory)
		{
			switch(laneInt)
			{
			case 0:
				current.accessArchives(current);
				System.out.println("Ugh...  ( coming down, take 1 damage )");
				current.takeDamage(current);
			case 1:
				current.accessRND(current);
				System.out.println("Ugh...  ( coming down, take 1 damage )");
				current.takeDamage(current);
			case 2:	
				current.accessHQ(current);
				System.out.println("Ugh...  ( coming down, take 1 damage )");
				current.takeDamage(current);
			default:
				current.accessRemote(current);
				System.out.println("Ugh...  ( coming down, take 1 damage )");
				current.takeDamage(current);
			}
			return true;
			
		}
		else{ System.out.println("Run ended.");System.out.println("Ugh...  ( coming down, take 1 damage )");current.takeDamage(current);return true;}
		}
		else {System.out.println("Takes 1 click and 2 credits to use.");return false;}
	}
}
