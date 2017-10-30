package NetRun.Card;

import NetRun.Game;
import NetRun.Ice.Ice;

// currently can't cancel if one wanted to target an empty remote server
public class InsideJob extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsideJob()
	{
		mCardName="Inside Job";
		mCardText="Make a run anywhere, skip the first ice you would encounter. Takes 1 click and 2 credits.";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>0&&current.getCredits()>1)
		{
		current.spendClick();
		current.setCredits(current.getCredits()-2);
		int laneInt=targetRun(current);
		boolean[][] holdIce=new boolean[5][6];
		for(int i=0;i<5;i++)for(int k=0;k<6;k++)holdIce[i][k]=current.getCorpIce()[i][k];
		boolean first=true;
		for(int i=0;i<5;i++)if(first==true&&current.getCorpIce()[4-i][laneInt]){first=false;current.getCorpIce()[4-i][laneInt]=false;}		
		boolean victory=true;
		int i=5;while(i--!=0&&victory)
		{
			if(current.getCorpIce()[i][laneInt])victory=Ice.encounterIce(current);
		}
		if(victory)
		{
			System.out.println("I knew I could get in here, now lets see.");
			switch(laneInt)
			{
			case 0:
				current.accessArchives(current);
				break;
			case 1:
				current.accessRND(current);
				break;
			case 2:	
				current.accessHQ(current);
				break;
			default:
				current.accessRemote(current);
				break;
			}
			current.setCorpIce(holdIce);
			return true;
		}
		else {System.out.println("Run ended.");current.setCorpIce(holdIce);return true;}
		}
		else {System.out.println("Takes 1 click and 2 credits to use.");return false;}
	}
}
