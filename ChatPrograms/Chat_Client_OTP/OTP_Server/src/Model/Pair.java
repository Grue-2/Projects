package Model;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable{
	private static final long serialVersionUID = 1L;
	private T1 t1;
	private T2 t2;
	
	public Pair(T1 newT1, T2 newT2){t1=newT1;t2=newT2;}
	public T1 getKey(){return t1;}
	public T2 getValue(){return t2;}
}
