package sos;

public class SOSgeneral extends SOSsolo{
	
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
				setLastScorer('B');
				
				if(getBSolo() != true || getRSolo() != true) {
					//If blue human scored, exit
					if(getTurn() != 'B' || getBSolo()) {

						//After a move, make computer move if necessary
						if(!isFull()) {
							if(getRSolo() == true && getTurn() == 'R') {
								makeCMove();
								setLastScorer('R');
							}
							else if(getBSolo() == true && getTurn() == 'B') {
								makeCMove();
								setLastScorer('B');
							}
						}
					}
				}
			}
			else if(getTurn() == 'R'){
				setBoardCell(row, col, (getRedLetter() == sORo.S)?  Cell.S: Cell.O);
				
				setTurn('B');
				updateScore('R');
				setLastScorer('R');
				
				if(getBSolo() != true || getRSolo() != true) {
					//If red human scored, exit
					if(getTurn() != 'R' || getRSolo()) {
						
						//computer move
						if(!isFull()) {
							if(getBSolo() == true && getTurn() == 'B'){
								makeCMove();
								setLastScorer('B');
							}
							else if(getRSolo() == true && getTurn() == 'R') {
								makeCMove();
								setLastScorer('R');
							}
						}
					}
				}
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
		setBSolo(false);
		setRSolo(false);
		
	}
	
	@Override
	public void resetGame() {
		reset();
		bScore = 0;
		rScore = 0;
		winner = 'N';
		if(getBSolo() == true && getRSolo() == false)
			makeRandMove();
	}
}