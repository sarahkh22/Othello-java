package ca.utoronto.utm.assignment1.othello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class OthelloTest {
    Othello othello;
    Move[] moves = {new Move(2, 4), new Move(2, 5), new Move(2, 6), new Move(2, 3), new Move(2, 2), new Move(3, 2),
            new Move(4, 2), new Move(5, 4), new Move(6, 4)};

    @BeforeEach
    public void setUp() throws Exception {
        othello = new Othello();
        othello.move(2, 4);
        othello.move(2, 5);
        othello.move(2, 6);
        othello.move(2, 3);

        // Board now looks like
        //   0 1 2 3 4 5 6 7
        //  +-+-+-+-+-+-+-+-+
        // 0| | | | | | | | |0
        //  +-+-+-+-+-+-+-+-+
        // 1| | | | | | | | |1
        //  +-+-+-+-+-+-+-+-+
        // 2| | | |O|X|X|X| |2
        //  +-+-+-+-+-+-+-+-+
        // 3| | | |O|O| | | |3
        //  +-+-+-+-+-+-+-+-+
        // 4| | | |O|X| | | |4
        //  +-+-+-+-+-+-+-+-+
        // 5| | | | | | | | |5
        //  +-+-+-+-+-+-+-+-+
        // 6| | | | | | | | |6
        // +-+-+-+-+-+-+-+-+
        // 7| | | | | | | | |7
        //  +-+-+-+-+-+-+-+-+
        //   0 1 2 3 4 5 6 7
        //
        // X:4 O:4  X moves next
        // row: col: X makes move (2,2)
        //
        //   0 1 2 3 4 5 6 7
        //  +-+-+-+-+-+-+-+-+
        // 0| | | | | | | | |0
        //  +-+-+-+-+-+-+-+-+
        // 1| | | | | | | | |1
        //  +-+-+-+-+-+-+-+-+
        // 2| | |X|X|X|X|X| |2
        //  +-+-+-+-+-+-+-+-+
        // 3| | | |X|O| | | |3
        //  +-+-+-+-+-+-+-+-+
        // 4| | | |O|X| | | |4
        //  +-+-+-+-+-+-+-+-+
        // 5| | | | | | | | |5
        //  +-+-+-+-+-+-+-+-+
        // 6| | | | | | | | |6
        //  +-+-+-+-+-+-+-+-+
        // 7| | | | | | | | |7
        //  +-+-+-+-+-+-+-+-+
        //   0 1 2 3 4 5 6 7
        //
        // X:7 O:2  O moves next

    }

    @org.junit.jupiter.api.Test
    public void testGetWhosTurn() {
        assertEquals(OthelloBoard.P1, othello.getWhosTurn());
        othello.move(2, 2);
        assertEquals(OthelloBoard.P2, othello.getWhosTurn());
    }

    @org.junit.jupiter.api.Test
    public void testGetCount() {
        assertEquals(4, othello.getCount(OthelloBoard.P1));
        assertEquals(4, othello.getCount(OthelloBoard.P2));
        othello.move(2, 2);
        assertEquals(7, othello.getCount(OthelloBoard.P1));
        assertEquals(2, othello.getCount(OthelloBoard.P2));
    }

    @org.junit.jupiter.api.Test
    public void testGetWinner() {
        Othello o = new Othello();
        for (int i = 0; i < moves.length; i++) {
            assertEquals(OthelloBoard.EMPTY, o.getWinner(), "During play");
            o.move(moves[i].getRow(), moves[i].getCol());
        }
        assertEquals(OthelloBoard.P1, o.getWinner(), "After winner");
    }

    @org.junit.jupiter.api.Test
    public void testIsGameOver() {
        Othello o = new Othello();
        for (int i = 0; i < moves.length; i++) {
            assertEquals(false, o.isGameOver(), "During play");
            o.move(moves[i].getRow(), moves[i].getCol());
        }
        assertEquals(true, o.isGameOver(), "After winner");
    }

}
