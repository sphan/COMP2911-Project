
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
	
	public Move Hint (int mode, int[][] Sudoku) {
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
		toSolve = search(copy(toSolve));
		return giveMove(Sudoku);
	}
	
	private Move giveMove(int[][] Sudoku) {
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
	
	private String[][] search (String[][] solve) {
		if (solve == null) {
			return null;
		}
		if (done(solve)) {
			return solve;
		}
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
		String[][] buffer = null;
		for (int i = 0; i < cost; i++) {
			buffer = search(assign(copy(solve), x, y, (int)(solve[x][y].charAt(i))));
			if (done(buffer)) {
				return buffer;
			}
		}
		return buffer;
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
