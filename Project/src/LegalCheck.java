/**
 * This class provides methods that are used
 * in checking if certain value has duplicates
 * in certain regions such as rows, column or 
 * 3x3 box.
 * @author Sandy
 *
 */
public class LegalCheck {
	
	/**
	 * Check if a particular move in a particular square
	 * for the puzzle is valid/legal. That is, there is
	 * no duplicates in its row, column and region.
	 * @param p The puzzle or board.
	 * @param s The square that we want to place the value
	 * in.
	 * @param val The value needed for duplicate checks.
	 * @return True if there is a duplicate in any direction,
	 * and false otherwise.
	 */
	public static boolean isNotLegal(Square[][] p, Square s, int val) {
		puzzle = p;
		int row = s.getRow();
		int column = s.getColumn();
		boolean duplicated = false;

		if (val != 0) //if has a value
			duplicated = hasDuplicate(row, column, val, s);	//checks if it has a duplicate
		return duplicated;
	}
	
	/**
	 * Check duplicates in rows and columns and 3x3 box.
	 * @param row The row where the square belongs to.
	 * @param column The column where the square belongs to.
	 * @param val The value that is needed for duplicate checks.
	 * @return True if there is a duplicate in current row or column.
	 * False otherwise.
	 */
	private static boolean hasDuplicate(int row, int column, int val, Square currentSquare) {
		// checks duplicates in row.
		for (int i = 0; i < Puzzle.COLUMN_NUMBER; i++) {
			if (puzzle[row][i].getType() == Square.EMPTY_CELL)
				continue;
			if (puzzle[row][i].getCurrentValue() == val && i != column)
				return true;
		}
		
		// checks duplicates in column.
		for (int i = 0; i < Puzzle.ROW_NUMBER; i++) {
			if (puzzle[i][column].getType() == Square.EMPTY_CELL)
				continue;
			if (puzzle[i][column].getCurrentValue() == val && i != row)
				return true;
		}
		
		// checks duplicated in 3x3 region.
		for (int i = (row / 3) * 3; i < (row / 3) * 3 + 3; i++) {
			for (int j = (column / 3) * 3; j < (column / 3) * 3 + 3; j++) {
				//for all in box
				if (puzzle[i][j] != currentSquare && 
						puzzle[i][j].getType() != Square.EMPTY_CELL && 
						puzzle[i][j].getCurrentValue() == val)
					return true; //found if not same square, not empty and same value
			}
		}
		
		return false;
	}
	
	private static Square[][] puzzle;
}
