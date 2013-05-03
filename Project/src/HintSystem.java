import java.lang.reflect.Array;


public class HintSystem {
	private int[][] Board;
	private static final int EASY = 0;
	private static final int MEDIUM = 1;
	private static final int ROW = 9;
	private static final int COLUMN = 9;
	private static final int BOXES = 9;
	
	public HintSystem(int[][] Sudoku) {
		Board = Sudoku;
	}
	
	public Move Hint (int mode, int[][] Sudoku) {
		if (mode == EASY) {
			//easy mode, look for solution in Board
			//find row with least length, add random number into empty square from board
			int row = 1;
			int size = 0;
			for (int i = 0; i < COLUMN; i++) {
				if (Sudoku[1][i] != 0) {
					size++;
				}
			}
			for (int i = 0; i < ROW; i++) {
				int buffer = 0;
				for (int j = 0; j < COLUMN; j++) {
					if (Sudoku[i][j] != 0) {
						buffer++;
					}
				}
				if (buffer <= size) {
					row = i;
				}
			}
			int y = 0;
			while (Sudoku[row][y] != 0) {
				y++;
			}
			Move move = new Move(row, y, Board[row][y]); 
			return move;
		} else if (mode == MEDIUM) {
			//random number into almost full box
			int[] boxes = new int[BOXES];
			for (int count = 0; count < BOXES; count = count + 3) {
				int total = 0;
				for (int a = count; a < count + 3; a++) {
					for (int b = 0; b < 3; b++) {
						if (Sudoku[a][b] != 0) {
							total++;
						}
					}
					boxes[count] = total;
					total = 0;
					for (int c = 3; c < 6 + 3; c++) {
						if (Sudoku[a][c] != 0) {
							total++;
						}
					}
					boxes[count + 1] = total;
					total = 0;
					for (int d = 6; d < 9; d++) {
						if (Sudoku[a][d] != 0) {
							total++;
						}
					}
					boxes[count + 2] = total;
					total = 0;
				}
			}
			//now find least full square
			for (int size: boxes) {
				
				
			}
			
			
		} else {
			//hard mode, find own hints
			//not done yet
		}
	}
}
