package sos;

public class SOSsimple extends SOSsolo{

	public SOSsimple(){
		setBoard(SIZE);
		setGameMode(GameMode.SIMPLE);
		initGame();
		setBSolo(false);
		setRSolo(false);
	}
	//Make a move in a simple game
	@Override
	public void makeMove(int row, int col) {
		if(row >= 0 && row < SIZE && col >= 0 && col < SIZE && getBoardCell(row, col) == Cell.EMPTY) {
			
			if(getTurn() == 'B') {
				setBoardCell(row, col, (getBlueLetter() == sORo.S)?  Cell.S: Cell.O);
				updateGameState();
				
				if(getGameState() == GameState.PLAYING) {
					setTurn('R');
					//Make computer move if necessary
					if(getRSolo() == true && getBSolo() == false)
						makeCMove();
				}
			}
			else if(getTurn() == 'R'){
				setBoardCell(row, col, (getRedLetter() == sORo.S)?  Cell.S: Cell.O);
				updateGameState();
				
				if(getGameState() == GameState.PLAYING) {
					setTurn('B');
					//Make computer move if necessary
					if(getBSolo() == true && getRSolo() == false)
						makeCMove();
					
				}
			}
			setLastScorer(getTurn());
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
		if(getBSolo() == true && getRSolo() == false)
			makeRandMove();
	}
}
