package NetRun.Card;

import NetRun.Demo;
import NetRun.Game;
import NetRun.Ice.Ice;

public class Eradicate extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Eradicate()
	{
		mCardName="Eradicate";
		mCardText="Make a run on Archives. If successful remove an ice from any spot. Takes 1 clicks and 3 credits.(Threat based ice returns on corps turn)";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0&&current.getCredits()>2)
		{
		current.spendClick();
		current.setCredits(current.getCredits()-3);	
		current.hitServer(0);
		boolean victory=true;
		int i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][0])victory=Ice.encounterIce(current);
		if(victory)
		{
			int lane=Demo.safeIntegerInput(current.mInputRead,1,6,"What lane do you want to burn Ice in?(type 1 for archives,2 for RND,3 for HQ,4 for R1...): ");
			int layer=Demo.safeIntegerInput(current.mInputRead,1,5,"What layer of ice do you want to target?(1 for the closest in 5 for the furthest out.): ")-1;
			destroyIce(current.getCorpIce(),layer,lane,current);

			System.out.println("Good thats out of the way.");
			return true;
		}
		else {System.out.println("Run ended.");return true;}	
	}else {System.out.println("You need 3 Credits and one click to use this.");return false;}
	}
	public void destroyIce(boolean[][] ice,int layer,int lane,Game current)
	{
		ice[layer][lane]=false;
		current.setCorpIce(ice);
		current.hitServer(lane);
		
	}
}
