import java.util.LinkedList;
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
	 * @param difficultyLevel The difficulty level
	 * partitioned into Easy, Medium and Hard.
	 */
	public Puzzle(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
		initialisePuzzle();
		generatePuzzle();
		printPuzzle();
	}
	
	/**
	 * Generate the whole puzzle with appropriate
	 * cells removed according to the difficulty
	 * level specified.
	 */
	private void generatePuzzle() {
		Random rand = new Random();
		boolean conflict = false;
		int k = 0;
		
		// Loop through the board and assign numbers to the squares
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				
				k = rand.nextInt(puzzle[i][j].getAvailableValues().size());
				while (hasDuplicate(i, j, puzzle[i][j].getAvailableValues().get(k)) ||
					   hasDuplicateInBox(puzzle[i][j].getThreeByThreeBox(), puzzle[i][j].getAvailableValues().get(k))) {
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
					} else
						conflict = false;
					k = rand.nextInt(puzzle[i][j].getAvailableValues().size());					
				}
				
				if (!conflict) {
					puzzle[i][j].setCurrentValue(puzzle[i][j].getAvailableValues().get(k));
					puzzle[i][j].getUsedValues().add(puzzle[i][j].getAvailableValues().get(k));
					puzzle[i][j].getAvailableValues().remove(k);
					puzzle[i][j].setType(Square.PREDEFINE_CELL);
				}
				
			}
		}
		
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
						puzzle[i][j] = new Square(boxNum[0], Square.EMPTY_CELL);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[1], Square.EMPTY_CELL);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[2], Square.EMPTY_CELL);
					}
				} else if (i > 2 && i <= 5) {
					if (j <= 2) {
						puzzle[i][j] = new Square(boxNum[3], Square.EMPTY_CELL);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[4], Square.EMPTY_CELL);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[5], Square.EMPTY_CELL);
					}
				} else if (i > 5 && i <= 8) {
					if (j <= 2) {
						puzzle[i][j] = new Square(boxNum[6], Square.EMPTY_CELL);
					} else if (j > 2 && j <= 5) {
						puzzle[i][j] = new Square(boxNum[7], Square.EMPTY_CELL);
					} else if (j > 5 && j <= 8) {
						puzzle[i][j] = new Square(boxNum[8], Square.EMPTY_CELL);
					}
				}
			}
		}
	}
	
	public void printPuzzle() {
		for (int i = 0; i < ROW_NUMBER; i++) {
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				System.out.print(puzzle[i][j].getCurrentValue() + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Check duplicates in rows and columns.
	 * @param row
	 * @param column
	 * @param val
	 * @return True if there is a duplicate in current row or column.
	 */
	public boolean hasDuplicate(int row, int column, int val) {
		for (int i = 0; i < COLUMN_NUMBER; i++) {
			if (puzzle[row][i].getType() == 0)
				continue;
			if (puzzle[row][i].getCurrentValue() == val)
				return true;
		}
		
		for (int i = 0; i < ROW_NUMBER; i++) {
			if (puzzle[i][column].getType() == 0)
				continue;
			if (puzzle[i][column].getCurrentValue() == val)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Check if there are duplicates in the 3x3 box where
	 * the square belongs.
	 * @param boxNum
	 * @param val
	 * @return
	 */
	public boolean hasDuplicateInBox(int boxNum, int val) {
		LinkedList<Square> list = getSquaresInThreeByThree(boxNum);
		for (Square s : list) {
			if (s.getType() == Square.EMPTY_CELL)
				continue;
			if (s.getCurrentValue() == val)
				return true;
		}
		return false;
	}
	
	/**
	 * Remove the number of cells according
	 * to the difficulty level set from the
	 * constructor.
	 */
	private void removeCells() {
		
	}
	
	/**
	 * Get the list of squares that belong to the
	 * 3x3 box number given in the parameter.
	 * @param threeByThreeIndex The 3x3 box number.
	 * @return The list of squares that are in the given 
	 * box number.
	 */
	public LinkedList<Square> getSquaresInThreeByThree(int threeByThreeIndex) {
		LinkedList<Square> threeByList = new LinkedList<Square>();
		int iStart = 0, iEnd = 0;
		int jStart = 0, jEnd = 0;
		if (threeByThreeIndex == 0 ||
				threeByThreeIndex == 1 ||
				threeByThreeIndex == 2) {
			iStart = 0;
			iEnd = 3;
			jStart = 0;
			jEnd = 3;
			if (threeByThreeIndex == 1) {
				jStart = 3;
				jEnd = 6;
			} else if (threeByThreeIndex == 2) {
				jStart = 6;
				jEnd = 6;
			}
		} else if (threeByThreeIndex == 3 ||
				threeByThreeIndex == 4 ||
				threeByThreeIndex == 5) {
			iStart = 3;
			iEnd = 6;
			jStart = 0;
			jEnd = 3;
			if (threeByThreeIndex == 4) {
				jStart = 3;
				jEnd = 6;
			} else if (threeByThreeIndex == 5) {
				jStart = 6;
				jEnd = 9;
			}
		} else if (threeByThreeIndex == 6 ||
				threeByThreeIndex == 7 ||
				threeByThreeIndex == 8) {
			iStart = 6;
			iEnd = 9;
			jStart = 0;
			jEnd = 3;
			if (threeByThreeIndex == 7) {
				jStart = 3;
				jEnd = 6;
			} else if (threeByThreeIndex == 8) {
				jStart = 6;
				jEnd = 9;
			}
		}
		
		for (int i = iStart; i < iEnd; i++) {
			for (int j = jStart; j < jEnd; j++) {
				threeByList.add(puzzle[i][j]);
			}
		}
		return threeByList;
	}

	
	private int difficultyLevel;
	
	// An array of the index of the 3x3 boxes.
	// An array of the position index of the 3x3 boxes in the following format.
	//  0 1 2
	//  3 4 5
	//  6 7 8
	// These should be a constant, as it will not be modified.
	private static final int[] boxNum = {0, 1, 2, 3, 4, 5, 6, 7, 8};
//	private static final int[] positionInBox = {0, 1, 2, 3, 4, 5, 6, 7, 8};
//	private static final int[] availableNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	// levels of difficulty (not sure where to place them yet).
	// Should this be placed in the main function or should this be
	// placed in any classes that have a difficulty defined.
//	public static final int EASY = 0;
//	public static final int MEDIUM = 1;
//	public static final int HARD = 2;
	private static final int ROW_NUMBER = 9;
	private static final int COLUMN_NUMBER = 9;
//	private static final int INITIAL_VALUE = 0;
	
	private static Square puzzle[][] = new Square[ROW_NUMBER][COLUMN_NUMBER];
}
