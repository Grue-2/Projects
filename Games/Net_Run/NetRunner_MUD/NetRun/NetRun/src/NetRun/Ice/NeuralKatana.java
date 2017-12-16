package NetRun.Ice;


import NetRun.Game;

public class NeuralKatana extends Ice {

		public NeuralKatana()
		{
			mStrength=3;
			mType=2;
			mSubType="antipersonal";
			mName="Neural Katana";
			mDescription="It has 150 counter damage. (type sentry)";
		}
		
		@Override public boolean triggerIce(Game current)
		{
			System.out.println("Stab. (take 3 damage)");
			current.takeDamage(current);
			current.takeDamage(current);
			current.takeDamage(current);
			return true;
		}
}

