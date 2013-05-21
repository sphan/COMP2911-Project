
public class HintSystem {
	private String[][] toSolve;
	
	public HintSystem() {
		toSolve = new String[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				toSolve[i][j] = "123456789";
			}
		}
	}
	
	public Move Hint (int[][] Sudoku) {
		// if obviously wrong, return null move
		if (isLegal(Sudoku) == false) {
			return null;
		}
		
		int[][] Sudoku1 = new int[9][9]; 
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Sudoku1[i][j] = 0;
			}
		}
		
		Sudoku1[0][0] = 5;
		Sudoku1[1][0] = 3;
		Sudoku1[0][1] = 6;
		Sudoku1[2][1] = 9;
		Sudoku1[2][2] = 8;
		Sudoku1[4][0] = 7;
		Sudoku1[1][3] = 1;
		Sudoku1[4][1] = 9;
		Sudoku1[5][1] = 5;
		Sudoku1[7][2] = 6;
		Sudoku1[0][3] = 8;
		Sudoku1[0][4] = 4;
		Sudoku1[0][5] = 7;
		Sudoku1[3][4] = 8;
		Sudoku1[4][3] = 6;
		Sudoku1[5][4] = 3;
		Sudoku1[4][5] = 2;
		Sudoku1[8][3] = 3;
		Sudoku1[8][4] = 1;
		Sudoku1[8][5] = 6;
		Sudoku1[1][6] = 6;
		Sudoku1[3][7] = 4;
		Sudoku1[4][7] = 1;
		Sudoku1[5][7] = 9;
		Sudoku1[4][8] = 8;
		Sudoku1[6][6] = 2;
		Sudoku1[7][6] = 8;
		Sudoku1[8][7] = 5;
		Sudoku1[8][8] = 9;
		Sudoku1[7][8] = 7;
		
		//has box 0 - 8
		//each box has 0 - 8 units
		//has 81 units
		if (done(toSolve)) {
			return giveMove(Sudoku);
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Sudoku[i][j] != 0) {
					toSolve[i][j] = Integer.toString(Sudoku[i][j]);
				}
			}
		}
		print();
		toSolve = search(copy(toSolve));
		//could be null
		return giveMove(Sudoku);
	}
	
	private Move giveMove(int[][] Sudoku) {
		//if null-- can't find solution
		//or gameboard generated is wrong
		if (Sudoku == null) {
			return null;
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Sudoku[i][j] == 0) {
					Move move = new Move(i, j, Integer.parseInt(toSolve[i][j]));
					return move;
				}
			}
		}
		return null;
	}
	
	private void print() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//if (j == 3 || j == 6 || j == 9) {
					//System.out.print("\n");
				//}
				System.out.print("s" + toSolve[i][j] + " ");
			}
			if (i == 2 || i == 5 || i == 8) {
				System.out.print("\n");
			}
		}
	}
	
	private String[][] search (String[][] solve) {
		//if incorrect, backtrack
		if (solve == null) {
			return null;
		}
		//if complete return
		if (isLegal(solve)) {
			return solve;
		}
		//finds square with least possibilities to search
		int x = 0;
		int y = 0;
		int cost = solve[0][0].length();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (solve[i][j].length() < cost) {
					cost = solve[i][j].length();
					x = i;
					y = j;
				}
			}
		}
		//searches possibilities
		//if complete, return
		//else return any that is valid
		String[][] validBuffer = null;
		String[][] buffer = null;
		for (int i = 0; i < cost; i++) {
			buffer = search(assign(copy(solve), x, y, (int)(solve[x][y].charAt(i))));
			if (done(buffer)) {
				return buffer;
			} else if (buffer != null) {
				validBuffer = buffer;
			}
		}
		return validBuffer;
	}
	
	private String[][] copy(String[][] a) {
	    String[][] b = new String[9][9];
	    for (int i = 0; i < 9; i++) {
	        for (int j = 0; j < 9; j++) {
	        	b[i][j] = a[i][j];
	        }
	    }
	    return b;
	}
	
	//is redundant- just return if valid ie. not null
	//replace with check to see if obviously wrong
	//otherwise return
	private Boolean done(String[][] check) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (check[i][j].length() != 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	private String[][] assign (String[][] search, int x, int y, int value) {
		String otherValues = search[x][y].replace("value", "");
		for (int i = 0; i < otherValues.length(); i++) {
			if (eliminate(search, x, y, Character.getNumericValue(otherValues.charAt(i))) == null) {
				return null;
			}
		}
		return search;
	}
	
	private String[][] eliminate(String[][] search, int x, int y, int value) {
		//tries to eliminate value from position x, y
		//if done, return
		if (search[x][y].indexOf((char)value) != -1) {
			return search;
		}
		search[x][y] = toSolve[x][y].replace("value", "");
		if (search[x][y].length() == 0) {
			return null;
		} else if (search[x][y].length() ==  1) {
			int value2 = Integer.parseInt(search[x][y]);
			Unit curr = new Unit(x, y);
			for (Unit u : curr.getPeers(curr)) {
				if (eliminate(search, u.getX(), u.getY(), value2) == null) {
					return null;
				}
			}
		}
		Unit curr = new Unit(x, y);
		for (Unit u : curr.getBox(curr)) {
			if (toSolve[u.getX()][u.getY()].length() == 0) {
				return null;
			} else if (toSolve[u.getX()][u.getY()].length() == 1) {
				if (assign(search, u.getX(), u.getY(), value) == null) {
					return null;
				}
			}
		}
		return search;
	}
}
