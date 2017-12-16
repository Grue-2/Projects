package NetRun.Console;

import NetRun.Game;

public class BacklogScheduler extends Console {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mStoredClicks;
	public BacklogScheduler()
	{
		mName="Desperado";
		mText="Whenever you get access to something gain 1 credit";
		mValue=2;
		mStoredClicks=0;	
	}
	public int getClicks(){return mStoredClicks;}
	public void storeClick(Game current)
	{
		current.spendClick();
		if(mStoredClicks<4)mStoredClicks++;
	}
	public void unsealClicks(Game current)
	{
		current.setClicks(current.getClicks()+mStoredClicks);
		mStoredClicks=0;
	}
	
	
}
