/**
 * Class to run the game and implement everything
 * @author Everyone
 *
 */
public class RunGame {
	
	/**
	 * Main method to start the game
	 * @param args
	 */
	public static void main(String args[]){
		Square[][] puzzle = Puzzle.createPuzzle(2);
		GameInterface board = new GameInterface(puzzle);
	}

}
