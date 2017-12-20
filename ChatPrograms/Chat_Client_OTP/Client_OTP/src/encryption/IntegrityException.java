package encryption;

public class IntegrityException extends Exception {
	IntegrityException(){
		super("Hash problem, keys out of line or the message has been tampered with.");
	}
}
