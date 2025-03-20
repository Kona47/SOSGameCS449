package sos;

public class SOSsimple extends SOSGame{

	public SOSsimple(){
		setBoard(SIZE);
		setGameMode(GameMode.SIMPLE);
		initGame();
	}
	//Make a move in a simple game
	@Override
	public void makeMove(int row, int col) {
		if(row >= 0 && row < SIZE && col >= 0 && col < SIZE && getBoardCell(row, col) == Cell.EMPTY) {
			if(getTurn() == 'B'){				
				setBoardCell(row, col, (getBlueLetter() == sORo.S)?  Cell.S: Cell.O);
				updateGameState();
				if(getGameState() == GameState.PLAYING)
					setTurn('R');
			}
			else {
				setBoardCell(row, col, (getRedLetter() == sORo.S)?  Cell.S: Cell.O);
				updateGameState();
				if(getGameState() == GameState.PLAYING)
					setTurn('B');
			}
		}
	}
	//Override update game state for a simple win
	@Override
	public void updateGameState() {
		if(checkForSOS() >= 1)
			setGameState((getTurn() == 'B')? GameState.BLUE_WON : GameState.RED_WON);
		else if(isFull()) 
			setGameState(GameState.DRAW);
	}
	
	@Override
	public void resetGame() {
		reset();
	}
}
