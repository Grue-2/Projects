package algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class jc_Diffe_Hellman {
	private static final String REMOTE_IP = "witiko.co";
	private static final int PORT_NUMBER = 55555;

	private static final BigInteger TWO = new BigInteger("2");

	// used open SSL
	private static BigInteger p = new BigInteger("0084AD26C1A2407763FD404161DCBE0E8F64856"
			+ "2D1F9A4AC2D43C2F95D8B0A732F37ABBEDB74DC1F8C627D6EAD683D23885795E72D8B85FA0ED"
			+ "F867DCAAAE244F7D08A4299DE20EC68DBA917B2911613CB54FB23DBF5E0059E8F6076119A448"
			+ "447F1ECF4F1ABAC881AED14842338D5AFAF3636B6CA364593C27CD34810EA0EB8C53A469EDF6"
			+ "78B01E5B3B69927D9A74B453870B25A8EBE03A1C6978442AAA067AD7CD1754D7BBDD57FDE9FE6"
			+ "3056DC1C36C989DE6B107EA2007F56981FD4043D726C40B148683094CD83F5C449009DA39D40C"
			+ "5F3A818611EC09332469645E719DD1FC1DE95DC6D73D3A1A0168E8F6CA3B58C47EF87B5CCDF271" 
			+ "CFF5D2B9015BF07", 16);
	private static BigInteger g = new BigInteger("5");

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner consoleScanner = new Scanner(System.in);
		System.out.println("Please enter your handle:");
		String handle = consoleScanner.nextLine();
		System.out.println("Please enter your target:");
		String target = consoleScanner.nextLine();
		System.out.println("Please enter your contribution to the key:");
		String text = consoleScanner.nextLine();
		consoleScanner.close();

		BigInteger input1 = diffie(new BigInteger(text)), input2 = null;

		Socket con = new Socket();
		con.connect(new InetSocketAddress(InetAddress.getByName(REMOTE_IP), PORT_NUMBER));
		try (ObjectInputStream netIn = new ObjectInputStream(con.getInputStream());
				ObjectOutputStream netOut = new ObjectOutputStream(con.getOutputStream())) {

			netOut.writeObject(handle);
			netOut.writeObject(target);
			netOut.writeObject(text);
			System.out.println("Output sent.");

			String inHandle = (String) netIn.readObject();
			String text2 = (String) netIn.readObject();
			System.out.println("Text contribution from: " + inHandle + " received");

			input2 = new BigInteger(text2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(input1 + " " + input2);

		BigInteger result = diffie(input2, input1);

		BufferedWriter save = new BufferedWriter(new PrintWriter(new FileOutputStream(new File("dfkey.txt"))));
		save.write(result.toString());

		con.close();
		save.close();
	}

	private static BigInteger diffie(BigInteger in, BigInteger base) {
		BigInteger result = BigInteger.ONE;

		while (in.compareTo(BigInteger.ONE) != 0) {
			// odd exponent case
			if ((in.mod(TWO).compareTo(BigInteger.ONE) == 0)) {
				result = (result.multiply(base)).mod(p);
				in = in.subtract(BigInteger.ONE);
			} else {
				base = (base.multiply(base)).mod(p);
				in = in.divide(TWO);
			}
		}
		result = (result.multiply(base)).mod(p);

		return result;
	}

	private static BigInteger diffie(BigInteger in) {
		return diffie(in, g);
	}

}
