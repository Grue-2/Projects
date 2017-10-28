/*
JC

October 28, 2017

Fixing and documenting Tic-Tac-Toe AI for GitHub.
 */

package AI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/*
	I removed quite a few tests, variability options, and learning
	styles for this version. But also fixed some of the poor design choices
	and made the AI function easily as a demo.
 */

//TODO: Make read and write only occur once on startup and shutdown ***

public class AI {
	// AI's preferences game state move preferences
	private int currentMovePrefs[][][][][][][][][][][];

	// memory of what moves it made this game
	private List<Move> movesMade;

	// AI object should only last for a single game.
	private boolean alive;

	// preferences loaded from file
	private static volatile int loadedMovePrefs[][][][][][][][][][][];

	static final SecureRandom rng;

	// Want to load from file only once during programs life span.
	static {
		rng = new SecureRandom();

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("preferences.ser"))) {
			loadedMovePrefs = (int[][][][][][][][][][][]) in.readObject();
		} catch (Exception e) {
			System.out.println("Save file could not be loaded. Defaulting knowledge base");

			// arrays should be defaulted to 0 in java.
			loadedMovePrefs = new int[3][3][3][3][3][3][3][3][3][3][3];
		}
	}

	public AI() {
		alive = true;

		movesMade = new ArrayList<>();
		
		currentMovePrefs = new int[3][3][3][3][3][3][3][3][3][3][3];

		for (int state1 = 0; state1 < 3; ++state1)
			for (int state2 = 0; state2 < 3; ++state2)
			for (int state3 = 0; state3 < 3; ++state3)
			for (int state4 = 0; state4 < 3; ++state4)
			for (int state5 = 0; state5 < 3; ++state5)
			for (int state6 = 0; state6 < 3; ++state6)
			for (int state7 = 0; state7 < 3; ++state7)
			for (int state8 = 0; state8 < 3; ++state8)
			for (int state9 = 0; state9 < 3; ++state9)
			for (int state10 = 0; state10 < 3; ++state10)
			for (int state11 = 0; state11 < 3; ++state11)
				currentMovePrefs[state1][state2][state3] 
								[state4][state5][state6] 
								[state7][state8][state9] 
								[state10][state11]
				= loadedMovePrefs[state1][state2][state3]
							     [state4][state5][state6]
							     [state7][state8][state9]
							     [state10][state11];
	}

	private int[][] stateDecoder(int codedState[][])
	{
		return new int[][]{
			{(codedState[0][0]+3)%3,(codedState[0][1]+3)%3,(codedState[0][2]+3)%3},
			{(codedState[1][0]+3)%3,(codedState[1][1]+3)%3,(codedState[1][2]+3)%3},
			{(codedState[2][0]+3)%3,(codedState[2][1]+3)%3,(codedState[2][2]+3)%3},
		};
	}
	
	// state values are: 0 for empty 1 for X, 2 for O
	public int[] makeMove(int state[][]) {
		if (alive) {
			
			int decodedState[][] = stateDecoder(state);
			
			int statePrefs[][] = currentMovePrefs[decodedState[0][0]][decodedState[0][1]][decodedState[0][2]]
												 [decodedState[1][0]][decodedState[1][1]][decodedState[1][2]]
												 [decodedState[2][0]][decodedState[2][1]][decodedState[2][2]];
			
			int maxPrefRating = Integer.MIN_VALUE;

			for (int row = 0; row < 3; ++row)
				for (int col = 0; col < 3; ++col)
					if (decodedState[row][col] == 0 && statePrefs[row][col] > maxPrefRating)
						maxPrefRating = statePrefs[row][col];

			List<int[]> options = new ArrayList<>(9);

			for (int row = 0; row < 3; ++row)
				for (int col = 0; col < 3; ++col)
					if (decodedState[row][col] == 0 && statePrefs[row][col] == maxPrefRating)
						options.add(new int[] { row, col });

			int optionChoice = rng.nextInt(options.size());

			int rowChoice = options.get(optionChoice)[0], colChoice = options.get(optionChoice)[1];

			movesMade.add(new Move(decodedState, rowChoice * 3 + colChoice));

			return new int[] { rowChoice, colChoice };
		} else
			return null;
	}

	// Has an overflow risk. In theory it should stop losing before
	// it occurs, and its executed enough times that the checks
	// slow training down. But it does concern me.
	public synchronized void gameOver(int gameResult) {
		if (alive && gameResult == -1) {

			Move lastMove = movesMade.get(movesMade.size() - 1), secondToLastMove = movesMade.get(movesMade.size() - 2);

			loadedMovePrefs[lastMove.state[0][0]][lastMove.state[0][1]][lastMove.state[0][2]]
							[lastMove.state[1][0]][lastMove.state[1][1]][lastMove.state[1][2]]
							[lastMove.state[2][0]][lastMove.state[2][1]][lastMove.state[2][2]]
							[lastMove.choice/ 3][lastMove.choice % 3] -= 10;

			loadedMovePrefs[secondToLastMove.state[0][0]][secondToLastMove.state[0][1]][secondToLastMove.state[0][2]]
					[secondToLastMove.state[1][0]][secondToLastMove.state[1][1]][secondToLastMove.state[1][2]]
					[secondToLastMove.state[2][0]][secondToLastMove.state[2][1]][secondToLastMove.state[2][2]]
					[secondToLastMove.choice/ 3][secondToLastMove.choice % 3] -= 1;
		}
		else if (alive && gameResult == 1)
		{
			Move lastMove = movesMade.get(movesMade.size() - 1);

			loadedMovePrefs[lastMove.state[0][0]][lastMove.state[0][1]][lastMove.state[0][2]]
							[lastMove.state[1][0]][lastMove.state[1][1]][lastMove.state[1][2]]
							[lastMove.state[2][0]][lastMove.state[2][1]][lastMove.state[2][2]]
							[lastMove.choice/ 3][lastMove.choice % 3] += 1;
		}

		alive = false;
	}

	public static synchronized void save() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("preferences.ser"));) {
			out.writeObject(loadedMovePrefs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}