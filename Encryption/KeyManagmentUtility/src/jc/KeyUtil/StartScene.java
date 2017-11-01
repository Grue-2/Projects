/*
JC

11/01/2017

@version GitHub-date
 */

package jc.KeyUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartScene {
	@FXML
	Button giveKey;
	@FXML
	TextField amtBytes;
	@FXML
	TextField keyField;
	@FXML
	Label currentAction;
	@FXML
	Label warning;
	@FXML
	CheckBox test;

	static String currentKey;

	public void initialize() {
		keyField.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldval, Object newval) {
				currentKey = (String) newval;
			}
		});
		String loaded = KeyManager.load();
		if (loaded != null)
			keyField.setText(loaded);
	}

	@FXML
	public Object giveKey() throws IOException {
		try {
			giveKey.setDisable(true);
			warning.setVisible(false);
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() {
					try {
						boolean hasTextKey = findTextKey();
						int leftToGet = getWantedBytes();

						if (!hasTextKey)
							if (!createTextKey(test.selectedProperty().get()))
								return null;

						BufferedReader fileReader = new BufferedReader(
								new FileReader(new File("BinaryStringFile.txt")));
						StringBuilder result = new StringBuilder();

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								currentAction.setText("Reading bytes off textFile");
							}
						});

						while (leftToGet > 0) {

							String line;
							if ((line = fileReader.readLine()) != null) {
								result.append(line);
								leftToGet--;
							} else if (!createTextKey(result, fileReader, test.selectedProperty().get()))
								return null;
						}

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								currentAction.setText("Removing used bytes from Text Key.");
							}
						});

						int count = 0;
						String line;
						BufferedWriter out = new BufferedWriter(
								new PrintWriter(new FileOutputStream(new File("BinaryStringFileTEMP.txt"))));
						while ((line = fileReader.readLine()) != null) {
							if (++count % 1000000 == 0)
								System.out.println(count / 1000000 +"e6 and counting bytes left in key.");
							out.append(line);
							out.append("\n");
						}

						fileReader.close();
						out.close();

						new File("BinaryStringFile.txt").delete();

						System.out.println("Successfully deleted used key material: "
								+ new File("BinaryStringFileTEMP.txt").renameTo(new File("BinaryStringFile.txt")));

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								keyField.appendText(result.toString());
								currentAction.setText("Key grab successful.");
								giveKey.setDisable(false);
							}
						});

					} catch (Exception e) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								giveKey.setDisable(false);
							}
						});
						return null;
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							giveKey.setDisable(false);
						}
					});
					return null;
				}
			};
			new Thread(task).start();
		} catch (Exception e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					new File("BinaryStringFile.txt").delete();
					new File("save.txt").delete();
					keyField.setText("");
					currentAction.setText("System hit an unexpected error.\nPossible undefined behavior");
					giveKey.setDisable(false);
				}
			});
		}

		return null;
	}

	private static void overwrite() throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File("BinaryStringFile.txt")))) {
			writer.write("");
		}
		new File("BinaryStringFile.txt").delete();
	}

	private boolean findTextKey() throws FileNotFoundException, IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentAction.setText("Searching for textKey.");
				currentAction.setVisible(true);
			}
		});
		File[] files = new File(".").listFiles();
		boolean hasTextKey = false;
		for (File f : files) {
			if (f.toString().contains("BinaryStringFile")) {
				try (BufferedReader br = new BufferedReader(new FileReader(new File("BinaryStringFile.txt")));) {
					if (br.readLine() != null) {
						hasTextKey = true;
						break;
					}
				}
			}
		}
		return hasTextKey;
	}

	private boolean createTextKey(boolean test)
			throws InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentAction.setText("Eating a binary key." + "\nMay take a while. (progress printed to console)"
						+ "\nInterupting makes KeyManager lose track of place.");
			}
		});
		int result;
		if ((result = KeyEater.eat(test)) != KeyEater.GOOD) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (result == KeyEater.NO_BINARY_KEY)
						currentAction.setText("Can't find binary key to eat.");
					else
						currentAction.setText("Can't find AES key to eat with.");
					giveKey.setDisable(false);
				}
			});
			return false;
		}
		if (result == KeyEater.FAILED_TEST) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					warning.setVisible(true);
					currentAction.setText(
							"KeyFile randomness testing failed\nTest result details in console on keyfile creation.");
					giveKey.setDisable(false);
					try {
						overwrite();
					} catch (FileNotFoundException e) {
						System.out.println("Failed to overwrite.");
						e.printStackTrace();
					}
				}
			});
			return false;
		}
		return true;

	}

	private boolean createTextKey(StringBuilder result, BufferedReader fileScanner, boolean test)
			throws InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException {
		int hold;
		if ((hold = KeyEater.eat(test)) != KeyEater.GOOD) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try (BufferedReader br = new BufferedReader(new FileReader(new File("BinaryStringFile.txt")));) {
						if (br.readLine() == null) {
							if (hold == KeyEater.NO_BINARY_KEY)
								currentAction.setText("Can't find binary key to eat.");
							else
								currentAction.setText("Can't find AES key to eat with.");
						} else {
							currentAction.setText("Can't find binary key to eat." + "\nJust appending whats left.");
							keyField.appendText(result.toString());
						}
					} catch (Exception e) {
					}
					giveKey.setDisable(false);
				}
			});
			fileScanner.close();
			overwrite();
			new File("BinaryStringFile.txt").delete();
			return false;
		}
		if (hold == KeyEater.FAILED_TEST) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					warning.setVisible(true);
					currentAction.setText(
							"KeyFile randomness testing failed\nTest result details in console on keyfile creation.");
					giveKey.setDisable(false);
					try {
						overwrite();
					} catch (FileNotFoundException e) {
						System.out.println("Failed to overwrite.");
						e.printStackTrace();
					}
				}
			});

			return false;
		}
		return true;
	}

	private int getWantedBytes() {
		try {
			return Integer.parseInt(amtBytes.getText());
		} catch (Exception e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					currentAction.setText(
							"Can't parse amount of bytes to get.\n" + " Please enter an integer value for amount.");
					giveKey.setDisable(false);
				}
			});
			return 0;
		}
	}
}
