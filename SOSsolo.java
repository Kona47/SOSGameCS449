package sos;

import java.util.Random;


public class SOSsolo extends SOSGame{
	
	public SOSsolo() {
		setBoard(SIZE);
		initGame();
	}
	
	public void updateGameState() {};
	public void makeMove(int row, int col) {};
	public void resetGame() {};
	
	//Make a computer move on game g. Called within simple/general make moves.
	public void makeCMove() {
		if (!makeScoringMove()) {
			makeRandMove();
		}
	}
	
	public boolean makeScoringMove() {
		Cell s = Cell.S;
		Cell o = Cell.O;
		Cell empty = Cell.EMPTY;
		//Check horizontal/vertical SO or S S
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE-2; j++) {
				//Horizontal
				if(getBoardCell(i,j) == s && getBoardCell(i,j+1) == o && getBoardCell(i,j+2) == empty) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i, j+2);
					return true;
				}
				else if(getBoardCell(i,j) == empty && getBoardCell(i,j+1) == o && getBoardCell(i,j+2) == s) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i, j);
					return true;
				}
				else if(getBoardCell(i,j) == s && getBoardCell(i,j+1) == empty && getBoardCell(i,j+2) == s) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.O);
					else
						setRedLetter(sORo.O);
					makeMove(i, j+1);
					return true;
				}
				//Vertical
				else if(getBoardCell(j,i) == s && getBoardCell(j+1,i) == o && getBoardCell(j+2,i) == empty) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(j+2, i);
					return true;
				}
				else if(getBoardCell(j,i) == empty && getBoardCell(j+1,i) == o && getBoardCell(j+2,i) == s) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(j, i);
					return true;
				}
				else if(getBoardCell(j,i) == s && getBoardCell(j+1,i) == empty && getBoardCell(j+2,i) == s) {
					if(getTurn() == 'B')
						setBlueLetter(sORo.O);
					else
						setRedLetter(sORo.O);
					makeMove(j+1, i);
					return true;
				}
			}
		}
		//check Diagonal SO, OS, or S S
		for(int i = 0; i < SIZE-2; i++) {
			for(int j = 0; j < SIZE-2; j++) {		
				if(getBoardCell(i,j) == s && getBoardCell(i+1,j+1) == o  && getBoardCell(i+2,j+2) == empty){
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i+2, j+2);
					return true;
				}
				if(getBoardCell(i,j) == empty && getBoardCell(i+1,j+1) == o  && getBoardCell(i+2,j+2) == s){
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i, j);
					return true;
				}
				if(getBoardCell(i,j) == s && getBoardCell(i+1,j+1) == empty && getBoardCell(i+2,j+2) == s){
					if(getTurn() == 'B')
						setBlueLetter(sORo.O);
					else
						setRedLetter(sORo.O);
					makeMove(i+1, j+1);
					return true;
				}
				if(getBoardCell(i, SIZE-1-j) == s && getBoardCell(i+1, SIZE-2-j) == o  && getBoardCell(i+2, SIZE-3-j) == empty){
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i+2, SIZE-3-j);
					return true;
				}
				if(getBoardCell(i, SIZE-1-j) == empty && getBoardCell(i+1, SIZE-2-j) == o  && getBoardCell(i+2, SIZE-3-j) == s){
					if(getTurn() == 'B')
						setBlueLetter(sORo.S);
					else
						setRedLetter(sORo.S);
					makeMove(i, SIZE-1-j);
					return true;
				}
				if(getBoardCell(i, SIZE-1-j) == s && getBoardCell(i+1, SIZE-2-j) == empty && getBoardCell(i+2, SIZE-3-j) == s){
					if(getTurn() == 'B')
						setBlueLetter(sORo.O);
					else
						setRedLetter(sORo.O);
					makeMove(i+1, SIZE-2-j);
					return true;
				}
			}
		}
		return false;
	}
	
	public void makeRandMove() {
		Random random = new Random();
		int emptyCells = getNumEmptyCells();
		if(emptyCells == 0)
			return;
		//Need random cell to put move on
		int target = random.nextInt(emptyCells);
		//Need a random letter move
		int so = random.nextInt(2);
		//Set the correct player to the random letter
		if(so == 0) {
			if(getTurn() == 'R')
				setRedLetter(sORo.S);
			else
				setBlueLetter(sORo.S);
		}
		else {
			if(getTurn() == 'R')
				setRedLetter(sORo.O);
			else
				setBlueLetter(sORo.O);
		}
		int index = 0;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(getBoardCell(i,j) == Cell.EMPTY) {
					if(index == target) {
						makeMove(i, j);
						System.out.println("Move made on: " + i + "," + j);
						return;
					}
					else
						index++;
				}
			}
		}
	}
	
	public int getNumEmptyCells() {
		int emptyCells = 0;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(getBoardCell(i,j) == Cell.EMPTY) {
					emptyCells++;
				}
			}
		}
		return emptyCells;
	}
}
