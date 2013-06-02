import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * A class that generates the Sudoku puzzle
 * according the level specified.
 * The board generation code was greatly insight from
 * http://www.codeproject.com/Articles/23206/Sudoku-Algorithm-Generates-a-Valid-Sudoku-in-0-018
 * @author tsph001 - Sandy Phan
 *
 */
public class Puzzle {
	
	/**
	 * Create a new Sudoku puzzle with the
	 * specified difficulty level.
	 * @param dl The difficulty level
	 * partitioned into Easy, Medium and Hard.
	 * @return Returns the 2D array of the puzzle.
	 */
	public static Square[][] createPuzzle(int dl) {
		difficultyLevel = dl;
		initialisePuzzle();
		generatePuzzle();
		printPuzzle();
		Calendar start = Calendar.getInstance();
		calculateTimeElapse(start);
		return puzzle;
	}
	
	/**
	 * Generate the whole puzzle with appropriate
	 * cells removed according to the difficulty
	 * level specified.
	 */
	private static void generatePuzzle() {
		Random rand = new Random();
		boolean conflict = false;
		int k = 0;
		int duplicateFound = 0;
		
		// Loop through the board and assign numbers to the squares
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				
				duplicateFound = 0;
				
				// This is when backtracking a square, and that square has no
				// more possible answers.
				if (puzzle[i][j].getAvailableValues().size() == 0) {
					puzzle[i][j].resetTrackingValues();
					puzzle[i][j].setCurrentValue(0);
					if (j > 1)
						j = j - 2;
					else {
						j = COLUMN_NUMBER - 2;
						i--;
					}
					continue;
				}
				
				try { //checks if there is a conflict
					k = rand.nextInt(puzzle[i][j].getAvailableValues().size());
					while (LegalCheck.isNotLegal(puzzle, puzzle[i][j], puzzle[i][j].getAvailableValues().get(k))) {
						duplicateFound++;
						puzzle[i][j].getUsedValues().add(puzzle[i][j].getAvailableValues().get(k));
						puzzle[i][j].getAvailableValues().remove(k);
						if (puzzle[i][j].getAvailableValues().size() == 0) {
							puzzle[i][j].resetTrackingValues();
							puzzle[i][j].setCurrentValue(0);
							if (j > 1)
								j = j - 2;
							else {
								j = COLUMN_NUMBER - 2;
								i--;
							}
							conflict = true;
							break;
						} else {
							conflict = false;
						}
							
						k = rand.nextInt(puzzle[i][j].getAvailableValues().size());					
					} //if no duplicate, no conflict
					if (duplicateFound == 0) {
						conflict = false;
					}
						
				} catch (IllegalArgumentException e) {
					System.out.println("Size was: "+ puzzle[i][j].getAvailableValues().size());
				}
				
				//if no conflict, set squares with values
				if (!conflict) {
					puzzle[i][j].setCurrentValue(puzzle[i][j].getAvailableValues().get(k));
					puzzle[i][j].getUsedValues().add(puzzle[i][j].getAvailableValues().get(k));
					puzzle[i][j].getAvailableValues().remove(k);
					puzzle[i][j].setType(Square.PREDEFINE_CELL);
//					System.out.println("Cell[" + i + "][" + j + "]: " + puzzle[i][j].getCurrentValue());
				}
				
			}
//			System.out.println();
		}
		printPuzzle();
		removeCells();
	}
	
	/**
	 * Initialize the puzzle by setting
	 * the values of the rows, columns
	 * and 3x3 box a square should be in.
	 */
	private static void initialisePuzzle() {
		// iterate through the grids/squares and initializes them
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				puzzle[i][j] = new Square(Square.EMPTY_CELL);
				puzzle[i][j].setColumn(j);
				puzzle[i][j].setRow(i);
			}
		}
	}
	
	/**
	 * Function to print out the sudoku
	 */
	public static void printPuzzle() {
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				if (puzzle[i][j].getCurrentValue() == 0) {
					System.out.print("  ");
				} else
					System.out.print(puzzle[i][j].getCurrentValue() + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Remove the number of cells according
	 * to the difficulty level set from the
	 * constructor.
	 */
	private static void removeCells() {
		Random rand = new Random();
		int prevVal = 0;
		int removeNum = 0;
		int min = 0, max = 0; //sets range to remove
		if (difficultyLevel == EASY) {
			max = 37;
			min = 32;
		} else if (difficultyLevel == MEDIUM) {
			max = 43;
			min = 38;
			
		} else if (difficultyLevel == HARD) {
			max = 49;
			min = 44; 
		}
		//finds specific amount to remove
		removeNum = min + (int)(Math.random() * ((max - min) + 1));
		//removes those numbers
		for (int i = 0; i < removeNum; i++) {
			int j = rand.nextInt(ROW_NUMBER);
			int k = rand.nextInt(COLUMN_NUMBER);
			if (puzzle[j][k].getCurrentValue() == 0 && 
					puzzle[j][k].getType() == Square.EMPTY_CELL) {
				i--;
				continue;
			}
			prevVal = puzzle[j][k].getCurrentValue();
			puzzle[j][k].setCurrentValue(INITIAL_VALUE);
			puzzle[j][k].setType(Square.EMPTY_CELL);
			updatePossibleValues();
			HintSystem h = new HintSystem();
			if (h.checkSolutions(puzzle) > 1) {
				puzzle[j][k].setCurrentValue(prevVal);
				puzzle[j][k].setType(Square.PREDEFINE_CELL);
				updatePossibleValues();
				i--;
			}
		}
	}
	
	/**
	 * Function to update the board based on the type of a square
	 */
	private static void updatePossibleValues() {
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				if (puzzle[i][j].getType() == Square.EMPTY_CELL) {//if empty cell, clear it
					puzzle[i][j].getAvailableValues().clear();
					for (int k = 1; k <= 9; k++) {
						if (LegalCheck.isNotLegal(puzzle, puzzle[i][j], k) == false)
							puzzle[i][j].getAvailableValues().add(k);
					}
					// print possible values for debugging
					System.out.print("Possible values for cell[" + i + "][" + j + "]: ");
					for (Integer k : puzzle[i][j].getAvailableValues()) {
						System.out.print(k + " ");
					}
					System.out.println();
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * This method calculates the amount of time elapsed since
	 * the start of the game. 
	 * @param startTime The time when the game was started.
	 * @return The number of seconds elapsed.
	 */
	public static long calculateTimeElapse(Calendar startTime) {
		Calendar endTime = Calendar.getInstance();
		Date st = startTime.getTime();
		Date et = endTime.getTime();
		long ls = st.getTime();
		long le = et.getTime();
		long diff = (le - ls) / 1000;
		return diff;
	}
	
	// levels of difficulty (not sure where to place them yet).
	// Should this be placed in the main function or should this be
	// placed in any classes that have a difficulty defined.
	public static final int EASY = 0;
	public static final int MEDIUM = 1;
	public static final int HARD = 2;
	public static final int ROW_NUMBER = 9;
	public static final int COLUMN_NUMBER = 9;
	private static final int INITIAL_VALUE = 0;
	private static int difficultyLevel;
	
	// An array of the index of the 3x3 boxes.
	// An array of the position index of the 3x3 boxes in the following format.
	//  0 1 2
	//  3 4 5
	//  6 7 8
	// These should be a constant, as it will not be modified.
	private static Square puzzle[][] = new Square[ROW_NUMBER][COLUMN_NUMBER];
}
