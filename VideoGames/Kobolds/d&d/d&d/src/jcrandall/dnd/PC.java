package jcrandall.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import jcrandall.dnd.Armor.Armor;
import jcrandall.dnd.Armor.Bravery;
import jcrandall.dnd.Armor.BreastPlate;
import jcrandall.dnd.Armor.Cloth;
import jcrandall.dnd.Armor.Leather;
import jcrandall.dnd.Passives.ArcaneRecovery;
import jcrandall.dnd.Passives.ArmorTrained;
import jcrandall.dnd.Passives.DivineSmite;
import jcrandall.dnd.Passives.EldritchBlast;
import jcrandall.dnd.Passives.Healer;
import jcrandall.dnd.Passives.LayOnHands;
import jcrandall.dnd.Passives.Rage;
import jcrandall.dnd.Passives.SecondWind;
import jcrandall.dnd.Passives.ShapeShift;
import jcrandall.dnd.Passives.UnarmoredFighter;
import jcrandall.dnd.Spell.Bless;
import jcrandall.dnd.Spell.BurningHands;
import jcrandall.dnd.Spell.CharmPerson;
import jcrandall.dnd.Spell.CureLightWounds;
import jcrandall.dnd.Spell.MagicMissle;
import jcrandall.dnd.Spell.Sleep;
import jcrandall.dnd.Spell.ThunderWave;
import jcrandall.dnd.Weapon.CrossBowAndDagger;
import jcrandall.dnd.Weapon.GreatAxe;
import jcrandall.dnd.Weapon.Greatsword;
import jcrandall.dnd.Weapon.ScimitarAndShield;
import jcrandall.dnd.Weapon.ShieldAndMace;
import jcrandall.dnd.Weapon.Weapon;

public class PC extends Character {

