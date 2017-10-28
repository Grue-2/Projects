package edu.orangecoastcollege.cs272.project.controller.jcrandall;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.orangecoastcollege.cs272.project.model.jcrandall.DBModel;
import edu.orangecoastcollege.cs272.project.model.jcrandall.FillInQuestion;
import edu.orangecoastcollege.cs272.project.model.jcrandall.ImageQuestion;
import edu.orangecoastcollege.cs272.project.model.jcrandall.Question;
import edu.orangecoastcollege.cs272.project.model.jcrandall.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


/**
 * Pathing currently only works compiling from src, probably a way to fix it for other uses
 *
 */

public class Controller {
	private static Controller mController;
	private User mUser;
	private DBModel mDB;
	private ObservableList<Question> mQuestionList;
	
	private static synchronized void generateInstance(){
		if(mController==null){
			mController=new Controller();
		}
	}
	
	public static Controller getInstance(){
		generateInstance();
		try{
			mController.mDB=new DBModel();
			
			mController.mDB.beginTransaction();
			mController.mDB.createTable("user(id INTEGER PRIMARY KEY,name TEXT,user_id TEXT,class_id TEXT)");
			mController.mDB.createTable("settings(id INTEGER PRIMARY KEY,sound INTEGER)");
			mController.mDB.createTable("fill_questions(id INTEGER PRIMARY KEY,num_q INTEGER,correct INTEGER,body TEXT,ans TEXT)");
			mController.mDB.createTable("image_questions(id INTEGER PRIMARY KEY,num_q INTEGER,correct INTEGER,body TEXT,ans TEXT)");
			if(mController.mDB.tableIsEmpty("settings"))
				mController.mDB.createRecord("settings",new String[]{"sound"},new String[]{"1"});
			mController.mDB.endTransation();
			
			
			ResultSet rs=mController.mDB.queryDB("*","user");
			if(rs.next())
				mController.mUser=new User(rs.getInt(1),
										   rs.getString(2),
										   rs.getString(3),
										   rs.getString(4)
										  );
			mController.mQuestionList=FXCollections.observableArrayList();
			ResultSet rsQ=mController.mDB.queryDB("*","fill_questions");
			while(rsQ.next()){
				mController.mQuestionList.add(new FillInQuestion(rs.getInt(1),
											                     rs.getInt(2),
											                     rs.getInt(3),
											                     rs.getString(4),
											                     rs.getString(5)
											 					));
			}
			ResultSet rsQ2=mController.mDB.queryDB("*","image_questions");
			while(rsQ2.next()){
				mController.mQuestionList.add(new ImageQuestion(rs.getInt(1),
																rs.getInt(2),
																rs.getInt(3),
																rs.getString(4),
																rs.getString(5)
															   ));
			}
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return mController;
	}
	public ObservableList<Question> getQuestionList(){
		return FXCollections.observableArrayList(mQuestionList);
	}
	public String getUserName(){
		return mUser.getName();
	}
	public boolean hasUser(){
		if(mUser==null)return false;
		else return true;
	}
	public void setUser(String user,String id,String clss){
		try{
			int db_id=mDB.createRecord("user",
					new String[]{"name","user_id","class_id"},
					new String[]{"'"+user+"'","'"+id+"'","'"+clss+"'"});
			mUser=new User(db_id,user,id,clss);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void deleteUser(){
		mUser=null;
		try{
			mDB.clearTable("user");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public boolean getSound(){
		try{
			return mDB.queryDB("sound","settings").getBoolean(1);	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	public void setSound(boolean on){
		try{
			mDB.updateTable("settings","sound",on?"1":"0");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void addTextQuestion(int num,int correct,String body,String ans){
		try{
		int db_id=mDB.createRecord("fill_questions",
								   new String[]{"num_q","correct","body","ans"},
								   new String[]{String.valueOf(num),String.valueOf(correct),"'"+body+"'","'"+ans+"'"}
								  );
		mQuestionList.add(new FillInQuestion(db_id,num,correct,body,ans));
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void addImageQuestion(int num,int correct,Image body,Image ans){
		try{
			String path=System.getProperty("user.dir");
			int db_id=mDB.createRecord("image_questions",new String[]{},new String[]{});
			mDB.updateRecord("image_questions",
								"num_q = "+num+" ,"
							  + " correct = "+correct+" ,"
							  + " body = "+("'"+path+"/src/Images/"+db_id+"_body.png"+"'")+", "
							  + " ans = "+("'"+path+"/src/Images/"+db_id+"_ans.png"+"'"), 
							"WHERE id = "+db_id  
							);
			writeImage(body,path+"/src/Images/"+db_id+"_body.png");
			writeImage(ans,path+"/src/Images/"+db_id+"_ans.png");
			mQuestionList.add(new ImageQuestion(db_id,
												num,
												correct,
												path+"/src/Images/"+db_id+"_body.png",
												path+"/src/Images/"+db_id+"_ans.png"
												));							
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	private void writeImage(Image i,String name){
		
	try{
		BufferedImage bi=SwingFXUtils.fromFXImage(i, null);
		ImageIO.write(bi,"png",new File(name));
	}
	catch(IOException e){
			e.printStackTrace();
		}
	}
	public void deleteQuestion(Question q){
		try{
			if(q instanceof ImageQuestion){
				mDB.deleteRecord("image_questions",String.valueOf(q.getID()));
				try{
					Files.deleteIfExists(new File(((ImageQuestion)q).getBody()).toPath());
					Files.deleteIfExists(new File(((ImageQuestion)q).getAns()).toPath());
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			if(q instanceof FillInQuestion){
				mDB.deleteRecord("fill_questions",String.valueOf(q.getID()));
			
			}
			mQuestionList.remove(q);
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
	}
}
