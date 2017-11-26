package agenda;

public class Agenda {
	public final double value; 
	// in credits
	public final String description, lore;
	// public description, lore to read out at end
	
	public Agenda(double value, String description, String lore)
	{
		this.value = value;
		this.description = description;
		this.lore = lore;
	}
	
	@Override public String toString()
	{
		return description;
	}
}
