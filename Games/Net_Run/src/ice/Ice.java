package ice;

import javafx.scene.image.Image;

/*
Adding ice 1) change count
		   2) add ice to Empty_Ice rng
		   3) create class / implement
 */

public abstract class Ice {
	public static final int ICE_COUNT = 3;
	
	public Image image, info;
	public abstract void encounter();
}
