package sos;

import sos.SOSGame.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestSOSGame {
	private SOSGame game;
	
	@BeforeEach
	public void setup(GameMode mode) {
		if(mode == GameMode.SIMPLE)
			game = new SOSsimple();
		else
			game = new SOSgeneral();
		game.setRedLetter(sORo.O);
	}
	
	@Test
	public void testSize() {  // AC 1.1 1.2
		setup(GameMode.SIMPLE);
		game.setSize(4);
		assertEquals(4, game.getSize());
	}
	
	@Test
	public void testStartSize() {  // AC 1.3
		setup(GameMode.SIMPLE);
		assertEquals(3, game.getSize());
	}
	
	@Test
	public void testGameMode() {  // AC 2.1
		setup(GameMode.SIMPLE);
		game = new SOSgeneral();
		assertEquals(GameMode.GENERAL, game.getGameMode());
	}
	
	@Test
	public void testNewGameMode() {  // AC 2.2
		setup(GameMode.SIMPLE);
		assertEquals(GameMode.SIMPLE, game.getGameMode());
	}
	
	@Test
	public void testNewBoard() {  // AC 3.1
		setup(GameMode.SIMPLE);
		for(int i = 0; i < game.getSize(); i++) {
			for(int j = 0; j < game.getSize(); j++) {
				assertEquals(game.getBoardCell(i,j), Cell.EMPTY);
			}
		}
		assertTrue(game.getTurn() == 'B');
	}
	
	@Test
	public void testValidSimpleMove() {  //AC 4.1
		setup(GameMode.SIMPLE);
		//Simulating an ongoing game
		game.makeMove(1, 1); //blue s move
		game.makeMove(0, 0); //red o move
		//Now check this move
		game.makeMove(0, 1); //Should be blue s move
		assertTrue(game.getBoardCell(0,1) == Cell.S); //Move made
		assertTrue(game.getTurn() == 'R'); //Turn changed
		
	}
	@Test // ChatGPT generated test   AC 4.2
    public void testInvalidSimpleMove() {
		setup(GameMode.SIMPLE);
        // Initial move: Blue places 'S' at (1,1)
        game.makeMove(1, 1);
        assertEquals(SOSGame.Cell.S, game.getCell(1, 1), "The first move should be an 'S'");

        // Capture the turn before the invalid move (should be Red's turn now)
        char previousTurn = game.getTurn();

        // Attempt to place another letter at the same spot (invalid move)
        game.makeMove(1, 1);

        // Ensure the cell is still 'S' (unchanged)
        assertEquals(SOSGame.Cell.S, game.getCell(1, 1), "Cell should remain unchanged after an invalid move");

        // Ensure the turn did NOT change
        assertEquals(previousTurn, game.getTurn(), "The turn should remain the same after an invalid move");
    }
	
	@Test
	public void testSimpleBlueWin() { //5.1
		setup(GameMode.SIMPLE);
		game.makeMove(0, 0);
		game.makeMove(0, 1);
		game.makeMove(0, 2);
		assertEquals(game.getGameState(), GameState.BLUE_WON);
	}
	
	@Test
	public void testSimpleRedWin() { //5.2
		setup(GameMode.SIMPLE);
		game.makeMove(0, 0);
		game.makeMove(0, 1);
		game.makeMove(1, 1);
		//change red's move to S, then make SOS
		game.setRedLetter(sORo.S);
		game.makeMove(0, 2);
		assertEquals(game.getGameState(), GameState.RED_WON);
	}
	
	@Test
	public void testContinuedSimpleGame() { // AC 5.3 & 5.4
		setup(GameMode.SIMPLE);
		//AC 5.3
		game.makeMove(0, 0);
		//Assure game is not over and turn is now red
		assertEquals(game.getGameState(), GameState.PLAYING);
		assertTrue(game.getTurn() == 'R');
		//AC 5.4
		//Now make a red move and ensure the same
		game.makeMove(1, 1);
		assertEquals(game.getGameState(), GameState.PLAYING);
		assertTrue(game.getTurn() == 'B');
	}
	
	@Test
	public void testDrawnSimpleGame() {  // AC 5.5
		setup(GameMode.SIMPLE);
		//fill board with all S' to create a draw game
		game.setRedLetter(sORo.S);
		game.makeMove(0, 0);
		game.makeMove(0, 1);
		game.makeMove(0, 2);
		game.makeMove(1, 0);
		game.makeMove(1, 1);
		game.makeMove(1, 2);
		game.makeMove(2, 0);
		game.makeMove(2, 1);
		game.makeMove(2, 2);
		assertTrue(game.isFull());
		assertEquals(game.getGameState(), GameState.DRAW, "There are no moves left, game drawn");
	}
	
	@Test  // ChatGPT generated test 2    AC 6.1
	public void testValidGeneralMove() {
		setup(GameMode.GENERAL);
	    char initialTurn = game.getTurn(); // Get initial turn (B or R)

	    game.makeMove(0, 0); // Make a valid move
	    Cell expectedCell = (game.getBlueLetter() == SOSGame.sORo.S) ? SOSGame.Cell.S : SOSGame.Cell.O;

	    // Check if the move was correctly placed on the board
	    assertEquals(expectedCell, game.getCell(0, 0));

	    // Check if the turn switched after making the move
	    assertNotEquals(initialTurn, game.getTurn());
	}
	
	@Test
    public void testInvalidGeneralMove() { // AC 6.2
		//Try to make move on same spot twice
		setup(GameMode.GENERAL);
        game.makeMove(1, 1);
        char previousTurn = game.getTurn();
        game.makeMove(1, 1);
        assertEquals(SOSGame.Cell.S, game.getCell(1, 1), "Cell should remain unchanged after an invalid move");
        assertEquals(previousTurn, game.getTurn(), "The turn should remain the same after an invalid move");
    }
	
	@Test
	public void testGeneralBlueWin() { // AC 7.1
		setup(GameMode.GENERAL);
		//Play through game where blue makes more SOSs
		game.makeMove(0, 0);
		game.makeMove(0, 1);
		game.makeMove(0, 2);
		game.makeMove(1, 0);
		game.makeMove(1, 1);
		game.makeMove(1, 2);
		game.makeMove(2, 0);
		game.makeMove(2, 1);
		game.makeMove(2, 2);
		assertEquals(game.getGameState(), GameState.BLUE_WON);
	}
	
	@Test
	public void testGeneralRedWin() { // AC 7.2
		setup(GameMode.GENERAL);
		//Play through game where red makes more SOSs
		game.makeMove(0, 0);
		game.makeMove(0, 1);
		game.makeMove(1, 0);
		game.setRedLetter(sORo.S);
		game.makeMove(0, 2);
		//Score should now be 1-0 red. Rest of board filled with S
		game.makeMove(1, 1);
		game.makeMove(1, 2);
		game.makeMove(2, 0);
		game.makeMove(2, 1);
		game.makeMove(2, 2);
		assertEquals(game.getGameState(), GameState.RED_WON);
	}
	
	@Test
	public void testContinuedGeneralGame() { // AC 7.3
		setup(GameMode.GENERAL);
		game.makeMove(0, 0);
		//Assure game is not over and turn is now red
		assertEquals(game.getGameState(), GameState.PLAYING);
		assertTrue(game.getTurn() == 'R');
		//Now make a red move and ensure the same
		game.makeMove(1, 1);
		assertEquals(game.getGameState(), GameState.PLAYING);
		assertTrue(game.getTurn() == 'B');
	}
	
	@Test
	public void testDrawnGeneralGame() { // AC 7.4
		setup(GameMode.GENERAL);
		game.makeMove(0, 0);
		game.makeMove(1, 1);
		game.setRedLetter(sORo.S);
		game.makeMove(2, 2); //blue scores
		game.makeMove(0, 1);
		game.makeMove(2, 1); //red scores
		game.makeMove(1, 0);
		game.makeMove(1, 2); //blue scores
		game.makeMove(2, 0);
		game.makeMove(0, 2); //red scores
		assertEquals(GameState.DRAW, game.getGameState(), "There are no moves left and score tied");
	}
	
	@Test
	public void testPlayerChangeLetter() {  
		setup(GameMode.SIMPLE);
		game.setBlueLetter(sORo.O);
		assertEquals(game.getBlueLetter(), sORo.O);
		
		game.setRedLetter(sORo.O);
		assertEquals(game.getBlueLetter(), sORo.O);
	}
}
	
