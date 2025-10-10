package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * A human player in the game Othello.
 * TODO: Document this class and make minimal changes as necessary.
 * 
 * @author arnold
 *
 */
public class PlayerHuman {

    // handles wrong inputs such as letters and numbers out of range
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private Othello othello;
	private char player;

    /**
     * This is the constructor. It creates a player instance and assigns the specific char to it (X or O).
     * @param othello the Othello game
     * @param player The player char (X or O)
     */
	public PlayerHuman(Othello othello, char player) {
		
		this.othello = othello;
		this.player = player;
	}

    /**
     *
     * Allows the player to choose a row and col for their next move. It does not check if the move is legal.
     *
     * @return new move objects with the row and col inputed
     */
	public Move getMove() {
		
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

    /**
     * Asks a user for an input and if it is invalid repeats the question until a valid number is inputed.
     *
     * @param message the message to display to the user
     * @return a valid integer for the move or -1 if invalid.
     */
	private int getMove(String message) {

		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
