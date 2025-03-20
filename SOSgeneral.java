package sos;

public class SOSgeneral extends SOSGame{
	
	private int bScore;
	private int rScore;
	
	public int getBScore() {return this.bScore;}
	public int getRScore() {return this.rScore;}
	
	public void setBScore(int increment) {this.bScore += increment;}
	public void setRScore(int increment) {this.rScore += increment;}
	
	public char nextTurn;
	
	//Checks win for general game
	public char winner;
	public char checkWin(){
		if(getBScore() > getRScore())
			winner = 'B';
		else if(getBScore() < getRScore()) {
			winner = 'R';
		}
		return winner;
	}
	
	//Make a general game move
	@Override
	public void makeMove(int row, int col) {
		if(row >= 0 && row < SIZE && col >= 0 && col < SIZE && getBoardCell(row, col) == Cell.EMPTY) {
			if(getTurn() == 'B'){
				setBoardCell(row, col, (getBlueLetter() == sORo.S)?  Cell.S: Cell.O);
				setTurn('R');
				updateScore('B');
			}
			else {
				setBoardCell(row, col, (getRedLetter() == sORo.S)?  Cell.S: Cell.O);
				setTurn('B');
				updateScore('R');
			}
		}
		updateGameState();
	}
	
	//Override updateGameState from SOSGame
	@Override
	public void updateGameState() {
		//if game over
		if(isFull()) {
			//check if blue won
			if(checkWin() == 'B')
				setGameState(GameState.BLUE_WON);
			//Check if red won
			else if(checkWin() == 'R')
				setGameState(GameState.RED_WON);
			//else draw
			else {
				setGameState(GameState.DRAW);
			}
		}
	}
	//Updates player scores if an SOS was made
	public void updateScore(char t) {	
		int point = checkForSOS();
		if(point >= 1) {
			if(t == 'B') {
				System.out.println("Blue Scored " + point);
				setBScore(point);
				setTurn('B');  
			}
			else if (t == 'R') {
				System.out.println("Red Scored " + point);
				setRScore(point);
				setTurn('R');
			}
		}	
	}
	
	
	public SOSgeneral() {
		setBoard(SIZE);
		initGame();
		setGameMode(GameMode.GENERAL);
		bScore = 0;
		rScore = 0;
		winner = 'N';
	}
	
	@Override
	public void resetGame() {
		reset();
		bScore = 0;
		rScore = 0;
		winner = 'N';
	}
}
