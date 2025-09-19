package ca.utoronto.utm.assignment1.othello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {
    Move move;

    @BeforeEach
    void setUp() {
        move = new Move(3, 5);
    }

    @AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getRow() {
        assertEquals(3, move.getRow(), "getRow");
    }

    @org.junit.jupiter.api.Test
    void getCol() {
        assertEquals(5, move.getCol(), "getCol");
    }

    @org.junit.jupiter.api.Test
    void testToString1() {
        assertEquals("(3,5)", move.toString(), "toString");
    }
}
