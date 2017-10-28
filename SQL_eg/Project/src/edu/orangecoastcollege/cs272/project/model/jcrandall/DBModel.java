/*
 * jc - v.03
 */

package edu.orangecoastcollege.cs272.project.model.jcrandall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBModel{
	private String mDBName;
	private Connection mConnection;
	private Statement mStmt;
	
	public DBModel(String name)throws SQLException,ClassNotFoundException{
		mDBName=name;
		establishConnectionAndStatement();
	}
	public DBModel()throws SQLException,ClassNotFoundException{
		this("ProjectDB");
	}
	private void establishConnectionAndStatement()throws SQLException,ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		mConnection=DriverManager.getConnection("jdbc:sqlite:"+mDBName);
		mStmt=mConnection.createStatement();
	}
	public void beginTransaction()throws SQLException{
		mStmt.executeUpdate("BEGIN TRANSACTION");
	}
	public void endTransation()throws SQLException{
		mStmt.executeUpdate("END TRANSACTION");
	}
	public void createTable(String str)throws SQLException{
		mStmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+str);
	}
	public  int createRecord(String tableName,String cols[],String vals[])throws SQLException{
		StringBuilder sb=new StringBuilder("INSERT INTO ").append(tableName);
		if(cols.length>0){
			sb.append("(");
			for(String s:cols){
				sb.append(s).append(",");
			}
			sb.setLength(sb.length()-1);
			sb.append(")VALUES(");
			for(String s:vals){
				sb.append(s).append(",");
			}
			sb.setLength(sb.length()-1);
			sb.append(")");
		}
		else{
			sb.append(" DEFAULT VALUES");
		}
		//System.out.println(sb.toString());
		mStmt.executeUpdate(sb.toString());
		return mStmt.executeQuery("SELECT id FROM "+tableName).getInt(1);
	}
	public void clearTable(String tableName)throws SQLException{
		mStmt.executeUpdate("DELETE FROM "+tableName);
	}
	public boolean tableIsEmpty(String tableName)throws SQLException{
		return !mStmt.executeQuery("SELECT * from "+tableName).next();
	}
	public void updateTable(String tableName, String cols,String val)throws SQLException{
		//System.out.println("UPDATE "+tableName+" SET "+cols+" = "+val);
		mStmt.executeUpdate("UPDATE "+tableName+" SET "+cols+" = "+val);
	}
	public void updateRecord(String tableName, String update,String where)throws SQLException{
		//System.out.println("UPDATE "+tableName+" SET "+update+" "+where);
		mStmt.executeUpdate("UPDATE "+tableName+" SET "+update+" "+where);
	}
	public ResultSet queryDB(String cols,String table)throws SQLException{
		return mStmt.executeQuery("SELECT "+cols+" FROM "+table);
	}
	public void deleteRecord(String tableName,String id)throws SQLException{
		mStmt.executeUpdate("DELETE FROM "+tableName+" WHERE id = "+id);
	}

}
