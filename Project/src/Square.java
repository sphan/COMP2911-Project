import java.util.LinkedList;

public class Square {
	
	/**
	 * Generate a square with its position and index
	 * defined.
	 * @param row The row in which the square is positioned.
	 * @param column The column in which the square is in.
	 * @param boxNum The 3x3 box number in which the square is in.
	 * @param positionInBox The position in the 3x3 box the square is in.
	 */
	public Square(int row, int column, int boxNum, int positionInBox) {
		this.row = row;
		this.column = column;
		this.threeByThreeBox = boxNum;
		this.positionInThreeByThree = positionInBox;
	}
	
	/**
	 * Set the current value of the square. This is
	 * the value to be displayed in the box in GUI.
	 * @param value An integer that specifies the value
	 * to be displayed.
	 */
	public void setCurrentValue(int value) {
		this.currentValue = value;
	}
	
	/**
	 * Get the current value of the square. This is the value
	 * displayed in the box in GUI.
	 * @return An integer that specifies the value displayed.
	 */
	public int getCurrentValue() {
		return this.currentValue;
	}

	private int row;
	private int column;
	private int threeByThreeBox;
	private int positionInThreeByThree;
	private int currentValue;
	private LinkedList<Integer> availableValues;
	private LinkedList<Integer> usedValues;
}
