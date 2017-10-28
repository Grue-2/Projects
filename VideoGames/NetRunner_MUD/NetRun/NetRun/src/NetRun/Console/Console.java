package NetRun.Console;

import java.io.Serializable;

public class Console implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String mName;
	protected String mText;
	protected int mValue;
	public String getName() {
		return mName;
	}
	public String getText() {
		return mText;
	}
	public int getValue() {
		return mValue;
	}
	public Console()
	{
		mName="Basic";
		mValue=7;
	}
	
}
