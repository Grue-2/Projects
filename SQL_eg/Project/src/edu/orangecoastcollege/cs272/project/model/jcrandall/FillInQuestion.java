package edu.orangecoastcollege.cs272.project.model.jcrandall;

public class FillInQuestion extends Question {
	private String mQuestionBody;
	private String mAnsBody;
	
	public FillInQuestion(int key,int num,int correct,String body,String ans){
		super(key,num,correct);
		mQuestionBody=body;
		mAnsBody=ans;
	}
	

	public String getBody(){
		return mQuestionBody;
	}
	public String getAns(){
		return mAnsBody;
	}
	
	@Override public String toString(){
		return "Question:\n"+mQuestionBody+"\n\nAnswer:\n"+mAnsBody;
	}
	
	
	
}
