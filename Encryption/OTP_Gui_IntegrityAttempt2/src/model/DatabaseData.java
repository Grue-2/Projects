package model;

public class DatabaseData {
	private Database database;
	private String keyYou,keyThem;
	DatabaseData(String keyYou, String keyThem){
		this.keyYou = keyYou; this.keyThem = keyThem;
	}
	
	public String getKeyYou() {return keyYou;}
	public String getKeyThem() {return keyThem;}
	public Database getDatabase() {return database;}
	
	void setKeyYou(String keyYou) {this.keyYou = keyYou;}
	void setKeyThem(String keyThem) {this.keyThem = keyThem;}
	void setDatabase(Database database) {this.database = database;}
}
