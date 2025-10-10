package ca.utoronto.utm.assignment1.othello;
/**
 *
 * A move in Othello game. A move is represented by a row and column. Once a move object is created it becomes immutable.
 *
 * @author arnold
 *
 */
// TODO: Javadoc this class
public class Move {
	private int row, col;

    /**
     *
     * This is the constructor. It constructs a new move object with the parameters row and col given.
     *
     * @param row the row index
     * @param col the column index
     */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

    /**
     *
     * @return the row index
     */
	public int getRow() {
		return row;
	}

    /**
     *
     * @return the column index
     */
	public int getCol() {
		return col;
	}

    /**
     *
     * @return a string representation of the row and column coordinates in this format "(row, col)"
     */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
