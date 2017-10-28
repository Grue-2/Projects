package edu.orangecoastcollege.cs272.project.model.jcrandall;

public class User {
	private String mName;
	private int mId;
	private String mUserID;
	private String mClassID;

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}
	public User(int id,String name,String userId,String classId){
		mName=name;
		mId=id;
		mUserID=userId;
		mClassID=classId;
	}	
}
