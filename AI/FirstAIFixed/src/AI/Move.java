/*
JC

October 28, 2017

Fixing and documenting Tic-Tac-Toe AI for GitHub.
 */

package AI;

/*
Class holds the tic-tac-toe move that was made, and the board state it was made in.
 */
public class Move {
	public int state[][];
	public int choice;
	
	public Move(int[][] state, int choice) {
		this.state = new int[][]{{state[0][0],state[0][1],state[0][2]},
								 {state[1][0],state[1][1],state[1][2]},
								 {state[2][0],state[2][1],state[2][2]}};
		
		this.choice = choice;
	}
}
