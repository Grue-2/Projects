package jcrandall.dnd;

import java.util.List;

import javafx.scene.image.Image;
import jcrandall.dnd.Passives.Passives;
import jcrandall.dnd.Spell.Spell;

abstract public class Character implements Actions,Comparable<Character>
{
	protected int mPos[];
	protected int mHPMax;
	protected int mHPCurrent;
	protected int mAC;
	protected Image mPortrait;
	protected List<Spell> mSpellBook;
	protected int[] mSpellsLeft;
	protected List<Passives> mPassives;
	protected int mInitiative;
	protected int mScores[];
	protected String mName;
	/**
	 * str 
	 * dex
	 * con
	 * int
	 * wis
	 * cha
	 */
	@Override
	public int compareTo(Character arg0) {
		return mInitiative-arg0.getInit();
	}
	public void damageChar(int n){mHPCurrent-=n;}
	public int getAC(){return mAC;}
	public int getHP(){return mHPCurrent;}
	public String getName(){return mName;}
	
	public int[] getScores(){return new int[]{mScores[0],mScores[1],mScores[2],mScores[3],mScores[4],mScores[5]};}
	public void setInit(int n){mInitiative=n;}
	public int getInit(){return mInitiative;}
	
	public Image getPortrait(){return mPortrait;}
	public int[] getPos() {
		return mPos;
	}
	public void setPos(int[] mPos) {
		this.mPos = mPos;
	}
	abstract public boolean move();
	abstract public String attack();
	abstract public boolean skill();
}
