
/**
 * A class that generates the Sudoku puzzle
 * according the level specified.
 * @author tsph001 - Sandy Phan
 *
 */
public class Puzzle {
	
	/**
	 * Create a new Sudoku puzzle with the
	 * specified difficulty level.
	 * @param difficultyLevel The difficulty level
	 * partitioned into Easy, Medium and Hard.
	 */
	public Puzzle(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
		initialisePuzzle();
		generatePuzzle();
	}
	
	/**
	 * Generate the whole puzzle with appropriate
	 * cells removed according to the difficulty
	 * level specified.
	 */
	private void generatePuzzle() {
		removeCells();
	}
	
	/**
	 * Initialize the puzzle by setting
	 * the values of the rows, columns
	 * and 3x3 box a square should be in.
	 */
	private void initialisePuzzle() {
		
	}
	
	/**
	 * Remove the number of cells according
	 * to the difficulty level set from the
	 * constructor.
	 */
	private void removeCells() {
		
	}

	private Square puzzle[][] = new Square[ROW_NUMBER][COLUMN_NUMBER];
	private int difficultyLevel;
	
	// levels of difficulty (not sure where to place them yet).
	// Should this be placed in the main function or should this be
	// placed in any classes that have a difficulty defined.
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;
	private static final int ROW_NUMBER = 9;
	private static final int COLUMN_NUMBER = 9;
}
