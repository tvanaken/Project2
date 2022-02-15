package edu.unca.csci202;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * Allows for the user to play Minesweeper on an 8 by 8 2D array board
 * made up of Cell objects.
 * 
 * @author Taylor Van Aken
 */
public class Gameboard {

	Random rand = new Random();
	Scanner scan = new Scanner(System.in);
	
	int rows = 8;
	int columns = 8;
	Cell[][] board = new Cell[rows][columns];
	private Stack<Integer> rowStack;
	private Stack<Integer> columnStack;
	int mineCount;
	int adjacentMines;
	int minesFound = 0;
	boolean allRight = true;
	boolean invalidResponse = true;
	
	/**
	 * Calls for a new board to be generated and runs through the the necessary
	 * methods to carry out a game of Minesweeper. Ends if the user's guess is
	 * incorrect, or all mines are found and the user does not choose to play again.
	 * @throws InvalidInputException if incorrect input is given by user.
	 */
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
	
	/**
	 * Runs through the generated board one cell at a time, checking the 8 surrounding
	 * cells to see if they're mines. The cells identity will change value depending on
	 * the number of mines surrounding the cell.
	 */
	public void checkAdjacent() {
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
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
	
	/**
	 * Before the rest of the cells are generated, the 10 mines are planted on the board
	 * in order to correctly give identities to the remaining cells.
	 */
	public void plantMine() {
		
		while (mineCount < 10) {
		
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					
					if (board[i][j] == null && mineCount < 10) {
						
						int chance = rand.nextInt(50);
						if (chance < 5) {
								
							board[i][j] = new Cell(Cell.mine, false);
							mineCount++;
					
						} else {
						
							continue;
						}
					}
				}
			}
		}
	}
	
	/**
	 * After 10 mines are planted, the rest of the cells are initialized as 0
	 * before calling checkAdjacent() which would increment the cells value accordingly.
	 */
	public void generateBoard() {
				
		plantMine();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				if (board[i][j] == null) {
				
					board[i][j] = new Cell(Cell.digit, false);
				
				}
			}
		}
		
		checkAdjacent();
	}
	
	/**
	 * Prints the current state of the board according to what the user has guessed
	 * so far.
	 */
	public void printBoard() {
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
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
	
	/**
	 * Asks the user if they would like the peek at the board.
	 * If yes, board prints with the mines showing.
	 */
	public void peekBoard() {
		
		System.out.print("Would you like to peek? (y/n): ");
		String response = scan.nextLine();
		
		while (!response.equalsIgnoreCase("n") && !response.equalsIgnoreCase("y") || response.isEmpty()) {
			
			System.out.println("Invalid input, please try again.");
			System.out.print("Would you like to peek? (y/n): ");
			response = scan.nextLine();
		}
		
		if (response.equalsIgnoreCase("y")) {
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					
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
	
	/**
	 * Asks user for a row input and column input, then whether they think there is
	 * a mine in that cell or not.
	 * @throws InvalidInputException if incorrect input is given by user.
	 */
	public void userGuess() throws InvalidInputException{
		
		int rowGuess = 0;
		int columnGuess = 0;
		rowStack = new Stack<Integer>();
		columnStack = new Stack<Integer>();
		
		while (true) {
			
			System.out.print("Please enter a row number: ");
			
			try {
				
				rowGuess = Integer.parseInt(scan.nextLine()) - 1;
				
				if (rowGuess < 0 || rowGuess > (rows - 1)) {
					
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
				
				if (columnGuess < 0 || columnGuess > (columns - 1)) {
					
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
			
			minesFound ++;
		} 
		else if (response.equals("n") && board[rowGuess][columnGuess].identity == '0') {
			
			rowStack.push(rowGuess);
			columnStack.push(columnGuess);
			
			while (rowStack.size() > 0) {
				
				expand(rowStack.pop(), columnStack.pop());
			}
			
		}  
		else if ((response.equals("y") && board[rowGuess][columnGuess].identity != Cell.mine) || (response.equals("n") && board[rowGuess][columnGuess].identity == Cell.mine)) {
		
			System.out.println("Boom! You lose.");
			allRight = false;
		}
	}
	
	/**
	 * Asks user if they would like to play another game of Minesweeper.
	 * @throws InvalidInputException if incorrect input is given by user.
	 */
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
	
	/**
	 * Prints the introduction to Minesweeper, asking the user if
	 * they would like to play. Exits program if 'n'.
	 */
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
	
	/**
	 * Resets variables and objects in order to allow a new game and board
	 * to be played.
	 * @throws InvalidInputException if incorrect input is given by user.
	 */
	public void reset() throws InvalidInputException {
		
		mineCount = 0;
		adjacentMines = 0;
		minesFound = 0;
		allRight = true;
		invalidResponse = true;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				
				board[i][j] = null;
			}
		}
		
		run();
	}
	
	/**
	 * Expands the board if a '0' identity cell is identified. Will expand as
	 * long as zeroes are found, marking the cells as guessed.
	 * @param row int popped from the rowStack
	 * @param column int popped from the columnStack
	 */
	public void expand(int row, int column) {
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				
				try {
					
					if (board[row - i][column - j].identity == '0' && board[row - i][column - j].guess == false) {
						
						rowStack.push(row - i);
						columnStack.push(column - j);
						board[row - i][column - j].guess = true;
					} else if (board[row - i][column - j].identity > '0') {
						
						board[row - i][column - j].guess = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					
				}
			}
		}
	}
}
