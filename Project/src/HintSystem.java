import java.util.ArrayList;

/**
 * A class to return a hint given a board state
 * @author Richard Li
 * Using a backup backtracking DFS from
 * from www.colloquial.com/games/sudoku/java_sudoku/html
 * which was written by Bob Carpenter 
 * not used in all instances of game
 * specific to this current board generation
 * only used sometimes when less than 17 numbers are given
 */
public class HintSystem {
	private String[][] toSolve;
	private Move move;
	
	/**
	 * Constructs a HintSystem
	 * initializes toSolve
	 */
	public HintSystem() {
		toSolve = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				toSolve[i][j] = "123456789";
			}
		}
		move = new Move(-1, -1, 0);
	}
	
	/**
	 * Creates a hint based on the current board state
	 * @param Sudoku the board
	 * @return a move with hint if found, else null
	 */
	public Move Hint (Square[][] Sudoku) {
		//prints original sudoku
		print(Sudoku);
		//a boolean to check if board is filled
		Boolean check = false;
		//sets up grid to eliminate possibilities
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Sudoku[i][j].getCurrentValue() != 0) {
					toSolve[i][j] = Integer.toString(Sudoku[i][j].getCurrentValue());
				} else {
					check = true;
				}
			}
		}
		//checks if board is filled
		if (check == false) 
			return null;
		//copy toSolve
		String[][] toCheck = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				toCheck[i][j] = toSolve[i][j];
			}
		}
		//looks for obvious solution
		if (search(toCheck) == true) {
			System.out.println("Move is " + move.getX() + "x " + move.getY() + "y " + move.getValue() + "value");
			return move;
		}
		System.out.println("Couldn't find an obvious solution, proceeding to do backtracking dfs:");
		System.out.println("");
		int[][] toSearch = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Sudoku[i][j].getCurrentValue() != 0) {
					toSearch[i][j] = Sudoku[i][j].getCurrentValue();
				}
			}
		}
		//backtracking dfs
		if (solve(0, toSearch)) {
			//if can find a solution, return hint from first branch state used
			printSolved(toSearch);
			System.out.println("Move is " + move.getX() + "x " + move.getY() + "y " + move.getValue() + "value");
			return move;
		}
		//if can't find obvious hint or possible solution, returns null
		return null;
	}
	
	/**
	 * Taken from www.colloquial.com/games/sudoku/java_sudoku/html
	 * Written by Bob Carpenter
	 * Finds the next position to check
	 * @param p the position
	 * @return the next position
	 */
	private int nextPosition (int p) {
		// position p is an integer: -1 means no more positions, otherwise, it stores 
		//   both the row and column of a position:
		//   the lowest 4 bits are the column number and the next 4 bits are row number
		int j = p & 15;          // j is the column number stored at the lowest 4 bits of p
		if (j < 8) return p+1;   // increase j by one
		int i = p >> 4;          // i is the row number stored at the next 4 bits of p
		if (i == 8) return -1;   // no more position
		return (i+1)<< 4;        // increase i by 1 and set j = 0
	}

	
	/**
	 * Taken from www.colloquial.com/games/sudoku/java_sudoku/html
	 * Written by Bob Carpenter
	 * A backtracking dfs for when we can't find obvious solution
	 * @param p
	 * @param cells
	 * @return
	 */
	private boolean solve(int p, int[][] cells) {
		// recursive backtrack search.

		if (p == -1) return true;  // no more positions
		if (p == -2) return false; // no solution; backtrack

		int j = p & 15;  // row number
		int i = p >> 4;  // column number

		if (cells[i][j] != 0)  // skip filled cells
			return solve(nextPosition(p),cells);

		for (int val = 1; val <= 9; ++val) {
			if (legal(i,j,val,cells)) {
				cells[i][j] = val;
				//keeps on trying to solve
				//if leads to fail, return try next, if no more, return false
				if (solve(nextPosition(p),cells)) {
					//sets the move to return
					move.setX(j);
					move.setY(i);
					move.setValue(val);
					return true;
				}
			}
		}
		cells[i][j] = 0; // reset on backtrack
		return false;
	}
	
	/**
	 * Taken from www.colloquial.com/games/sudoku/java_sudoku/html
	 * Written by Bob Carpenter
	 * Checks if move is legal
	 * @param i the y position
	 * @param j the x position
	 * @param val the value
	 * @param cells the board
	 * @return
	 */
	static boolean legal(int i, int j, int val, int[][] cells) {
		// check if val is legal at cells[i][j].
		for (int k = 0; k < 9; ++k)  // row
			if (val == cells[k][j])
				return false;

		for (int k = 0; k < 9; ++k) // col
			if (val == cells[i][k])
				return false;

		int boxRowOffset = (i / 3)*3;
		int boxColOffset = (j / 3)*3;
		for (int k = 0; k < 3; ++k) // box
			for (int m = 0; m < 3; ++m)
				if (val == cells[boxRowOffset+k][boxColOffset+m])
					return false;

		return true; // no violations, so it's legal
	}
	
	/**
	 * Checks if there is an obvious hint to give
	 * Changes the private Move object to hint if found
	 * @param solve the board
	 * @return true if yes, else false
	 */
    private Boolean search(String[][] solve) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println("Checking " + j + "x " + i + "y " + solve[i][j]);
				if (solve[i][j].length() == 1) {
					//gets all the values so far
					int value = Integer.parseInt(solve[i][j]);
					//gets the peers
					Unit curr = new Unit(j, i);
					ArrayList<Unit> list = curr.getPeers(curr);
					for (Unit u : list) { //eliminates the values on all of the unit's peers
						solve[u.getY()][u.getX()] = solve[u.getY()][u.getX()].replace(Integer.toString(value), "");
					}
				}
			}
		}
		if (checkPossibilities(solve)) //checks if there is any hint to return
			return true;
		return false;
    }
	
    /**
     * Checks if there are any hints possible from eliminating possibilities
     * @param toCheck the board
     * @return true or yes, else false
     */
	private Boolean checkPossibilities(String[][] toCheck) {
		//Rule 1: if square with 1 possibility, found hint to give
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (toCheck[i][j].length() == 1 && 
						Integer.parseInt(toSolve[i][j]) != Integer.parseInt(toCheck[i][j])) {
					//for all on board if just 1 value and it is not a given, return that as hint
					move.setX(j);
					move.setY(i);
					move.setValue(Integer.parseInt(toCheck[i][j]));
					return true;
				}
			}
		}
		//Rule 2: for each unit, if only 1 square has number and others don't, found hint
		int[] buffer = new int[9];
		clearBuffer(buffer, 9);
		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				Unit curr = new Unit(j, i);
				ArrayList<Unit> units = curr.getBox(curr);
				for (Unit u : units) {
					for (int k = 0; k < toCheck[u.getY()][u.getX()].length() && toCheck[u.getY()][u.getX()].length() != 1; k++) {
						buffer[((int)toCheck[u.getY()][u.getX()].charAt(k) - 49)]++;
					}
				}
				//for each box of 9 squares on the board, create an array with the number
				//of times a possible value occurs
				for (int k = 0; k < 9; k++) {
					if (buffer[k] == 1) {
						//if value only occurs once in the box, return that as the hint
						for (Unit u : units) {
							if (toCheck[u.getY()][u.getX()].contains(Integer.toString(k + 1))) {
								move.setX(u.getX());
								move.setY(u.getY());
								move.setValue(k + 1);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Clears an array- sets all as 0
	 * @param buffer the array
	 * @param length length of array
	 * @return array
	 */
	private int[] clearBuffer(int[] buffer, int length) {
		for (int i = 0; i < length; i++) { //for all in the array, set to 0
			buffer[i] = 0;
		}
		return buffer;
	}
	
	/**
	 * Prints the board given
	 * @param solved the board
	 */
	private void print(Square[][] solved) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//for all squares on the board, print it
				System.out.print(solved[i][j].getCurrentValue() + " ");
				if (j == 2 || j == 5) {
					System.out.print(" ");
				}
				if (j == 8) {
					System.out.print(" \n");
				}
			}
			if (i == 2 || i == 5 || i == 8) {
				System.out.print("\n");
			}
		}
	}
	
	/**
	 * Prints own board used
	 * @param solved board used
	 */
	private void printSolved(int[][] solved) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//for all squares on the board, print it
				System.out.print(solved[i][j] + " ");
				if (j == 2 || j == 5) {
					System.out.print(" ");
				}
				if (j == 8) {
					System.out.print(" \n");
				}
			}
			if (i == 2 || i == 5 || i == 8) {
				System.out.print("\n");
			}
		}
	}
}