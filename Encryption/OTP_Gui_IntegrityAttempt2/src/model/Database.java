package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public static DatabaseData getDataBaseInformation(){
		DatabaseData loadedData = new DatabaseData("","");
		loadedData.setDatabase(new Database(loadedData));
		return loadedData;
	}
	
	private Database(DatabaseData loadedData) {
		try(Connection connectionToDatabase = DriverManager.getConnection("jdbc:sqlite:OTPGUIDB.db");
				Statement statement = connectionToDatabase.createStatement();){
			
			if(connectionToDatabase == null){/*TODO: this*/}
			
			statement.execute("CREATE TABLE IF NOT EXISTS datatable("
							 +"keyyou text, keythem text);");
			
			ResultSet rs = statement.executeQuery("SELECT keyyou, keythem FROM datatable");
			
			if(rs.next()) {
				loadedData.setKeyYou(rs.getString("keyyou"));
				loadedData.setKeyThem(rs.getString("keythem"));
			}
			else
				statement.executeUpdate("INSERT INTO datatable(keyyou,keythem) VALUES(\"\",\"\")");
			
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public boolean saveState(String keyYou, String keyThem) {
		try(Connection connectionToDatabase = DriverManager.getConnection("jdbc:sqlite:OTPGUIDB.db");
				Statement statement = connectionToDatabase.createStatement();){
			
			PreparedStatement pStmt = connectionToDatabase.prepareStatement(
					"UPDATE datatable SET keyyou=?,keythem=?;");
			
			pStmt.setString(1,keyYou);
			pStmt.setString(2,keyThem);
			pStmt.execute();
			
		} catch (SQLException e) {e.printStackTrace(); return false;}
		
		return true;
	}
}
