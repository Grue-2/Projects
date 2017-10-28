/*
JC

10/26/2017 - clean up for github update

This application acts like a mini chat program
piggybacking on discord servers letting you
talk to your discord friends with RSA encrypted
text.

That is anything pass through discord is RSA encrypted
text.

The general idea is this kind of program allows end to end
encryption on services which do not normally support it.
 */

package jc.CryptoDiscordChat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CryptoChatChatRSA extends Application {
	private static Stage stage;
	private static Class cl4ss;

	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("CryptoChat");
		cl4ss = getClass();
		stage = arg0;
		loadScene("StartScene.fxml");
	}

	public static void loadScene(String scene) throws IOException {
		Scene sceneObject = new Scene(FXMLLoader.load(cl4ss.getResource(scene)));
		stage.setScene(sceneObject);
		stage.show();
	}

	/*
	 * The code for this project started before I had any experience with
	 * threads. Instead of cleaner solutions like are found in KeyManager I just
	 * brute forced it.
	 * 
	 * The issue is javaFX has an integrated API for asking the thread to
	 * perform tasks. I did not know the API existed or would exist due to my
	 * inexperience. This was the work around I found at the time.
	 * 
	 * Part of me thinks I should fix it before putting it up on github, but
	 * part of me also kind of likes seeing how I worked around aspects of code
	 * I diddn't understand.
	 */
	// kind of a gross work around to ordering of threads
	static void pause() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		save();
		System.exit(0);
	}

	static void save() {
		String files[] = { "userfile.txt", "targetfile.txt", "tokenfile.txt" };
		String data[] = { StartScene.user, StartScene.target, StartScene.token };

		for (int i = 0; i < 3; ++i) {
			try (PrintWriter writer = new PrintWriter(new File(files[i]))) {
				writer.println(data[i]);

			} catch (FileNotFoundException e) {
				System.err.println("Problem saving files.");

				e.printStackTrace();
			}

		}
	}

	static void loadKey() {
		String files[] = { "userfile.txt", "targetfile.txt", "tokenfile.txt" };
		String data[] = new String[3];

		for (int i = 0; i < 3; ++i) {
			try (Scanner reader = new Scanner(new File(files[i]))) {
				StringBuilder loadedData = new StringBuilder();

				while (reader.hasNextLine())
					loadedData.append(reader.nextLine());

				data[i] = loadedData.toString(); // this doesn't change the
													// actual object

			} catch (FileNotFoundException e) {
				// fail silently when there's no save file
			}
		}

		if (data[0] == null)
			StartScene.user = "";
		else
			StartScene.user = data[0];

		if (data[1] == null)
			StartScene.target = "";
		else
			StartScene.target = data[1];

		if (data[2] == null)
			StartScene.token = "";
		else
			StartScene.token = data[2];
	}

}
