package ca.utoronto.utm.assignment1.othello;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold
 *
 */

public class PlayerGreedy {

    private char player;
    private Othello othello;

    public PlayerGreedy(Othello othello, char player) {
        this.player = player;
        this.othello = othello;
    }

    public Move getMove() {
        int amount = -1;
        Move best = null;

        for (int row = 0; row < othello.board.getDimension(); row = row + 1) {
            for (int col = 0; col < othello.board.getDimension(); col = col + 1) {
                if (othello.board.get(row, col) == OthelloBoard.EMPTY) {
                    int flipamount = 0;
                    boolean valid = false;
                    for (int drow = -1; drow <= 1; drow = drow + 1) {
                        for (int dcol = -1; dcol <= 1; dcol = dcol + 1) {
                            if (drow != 0 || dcol != 0) {
                                int flips = 0;
                                int r = row + drow;
                                int c = col + dcol;
                                char opponent = OthelloBoard.otherPlayer(player);

                                while (othello.board.get(r, c) == opponent && r >= 0 && r < othello.board.getDimension() && c >= 0 && c < othello.board.getDimension()) {
                                    flips = flips + 1;
                                    r = r + drow;
                                    c = c + dcol;
                                }
                                if (flips > 0 && r >= 0 && r < othello.board.getDimension() && c >= 0 && c < othello.board.getDimension() && othello.board.get(r, c) == player) {
                                    flipamount = flipamount + flips;
                                    valid = true;

                                }
                            }
                        }
                    }
                    if (valid == true) {
                        int a = othello.board.getCount(player) + flipamount + 1;
                        if (a > amount) {
                            amount = a;
                            best = new Move(row, col);
                        } else if (a == amount) {
                            if (best == null || row < best.getRow() || (row == best.getRow() && col < best.getCol())) {
                                best = new Move(row, col);
                            }
                        }
                    }
                }
            }
        }
        return best;
    }
}