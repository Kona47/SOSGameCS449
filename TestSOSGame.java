package sos;

import sos.SOSGame.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestSOSGame {
	private SOSGame game;
	
	@BeforeEach
	public void setup() {
		game = new SOSGame();
		game.red = sORo.O;
	}
	
	@Test
	public void testSize() {  // AC 1.1 1.2
		setup();
		game.setSize(4);
		assertEquals(4, game.getSize());
	}
	
	@Test
	public void testStartSize() {  // AC 1.3
		setup();
		assertEquals(3, game.getSize());
	}
	
	@Test
	public void testGameMode() {  // AC 2.1
		setup();
		game.setGameMode(gameMode.GENERAL);
		assertEquals(gameMode.GENERAL, game.getGameMode());
	}
	
	@Test
	public void testNewGameMode() {  // AC 2.2
		setup();
		assertEquals(gameMode.SIMPLE, game.getGameMode());
	}
	
	@Test
	public void testNewBoard() {  // AC 3.1
		setup();
		for(int i = 0; i < game.SIZE; i++) {
			for(int j = 0; j < game.SIZE; j++) {
				assertEquals(game.board[i][j], Cell.EMPTY);
			}
		}
		assertTrue(game.turn == 'B');
	}
	
	@Test
	public void testValidSimpleMove() {  //AC 4.1
		setup();
		//Simulating an ongoing game
		game.makeMove(1, 1); //blue s move
		game.makeMove(0, 0); //red o move
		//Now check this move
		game.makeMove(0, 1); //Should be blue s move
		assertTrue(game.board[0][1] == Cell.S); //Move made
		assertTrue(game.turn == 'R'); //Turn changed
		
	}
	@Test // ChatGPT generated test   AC 4.2
    public void testInvalidSimpleMove() {
		setup();
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
	
	@Test  // ChatGPT generated test 2    AC 6.1
	public void testValidGeneralMove() {
		setup();
	    game.setGameMode(gameMode.GENERAL); // Set to GENERAL mode
	    char initialTurn = game.getTurn(); // Get initial turn (B or R)

	    game.makeMove(0, 0); // Make a valid move
	    Cell expectedCell = (game.blue == SOSGame.sORo.S) ? SOSGame.Cell.S : SOSGame.Cell.O;

	    // Check if the move was correctly placed on the board
	    assertEquals(expectedCell, game.getCell(0, 0));

	    // Check if the turn switched after making the move
	    assertNotEquals(initialTurn, game.getTurn());
	}
	
	@Test
    public void testInvalidGeneralMove() {  // AC 6.2
		setup();
		game.setGameMode(gameMode.GENERAL);
        game.makeMove(1, 1);
        char previousTurn = game.getTurn();
        game.makeMove(1, 1);
        assertEquals(SOSGame.Cell.S, game.getCell(1, 1), "Cell should remain unchanged after an invalid move");
        assertEquals(previousTurn, game.getTurn(), "The turn should remain the same after an invalid move");
    }
	
	@Test
	public void testPlayerChangeLetter() {
		setup();
		game.setBlueLetter(sORo.O);
		assertEquals(game.blue, sORo.O);
		
		game.setRedLetter(sORo.O);
		assertEquals(game.red, sORo.O);
	}
	
}