	private List<Level> mLevel;
	private int mExperience;
	private Weapon mWeapon;
	private Armor mArmor;
	
	
	@Override public String toString()
	{
		StringBuilder result=new StringBuilder();
		result.append(mLevel.get(0).getLevelClass()+": "+mLevel.get(0).getLevelClass().getDescription()+"\n");
		result.append("Stats:\nStrength    : "+mScores[0]+"\n");
		result.append("Dexterity   : "+mScores[1]+"\n");
		result.append("Constitution: "+mScores[2]+"\n");
		result.append("Intelligence: "+mScores[3]+"\n");
		result.append("Wisdom	   : "+mScores[4]+"\n");
		result.append("Charisma    : "+mScores[5]+"\n");
		
		return result.toString();
	}
	
	

	
	private PC(PlayerClass pClass)
	{
		Random rng=new Random();
		mLevel=new ArrayList<>();
		mExperience=0;
		mPos=new int[2];
		mScores=new int[6];
		switch(pClass)
		{
		case BARBARIAN:
			mScores[0]=rng.nextInt(7)+12;
			mScores[1]=rng.nextInt(4)+10;
			mScores[2]=rng.nextInt(11)+8;
			mScores[3]=rng.nextInt(15)+2;
			mScores[4]=rng.nextInt(13)+4;
			mScores[5]=rng.nextInt(15)+4;
			mHPMax=12+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=10+(mScores[2]-10)/2+(mScores[1]-10)/2;
			mPortrait=new Image("Images/barbarian.png");
			mSpellBook=new ArrayList<>();
			mSpellsLeft=new int[0];
			mPassives=new ArrayList<>();mPassives.add(new UnarmoredFighter());mPassives.add(new Rage());
			mLevel.add(new Level(PlayerClass.BARBARIAN));
			mArmor=new Bravery();
			mWeapon=new GreatAxe();
			mName="Barbarian";
			break;
		case CLERIC:	
			mScores[0]=rng.nextInt(9)+8;
			mScores[1]=rng.nextInt(7)+6;
			mScores[2]=rng.nextInt(7)+12;
			mScores[3]=rng.nextInt(11)+8;
			mScores[4]=rng.nextInt(7)+12;
			mScores[5]=rng.nextInt(9)+8;
			mHPMax=(10+(mScores[2]-10)/2)+2;
			mHPCurrent=mHPMax;
			mAC=16;
			mPortrait=new Image("Images/cleric.png");
			mSpellBook=new ArrayList<>();mSpellBook.add(new CureLightWounds());mSpellBook.add(new Bless());
			mSpellsLeft=new int[]{2};
			mPassives=new ArrayList<>();mPassives.add(new Healer());
			mLevel.add(new Level(PlayerClass.CLERIC));
			mArmor=new BreastPlate();
			mWeapon=new ShieldAndMace();
			mName="Cleric";
			break;
		case DRUID:
			mScores[0]=rng.nextInt(11)+6;
			mScores[1]=rng.nextInt(7)+10;
			mScores[2]=rng.nextInt(7)+10;
			mScores[3]=rng.nextInt(7)+8;
			mScores[4]=rng.nextInt(7)+12;
			mScores[5]=rng.nextInt(13)+4;
			mHPMax=8+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=(11+(mScores[1]-10)/2)+2;
			mPortrait=new Image("Images/druid.png");
			mSpellBook=new ArrayList<>();mSpellBook.add(new CureLightWounds());mSpellBook.add(new ThunderWave());
			mSpellsLeft=new int[]{2};
			mPassives=new ArrayList<>();mPassives.add(new ShapeShift());
			mLevel.add(new Level(PlayerClass.DRUID));
			mArmor=new Leather();
			mWeapon=new ScimitarAndShield();
			mName="Druid";
			break;
		case FIGHTER:	
			mScores[0]=rng.nextInt(7)+12;
			mScores[1]=rng.nextInt(7)+12;
			mScores[2]=rng.nextInt(7)+12;
			mScores[3]=rng.nextInt(11)+6;
			mScores[4]=rng.nextInt(11)+6;
			mScores[5]=rng.nextInt(11)+8;
			mHPMax=10+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=17;
			mPortrait=new Image("Images/fighter.png");
			mSpellBook=new ArrayList<>();
			mSpellsLeft=new int[0];
			mPassives=new ArrayList<>();mPassives.add(new ArmorTrained());mPassives.add(new SecondWind());
			mLevel.add(new Level(PlayerClass.FIGHTER));
			mArmor=new BreastPlate();
			mWeapon=new Greatsword();
			mName="Fighter";
			break;
		case PALADIN:
			mScores[0]=rng.nextInt(7)+12;
			mScores[1]=rng.nextInt(7)+8;
			mScores[2]=rng.nextInt(7)+10;
			mScores[3]=rng.nextInt(5)+8;
			mScores[4]=rng.nextInt(11)+6;
			mScores[5]=rng.nextInt(7)+12;
			mHPMax=10+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=16;
			mPortrait=new Image("Images/paladin.png");
			mSpellBook=new ArrayList<>();mSpellBook.add(new CureLightWounds());
			mSpellsLeft=new int[]{1};
			mPassives=new ArrayList<>();mPassives.add(new LayOnHands());mPassives.add(new DivineSmite());
			mLevel.add(new Level(PlayerClass.PALADIN));
			mArmor=new BreastPlate();
			mWeapon=new Greatsword();
			mName="Paladin";
			break;
		case WARLOCK:	
			mScores[0]=rng.nextInt(13)+6;
			mScores[1]=rng.nextInt(9)+8;
			mScores[2]=rng.nextInt(7)+10;
			mScores[3]=rng.nextInt(9)+10;
			mScores[4]=rng.nextInt(19);
			mScores[5]=rng.nextInt(7)+14;
			mHPMax=8+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=10+(mScores[1]-10)/2;
			mPortrait=new Image("Images/warlock.png");
			mSpellBook=new ArrayList<>();mSpellBook.add(new BurningHands());mSpellBook.add(new CharmPerson());
			mSpellsLeft=new int[]{1};
			mPassives=new ArrayList<>();mPassives.add(new EldritchBlast());
			mLevel.add(new Level(PlayerClass.WARLOCK));
			mArmor=new Cloth();
			mWeapon=new CrossBowAndDagger();
			mName="Warlock";
			break;
		case MAGIC_USER:
			mScores[0]=rng.nextInt(11);
			mScores[1]=rng.nextInt(17)+4;
			mScores[2]=rng.nextInt(9)+6;
			mScores[3]=rng.nextInt(7)+14;
			mScores[4]=rng.nextInt(19);
			mScores[5]=rng.nextInt(19);
			mHPMax=8+(mScores[2]-10)/2;
			mHPCurrent=mHPMax;
			mAC=10+(mScores[1]-10)/2;
			mPortrait=new Image("Images/magicUser.png");
			mSpellBook=new ArrayList<>();mSpellBook.add(new Sleep());mSpellBook.add(new MagicMissle());
			mSpellsLeft=new int[]{2};
			mPassives=new ArrayList<>();mPassives.add(new ArcaneRecovery());
			mLevel.add(new Level(PlayerClass.MAGIC_USER));
			mArmor=new Cloth();
			mWeapon=new CrossBowAndDagger();
			mName="Magic User";
			break;
		default:	
			mScores[0]=rng.nextInt(21);
			mScores[1]=rng.nextInt(21);
			mScores[2]=rng.nextInt(21);
			mScores[3]=rng.nextInt(21);
			mScores[4]=rng.nextInt(21);
			mScores[5]=rng.nextInt(21);
			mHPMax=rng.nextInt(13)+(mScores[2]-10)/2+1;
			mHPCurrent=mHPMax;
			mAC=10+(mScores[1]-10)/2+((rng.nextInt(10)>8)?1:0);
			if(rng.nextInt(50)==0)mPortrait=new Image("Images/wasteofskin.png");
			else mPortrait=new Image("Images/wasteofskin2.png");
			mSpellBook=new ArrayList<>();
			mSpellsLeft=new int[]{3};
			mPassives=new ArrayList<>();
			mLevel.add(new Level(PlayerClass.WASTE_OF_SKIN));
			mArmor=new Cloth();
			mWeapon=new CrossBowAndDagger();
			mName="Waste of Time and Space.";
			break;
			
		}		
	}
	public static PC makeRandomPC()
	{
		PlayerClass pClass;
		Random rng=new Random();
		switch(rng.nextInt(8))
		{
		case 0:
			pClass=PlayerClass.BARBARIAN;
			break;
		case 1:	
			pClass=PlayerClass.CLERIC;
			break;
		case 2:
			pClass=PlayerClass.DRUID;
			break;
		case 3:	
			pClass=PlayerClass.FIGHTER;
			break;
		case 4:
			pClass=PlayerClass.PALADIN;
			break;
		case 5:	
			pClass=PlayerClass.WARLOCK;
			break;
		case 6:
			pClass=PlayerClass.MAGIC_USER;
			break;
		default:	
			pClass=PlayerClass.WASTE_OF_SKIN;
			
		}
		return new PC(pClass);
	}
	
	
	
	
	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String attack() {
		// TODO Auto-generated method stub
		if(getHP()<=0)return "Pc is knocked out.";
		return "PC skips turn.";
	}

	@Override
	public boolean skill() {
		// TODO Auto-generated method stub
		return false;
	}




	
	
	
}
