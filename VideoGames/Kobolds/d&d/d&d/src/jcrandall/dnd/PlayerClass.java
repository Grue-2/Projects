package jcrandall.dnd;

public enum PlayerClass {
	BARBARIAN, PALADIN, DRUID, WARLOCK, MAGIC_USER, FIGHTER, CLERIC, WASTE_OF_SKIN;

	@Override
	public String toString() {
		return (name().charAt(0) + name().substring(1).toLowerCase()).replaceAll("_", " ");
	}

	public String getDescription() {
		switch (this) {
		case BARBARIAN:
			return "Raging armorless Hulk capable of\ntaking damage and dishing it out in return.";
		case CLERIC:
			return "Tanky front line healer. Has buffs.";
		case DRUID:
			return "Worships trees. Can turn into animals\nwhich can absorb alot of punishment. Can heal.";
		case FIGHTER:
			return "Armored front line fighter,\ncan tank and deal damage.";
		case PALADIN:
			return "Has backup healing, but mostly is\na Tanky front line fighter. Can use his magic\nto do high burst damage instead of healing.";
		case WARLOCK:
			return "Powerful sustain damage, back line caster.\n Normally bad up close.";
		case MAGIC_USER:
			return "Has spells which can only be used a few times,\n great for hard situations,\n but low sustained power and weak in combat.";
		default:
			return "No redeeming values.\nDoesn't know his bloodtype is the same as the clerics.";
		}
	}

}

/**
 * switch(this){ case BARBARIAN: break; case CLERIC: break; case DRUID: break;
 * case FIGHTER: break; case PALADIN: break; case WARLOCK: break; case MAGIC_USER:
 * break; default: }
 */
