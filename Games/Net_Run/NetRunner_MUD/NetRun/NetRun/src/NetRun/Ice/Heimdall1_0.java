package NetRun.Ice;

import NetRun.Game;

public class Heimdall1_0 extends Ice {

		public Heimdall1_0()
		{
			mStrength=6;
			mType=0;
			mSubType="Bioroid";
			mName="Heimdall 1.0";
			mDescription="I hear the shift of every bit amid the flow of the datastream.\nI hear the whispers of my mothers, and their commands are law.\nThe realm beyond is forbidden.(type barrier)";
		}
		
		@Override public boolean triggerIce(Game current)
		{
			bioroidDamage(current);
			if(!bioroidEnd(current))return false;
			return bioroidEnd(current);
		}
	}

