package edu.unca.csci202;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Gameboard {

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	
	Cell[][] board = new Cell[8][8];
	private Stack<Cell> cellStack;
	int mineCount;
	int adjacentMines;
	int minesFound = 0;
	boolean allRight = true;
	boolean invalidResponse = true;
	
	public void run() throws InvalidInputException{
		
		generateBoard();
		
		while (allRight) {
			
			if (minesFound == mineCount) {
				
				System.out.println("You win!");
				break;
			}
			printBoard();
			peekBoard();
			userGuess();
		
		}
		playAgain();
	}
	
	public void checkAdjacent() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (board[i][j].identity != Cell.mine) {
					
					adjacentMines = 0;
					
					for (int k = -1; k < 2; k++) {
						for (int l = -1; l < 2; l++) {
							
							try {
								if (board[i + k][j + l].identity == Cell.mine) {
									
									adjacentMines++;
									board[i][j].identity = (char)(adjacentMines + '0');
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								
							}
						}
					}
				}
			}
		}
	}
	
	public void plantMine() {
		
		while (mineCount < 10) {
		
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					
					if (board[i][j] == null && mineCount < 10) {
						
						int chance = rand.nextInt(50);
						if (chance < 5) {
								
							board[i][j] = new Cell(Cell.mine, false, false);
							mineCount++;
					
						} else {
						
							continue;
						}
					}
				}
			}
		}
	}
	
	public void generateBoard() {
				
		plantMine();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (board[i][j] == null) {
				
					board[i][j] = new Cell(Cell.digit, false, false);
				
				}
			}
		}
		
		checkAdjacent();
	}
	
	public void printBoard() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (board[i][j].guess == false) {
					
					System.out.print('-' + " ");
				}
				else {
					
					System.out.print(board[i][j].identity + " ");
				}
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public void peekBoard() {
		
		System.out.print("Would you like to peek? (y/n): ");
		String response = scan.nextLine();
		
		while (!response.equalsIgnoreCase("n") && !response.equalsIgnoreCase("y") || response.isEmpty()) {
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to peek? (y/n): ");
			response = scan.nextLine();
		}
		
		if (response.equalsIgnoreCase("y")) {
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					
					if (board[i][j].identity != Cell.mine && board[i][j].guess == false) {
						
						System.out.print("- ");
					} else {
						
						System.out.print(board[i][j].identity + " ");
					}
				}
				
				System.out.println();
			}
			
			System.out.println();
		}
	}
	
	public void userGuess() throws InvalidInputException{
		
		int rowGuess = 0;
		int columnGuess = 0;
		cellStack = new Stack<Cell>();
		
		while (true) {
			
			System.out.print("Please enter a row number: ");
			
			try {
				
				rowGuess = Integer.parseInt(scan.nextLine()) - 1;
				
				if (rowGuess < 0 || rowGuess > 7) {
					
					throw new InvalidInputException();
				} else {
					
					break;
				}
			} catch (Exception e) {
				
				System.out.println("Invalid input, please try again.");
			}
		}
		
		while (true) {
			
			System.out.print("Please enter a column number: ");
			
			try {
				
				columnGuess = Integer.parseInt(scan.nextLine()) - 1;
				
				if (columnGuess < 0 || columnGuess > 7) {
					
					throw new InvalidInputException();
				} else {
					
					break;
				}
			} catch (Exception e) {
				
				System.out.println("Invalid input, please try again.");
			}
		}
			
		System.out.print("Does row " + (rowGuess + 1) + " and column " + (columnGuess + 1) + " contain a mine? (y/n): ");
		String response = scan.nextLine();
		board[rowGuess][columnGuess].guess = true;
		
		
		while (!response.equalsIgnoreCase("n") && !response.equalsIgnoreCase("y") || response.isEmpty()) {
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Does row " + (rowGuess + 1) + " and column " + (columnGuess + 1) + " contain a mine? (y/n): ");
			response = scan.nextLine();
		}
		
		if (response.equals("y") && board[rowGuess][columnGuess].identity == Cell.mine) {
			
			board[rowGuess][columnGuess].correct = true;
			minesFound ++;
			
		} 
		else if (response.equals("n") && board[rowGuess][columnGuess].identity == '0') {
			
			board[rowGuess][columnGuess].correct = true;
			expand(rowGuess, columnGuess);
		} 
		else if (response.equals("n") && board[rowGuess][columnGuess].identity != Cell.mine) {
			
			board[rowGuess][columnGuess].correct = true;
		} 
		else if ((response.equals("y") && board[rowGuess][columnGuess].identity != Cell.mine) || (response.equals("n") && board[rowGuess][columnGuess].identity == Cell.mine)) {
		
			System.out.println("Boom! You lose.");
			allRight = false;
		}
	}
		
	public void playAgain() throws InvalidInputException{
		
		System.out.println("Thank you for playing Minesweeper.");
		System.out.print("Would you like to play again? (y/n): ");
		String response = scan.nextLine();
		
		while (!response.equalsIgnoreCase("n") && !response.equalsIgnoreCase("y") || response.isEmpty()) {
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to play again? (y/n): ");
			response = scan.nextLine();
		}
		
		if (response.equalsIgnoreCase("n")) {
			
			System.out.println("Goodbye!");
			System.exit(0);
		} else {
			
			reset();
		}
	}
	
	public void intro() {
		
		System.out.println("Welcome to Minesweeper!");
		System.out.print("Would you like to play a game? (y/n): ");
		String response = scan.nextLine();
		
		while (!response.equalsIgnoreCase("n") && !response.equalsIgnoreCase("y") || response.isEmpty()) {
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to play a game? (y/n): ");
			response = scan.nextLine();
		}
		
		if (response.equalsIgnoreCase("n")) {
			
			System.out.println("Goodbye!");
			System.exit(0);
		}
	}
	
	public void reset() throws InvalidInputException {
		
		mineCount = 0;
		adjacentMines = 0;
		minesFound = 0;
		allRight = true;
		invalidResponse = true;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				board[i][j] = null;
			}
		}
		
		run();
	}
	
	public void expand(int rowGuess, int columnGuess) {
		
		try {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
										
					if (board[rowGuess - i][columnGuess - j].identity == '0' && board[rowGuess - i][columnGuess - j].guess == false) {
						
						cellStack.push(board[rowGuess - i][columnGuess - j]);
						board[rowGuess - i][columnGuess - j].guess = true;
					} else if (board[rowGuess - i][columnGuess - j].identity >= '0') {
						
						board[rowGuess - i][columnGuess - j].guess = true;
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
	}
}
