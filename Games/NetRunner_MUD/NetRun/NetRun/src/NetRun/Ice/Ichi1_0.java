package NetRun.Ice;

import NetRun.Game;

public class Ichi1_0 extends Ice{
	public Ichi1_0()
	{
		mStrength=4;
		mType=2;
		mSubType="Bioroid";
		mName="Ichi 1.0";
		mDescription="My reputation would precede me, if any could speak of it.(sentry)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		bioroidDestroy(current);
		bioroidDestroy(current);
		bioroidDamage(current);
		return true;
	}
}
