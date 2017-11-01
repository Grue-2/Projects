/*
JC

11/01/2017

@version GitHub-date
 */

package jc.KeyUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

-tldr; : Program was made for USBs with cryptographic keys, lets user get
key material and manages deletion in an attempt to reduce possible double
use of key material in padding.

The goal of this program was to have an easy interface to the USBs
filled with cryptographic key material distributed for OTP communications.

The keys were generated using a TrueRNGv3. The results were von neumann whitened,
then tested using (source: https://www.freebsd.org/doc/en/articles/ipsec-must/code.html )
an implementation of Maurer's Universal Statistical Test.

The keys are distributed into 375,000,000 byte files and are AES encrypted using java's
implementation. (code can be found in AES.java in this project )

The AES key along with the binary key material files are placed on a USB along with this program.

The end user in order to obtain binary string entropy by pressing the "give" button.

This program then decrypts using the AES key located in the project, tests the binary files
again for randomness using the five basic randomness tests from
(Source/reference http://cacr.uwaterloo.ca/hac/about/chap5.pdf) Handbook of Applied Cryptography, 
by A. Menezes, P. van Oorschot, and S. Vanstone, CRC Press, 1996.

and expands the key material into a binary string, deleting everything as it progresses in an
attempt to eliminate as many possible ways to wind up double using key material.

Several versions were attempted to make the failures fail safe in that key material can be lost
but never used twice by mistake. (Unless of course the end user just copy pastes the binary string
given to them instead of cutting it).

*/
public class KeyManager extends Application {

	private static final String VERSION = "v.01";

	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("Key Manager " + VERSION);
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("StartScene.fxml"))));
		arg0.show();
	}

	@Override
	public void stop() throws FileNotFoundException {
		save();
		System.exit(0);
	}

	private void save() throws FileNotFoundException {
		if (StartScene.currentKey != null) {
			PrintWriter out = new PrintWriter(new FileOutputStream(new File("save.txt")));
			out.write(StartScene.currentKey);
			out.close();
		} else {
			PrintWriter out = new PrintWriter(new FileOutputStream(new File("save.txt")));
			out.write("");
			out.close();
		}
		deleteEmptyBSF();
	}

	static String load() {
		deleteEmptyBSF();
		try (BufferedReader in = new BufferedReader(new FileReader((new File("save.txt"))))) {
			return in.readLine();
		} catch (IOException e) {
		}
		return null;

	}

	private static void deleteEmptyBSF() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File("BinaryStringFile.txt")));) {
			if (br.readLine() == null) {
				br.close();
				new File("BinaryStringFile.txt").delete();
			}
		} catch (Exception e) {
		}
	}
}
