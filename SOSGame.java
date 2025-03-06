package sos;

public class SOSGame {
	protected int SIZE = 3;
	
	public void setSize(int i) {
		this.SIZE = i;
		this.board = new Cell[SIZE][SIZE];
		this.fillBoard();
	}
	
	protected gameMode currentGameMode;
	
	public enum gameMode{
		SIMPLE, GENERAL
	}
	
	public void setGameMode(gameMode g) {this.currentGameMode = g;}
	
	public enum Cell{
		EMPTY, S, O;
	}
	
	public enum GameState{
		PLAYING, DRAW, BLUE_WON, RED_WON
	}
	//For knowing what type is selected by player
	public enum sORo{
		S, O
	}
	protected sORo blue;
	protected sORo red;
	
	public void setBlueLetter(sORo value) {this.blue = value;}
	public void setRedLetter(sORo value) {this.red = value;}
	
	protected GameState currentGameState;
	
	protected Cell[][] board;
	
	protected char turn;
	
	public int getSize() {
		return SIZE;
	}
	
	public char getTurn() {
		return turn;
	}
	
	public GameState getGameState() {
		return currentGameState;
	}
	
	public gameMode getGameMode() {
		return currentGameMode;
	}
	
	public void fillBoard(){
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				board[i][j] = Cell.EMPTY;
			}
		}
	}
	
	public Cell getCell(int row, int column) {
		if (row >= 0 && row < SIZE && column >= 0 && column < SIZE) {
			return board[row][column];
		} 
		else {
			return null;
		}
	}
	
	public SOSGame() {
		board = new Cell[SIZE][SIZE];
		fillBoard();
		blue = sORo.S;
		red = sORo.S;
		currentGameMode = gameMode.SIMPLE;
		turn = 'B';
		currentGameState = GameState.PLAYING;
	}
	
	public void makeMove(int row, int column) {
		if(row >= 0 && row < SIZE && column >= 0 && column < SIZE && board[row][column] == Cell.EMPTY) {
			if(turn == 'B'){
				board[row][column] = (blue == sORo.S)?  Cell.S: Cell.O;
				turn = 'R';
			}
			else {
				board[row][column] = (red == sORo.S)?  Cell.S: Cell.O;
				turn = 'B';
			}
		}
	}
}