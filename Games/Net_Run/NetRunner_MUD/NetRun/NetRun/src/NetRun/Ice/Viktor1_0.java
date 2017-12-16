package NetRun.Ice;

import NetRun.Game;

public class Viktor1_0 extends Ice {
	public Viktor1_0()
	{
		mStrength=3;
		mType=1;
		mSubType="Bioroid";
		mName="Viktor 1.0";
		mDescription="My name is Viktor. Nice to meet you. Would you like to play a game?(codegate)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		bioroidDamage(current);
		return bioroidEnd(current);
	}
}



