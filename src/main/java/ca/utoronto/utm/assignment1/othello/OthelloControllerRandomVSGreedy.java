package ca.utoronto.utm.assignment1.othello;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author arnold
 *
 */
public class OthelloControllerRandomVSGreedy {


    protected Othello othello;
    PlayerRandom player1;
    PlayerGreedy player2;

    /**
     * Constructs a new OthelloController with a new Othello game, ready to play
     * with two users at the console.
     */
    public OthelloControllerRandomVSGreedy() {

        this.othello = new Othello();
        this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
    }

    public void play() {

        while (!othello.isGameOver()) {
            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player1.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player2.getMove();

            if (move != null){
                othello.move(move.getRow(), move.getCol());
            }
        }
    }

    public char getWinner(){
        return othello.getWinner();
    }

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */

	public static void main(String[] args) {

		int p1wins = 0, p2wins = 0, numGames = 10000;

        for (int i = 0; i < numGames; i = i + 1){
            OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
            oc.play();

            char winner = oc.getWinner();
            if (winner == OthelloBoard.P1){
                p1wins = p1wins + 1;
            } else if (winner == OthelloBoard.P2){
                p2wins = p2wins + 1;
            }

        }

		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
