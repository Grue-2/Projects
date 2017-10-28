package NetRun.Operation;

import NetRun.Game;
import NetRun.Card.Card;
import NetRun.Card.Empty;

public class ScorchedEarth extends Operation
{
	public ScorchedEarth()
	{
	mOpName="Scorched Earth";
	mOpText="I'd like to remind the ladies and gentlemen of the press that several\n of the buildings damaged in the blast were owned by Illyxr subsidiaries.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCorpTurns()>20)
		{
			Card[] x=current.getHand();
			int sum=0;
			for(Card c:x)if(c instanceof Empty)sum++;
			if(sum>1)
			{
				System.out.println("You diddn't see it coming.(take 4 damage)");
				current.takeDamage(current);
				current.takeDamage(current);
				current.takeDamage(current);
				current.takeDamage(current);
			}
			else 
			{
				current.setHand(new Card[]{(new Empty()),(new Empty()),(new Empty()),(new Empty()),(new Empty())});
				System.out.println("It still rings in your ears. But thank goodness you're still okay. Your head feels a little bit frazzled your alright.");
			}
			
		}else System.out.println("You hear the corps went after somebody, good it wasn't you.");
	}
	
}
