package jcrandall.dnd;

public class Level {
	private PlayerClass mLevelType;
	public Level(PlayerClass c)
	{
		mLevelType=c;
	}
	public PlayerClass getLevelClass(){return mLevelType;}
}
