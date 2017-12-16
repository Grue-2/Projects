package NetRun.Card;

import java.util.Random;

import NetRun.Game;
import NetRun.Ice.Ice;

public class Notoriety extends Card {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Notoriety()
	{
		mCardName="Notoriety";
		mCardText="Make a run on every central server, if each one is successful you get a large agenda ontop of it.(takes 4 clicks and 1 credit)";
	}
	public boolean useCard(Game current)
	{
		if(current.getClicks()>3)
		{
			current.hitServer(1);
			current.hitServer(2);
			current.hitServer(3);
			current.spendClick();
			current.spendClick();
			current.spendClick();
			current.spendClick();
			
			System.out.println("Running Archives.");
			boolean victory=true;
			int i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][0])victory=Ice.encounterIce(current);
			if(victory)
			{
				current.accessArchives(current);
				System.out.println("Part way there.");
			}
			else 
			{
				System.out.println("Run ended.");
				return true;
			}
			System.out.println("Running RND.");
			i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][1])victory=Ice.encounterIce(current);
			if(victory)
			{
				current.accessRND(current);
				System.out.println("Almost got it.");
			}
			else 
			{
				System.out.println("Run ended.");
				return true;
			}
			System.out.println("Running HQ.");
			i=5;while(i--!=0&&victory)if(current.getCorpIce()[i][2])victory=Ice.encounterIce(current);
			if(victory)
			{
				current.accessHQ(current);
				System.out.println("Nice. And for the fourth click you gloat about it on reddit!\nAlso you get a big agenda for downtime. ");
				Random rng=new Random();
				if(rng.nextInt(100)==1){System.out.println("Just kidding.");current.tGTBT(25);}
				else current.addAgenda(25);
				
				return true;
			}
			else 
			{
				System.out.println("Run ended.");
				return true;
			}
			
			
			
		
		
		}
		else 
		{
			System.out.println("Need 4 clicks to use this, and one credit.");
			return false;
		}
	}
}
