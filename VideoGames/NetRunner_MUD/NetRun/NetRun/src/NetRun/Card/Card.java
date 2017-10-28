package NetRun.Card;

import java.io.Serializable;
import java.util.Random;

import NetRun.Demo;
import NetRun.Game;

abstract public class Card implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String mCardName;
	protected String mCardText;
	
	public static Card drawCard(Game current,boolean loud)
	{
		Random rng=new Random();
		int choice=rng.nextInt(current.getDeck().size());
		Card toDraw=current.getDeck().get(choice);
		if(toDraw instanceof Eradicate)
		{
			if(loud)System.out.println("You drew eradicate.");
			return new Eradicate();
		}
		else if(toDraw instanceof AccountSiphon)
		{
			if(loud)System.out.println("You drew account siphon.");
			return new AccountSiphon();
		}
		else if(toDraw instanceof Diesel)
		{
			if(loud)System.out.println("You drew Diesel.");
			return new Diesel();
		}
		else if(toDraw instanceof EasyMark)
		{
			if(loud)System.out.println("You drew easy mark.");
			return new EasyMark();
		}
		else if(toDraw instanceof InsideJob)
		{
			if(loud)System.out.println("You drew inside job.");
			return new InsideJob();
		}
		else if(toDraw instanceof IveHadWorse)
		{
			if(loud)System.out.println("You drew i've had worse.");
			return new IveHadWorse();
		}
		else if(toDraw instanceof LawyerUp)
		{
			if(loud)System.out.println("You drew lawyer up.");
			return new LawyerUp();
		}
		else if(toDraw instanceof MakersEye)
		{
			if(loud)System.out.println("You drew maker's eye.");
			return new MakersEye();
		}
		else if(toDraw instanceof Notoriety)
		{
			if(loud)System.out.println("You drew notoriety.");
			return new Notoriety();
		}
		else if(toDraw instanceof Stimhack)
		{
			if(loud)System.out.println("You drew stimhack.");
			return new Stimhack();
		}
		else if(toDraw instanceof SureGamble)
		{
			if(loud)System.out.println("You drew sure gamble.");
			return new SureGamble();
		}
		else // vamp
		{
			if(loud)System.out.println("You drew vamp.");
			return new Vamp();
		}
	}
	
	public static Card drawBaseCard()
	{
		Random rng=new Random();		
		switch(rng.nextInt(4))
		{
		case 0:
			return new EasyMark();
		case 1:
			return new InsideJob();
		case 2:
			return new AccountSiphon();
		default:
			return new MakersEye();
		}
	}
	
	
	@Override public String toString()
	{
		return mCardName+": "+mCardText;
	}
	
	abstract public boolean useCard(Game current);
	
	protected int targetRun(Game current) // returns 0 to 5
	{
		int laneInt;
		while(true){
		laneInt=Demo.safeIntegerInput(current.mInputRead,1,6,"Which lane do you want to run?(1 for Archives, 2 for RND, 3 for HQ, 4 for R1... ): ")-1;
		if(laneInt==5&&current.getCorpIce()[0][5]==false){System.out.println("Sever empty, try a differnt target.");continue;}
		if(laneInt==4&&current.getCorpIce()[0][4]==false){System.out.println("Sever empty, try a differnt target.");continue;}
		if(laneInt==3&&current.getCorpIce()[0][3]==false){System.out.println("Sever empty, try a differnt target.");continue;}
		
		break;}
		current.hitServer(laneInt);
		return laneInt;
	}
	
	
}
