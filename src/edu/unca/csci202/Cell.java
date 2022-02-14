package edu.unca.csci202;

/**
 * Objects to be used for every spot of the gameboard
 * @author Taylor Van Aken
 */
public class Cell {

	static char mine = 'M';
	static char digit = '0';
	boolean guess;
	char identity;
	
	/**
	 * Objects representing the spaces of the gameboard.
	 * @param identity either identified as a mine('M') or a digit representing
	 * the number of surrounding mines.
	 * @param guess true if the cell has been guessed by the user.
	 */
	public Cell(char identity, boolean guess) {
		
		this.identity = identity;
		this.guess = guess;
	}
}
