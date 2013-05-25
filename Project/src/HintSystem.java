import java.util.ArrayList;

public class HintSystem {
	private String[][] toSolve;
	private Move move;
	
	public HintSystem() {
		toSolve = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				toSolve[i][j] = "123456789";
			}
		}
		move = new Move(-1, -1, 0);
	}
	
	public Move Hint (int[][] Sudoku) {
		// if obviously wrong, return null move
		/*
		if (isLegal(Sudoku) == false) {
			return null;
		}
		*/
		
		//Below is test case
		int[][] Sudoku1 = new int[9][9]; 
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Sudoku1[i][j] = 0;
			}
		}
		Sudoku1[0][0] = 5;
		Sudoku1[0][1] = 3;
		Sudoku1[1][0] = 6;
		Sudoku1[2][1] = 9;
		Sudoku1[2][2] = 8;
		Sudoku1[0][4] = 7;
		Sudoku1[1][3] = 1;
		Sudoku1[1][4] = 9;
		Sudoku1[1][5] = 5;
		Sudoku1[2][7] = 6;
		Sudoku1[3][0] = 8;
		Sudoku1[4][0] = 4;
		Sudoku1[5][0] = 7;
		Sudoku1[4][3] = 8;
		Sudoku1[3][4] = 6;
		Sudoku1[4][5] = 3;
		Sudoku1[5][4] = 2;
		Sudoku1[3][8] = 3;
		Sudoku1[4][8] = 1;
		Sudoku1[5][8] = 6;
		Sudoku1[6][1] = 6;
		Sudoku1[7][3] = 4;
		Sudoku1[7][4] = 1;
		Sudoku1[7][5] = 9;
		Sudoku1[8][4] = 8;
		Sudoku1[6][6] = 2;
		Sudoku1[6][7] = 8;
		Sudoku1[7][8] = 5;
		Sudoku1[8][8] = 9;
		Sudoku1[8][7] = 7;
		
		//Sudoku1[0][2] = 4;
		//Re-enable this to find an obvious solution
		
		//Addressed as Sudoku1[y][x]
		
		Sudoku = Sudoku1;
		
		//has unit 0 - 8
		//each unit has 0 - 8 squares
		//has 81 squares
		
		//prints original sudoku
		printSolved(Sudoku);
		
		//sets up grid to eliminate possibilities
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Sudoku[i][j] != 0) {
					toSolve[i][j] = Integer.toString(Sudoku[i][j]);
				}
			}
		}
		
		//looks for obvious solution
		if (search(toSolve.clone()) == true) {
			System.out.println("Move is " + move.getX() + "x " + move.getY() + "y " + move.getValue() + "value");
			return move;
		}
		
		
		System.out.println("Couldn't find an obvious solution, proceeding to do backtracking dfs:");
		System.out.println("");
		
		//backtracking dfs
		solve(0, Sudoku);
		
		printSolved(Sudoku);
		
		System.out.println("Move is " + move.getX() + "x " + move.getY() + "y " + move.getValue() + "value");
		return move;
	}
	
	
	/*
		The below 3 functions was taken from online example - link is...
		For backtracking dfs when can't find obvious solution
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
	
    /*
    End of copied code
    */
	
    /*
    Below this is own code - thus the untidiness
    Used to find obvious solution
    */
	
    private Boolean search (String[][] solve) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (solve[i][j].length() == 1) {
					int value = Integer.parseInt(solve[i][j]);
					Unit curr = new Unit(j, i);
					ArrayList<Unit> list = curr.getPeers(curr);
					for (Unit u : list) {
						solve[u.getY()][u.getX()] = solve[u.getY()][u.getX()].replace(Integer.toString(value), "");
					}
				}
			}
		}
		if (checkPossibilities(solve))
			return true;
		return false;
    }
	
	private Boolean checkPossibilities(String[][] toCheck) {
		//Rule 1: if square with 1 possibility, found hint to give
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (toCheck[i][j].length() == 1 && 
						Integer.parseInt(toSolve[i][j]) != 
						Integer.parseInt(toCheck[i][j])) {
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
				for (int k = 0; k < 9; k++) {
					if (buffer[k] == 1) {
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
	
	private int[] clearBuffer(int[] buffer, int length) {
		for (int i = 0; i < length; i++) {
			buffer[i] = 0;
		}
		return buffer;
	}
	
	private void printSolved(int[][] solved) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//if (j == 3 || j == 6 || j == 9) {
					//System.out.print("\n");
				//}
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
