package NetRun.Resource;

import java.io.Serializable;

public class Resource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String mResourceName;
	protected String mResourceText;
	@Override public String toString()
	{
		return mResourceName+": "+mResourceText;
	}
	
}
