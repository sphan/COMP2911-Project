
public class PuzzleTester {

	public static void main(String args[]) {
		System.out.println("Easy puzzle:");
		Puzzle.createPuzzle(Puzzle.EASY);
		System.out.println();
		System.out.println("Medium puzzle:");
		Puzzle.createPuzzle(Puzzle.MEDIUM);
		System.out.println();
		System.out.println("Hard puzzle:");
		Puzzle.createPuzzle(Puzzle.HARD);
		System.out.println();
//		p.printPuzzle();
	}
}
