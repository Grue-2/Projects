package NetRun.Ice;

import NetRun.Game;

public class Rototurret extends Ice {
	public Rototurret()
	{
		mStrength=0;
		mType=2;
		mSubType="Destroyer";
		mName="RotoTurret";
		mDescription="Whrrrrr!";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		System.out.println("RotoTurret shott down the first program in memory.(sentry)");
		current.destroyProgram();
		return false;
	}
}
