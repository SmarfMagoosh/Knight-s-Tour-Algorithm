package algorithms;

/**
 * basic class to keep track of move order better
 * @author Evan Dreher
 * @since 11/4/2022
 */
public class Move {
	// board coordinates
	private int x;
	private int y;
	
	/**
	 * Move constructor
	 * @param x rank of the move
	 * @param y file of the move
	 * @since 11/4/2022
	 */
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * simple getter
	 * @return the rank of the move
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * simple setter
	 * @param x the new x value
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * simple getter
	 * @return the file of the move
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * simple setter
	 * @param y the new y value for the move
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return string representation as a coordinate pair
	 * @since 11/4/2022
	 * @author Evan Dreher
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
