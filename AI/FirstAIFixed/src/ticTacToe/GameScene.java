/*
JC

October 28, 2017

Fixing and documenting Tic-Tac-Toe AI for GitHub.
 */

package ticTacToe;

import java.util.Random;

import AI.AI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameScene {

	@FXML
	Label title;
	@FXML
	ImageView splash;
	@FXML
	GridPane gp;
	@FXML
	AnchorPane root;
	@FXML
	BorderPane bp;
	@FXML
	Button next;

	private AI ai;
	private boolean xTurn = true;
	private boolean notEnded = true;
	private boolean aiIsX = false;
	private int[][] state = new int[3][3];
	private Button[][] buttons = new Button[3][3];

	public void initialize() {
		if (TicTacToeGame.AI_GAME)
			ai = new AI();

		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 3; k++) {
				Button b = new Button();
				b.setGraphic(new ImageView(new Image("TicTacToe/blank.png", 288, 125, false, false)));
				gp.add(b, i, k);
				int x = i, x2 = k;// enclosing scope???
				b.setOnAction(e -> pcTakeTurn(x, x2));
				buttons[i][k] = b;
			}
		if (new Random().nextInt(2) == 1) {
			title.setText(title.getText() + ": You're O's AI is X");
			aiIsX = true;
			aiTakeTurn();

		} else
			title.setText(title.getText() + ": You're X's AI is O");
	}

	// needs to be public -> nope
	@FXML
	private Object next() {
		TicTacToeGame.startAGame();
		return null;
	}

	private void pcTakeTurn(int i, int k) {
		takeTurn(i, k);
		if (notEnded)
			aiTakeTurn();
	}

	public void takeTurn(int i, int k) {
		if (notEnded) {
			if (xTurn) {
				buttons[i][k].setVisible(false);
				buttons[i][k].setManaged(false);
				buttons[i][k] = null;
				gp.add(new ImageView(new Image("TicTacToe/x.png", 288, 125, false, false)), i, k);
				state[i][k] = 1;
			} else {
				buttons[i][k].setVisible(false);
				buttons[i][k].setManaged(false);
				buttons[i][k] = null;
				gp.add(new ImageView(new Image("TicTacToe/o.png", 288, 125, false, false)), i, k);
				state[i][k] = -1;
			}
			xTurn = !xTurn;
			testVictory();
		}
	}

	public void testVictory() {
		int sumDiag = 0, sumDiag2 = 0;
		boolean noneEmpty = true;
		
		for (int i = 0; i < 3; i++) {
			int sumRow = 0, sumCol = 0;
			for (int k = 0; k < 3; k++) {
				sumRow += state[i][k];
				sumCol += state[k][i];
				if (state[i][k] == 0)
					noneEmpty = false;
			}
			if (sumRow == 3) {
				win("X");
				if (TicTacToeGame.AI_GAME)
					return;
			} else if (sumRow == -3) {
				win("O");
				return;
			} else if (sumCol == 3) {
				win("X");
				return;
			} else if (sumCol == -3) {
				win("O");
				return;
			}
			sumDiag += state[i][i];
			sumDiag2 += state[2 - i][i];
		}
		if (sumDiag == 3) {
			win("X");
			return;
		} else if (sumDiag == -3) {
			win("O");
			return;
		} else if (sumDiag2 == 3) {
			win("X");
			return;
		} else if (sumDiag2 == -3) {
			win("O");
			return;
		} else if (noneEmpty)
			win("Nobody");
	}

	private void win(String winner) {
		notEnded = false;
		
		if (TicTacToeGame.AI_GAME) 
		{
			if (winner.equals("X")) {
				if (aiIsX)
					ai.gameOver(1);
				else
					ai.gameOver(-1);
			} else if (winner.equals("O")){
				if (aiIsX)
					ai.gameOver(-1);
				else
					ai.gameOver(1);
			}
			else
				ai.gameOver(0);
		}
		
		title.setText(title.getText() + ": " + winner + " wins !");
	}

	private void aiTakeTurnRandom() {
		Random rng = new Random();
		int i = rng.nextInt(3), k = rng.nextInt(3);
		if (buttons[i][k] != null)
			takeTurn(i, k);
		else
			aiTakeTurnRandom();
	}

	private void aiTakeTurn() {
		if (TicTacToeGame.AI_GAME) {
			int[] move = ai.makeMove(state);
			takeTurn(move[0], move[1]);
		} else
			aiTakeTurnRandom();
	}

}
