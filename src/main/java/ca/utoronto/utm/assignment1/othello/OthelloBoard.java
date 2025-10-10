package ca.utoronto.utm.assignment1.othello;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == P1){
            return P2;}
        if (player == P2){
            return P1;}
        return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {

        if (validCoordinate(row, col)) {
            return board[row][col];
        }
        return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
        if (row >= 0 && row < dim && col >= 0 && col < dim){
            return true;}
		return false;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
        if (validCoordinate(row, col) == false){
            return EMPTY;}

        if (board[row][col] != EMPTY){
            return EMPTY;}

        int r;
        int c;
        r = row + drow;
        c = col + dcol;

        if (validCoordinate(r, c) == false || board[r][c] == EMPTY){
            return EMPTY;
        }

        char opponent = board[r][c];
        char player = otherPlayer(opponent);

        while (validCoordinate(r, c) && board[r][c] == opponent){
            r = r + drow;
            c = c + dcol;
        }

        if (validCoordinate(r, c) == true && board[r][c] == player){
            return player;
        }

        return EMPTY;
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOO0
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
        if (validCoordinate(row, col) == false){
            return -1;
        }

        if (board[row][col] != EMPTY){
            return -1;
        }

        int r = row + drow;
        int c = col + dcol;
        int count = 0;

        if (validCoordinate(r, c) == false){
            return -1;
        }

        if (board[r][c] == EMPTY){
            return -1;
        }

        if (board[r][c] == player){
            return -1;
        }
        while (validCoordinate(r, c) && board[r][c] == otherPlayer(player)){
            r = r + drow;
            c = c + dcol;
            count = count + 1;
        }

        if (validCoordinate(r, c) == false){
            return -1;
        }

        if (board[r][c] != player){
            return -1;
        }

        for (int i = 1; i <= count; i = i + 1){
            board[row + i * drow][col + i * dcol] = player;
        }

        return count;
	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
		return alternation(row, col, drow, dcol);
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
        boolean player1;
        player1 = false;
        boolean player2;
        player2 = false;

        for (int row = 0; row < dim; row = row + 1){
            for (int col = 0; col < dim; col = col + 1){
                if (board[row][col] != EMPTY){

                }
                else{
                    for (int drow = -1; drow <= 1; drow = drow + 1){
                        for (int dcol = -1; dcol <= 1; dcol = dcol + 1){
                            if (drow != 0 || dcol != 0) {
                                char m = alternation(row, col, drow, dcol);
                                if (m == P1){
                                    player1 = true;
                                }
                                if (m == P2){
                                    player2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
		if (player1 && player2){
            return BOTH;
        }
        else if (player1) {
            return P1;
        }
        else if (player2) {
            return P2;
        }
        return EMPTY;
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		boolean changed = false;
        if (validCoordinate(row, col) == false){
            return false;
        }

        if (board[row][col] != EMPTY){
        return false;
    }

        for (int drow = -1; drow <= 1; drow = drow + 1) {
            for (int dcol = -1; dcol <= 1; dcol = dcol + 1) {
                if (drow != 0 || dcol != 0) {
                    if (alternation(row, col, drow, dcol) == player) {
                        flip(row, col, drow, dcol, player);
                        changed = true;
                    }
                }
            }
        }
        if (changed == true){
            board[row][col] = player;
            return true;
        }
        // HINT: Use some of the above helper methods to get this methods
		// job done!

		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
        int row;
        int col;
        for (row = 0; row < dim; row = row + 1){
            for (col = 0; col < dim; col = col + 1){
                if (get(row, col) == player)
                    count = count + 1;
            }
        }
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

        OthelloBoard b = new OthelloBoard(8);

        // Fill everything with EMPTY
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b.board[i][j] = OthelloBoard.EMPTY;
            }
        }

        // Set up your custom board:
        b.board[0][0] = OthelloBoard.P2; b.board[0][1] = OthelloBoard.P2; b.board[0][2] = OthelloBoard.P2; b.board[0][3] = OthelloBoard.P2;
        b.board[0][4] = OthelloBoard.P2; b.board[0][5] = OthelloBoard.P2; b.board[0][6] = OthelloBoard.P2;

        b.board[1][1] = OthelloBoard.P2; b.board[1][2] = OthelloBoard.P2; b.board[1][3] = OthelloBoard.P2;
        b.board[1][4] = OthelloBoard.P2; b.board[1][5] = OthelloBoard.P2; b.board[1][6] = OthelloBoard.P2; b.board[1][7] = OthelloBoard.P2;

        b.board[2][2] = OthelloBoard.P2; b.board[2][3] = OthelloBoard.P2; b.board[2][4] = OthelloBoard.P2;
        b.board[2][5] = OthelloBoard.P2; b.board[2][6] = OthelloBoard.P2; b.board[2][7] = OthelloBoard.P1;

        b.board[3][3] = OthelloBoard.P2; b.board[3][4] = OthelloBoard.P2; b.board[3][5] = OthelloBoard.P2;
        b.board[3][6] = OthelloBoard.P2; b.board[3][7] = OthelloBoard.P1;

        b.board[4][4] = OthelloBoard.P1; b.board[4][5] = OthelloBoard.P2; b.board[4][6] = OthelloBoard.P2; b.board[4][7] = OthelloBoard.P1;

        b.board[5][5] = OthelloBoard.P2; b.board[5][6] = OthelloBoard.P2; b.board[5][7] = OthelloBoard.P1;

        b.board[6][6] = OthelloBoard.P2; b.board[6][7] = OthelloBoard.P1;

        b.board[7][7] = OthelloBoard.P1;
        System.out.println("Testing flip method: before");
        System.out.println(b);

        System.out.println("Testing alternation:");
        System.out.println("alternation(0,0,0,1)=" + b.alternation(0,0,0,1));
        System.out.println("alternation(1,1,0,1)=" + b.alternation(1,1,0,1));
        System.out.println("alternation(2,2,0,1)=" + b.alternation(2,2,0,1));
        System.out.println("alternation(3,3,0,1)=" + b.alternation(3,3,0,1));
        System.out.println("alternation(4,4,0,1)=" + b.alternation(4,4,0,1));
        System.out.println("alternation(5,5,0,1)=" + b.alternation(5,5,0,1));
        System.out.println("alternation(6,6,0,1)=" + b.alternation(6,6,0,1));
        System.out.println("alternation(7,7,0,1)=" + b.alternation(7,7,0,1));
        System.out.println("alternation(0,-1,0,1)=" + b.alternation(0,-1,0,1));
        System.out.println("alternation(1,0,0,1)=" + b.alternation(1,0,0,1));
        System.out.println("alternation(2,1,0,1)=" + b.alternation(2,1,0,1));
        System.out.println("alternation(3,2,0,1)=" + b.alternation(3,2,0,1));
        System.out.println("alternation(4,3,0,1)=" + b.alternation(4,3,0,1));
        System.out.println("alternation(5,4,0,1)=" + b.alternation(5,4,0,1));
        System.out.println("alternation(6,5,0,1)=" + b.alternation(6,5,0,1));
        System.out.println("alternation(7,6,0,1)=" + b.alternation(7,6,0,1));

        System.out.println();
        System.out.println("Testing hasMove:");
        System.out.println("hasMove(0,0,0,1)=" + b.hasMove(0,0,0,1));
        System.out.println("hasMove(1,1,0,1)=" + b.hasMove(1,1,0,1));
        System.out.println("hasMove(2,2,0,1)=" + b.hasMove(2,2,0,1));
        System.out.println("hasMove(3,3,0,1)=" + b.hasMove(3,3,0,1));
        System.out.println("hasMove(4,4,0,1)=" + b.hasMove(4,4,0,1));
        System.out.println("hasMove(5,5,0,1)=" + b.hasMove(5,5,0,1));
        System.out.println("hasMove(6,6,0,1)=" + b.hasMove(6,6,0,1));
        System.out.println("hasMove(7,7,0,1)=" + b.hasMove(7,7,0,1));
        System.out.println("hasMove(0,-1,0,1)=" + b.hasMove(0,-1,0,1));
        System.out.println("hasMove(1,0,0,1)=" + b.hasMove(1,0,0,1));
        System.out.println("hasMove(2,1,0,1)=" + b.hasMove(2,1,0,1));
        System.out.println("hasMove(3,2,0,1)=" + b.hasMove(3,2,0,1));
        System.out.println("hasMove(4,3,0,1)=" + b.hasMove(4,3,0,1));
        System.out.println("hasMove(5,4,0,1)=" + b.hasMove(5,4,0,1));
        System.out.println("hasMove(6,5,0,1)=" + b.hasMove(6,5,0,1));
        System.out.println("hasMove(7,6,0,1)=" + b.hasMove(7,6,0,1));

	}
}
