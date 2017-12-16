package NetRun.Ice;

import NetRun.Game;

public class IceWall extends Ice{
	public IceWall()
	{
		mStrength=1;
		mType=0;
		mSubType="None";
		mName="Ice Wall";
		mDescription="I asked for an ice as impenetrable as a wall.\nI can't decide if someone down in R&D has a\n warped sense of humor or just a very literal mind.(barrier)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		System.out.println("Thud.");
		return false;
	}
}
