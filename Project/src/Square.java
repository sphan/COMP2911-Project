
public class Square {
	
	/**
	 * Generate a square with its position and index
	 * defined.
	 * @param row The row in which the square is positioned.
	 * @param column The column in which the square is in.
	 * @param boxNum The 3x3 box number in which the square is in.
	 * @param positionInBox The position in the 3x3 box the square is in.
	 */
	public Square(int boxNum, int type) {
		this.threeByThreeBoxIndex = boxNum;
		this.type = type;
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
	 * Set the 3x3 box in which the current square is in.
	 * Index from 0 to 8.
	 * @param threeByThreeBoxIndex The 3x3 box number that
	 * the square is in range from 0 to 8.
	 */
	public void setThreeByThreeBox(int threeByThreeBoxIndex) {
		this.threeByThreeBoxIndex = threeByThreeBoxIndex;
	}

	/**
	 * Set the position of the square in the 3x3 box.
	 * @param positionInThreeByThree
	 */
	public void setPositionInThreeByThree(int positionInThreeByThree) {
		this.positionInThreeByThree = positionInThreeByThree;
	}
	
	/**
	 * Set the type of a square. The types can be Empty,
	 * or User-input, or hint, or predefined or error. This
	 * is used to determine the GUI for the cell/square.
	 * @param squareType
	 */
	public void setType(int squareType) {
		this.type = squareType;
	}
	
	/**
	 * Get the current value of the square. This is the value
	 * displayed in the box in GUI.
	 * @return An integer that specifies the value displayed.
	 */
	public int getCurrentValue() {
		return this.currentValue;
	}
	
	/**
	 * Get the 3x3 box in which the current square is in.
	 * @return The 3x3 box number of the current square.
	 */
	public int getThreeByThreeBox() {
		return threeByThreeBoxIndex;
	}
	
	public int getPositionInThreeByThree() {
		return positionInThreeByThree;
	}
	
	/**
	 * Get the type of the square.
	 * @return
	 */
	public int getType() {
		return this.type;
	}
	
//	private int row;
//	private int column;
	private int threeByThreeBoxIndex;
	private int positionInThreeByThree;
	

	private int currentValue;
	private int type;
//	private LinkedList<Integer> availableValues;
//	private LinkedList<Integer> usedValues;
	public static final int EMPTY_CELL = 0;
	public static final int HINT_CELL = 1;
	public static final int USER_INPUT_CELL = 2;
	public static final int PREDEFINE_CELL = 3;
	public static final int ERROR_CELL = 4;
}
