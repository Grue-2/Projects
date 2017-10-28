/**
 * 
 * Work on previous shift rate usability/ UI design
 * 
 * @file DietCounter.java
 * @author JC
 * @version v.02
 */
package diet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DietCounter extends Application{

	@FXML private BorderPane bp;
	@FXML private Button consume;
	@FXML volatile private TextField currentC;
	@FXML private TextField cToEat;
	@FXML private TextField rate;
	@FXML private Button start;
	@FXML private Label label;
	
	private final static long MILI_SECONDS_PER_DAY=86_400_000;
	
	public static void main(String args[]){
		//System.out.println(LocalDateTime.now());
		Application.launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("DietScene.fxml"))));
		arg0.setTitle("Calories Available.");
		arg0.show();
	}
	
	@FXML Object consume(){
		currentC.setText((""+(-Integer.parseInt(cToEat.getText())+Integer.parseInt(currentC.getText()))));
		cToEat.setText("0");
		return null;
	}
	
	@FXML Object start(){
		try(Connection c=DriverManager.getConnection("jdbc:sqlite:save.db");Statement s=c.createStatement()){
			String newRate=this.rate.getText();
			ResultSet rs=s.executeQuery("SELECT * FROM save");
			int leftOver=rs.getInt("calories");
			int rate=rs.getInt("rate");
			String oldDate=rs.getString("date");
			
			long diffInMilli=Duration.between(LocalDateTime.of(Integer.parseInt(oldDate.substring(0,4)),
															   Integer.parseInt(oldDate.substring(5,7)),
															   Integer.parseInt(oldDate.substring(8,10)),
															   Integer.parseInt(oldDate.substring(11,13)),
															   Integer.parseInt(oldDate.substring(14,16)),
															   Integer.parseInt(oldDate.substring(17,19))
					),LocalDateTime.now()).toMillis();
			
			
			this.rate.setText(""+rate);
			//System.out.println(diffInMilli*Integer.parseInt(this.rate.getText())/MILI_SECONDS_PER_DAY);
			currentC.setText(""+(leftOver+(diffInMilli*Integer.parseInt(this.rate.getText()))/MILI_SECONDS_PER_DAY));
			System.out.println(""+(leftOver+(MILI_SECONDS_PER_DAY*Integer.parseInt(this.rate.getText()))/MILI_SECONDS_PER_DAY));

			this.rate.setText(newRate);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace(); if can't load just skip it
		}
		
		Timer ticky=new Timer(true);
		ticky.scheduleAtFixedRate(new incrementer(),MILI_SECONDS_PER_DAY/Long.parseLong(rate.getText()),MILI_SECONDS_PER_DAY/Long.parseLong(rate.getText()));
		start.setText("Save and Exit");
		start.setOnAction(e->saveAndExit());
		
		rate.setEditable(false);
		return null;
	}
	
	private class incrementer extends TimerTask{

		@Override
		public void run() {
			currentC.setText(""+(Integer.parseInt(currentC.getText())+1));
		}
		
	}
	
	private void saveAndExit(){
		try(Connection c=DriverManager.getConnection("jdbc:sqlite:save.db");Statement s=c.createStatement()){
			s.executeUpdate("DROP TABLE IF EXISTS save");
			s.executeUpdate("CREATE TABLE save(calories INTEGER,rate INTEGER,date TEXT)");
			//System.out.println("INSERT INTO save VALUES("+currentC.getText()+","+rate.getText()+",'"+LocalDateTime.now()+"')");
			s.executeUpdate("INSERT INTO save VALUES("+currentC.getText()+","+rate.getText()+",'"+LocalDateTime.now()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	
	//@Override public void stop(){
		// Deson't save -> issue knowing how to get values at time of stop call
	//}
	
	
}
