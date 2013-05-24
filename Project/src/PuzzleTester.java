
public class PuzzleTester {

	public static void main(String args[]) {
		int[][] Sudoku1 = new int[9][9]; 
		HintSystem h = new HintSystem();
		h.Hint(Sudoku1);
		Puzzle p = new Puzzle(0);
//		p.printPuzzle();
	}
}
