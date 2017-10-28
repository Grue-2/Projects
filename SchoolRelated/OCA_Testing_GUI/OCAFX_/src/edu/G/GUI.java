package edu.G;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GUI extends Application
{
	// This is so jank, but at least it works vaguely. -G.
	
	/*
			Ya know, I could probably turn a better developed version of this as my project	
	 */
	Media correctNoise=new Media(new File("sounds/smb_coin.mp3").toURI().toString()),
			wrongNoise=new Media(new File("sounds/smb2_shrink.mp3").toURI().toString()),
			winNoise=new Media(new File("sounds/smb_stage_clear.mp3").toURI().toString());
	ImageView banner=new ImageView(new Image("images/banner.png"));
	Button b1,b2,b3,b4,b5,b6,b7,b8,cont;
	ImageView a,b,c,d,e,f,g,h,i,j;
	ImageView question,awns;
	Image box=new Image("images/box.png",50,50,false,false);
	Image checkedBox=new Image("images/checkedbox.png",50,50,false,false);
	Image cont_img=new Image("images/continue.png");
	static List<Question> lst=new ArrayList<>(),lstWrong=new ArrayList<>();
	static int ans=0,currentQ=0;
	BorderPane bp=new BorderPane();
	boolean showing=true;
	public static void main(String[] args){
		
		//OCA assessment test
		lst.add(new Question(new Image("images/AT-Q-1.png"),new Image("images/AT-1-A.png",1150,0,false,false),(int)1E4,7));
		lst.add(new Question(new Image("images/AT-2-Q.png"),new Image("images/AT-2-A.png",1150,0,false,false),(int)1E2,5));
		lst.add(new Question(new Image("images/AT-3-Q.png"),new Image("images/AT-3-A.png",1150,0,false,false),11100,7));
		lst.add(new Question(new Image("images/AT-4-Q.png"),new Image("images/AT-4-A.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/AT-5-Q.png"),new Image("images/AT-5-A.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/AT-6-Q.png"),new Image("images/AT-6-A.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/AT-7-Q.png"),new Image("images/AT-7-A.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/AT-8-Q.png"),new Image("images/AT-8-A.png",1150,0,false,false),1,8));
		lst.add(new Question(new Image("images/AT-9-Q.png"),new Image("images/AT-9-A.png",1150,0,false,false),110,6));
		lst.add(new Question(new Image("images/AT-10-Q.png"),new Image("images/AT-10-A.png",1150,0,false,false),101100,7));
		lst.add(new Question(new Image("images/AT-11-Q.png"),new Image("images/AT-11-A.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/AT-12-Q.png"),new Image("images/AT-12-A.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/AT-13-Q.png"),new Image("images/AT-13-A.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/AT-14-Q.png"),new Image("images/AT-14-A.png",1150,0,false,false),111000,6));
		lst.add(new Question(new Image("images/AT-15-Q.png"),new Image("images/AT-15-A.png",1150,0,false,false),10101,5));
		lst.add(new Question(new Image("images/AT-16-Q.png"),new Image("images/AT-16-A.png",1150,0,false,false),1101000,7));
		lst.add(new Question(new Image("images/AT-17-Q.png"),new Image("images/AT-17-A.png",1150,0,false,false),100101,6));
		lst.add(new Question(new Image("images/AT-18-Q.png"),new Image("images/AT-18-A.png",1150,0,false,false),1010,6));
		lst.add(new Question(new Image("images/AT-19-Q.png"),new Image("images/AT-19-A.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/AT-20-Q.png"),new Image("images/AT-20-A.png",1150,0,false,false),101,6));
		//OCA CH 1
		lst.add(new Question(new Image("images/ch1-1-q.png"),new Image("images/ch1-1-a.png",1150,0,false,false),10011,6));
		lst.add(new Question(new Image("images/ch1-2-q.png"),new Image("images/ch1-2-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch1-3-q.png"),new Image("images/ch1-3-a.png",1150,0,false,false),11010,7));
		lst.add(new Question(new Image("images/ch1-4-q.png"),new Image("images/ch1-4-a.png",1150,0,false,false),11,5));
		lst.add(new Question(new Image("images/ch1-5-q.png"),new Image("images/ch1-5-a.png",1150,0,false,false),1100,6));
		lst.add(new Question(new Image("images/ch1-6-q.png"),new Image("images/ch1-6-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch1-7-q.png"),new Image("images/ch1-7-a.png",1150,0,false,false),111,6));
		lst.add(new Question(new Image("images/ch1-8-q.png"),new Image("images/ch1-8-a.png",1150,0,false,false),10,7));
		lst.add(new Question(new Image("images/ch1-9-q.png"),new Image("images/ch1-9-a.png",1150,0,false,false),11101,7));
		lst.add(new Question(new Image("images/ch1-10-q.png"),new Image("images/ch1-10-a.png",1150,0,false,false),10000,7));
		lst.add(new Question(new Image("images/ch1-11-q.png"),new Image("images/ch1-11-a.png",1150,0,false,false),1100,7));
		lst.add(new Question(new Image("images/ch1-12-q.png"),new Image("images/ch1-12-a.png",1150,0,false,false),1000000,7));
		lst.add(new Question(new Image("images/ch1-13-q.png"),new Image("images/ch1-13-a.png",1150,0,false,false),1001,7));
		lst.add(new Question(new Image("images/ch1-14-q.png"),new Image("images/ch1-14-a.png",1150,0,false,false),1000,7));
		lst.add(new Question(new Image("images/ch1-15-q.png"),new Image("images/ch1-15-a.png",1150,0,false,false),10001,6));
		lst.add(new Question(new Image("images/ch1-16-q.png"),new Image("images/ch1-16-a.png",1150,0,false,false),1110,7));
		lst.add(new Question(new Image("images/ch1-17-q.png"),new Image("images/ch1-17-a.png",1150,0,false,false),10001,7));
		lst.add(new Question(new Image("images/ch1-18-q.png"),new Image("images/ch1-18-a.png",1150,0,false,false),11100,7));
		lst.add(new Question(new Image("images/ch1-19-q.png"),new Image("images/ch1-19-a.png",1150,0,false,false),1010,6));
		lst.add(new Question(new Image("images/ch1-20-q.png"),new Image("images/ch1-20-a.png",1150,0,false,false),10010,7));
		lst.add(new Question(new Image("images/ch1-21-q.png"),new Image("images/ch1-21-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch1-22-q.png"),new Image("images/ch1-22-a.png",1150,0,false,false),10010,6));
		lst.add(new Question(new Image("images/ch1-23-q.png"),new Image("images/ch1-23-a.png",1150,0,false,false),1100,6));
		//OCA CH 2
		lst.add(new Question(new Image("images/ch2-1-q.png"),new Image("images/ch2-1-a.png",1150,0,false,false),1001,6));
		lst.add(new Question(new Image("images/ch2-2-q.png"),new Image("images/ch2-2-a.png",1150,0,false,false),1011,6));
		lst.add(new Question(new Image("images/ch2-3-q.png"),new Image("images/ch2-3-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch2-4-q.png"),new Image("images/ch2-4-a.png",1150,0,false,false),101110,6));
		lst.add(new Question(new Image("images/ch2-5-q.png"),new Image("images/ch2-5-a.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/ch2-6-q.png"),new Image("images/ch2-6-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch2-7-q.png"),new Image("images/ch2-7-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch2-8-q.png"),new Image("images/ch2-8-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch2-9-q.png"),new Image("images/ch2-9-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch2-10-q.png"),new Image("images/ch2-10-a.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/ch2-11-q.png"),new Image("images/ch2-11-a.png",1150,0,false,false),1,5));
		lst.add(new Question(new Image("images/ch2-12-q.png"),new Image("images/ch2-12-a.png",1150,0,false,false),1000,4));
		lst.add(new Question(new Image("images/ch2-13-q.png"),new Image("images/ch2-13-a.png",1150,0,false,false),1,4));
		lst.add(new Question(new Image("images/ch2-14-q.png"),new Image("images/ch2-14-a.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/ch2-15-q.png"),new Image("images/ch2-15-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch2-16-q.png"),new Image("images/ch2-16-a.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/ch2-17-q.png"),new Image("images/ch2-17-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch2-18-q.png"),new Image("images/ch2-18-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch2-19-q.png"),new Image("images/ch2-19-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch2-20-q.png"),new Image("images/ch2-20-a.png",1150,0,false,false),10,5));
		// OCA CH 3
		lst.add(new Question(new Image("images/ch3-1-q.png"),new Image("images/ch3-1-a.png",1150,0,false,false),1_000_000,7));
		lst.add(new Question(new Image("images/ch3-2-q.png"),new Image("images/ch3-2-a.png",1150,0,false,false),1101,6));
		lst.add(new Question(new Image("images/ch3-3-q.png"),new Image("images/ch3-3-a.png",1150,0,false,false),10110,7));
		lst.add(new Question(new Image("images/ch3-4-q.png"),new Image("images/ch3-4-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch3-5-q.png"),new Image("images/ch3-5-a.png",1150,0,false,false),100_000,6));
		lst.add(new Question(new Image("images/ch3-6-q.png"),new Image("images/ch3-6-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch3-7-q.png"),new Image("images/ch3-7-a.png",1150,0,false,false),11010,6));
		lst.add(new Question(new Image("images/ch3-8-q.png"),new Image("images/ch3-8-a.png",1150,0,false,false),11001,7));
		lst.add(new Question(new Image("images/ch3-9-q.png"),new Image("images/ch3-9-a.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/ch3-10-q.png"),new Image("images/ch3-10-a.png",1150,0,false,false),100000,7));
		lst.add(new Question(new Image("images/ch3-11-q.png"),new Image("images/ch3-11-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch3-12-q.png"),new Image("images/ch3-12-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch3-13-q.png"),new Image("images/ch3-13-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch3-14-q.png"),new Image("images/ch3-14-a.png",1150,0,false,false),101,5));
		lst.add(new Question(new Image("images/ch3-15-q.png"),new Image("images/ch3-15-a.png",1150,0,false,false),110100,6));
		lst.add(new Question(new Image("images/ch3-16-q.png"),new Image("images/ch3-16-a.png",1150,0,false,false),100,7));
		lst.add(new Question(new Image("images/ch3-17-q.png"),new Image("images/ch3-17-a.png",1150,0,false,false),100000,7));
		lst.add(new Question(new Image("images/ch3-18-q.png"),new Image("images/ch3-18-a.png",1150,0,false,false),11101,7));
		lst.add(new Question(new Image("images/ch3-19-q.png"),new Image("images/ch3-19-a.png",1150,0,false,false),110,5));
		lst.add(new Question(new Image("images/ch3-20-q.png"),new Image("images/ch3-20-a.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/ch3-21-q.png"),new Image("images/ch3-21-a.png",1150,0,false,false),100,7));
		lst.add(new Question(new Image("images/ch3-22-q.png"),new Image("images/ch3-22-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch3-23-q.png"),new Image("images/ch3-23-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch3-24-q.png"),new Image("images/ch3-24-a.png",1150,0,false,false),100,5));
		lst.add(new Question(new Image("images/ch3-25-q.png"),new Image("images/ch3-25-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch3-26-q.png"),new Image("images/ch3-26-a.png",1150,0,false,false),1011,5));
		lst.add(new Question(new Image("images/ch3-27-q.png"),new Image("images/ch3-27-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch3-28-q.png"),new Image("images/ch3-28-a.png",1150,0,false,false),101000,6));
		lst.add(new Question(new Image("images/ch3-29-q.png"),new Image("images/ch3-29-a.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/ch3-30-q.png"),new Image("images/ch3-30-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch3-31-q.png"),new Image("images/ch3-31-a.png",1150,0,false,false),10,7));
		lst.add(new Question(new Image("images/ch3-32-q.png"),new Image("images/ch3-32-a.png",1150,0,false,false),10000,7));
		lst.add(new Question(new Image("images/ch3-33-q.png"),new Image("images/ch3-33-a.png",1150,0,false,false),10,6));
		//OCA CH 4
		lst.add(new Question(new Image("images/ch4-1-q.png"),new Image("images/ch4-1-a.png",1150,0,false,false),110,6));
		lst.add(new Question(new Image("images/ch4-2-q.png"),new Image("images/ch4-2-a.png",1150,0,false,false),1001,6));
		lst.add(new Question(new Image("images/ch4-3-q.png"),new Image("images/ch4-3-a.png",1150,0,false,false),1101,7));
		lst.add(new Question(new Image("images/ch4-4-q.png"),new Image("images/ch4-4-a.png",1150,0,false,false),1000011,7));
		lst.add(new Question(new Image("images/ch4-5-q.png"),new Image("images/ch4-5-a.png",1150,0,false,false),1001000,7));
		lst.add(new Question(new Image("images/ch4-6-q.png"),new Image("images/ch4-6-a.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/ch4-7-q.png"),new Image("images/ch4-7-a.png",1150,0,false,false),101110,6));
		lst.add(new Question(new Image("images/ch4-8-q.png"),new Image("images/ch4-8-a.png",1150,0,false,false),10110,6));
		lst.add(new Question(new Image("images/ch4-9-q.png"),new Image("images/ch4-9-a.png",1150,0,false,false),10100,5));
		lst.add(new Question(new Image("images/ch4-10-q.png"),new Image("images/ch4-10-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch4-11-q.png"),new Image("images/ch4-11-a.png",1150,0,false,false),10010,6));
		lst.add(new Question(new Image("images/ch4-12-q.png"),new Image("images/ch4-12-a.png",1150,0,false,false),1000,6));
		lst.add(new Question(new Image("images/ch4-13-q.png"),new Image("images/ch4-13-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch4-14-q.png"),new Image("images/ch4-14-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch4-15-q.png"),new Image("images/ch4-15-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch4-16-q.png"),new Image("images/ch4-16-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch4-17-q.png"),new Image("images/ch4-17-a.png",1150,0,false,false),11010,7));
		lst.add(new Question(new Image("images/ch4-18-q.png"),new Image("images/ch4-18-a.png",1150,0,false,false),1000100,7));
		lst.add(new Question(new Image("images/ch4-19-q.png"),new Image("images/ch4-19-a.png",1150,0,false,false),1000001,7));
		lst.add(new Question(new Image("images/ch4-20-q.png"),new Image("images/ch4-20-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch4-21-q.png"),new Image("images/ch4-21-a.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/ch4-22-q.png"),new Image("images/ch4-22-a.png",1150,0,false,false),10000,7));
		lst.add(new Question(new Image("images/ch4-23-q.png"),new Image("images/ch4-23-a.png",1150,0,false,false),1,7));
		lst.add(new Question(new Image("images/ch4-24-q.png"),new Image("images/ch4-24-a.png",1150,0,false,false),10110,6));
		lst.add(new Question(new Image("images/ch4-25-q.png"),new Image("images/ch4-25-a.png",1150,0,false,false),10001,7));
		lst.add(new Question(new Image("images/ch4-26-q.png"),new Image("images/ch4-26-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch4-27-q.png"),new Image("images/ch4-27-a.png",1150,0,false,false),100,6));
		lst.add(new Question(new Image("images/ch4-28-q.png"),new Image("images/ch4-28-a.png",1150,0,false,false),101001,6));
		lst.add(new Question(new Image("images/ch4-29-q.png"),new Image("images/ch4-29-a.png",1150,0,false,false),100_001,6));
		//OCA CH 5
		lst.add(new Question(new Image("images/Ch-5-Q-1.png"),new Image("images/Ch-5-A-1.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/Ch-5-Q-2.png"),new Image("images/Ch-5-A-2.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/Ch-5-Q-3.png"),new Image("images/Ch-5-A-3.png",1150,0,false,false),11011,6));
		lst.add(new Question(new Image("images/Ch-5-Q-4.png"),new Image("images/Ch-5-A-4.png",1150,0,false,false),10100,5));
		lst.add(new Question(new Image("images/Ch-5-Q-5.png"),new Image("images/Ch-5-A-5.png",1150,0,false,false),111001,6));
		lst.add(new Question(new Image("images/Ch-5-Q-6.png"),new Image("images/Ch-5-A-6.png",1150,0,false,false),1000,5));
		lst.add(new Question(new Image("images/Ch-5-Q-7.png"),new Image("images/Ch-5-A-7.png",1150,0,false,false),110,5));
		//quiz next week
		lst.add(new Question(new Image("images/ch5-8-q.png"),new Image("images/ch5-8-a.png",1150,0,false,false),100000,6));
		lst.add(new Question(new Image("images/ch5-9-q.png"),new Image("images/ch5-9-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch5-10-q.png"),new Image("images/ch5-10-a.png",1150,0,false,false),110110,7));
		lst.add(new Question(new Image("images/ch5-11-q.png"),new Image("images/ch5-11-a.png",1150,0,false,false),11001,6));
		lst.add(new Question(new Image("images/ch5-12-q.png"),new Image("images/ch5-12-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch5-13-q.png"),new Image("images/ch5-13-a.png",1150,0,false,false),1,6));
		lst.add(new Question(new Image("images/ch5-14-q.png"),new Image("images/ch5-14-a.png",1150,0,false,false),100,5));
		lst.add(new Question(new Image("images/ch5-15-q.png"),new Image("images/ch5-15-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch5-16-q.png"),new Image("images/ch5-16-a.png",1150,0,false,false),10000,5));
		lst.add(new Question(new Image("images/ch5-17-q.png"),new Image("images/ch5-17-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch5-18-q.png"),new Image("images/ch5-18-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch5-19-q.png"),new Image("images/ch5-19-a.png",1150,0,false,false),100101,6));
		lst.add(new Question(new Image("images/ch5-20-q.png"),new Image("images/ch5-20-a.png",1150,0,false,false),1,5));
		//OCA CH 6
		lst.add(new Question(new Image("images/ch6-1-q.png"),new Image("images/ch6-1-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch6-2-q.png"),new Image("images/ch6-2-a.png",1150,0,false,false),1010,6));
		lst.add(new Question(new Image("images/ch6-3-q.png"),new Image("images/ch6-3-a.png",1150,0,false,false),100,5));
		lst.add(new Question(new Image("images/ch6-4-q.png"),new Image("images/ch6-4-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch6-5-q.png"),new Image("images/ch6-5-a.png",1150,0,false,false),1011,5));
		lst.add(new Question(new Image("images/ch6-6-q.png"),new Image("images/ch6-6-a.png",1150,0,false,false),100,5));
		lst.add(new Question(new Image("images/ch6-7-q.png"),new Image("images/ch6-7-a.png",1150,0,false,false),100,5));
		lst.add(new Question(new Image("images/ch6-8-q.png"),new Image("images/ch6-8-a.png",1150,0,false,false),10000,6));
		lst.add(new Question(new Image("images/ch6-9-q.png"),new Image("images/ch6-9-a.png",1150,0,false,false),10,6));
		lst.add(new Question(new Image("images/ch6-10-q.png"),new Image("images/ch6-10-a.png",1150,0,false,false),10000,7));
		lst.add(new Question(new Image("images/ch6-11-q.png"),new Image("images/ch6-11-a.png",1150,0,false,false),1,7));
		lst.add(new Question(new Image("images/ch6-12-q.png"),new Image("images/ch6-12-a.png",1150,0,false,false),1001011,7));
		lst.add(new Question(new Image("images/ch6-13-q.png"),new Image("images/ch6-13-a.png",1150,0,false,false),10111,5));
		lst.add(new Question(new Image("images/ch6-14-q.png"),new Image("images/ch6-14-a.png",1150,0,false,false),11101,5));
		lst.add(new Question(new Image("images/ch6-15-q.png"),new Image("images/ch6-15-a.png",1150,0,false,false),11011,6));
		lst.add(new Question(new Image("images/ch6-16-q.png"),new Image("images/ch6-16-a.png",1150,0,false,false),10,5));
		lst.add(new Question(new Image("images/ch6-17-q.png"),new Image("images/ch6-17-a.png",1150,0,false,false),11101,5));
		lst.add(new Question(new Image("images/ch6-18-q.png"),new Image("images/ch6-18-a.png",1150,0,false,false),10111,6));
		lst.add(new Question(new Image("images/ch6-19-q.png"),new Image("images/ch6-19-a.png",1150,0,false,false),10100,6));
		lst.add(new Question(new Image("images/ch6-20-q.png"),new Image("images/ch6-20-a.png",1150,0,false,false),10001,6));
		//
		Collections.shuffle(lst);
		Application.launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		
		a=new ImageView(new Image("images/A.png"));
		b=new ImageView(new Image("images/B.png"));
		c=new ImageView(new Image("images/C.png"));
		d=new ImageView(new Image("images/D.png"));
		e=new ImageView(new Image("images/E.png"));
		f=new ImageView(new Image("images/F.png"));
		g=new ImageView(new Image("images/G.png"));
		h=new ImageView(new Image("images/H.png"));
		i=new ImageView(new Image("images/I.png"));
		j=new ImageView(new Image("images/J.png"));
		b1=new Button();b1.setGraphic(new ImageView(box));b1.setOnAction(e->bPress(1,b1));
		b2=new Button();b2.setGraphic(new ImageView(box));b2.setOnAction(e->bPress(2,b2));
		b3=new Button();b3.setGraphic(new ImageView(box));b3.setOnAction(e->bPress(3,b3));
		b4=new Button();b4.setGraphic(new ImageView(box));b4.setOnAction(e->bPress(4,b4));
		b5=new Button();b5.setGraphic(new ImageView(box));b5.setOnAction(e->bPress(5,b5));
		b6=new Button();b6.setGraphic(new ImageView(box));b6.setOnAction(e->bPress(6,b6));
		b7=new Button();b7.setGraphic(new ImageView(box));b7.setOnAction(e->bPress(7,b7));
		b8=new Button();b8.setGraphic(new ImageView(box));b8.setOnAction(e->bPress(8,b8));
		cont=new Button();cont.setGraphic(new ImageView(cont_img));
		cont.setOnAction(e->pressCont());
		swapQuestion();
		
		GridPane buttons=new GridPane();
		buttons.setVgap(5);buttons.setHgap(5);buttons.setPadding(new Insets(50,15,0,300));
		buttons.add(b1,0,0);buttons.add(a, 1, 0);
		buttons.add(b2,0,1);buttons.add(b, 1, 1);
		buttons.add(b3,0,2);buttons.add(c, 1, 2);
		buttons.add(b4,0,3);buttons.add(d, 1, 3);
		buttons.add(b5,0,4);buttons.add(e, 1, 4);
		buttons.add(b6,0,5);buttons.add(f, 1, 5);
		buttons.add(b7,0,6);buttons.add(g, 1, 6);
		buttons.add(b8,0,7);buttons.add(h, 1, 7);
		

		bp.setStyle("-fx-background-color: #ccc7ad;");
		bp.setBottom(cont);bp.setCenter(question);bp.setLeft(buttons);
		
		BorderPane.setAlignment(question,Pos.CENTER_LEFT);bp.setTop(banner);
		BorderPane.setAlignment(buttons,Pos.CENTER_RIGHT);BorderPane.setAlignment(cont,Pos.TOP_CENTER);
		BorderPane.setAlignment(banner,Pos.TOP_CENTER);
		Scene s=new Scene(bp,1600,900);
		arg0.setScene(s);
		arg0.show(); 
	}
	
	void bPress(int place,Button b)
	{
		if(ans%(int)(Math.pow(10,place))/(int)(Math.pow(10,place-1))==0)
		{
			b.setGraphic(new ImageView(checkedBox));
			ans+=Math.pow(10,place-1);
		}
		else
		{
			b.setGraphic(new ImageView(box));
			ans-=Math.pow(10,place-1);
		}
	}
	void swapQuestion()
	{
		int num=currentQ;boolean end=false;
		if(currentQ>=lst.size())
		{	
			if(lstWrong.size()==0)
			{
				new MediaPlayer(winNoise).play();
				end=true;
				new Timer().schedule(new TimerTask(){@Override public void run(){System.exit(0);}},5500);
			}
			else
			{
				currentQ=0;num=currentQ;
				lst=lstWrong;
				lstWrong=new ArrayList<Question>();
				Collections.shuffle(lst);
			}
		}
		if(!end){
		question=new ImageView(lst.get(num).mQ);
		awns=new ImageView(lst.get(num).mA);
		int x=lst.get(num).mNumQ;
		manage(x--,b1,a);manage(x--,b2,b);manage(x--,b3,c);manage(x--,b4,d);
		manage(x--,b5,e);manage(x--,b6,f);manage(x--,b7,g);manage(x--,b8,h);
		ans=0;
		b1.setGraphic(new ImageView(box));b2.setGraphic(new ImageView(box));b3.setGraphic(new ImageView(box));
		b4.setGraphic(new ImageView(box));b5.setGraphic(new ImageView(box));b6.setGraphic(new ImageView(box));
		b7.setGraphic(new ImageView(box));b8.setGraphic(new ImageView(box));}
	}
	private void manage(int x,Button b1,ImageView a)
	{
		if(x>0){b1.setVisible(true);b1.setManaged(true);a.setVisible(true);a.setManaged(true);}
		else{b1.setVisible(false);b1.setManaged(false);a.setManaged(false);a.setVisible(false);}
	}
	void pressCont()
	{
		if(showing)
		{
			System.out.println(lst.get(currentQ).mAns+"\n"+ans+"\nX");
		if(lst.get(currentQ).mAns==ans)
		{
			bp.setTop(new ImageView(new Image("images/correct.png")));
			new MediaPlayer(correctNoise).play();
		}
		else
		{
			bp.setTop(new ImageView(new Image("images/nope.png")));
			lstWrong.add(lst.get(currentQ));
			
			new MediaPlayer(wrongNoise).play();
		}
		bp.setCenter(awns);
		showing=false;
		}
		else
		{
			currentQ++;
			swapQuestion();
			bp.setTop(banner);
			bp.setCenter(question);
			showing=true;
		}
	}
	
	
}
