package algorithms;

import java.util.ArrayList;

public class Tour {
	private int[][] path;         // shows the path the knight has taken
	private boolean[][] visited;  // shows the squares the knight has visited
	private int steps;            // the current number of moves the knight has made
	private boolean solvable;     // true if the board has been saved false otherwise
	private long runtime;         // tracks the times it took to solve theboard

	// @author Evan Dreher

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// -------------------------------WORKING
	// CODE----------------------------------------
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Initializes the knight's tour problem on a square board
	 * @param size the number of files and ranks on the chess board
	 */
	public Tour(int size) {
		path = new int[size][size];
		visited = new boolean[size][size];
		steps = 1;
		
		for(int[] i : path) {
			for(int j : i) {
				j = 0;
			}
		}
		for(boolean[] b : visited) {
			for(boolean bool : b) {
				bool = false;
			}
		}
	}

	/**
	 * Initializes a knight's tour problem on a rectangular board
	 * @param ranks the number of ranks on the chess board
	 * @param files the number of files on the chess board
	 */
	public Tour(int ranks, int files) {
		path = new int[ranks][files];
		visited = new boolean[ranks][files];
		for(int[] ia : path) {
			for(int i : ia) {
				i = 0;
			}
		}
		for(boolean[] ba : visited) {
			for(boolean b : ba) {
				b = false;
			}
		}
		steps = 1;
	}

	/**
	 * simple getter method
	 * @return solvable class member
	 */
	public boolean isSolved() {
		return solvable;
	}

	/**
	 * simple getter method
	 * @return runtime class member
	 */
	public long runtime() {
		return runtime;
	}

	/**
	 * makes the first call to the recursive solution finder and tracks the time to
	 * find a solution
	 * @param fast set true to use warnsdorff rule for faster runtimes or false to
	 *             use default backtracking
	 */
	public void start(boolean fast) {
		runtime = System.currentTimeMillis();
		recTour(0, 0, fast);
		runtime = System.currentTimeMillis() - runtime;
		if (path[0][0] == 0) {
			solvable = false;
		} else {
			solvable = true;
		}
	}

	/**
	 * finds all legal knight-moves from [row][col] that have not been visited
	 * 
	 * @param row the rank to find moves from
	 * @param col the file to find moves from
	 * @return a collection of integer pairs represented as arrays of length 2.
	 */
	public ArrayList<int[]> findMoves(int row, int col) {
		ArrayList<int[]> legalMoves = new ArrayList<>();
		if(row + 2 < path.length) {
			if(col + 1 < path[0].length) {
				int[] move = {row + 2, col + 1};
				legalMoves.add(move);
			}
			if(col - 1 >= 0) {
				int[] move = {row + 2, col - 1};
				legalMoves.add(move);
			}
		}
		if(row - 2 >= 0) {
			if(col + 1 < path[0].length) {
				int[] move = {row - 2, col + 1};
				legalMoves.add(move);
			}
			if(col - 1 >= 0) {
				int[] move = {row - 2, col - 1};
				legalMoves.add(move);
			}
		}
		if(row + 1 < path.length) {
			if(col + 2 < path[0].length) {
				int[] move = {row + 1, col + 2};
				legalMoves.add(move);
			}
			if(col - 2 >= 0) {
				int[] move = {row + 1, col - 2};
				legalMoves.add(move);
			}
		}
		if(row - 1 >= 0) {
			if(col + 2 < path[0].length) {
				int[] move = {row - 1, col + 2};
				legalMoves.add(move);
			}
			if(col - 2 >= 0) {
				int[] move = {row - 1, col - 2};
				legalMoves.add(move);
			}
		}
		return legalMoves;
	}

	/**
	 * determines if a solution has been found
	 * 
	 * @return true if every square has been visited, false otherwise.
	 */
	public boolean tourCompleted() {
		for(boolean[] ba : visited) {
			for(boolean b : ba) {
				if(!b) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * functions like findMoves() but sorts those moves in ascending order of how
	 * many options there are from the square you move to
	 * 
	 * @param row the rank to find moves from
	 * @param col the file to find mvoes from
	 * @returna a collection of integer pairs represented as arrays
	 */
	public ArrayList<int[]> warnsdorffMoves(int row, int col) {
		ArrayList<int[]> moves = findMoves(row, col);

		// find out how many continuing moves there are from each move
		int[] branches = new int[moves.size()];
		for (int i = 0; i < branches.length; i++) {
			branches[i] = findMoves(moves.get(i)[0], moves.get(i)[1]).size();
		}

		// counting sort those moves
		int[] counts = new int[9];
		for (int i : counts) {
			counts[i] = 0;
		}
		for (int i : branches) {
			counts[i]++;
		}

		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}

		int[][] warnsdorffMoves = new int[moves.size()][2];
		int[] output = new int[moves.size()];
		for (int i = warnsdorffMoves.length - 1; i >= 0; i--) {
			warnsdorffMoves[counts[branches[i]] - 1] = moves.get(i);
			output[counts[branches[i]] - 1] = branches[i];
			counts[branches[i]]--;
		}
		ArrayList<int[]> ret = new ArrayList<>();
		for (int[] ia : warnsdorffMoves) {
			ret.add(ia);
		}
		return ret;
	}

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm
	 * @param row  the rank that the knight is on
	 * @param col  the file that the knight is on
	 * @param fast set true to use warnsdorffRules for faster runtimes, false
	 *             otherwise
	 */
	public void recTour(int row, int col, boolean fast) {
		// mark current square
		visited[row][col] = true;
		path[row][col] = steps;
		steps++;
		
		// try all branches from current square
		ArrayList<int[]> moves;
		if(fast) {
			moves = warnsdorffMoves(row, col);
		} else {
			moves = findMoves(row, col);
		}
		
		for(int[] ia : moves) {
			if(!visited[ia[0]][ia[1]]) {
				recTour(ia[0], ia[1], fast);
			}
		}
		
		// check if problem is done
		if(tourCompleted()) {
			return;
		}
		
		//unmark square if no child paths works
		visited[row][col] = false;
		path[row][col] = 0;
		steps--;
	}

	@Override
	public String toString() {
		String ts = "Path:\n";
		int maxDigits = Integer.toString(path[0].length * path.length).length() + 1;
		for (int[] ia : path) {
			for (int i : ia) {
				int iDigits = Integer.toString(i).length();
				String intStr = "|" + i;
				for (int j = iDigits; j <= maxDigits; j++) {
					intStr += " ";
				}
				ts += intStr;
			}
			ts += "|\n";
		}
		return ts;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// --------------------------NOT WORKING CODE-----------------------------------
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm
	 * 
	 * @param row  the rank that the knight is on
	 * @param col  the file that the knight is on
	 * @param fast set true to use warnsdorffRules for faster runtimes, false
	 *             otherwise
	 */
	public void recTourEnd(int row, int col, boolean fast, int endRow, int endCol) {

		// mark current square
		path[row][col] = steps;
		steps++;

		if (row == endRow && col == endCol && tourCompleted()) {
			return;
		}

		// try all branches from current square
		ArrayList<int[]> moves;
		if (fast) {
			moves = warnsdorffMoves(row, col);
		} else {
			moves = findMoves(row, col);
		}
		for (int[] ia : moves) {
			if (path[ia[0]][ia[1]] == 0) {
				recTourEnd(ia[0], ia[1], fast, endRow, endCol);
			}
		}

//		// check if problem is done
//		if (tourCompleted()) {
//			return;
//		}

		// unmark square if no child paths works
		path[row][col] = 0;
		steps--;
	}

	// @author Christian Previtali

	// @author Christina Kasunick
}
