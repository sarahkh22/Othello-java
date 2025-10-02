package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom {
	
	private Random rand = new Random();
    private Othello othello;
    private char player;

    public PlayerRandom(Othello othello, char player){
        this.othello = othello;
        this.player = player;

    }

	public Move getMove() {
		ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < othello.board.getDimension(); row = row +1){
            for (int col = 0; col < othello.board.getDimension(); col = col + 1){
                if (othello.board.get(row, col) == OthelloBoard.EMPTY){
                    boolean valid = false;
                    for (int drow = -1; drow <= 1; drow = drow + 1){
                        for (int dcol = -1; dcol <= 1; dcol = dcol + 1){
                            if (drow != 0 || dcol != 0){
                                char result = simplealternation(row, col, drow, dcol, player);
                                if (result == player){
                                    valid = true;
                                }
                            }
                        }
                    }
                    if (valid == true){
                        Move newmove = new Move(row, col);
                        moves.add(newmove);
                    }
                }
            }
        }
        if (moves.isEmpty()){
            return null;
        }
        else{
            int randomIndex = rand.nextInt(moves.size());
            return moves.get(randomIndex);
        }
	}

    public char simplealternation(int row, int col, int drow, int dcol, char player){
        if (row < 0 || row >= othello.board.getDimension() || col < 0 || col >= othello.board.getDimension()){
            return OthelloBoard.EMPTY;}

        if (othello.board.get(row, col) != OthelloBoard.EMPTY){
            return OthelloBoard.EMPTY;}

        int r;
        int c;
        r = row + drow;
        c = col + dcol;

        if (r < 0 || r >= othello.board.getDimension() || c < 0 || c >= othello.board.getDimension() || othello.board.get(r, c) == OthelloBoard.EMPTY){
            return OthelloBoard.EMPTY;
        }

        char opponent = othello.board.otherPlayer(player);

        if (othello.board.get(r, c) != opponent){
            return OthelloBoard.EMPTY;
        }

        while (r >= 0 && c >= 0 && r < othello.board.getDimension() && c < othello.board.getDimension() && othello.board.get(r, c) == opponent){
            r = r + drow;
            c = c + dcol;
        }

        if (r >= 0 && c >= 0 && r < othello.board.getDimension() && c < othello.board.getDimension() && othello.board.get(r, c) == player){
            return player;
        }

        return OthelloBoard.EMPTY;
    }
}
