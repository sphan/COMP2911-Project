
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
		// iterate through the grids/squares.
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				if (i <= 2) {
					if (j <= 2) {
						puzzle[i][j] = new Square(boxNum[0], INITIAL_VALUE);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[1], INITIAL_VALUE);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[2], INITIAL_VALUE);
					}
				} else if (i > 2 && i <= 5) {
					if (j <= 2) {
						puzzle[i][j] = new Square(boxNum[3], INITIAL_VALUE);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[4], INITIAL_VALUE);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[5], INITIAL_VALUE);
					}
				} else if (i > 5 && i <= 8) {
					if (j <= 2) {
						puzzle[i][j] = new Square(boxNum[6], INITIAL_VALUE);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[7], INITIAL_VALUE);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[8], INITIAL_VALUE);
					}
				}
			}
		}
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
	
	// An array of the index of the 3x3 boxes.
	// An array of the position index of the 3x3 boxes in the following format.
	//  0 1 2
	//  3 4 5
	//  6 7 8
	// These should be a constant, as it will not be modified.
	private static final int[] boxNum = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final int[] positionInBox = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	
	// levels of difficulty (not sure where to place them yet).
	// Should this be placed in the main function or should this be
	// placed in any classes that have a difficulty defined.
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int HARD = 2;
	private static final int ROW_NUMBER = 9;
	private static final int COLUMN_NUMBER = 9;
	private static final int INITIAL_VALUE = 0;
}
