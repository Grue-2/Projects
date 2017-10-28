package PlantSelector;

public class Plant {
	private String name;
	private String genus;
	private String species;
	private String varietal;
	private String layer;
	
	private String cold;
	private String hot;
	private String dry;
	private String wet;
	private String sun;
	
	private String lifespan;
	private String seasonality;
	private String leaves;
	private String propagation;
	private String wind;
	
	private String ph;
	private String n;
	private String p;
	private String k;
	private String drainage;
	private String product;
	
	public Plant(String name,String genus,String species,String varietal,String layer,String cold,String hot,
			String dry,String wet,String sun,String lifespan,String seasonality,String leaves,String propagation,
			String wind,String ph,String n,String p,String k,String drainage,String product){
		this.name=name;this.genus=genus;this.species=species;this.varietal=varietal;this.layer=layer;
		this.cold=cold;this.hot=hot;this.dry=dry;this.wet=wet;this.sun=sun;this.lifespan=lifespan;
		this.seasonality=seasonality;this.leaves=leaves;this.propagation=propagation;this.wind=wind;
		this.ph=ph;this.n=n;this.p=p;this.k=k;this.drainage=drainage;this.product=product;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName() {
		return name;
	}
	public String getGenus() {
		return genus;
	}
	public String getSpecies() {
		return species;
	}
	public String getVarietal() {
		return varietal;
	}
	public String getLayer() {
		return layer;
	}
	public String getCold() {
		return cold;
	}
	public String getHot() {
		return hot;
	}
	public String getDry() {
		return dry;
	}
	public String getWet() {
		return wet;
	}
	public String getSun() {
		return sun;
	}
	public String getLifespan() {
		return lifespan;
	}
	public String getSeasonality() {
		return seasonality;
	}
	public String getLeaves() {
		return leaves;
	}
	public String getPropagation() {
		return propagation;
	}
	public String getWind() {
		return wind;
	}
	public String getPh() {
		return ph;
	}
	public String getN() {
		return n;
	}
	public String getP() {
		return p;
	}
	public String getK() {
		return k;
	}
	public String getDrainage() {
		return drainage;
	}
	public String getProduct() {
		return product;
	}
	
	
}
