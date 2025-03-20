package sos;

import java.util.HashSet;
import java.util.Set;

public abstract class SOSGame {
	
	protected int SIZE = 3;
	
	public void setSize(int i) {
		this.SIZE = i;
		this.board = new Cell[SIZE][SIZE];
		this.fillBoard();
	}
	
	public int getSize() {return SIZE;}
	
	public enum GameMode{
		SIMPLE, GENERAL
	}
	
	private GameMode currentGameMode;
	
	public GameMode getGameMode() {return currentGameMode;}
	public void setGameMode(GameMode gm) {this.currentGameMode = gm;}
	
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
	private sORo blue;
	private sORo red;
	
	public void setBlueLetter(sORo value) {this.blue = value;}
	public void setRedLetter(sORo value) {this.red = value;}
	
	public sORo getBlueLetter() {return this.blue;}
	public sORo getRedLetter() {return this.red;}
	
	private GameState currentGameState;
	
	public GameState getGameState() {return currentGameState;}
	public void setGameState(GameState gs) {this.currentGameState = gs;}
	
	//board of cells, with getters and setters for whole board and 1 cell
	private Cell[][] board;
	
	public Cell[][] getBoard(){return board;}
	public Cell getBoardCell(int row, int col) {return board[row][col];}
	public void setBoard(int size) {board = new Cell[size][size];}
	public void setBoardCell(int row, int col, Cell c) {board[row][col] = c;}
	
	private char turn;
	
	public char getTurn() {return turn;}
	public void setTurn(char t) {this.turn = t;}
	
	//Abstract for updating game state after a move is made.
	public abstract void updateGameState();
	//Abstract for making a move in a game
	public abstract void makeMove(int row, int col);
	//Keep track of sos that have been scored
	protected Set<String> foundSOS = new HashSet<>();
	//For checking for a new S-O-S sequence.
	public int checkForSOS(){
		Cell s = Cell.S;
		Cell o = Cell.O;
		//keep track if multiple sos made in same move
		int countSOS = 0;
		
		//Check rows and columns
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE-2; j++) {
				//check for S-O-S
				if(board[i][j] == s && board[i][j+1] == o && board[i][j+2] == s) {
					String key = i + "," + j + " " + i + ',' + (j+2);
					if (!foundSOS.contains(key)) {
						foundSOS.add(key);
						countSOS++;
						
					}
				}// j = 0, i = 1 is line down the middle
				else if(board[j][i] == s && board[j+1][i] == o && board[j+2][i] == s) {
					String key = j + "," + i + " " + (j+2) + ',' + i;
					if (!foundSOS.contains(key)) {
						foundSOS.add(key);
						countSOS++;
					}
				}
			}
		}
		//Check Diagonals
		for(int x = 0; x < SIZE-2; x++) {
			for(int y = 0; y < SIZE-2; y++) {
				//check for S-O-S
				if(board[x][y] == s && board[x+1][y+1] == o && board[x+2][y+2] == s) {
					String key = x + "," + y + " " + (x+2) + ',' + (y+2);
					if (!foundSOS.contains(key)) {
						foundSOS.add(key);
						countSOS++;
					}
				}
				if(board[SIZE-1-x][y] == s && board[SIZE-2-x][y+1] == o && board[SIZE-3-x][y+2] == s) {
					String key = (SIZE-1-x) + "," + y + " " + (SIZE-3-x) + ',' + (y+2);
					if (!foundSOS.contains(key)) {
						foundSOS.add(key);
						countSOS++;
					}
				}
			}
		}
		//Otherwise no new SOS, so continue
		return countSOS;
	}
	
	//Check for if board is full. Also would mean a draw for simple game
	public boolean isFull() {
		for (int row = 0; row < SIZE; ++row) {
			for (int col = 0; col < SIZE; ++col) {
				if (board[row][col] == Cell.EMPTY) {
					return false; //An empty cell found, so moves still available
				}
			}
		}
		return true;
	}

	// reset game abstract function
	public void reset() {
		initGame();
		foundSOS.clear();
	}
	public abstract void resetGame();
	
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
	//Things to start a new game
	public void initGame() {
		this.fillBoard();
		this.setBlueLetter(sORo.S);
		this.setRedLetter(sORo.S);
		this.setTurn('B');
		this.setGameState(GameState.PLAYING);
	}
	
	public SOSGame() {
		board = new Cell[SIZE][SIZE];
		initGame();
	}
	
}