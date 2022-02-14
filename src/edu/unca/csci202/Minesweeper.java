package edu.unca.csci202;

/**
 * Main class to run the Minesweeper game.
 * @author Taylor Van Aken
 */
public class Minesweeper {

	/**
	 * Creates a new instance of Gameboard before asking the user if
	 * they would like to play. Runs the game if input is 'y'.
	 * @param args
	 * @throws InvalidInputException if incorrect input is given by user.
	 */
	public static void main(String[] args) throws InvalidInputException {
		
		Gameboard game = new Gameboard();
		
		game.intro();
		
		game.run();
	}

}
