public class PuzzleTester {

	public static void main(String args[]) {
		HintSystem h = new HintSystem();
		System.out.println("Easy puzzle:");
		h.Hint(Puzzle.createPuzzle(Puzzle.EASY));
		System.out.println();
		System.out.println("Medium puzzle:");
		h.Hint(Puzzle.createPuzzle(Puzzle.MEDIUM));
		System.out.println();
		System.out.println("Hard puzzle:");
		h.Hint(Puzzle.createPuzzle(Puzzle.HARD));
		System.out.println();

		//int[][] Sudoku1 = new int[9][9]; 
		//h.Hint(Sudoku1);
		//Puzzle p = new Puzzle(0);

//		p.printPuzzle();
	}
}
