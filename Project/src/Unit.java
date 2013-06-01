import java.util.ArrayList;

/**
 * Class to a square on the board
 * @author Richard
 */
public class Unit {
	private int x;
	private int y;
	
	/**
	 * Construction to create a new unit
	 * @param a the x coordinate
	 * @param b the y coordinate
	 */
	public Unit (int a, int b) {
		x = a; 
		y = b;
	}
	
	/**
	 * Returns the X value
	 * @return x
	 */
	public int getX () {
		return x;
	}
	
	/**
	 * Return the X value
	 * @return
	 */
	public int getY () {
		return y;
	}
	
	/**
	 * Checks if two squares are equal
	 * @param b the other unit to compare with this
	 * @return true or false
	 */
	public Boolean equals (Unit b) {
		if (x == b.getX() && y == b.getY()) { //compares x and y
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the a list of peers of this unit
	 * @param a the unit
	 * @return array list of peers
	 */
	public ArrayList<Unit> getPeers(Unit a) {
		ArrayList<Unit> toReturn = new ArrayList<Unit>();
		//finds the peers along the horizontal
		for (int i = 0; i < 9; i++) {
			if (i != a.getX()) {
				toReturn.add(new Unit(i, a.getY()));
			}
		}
		//finds the peers along the vertical
		for (int i = 0; i < 9; i++) {
			if (i != a.getY()) {
				toReturn.add(new Unit(a.getX(), i));
			}
		}
		//finds the peers in the same box if not in the current list
		ArrayList<Unit> box = getBox(a);
		for (Unit u : box) {
			if (toReturn.contains(u) == false && u.equals(a) == false) {
				toReturn.add(u);
			}
		}
		return toReturn;
	}
	
	/**
	 * Gets the other squares in the same box as the unit
	 * @param a the unit
	 * @return array list of squares in the same box
	 */
	public ArrayList<Unit> getBox(Unit a) {
		ArrayList<Unit> toReturn = new ArrayList<Unit>();
		int first = a.getX()/3;
		int second = a.getY()/3;
		for (int i = first*3; i < first*3 + 3; i++) {
			for (int j = second*3; j < second*3 + 3; j++) {
				Unit buffer = new Unit(i, j);
				if (buffer.equals(a) == false) {
					toReturn.add(buffer);
				}
			}
		}
		return toReturn;
	}
}
