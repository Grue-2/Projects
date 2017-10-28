package jcrandall.dnd.Monster;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import jcrandall.dnd.DemoDnD;
import jcrandall.dnd.PC;
import jcrandall.dnd.Passives.PackTactics;
import jcrandall.dnd.Passives.Passives;

public class Kobold extends Monster{
	/*
	protected int mPos[];
	protected int mHPMax;
	protected int mHPCurrent;
	protected int mAC;
	protected Image mPortrait;
	protected List<Spell> mSpellBook;
	protected int[] mSpellsLeft;
	protected List<Passives> mPassives;*/
	
	
	public Kobold()
	{
		mPos=new int[2];
		mHPMax=DemoDnD.dX(6)+DemoDnD.dX(6)-2;
		mHPCurrent=mHPMax;
		mAC=12;
		mPortrait=new Image("Images/kobold.png");
		mPassives=new ArrayList<Passives>();mPassives.add(new PackTactics());
		mScores=new int[]{7,15,9,8,7,8};
		mName="Kobold";
	}
	
	@Override public String toString()
	{
		return "Kobold: Enjoys biting things";
	}
	
	@Override
	public boolean move() 
	{
		while(true){
		switch(DemoDnD.dX(9))
		{
		case 1:
			if(validMove(1,0))return true;
			else continue;
		case 2:
			if(validMove(-1,0))return true;
			else continue;
		case 3:
			if(validMove(1,1))return true;
			else continue;
		case 4:
			if(validMove(1,-1))return true;
			else continue;
		case 5:
			if(validMove(-1,0))return true;
			else continue;
		case 6:
			if(validMove(-1,1))return true;
			else continue;
		case 7:
			if(validMove(0,-1))return true;
			else continue;
		case 8:
			if(validMove(0,1))return true;
			else continue;
		default:
			return true;
		}}
	}
	private boolean validMove(int x,int y)
	{
		if(DemoDnD.spaceIsEmpty(getPos()[0]+x,getPos()[1]+y)&&getPos()[0]+x<13&&getPos()[1]+y<7&&getPos()[0]+x>=0&&getPos()[1]+y>=0)
		{setPos(new int[]{getPos()[0]+x,getPos()[1]+y});return true;}
		else return false;
	}
	

	@Override
	public String attack() 
	{
		PC target;int attackRoll=DemoDnD.dX(20),damageroll=DemoDnD.dX(4)+2;
		if((target=DemoDnD.randomPCTarget())==null)return "";
		if(DemoDnD.dX(20)+4>=target.getAC())
		{
			target.damageChar(DemoDnD.dX(4)+2);
			if(target.getHP()<=0)return "Kobold rolls "+attackRoll+" hitting "+target.getName()+" for "+damageroll+" damage with his sling knocking him out.";
			return "Kobold rolls "+attackRoll+" hitting "+target.getName()+" for "+damageroll+" damage with his sling.";
		}
		else return "Kobold rolls "+attackRoll+" missing "+target.getName();
	}

	@Override
	public boolean skill() 
	{
		return false;
	}
	
}
