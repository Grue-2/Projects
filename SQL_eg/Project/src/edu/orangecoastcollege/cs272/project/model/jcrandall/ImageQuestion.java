package edu.orangecoastcollege.cs272.project.model.jcrandall;

import javafx.scene.image.Image;

public class ImageQuestion extends Question {
	private String mQuestionBody;
	private String mAnsBody;
	
	public ImageQuestion(int key,int num,int correct,String body,String ans){
		super(key,num,correct);
		mQuestionBody=body;
		mAnsBody=ans;
	}
	
	@Override public String toString(){
		return "Question:\n"+mQuestionBody+"\n\nAnswer:\n"+mAnsBody;
	}
	
	public String getBody(){
		return mQuestionBody;
	}
	public String getAns(){
		return mAnsBody;
	}
	
	
}
