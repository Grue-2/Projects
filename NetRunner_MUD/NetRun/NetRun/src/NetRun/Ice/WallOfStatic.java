package NetRun.Ice;

import NetRun.Game;

public class WallOfStatic extends Ice {
	public WallOfStatic()
	{
		mStrength=3;
		mType=0;
		mSubType="None";
		mName="Wall of Static";
		mDescription="Bzzzzzzz.(barrier)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		System.out.println("Bzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz.");
		return false;
	}
}
