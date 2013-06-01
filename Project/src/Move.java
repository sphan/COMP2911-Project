/**
 * A class to represent moves on the board, consisting of the position and the value
 * @author Richard
 */
public class Move {
	private int x;
	private int y;
	private int value;
	
	/**
	 * Constructs a new move
	 * @param x the x coor
	 * @param y the y coor
	 * @param value the value
	 */
	public Move (int x, int y, int value) {
		this.setX(x);
		this.setY(y);
		this.setValue(value);
	}

	/**
	 * Returns the value
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value
	 * @param value new value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Returns the y coor
	 * @return y y coor
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y coor
	 * @param y y coor
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the x coor
	 * @return x x coor
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coor
	 * @param x x coor
	 */
	public void setX(int x) {
		this.x = x;
	}
}
