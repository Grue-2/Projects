/*
JC

October 28, 2017

Fixing and documenting Tic-Tac-Toe AI for GitHub.
 */

package AI;

import java.text.DecimalFormat;

/*
AI doesn't come with any game knowledge, trainer
makes it play against random moves until it stops losing.
 */
public class AI_Trainer {
	private static int[][] state;
	private static int drawCount;
	private static int notLoseCount;
	private static boolean aiIsX;
	private static AI x, o;
	private static boolean xTurn;
	private static double previousBest;
	static {
		previousBest = 0.0;
	}

	public static void main(String... args) {
		trainAI();

		System.out.println("AI trained.");
	}

	private static void trainAI() {
		boolean failedTest = true;

		while (failedTest) {
			sparringPhase();

			failedTest = testPhase();
		}
	}

	private static void sparringPhase() {
		drawCount = 0;

		NEXT_GAME: while (drawCount < 3) {
			// DEBUG:System.out.println("trial");
			// DEBUG:System.out.println(drawCount);

			xTurn = true;
			state = new int[3][3];
			x = new AI();
			o = new AI();
			int move[];

			while (true) {
				// DEBUG:System.out.println("move");

				move = x.makeMove(state);
				takeTurn(move[0], move[1]);
				if (testGameOver())
					continue NEXT_GAME;
				move = o.makeMove(state);
				takeTurn(move[0], move[1]);
				if (testGameOver())
					continue NEXT_GAME;
			}

		}
	}

	// The test is winning or drawing 100,000 games in a row
	// versus a completely random opponent
	private static boolean testPhase() {
		notLoseCount = 0;
		// decay previous best to eliminate high rolls
		// from stopping saving progress on training.
		previousBest -= .01;

		NEXT_GAME: for (int trial = 0; trial < 100_000; ++trial) {
			if (trial != notLoseCount) {
				// DEBUG:System.out.println(notLoseCount);
				DecimalFormat twoPlaces = new DecimalFormat("#.00");
				if (notLoseCount / 100.0 > previousBest * .50) {
					previousBest = notLoseCount / 100.0;
					System.out.println("Currently at " + twoPlaces.format(previousBest)
							+ "% through the test. (Not a progress bar, will alternate alot.)\n");
					AI.save();
				}
				return true;
			}

			xTurn = true;
			state = new int[3][3];
			int move[];

			if (AI.rng.nextInt(2) == 1) {
				x = new AI();
				aiIsX = true;

				while (true) {
					move = x.makeMove(state);
					takeTurn(move[0], move[1]);
					if (testGameOver())
						continue NEXT_GAME;

					move = randomMove(state);
					takeTurn(move[0], move[1]);
					if (testGameOver())
						continue NEXT_GAME;
				}
			} else {
				o = new AI();
				aiIsX = false;

				while (true) {
					move = randomMove(state);
					takeTurn(move[0], move[1]);
					if (testGameOver())
						continue NEXT_GAME;

					move = o.makeMove(state);
					takeTurn(move[0], move[1]);
					if (testGameOver())
						continue NEXT_GAME;

				}

			}
		}

		AI.save();
		System.out.println("AI passed test. Just means it can beat randomness, it may have gotten lucky.");
		return false;
	}

	private static int[] randomMove(int state[][]) {
		int move[];

		do {
			move = new int[] { AI.rng.nextInt(3), AI.rng.nextInt(3) };

		} while (state[move[0]][move[1]] != 0);

		return move;
	}

	public static void takeTurn(int i, int k) {
		if (xTurn)
			state[i][k] = 1;
		else
			state[i][k] = -1;

		xTurn = !xTurn;
	}

	public static boolean testGameOver() {
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
				win(1);
				if (aiIsX)
					++notLoseCount;
				return true;
			} else if (sumRow == -3) {
				win(2);
				if (!aiIsX)
					++notLoseCount;
				return true;
			} else if (sumCol == 3) {
				win(1);
				if (aiIsX)
					++notLoseCount;
				return true;
			} else if (sumCol == -3) {
				win(2);
				if (!aiIsX)
					++notLoseCount;
				return true;
			}
			sumDiag += state[i][i];
			sumDiag2 += state[2 - i][i];
		}
		if (sumDiag == 3) {
			win(1);
			if (aiIsX)
				++notLoseCount;
			return true;
		} else if (sumDiag == -3) {
			win(2);
			if (!aiIsX)
				++notLoseCount;
			return true;
		} else if (sumDiag2 == 3) {
			win(1);
			if (aiIsX)
				++notLoseCount;
			return true;
		} else if (sumDiag2 == -3) {
			win(2);
			if (!aiIsX)
				++notLoseCount;
			return true;
		} else if (noneEmpty) {
			win(0);
			++drawCount;
			++notLoseCount;
			return true;
		} else
			return false;
	}

	private static void win(int winner) {
		if (winner == 1) {
			x.gameOver(1);
			o.gameOver(-1);
		} else if (winner == 2) {
			x.gameOver(-1);
			o.gameOver(1);
		} else {
			x.gameOver(0);
			o.gameOver(0);
		}
	}
}
