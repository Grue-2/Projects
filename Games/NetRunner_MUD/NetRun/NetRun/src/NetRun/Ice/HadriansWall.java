package NetRun.Ice;

import NetRun.Game;

public class HadriansWall extends Ice {

		public HadriansWall()
		{
			mStrength=7;
			mType=0;
			mSubType="None";
			mName="Hadrians Wall";
			mDescription="Its a biiiig ice wall.(barrier)";
		}
		
		@Override public boolean triggerIce(Game current)
		{
			System.out.println("Thud!");
			return false;
		}
}
