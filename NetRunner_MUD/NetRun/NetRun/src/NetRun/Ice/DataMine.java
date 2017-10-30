package NetRun.Ice;

import NetRun.Game;

public class DataMine extends Ice {
	public DataMine()
	{
		mStrength=2;
		mType=3;
		mSubType="Anti personal";
		mName="Data Mine";
		mDescription="Access HarmlessFile.datZ -> Are you sure? y/n-ANR(type trap)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
			
		System.out.println("Kaboom.");
		current.takeDamage(current);
		return true;
	}
}
