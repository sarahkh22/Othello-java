# Othello (Reversi) Game

A fully playable implementation of the classic **Othello (Reversi)** board game developed in **Java** using object-oriented programming principles. The project includes multiple game modes, modular controller design, and AI players ranging from random to greedy heuristic-based strategies.


## Features

- Complete implementation of Othello game rules using an 8×8 board
- Move validation with automatic disc flipping in all directions
- Multiple player types:
  - Human player
  - Random AI
  - Greedy heuristic AI
- Multiple game modes via controllers:
  - Human vs Human
  - Human vs Random AI
  - Human vs Greedy AI
  - Random vs Random
  - Random vs Greedy
- Modular controller-based architecture supporting different gameplay configurations
- Game state tracking including legal moves and win detection


## Project Structure

- `Othello.java` → Main entry point of the game
- `OthelloBoard.java` → Board representation and game logic
- `Move.java` → Represents a move on the board
- `PlayerHuman.java` → Human player implementation
- `PlayerRandom.java` → AI that selects random valid moves
- `PlayerGreedy.java` → AI that evaluates moves using a heuristic
- Controllers:
  - `OthelloControllerHumanVSHuman.java`
  - `OthelloControllerHumanVSRandom.java`
  - `OthelloControllerHumanVSGreedy.java`
  - `OthelloControllerRandomVSRandom.java`
  - `OthelloControllerRandomVSGreedy.java`


## How It Works

The game uses an 8×8 grid stored in `OthelloBoard`. When a player places a piece, the program checks all eight directions to determine valid flips and updates the board accordingly.

AI behavior varies by player type:
- **Random AI** selects any legal move uniformly at random.
- **Greedy AI** evaluates all legal moves and selects the one with the highest heuristic score based on board advantage.

Controllers manage the flow of the game between two players, handling turn order and game termination.


## Technologies Used

- Java
- Object-Oriented Programming (OOP)


## Running the Project

Compile all files:

```bash
javac *.java
```

Run a specific controller (example: Human vs Greedy AI):

```bash
java OthelloControllerHumanVSGreedy
```


## Future Improvements

- Implement Minimax with Alpha-Beta Pruning for stronger AI gameplay
- Add difficulty scaling for AI players
- Improve heuristic evaluation (corner priority, mobility, stability)
- Add graphical user interface (GUI)
