package algorithms;

import java.util.ArrayList;

/**
 * Defines a chessboard and methods that allow the knight's tour to be performed on said chessboard
 * @author Evan Dreher
 * @since 10/19/2022
 */
public class Tour {
	private int[][] path;
	private boolean[][] visited;

	private int steps;
	private boolean solved;
	private long runtime;
	private int length;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// -------------------------------WORKING
	// CODE----------------------------------------
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Initializes the knight's tour problem on a square board
	 * 
	 * @param size the number of files and ranks on the chess board
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	@SuppressWarnings("unused")
	public Tour(int size) {
		path = new int[size][size];
		visited = new boolean[size][size];
		steps = 1;
		solved = false;
		length = (size * size) + 1;

		for (int[] ia : path) {
			for (int i : ia) {
				i = 0;
			}
		}
		for (boolean[] ba : visited) {
			for (boolean b : ba) {
				b = false;
			}
		}
	}

	/**
	 * Initializes a knight's tour problem on a rectangular board
	 * 
	 * @param ranks the number of ranks on the chess board
	 * @param files the number of files on the chess board
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	@SuppressWarnings("unused")
	public Tour(int ranks, int files) {
		path = new int[ranks][files];
		visited = new boolean[ranks][files];
		steps = 1;
		solved = false;
		length = (ranks * files) + 1;

		for (int[] ia : path) {
			for (int i : ia) {
				i = 0;
			}
		}
		for (boolean[] ba : visited) {
			for (boolean b : ba) {
				b = false;
			}
		}

	}

	/**
	 * simple getter method
	 * 
	 * @return solvable class member
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public boolean isSolved() {
		return solved;
	}

	/**
	 * simple getter method
	 * 
	 * @return runtime class member
	 * @since 10/20/2022
	 * @author Evan Dreher
	 */
	public long runtime() {
		return runtime;
	}

	/**
	 * makes the first call to the recursive solution finder and tracks the time to
	 * find a solution
	 * 
	 * @param set fast to true to use warnsdorff rule for faster runtimes or false
	 *            to use default backtracking
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void start() {
		runtime = System.currentTimeMillis();
		recTourEnd(0, 0, 1, path[0].length - 2);
		runtime = System.currentTimeMillis() - runtime;
	}

	/**
	 * determines if a solution has been found
	 * 
	 * @return true if every square has been visited, false otherwise.
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public boolean tourCompleted() {
		return steps == length;
	}

	/**
	 * finds all legal knight-moves from [rank][file] that have not been visited
	 * 
	 * @param rank the rank to find moves from
	 * @param file the file to find moves from
	 * @return a filelection of integer pairs represented as arrays of length 2.
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public ArrayList<int[]> findMoves(int rank, int file) {
		ArrayList<int[]> legalMoves = new ArrayList<>();
		if (rank + 2 < path.length) {
			if (file + 1 < path[0].length) {
				int[] move = { rank + 2, file + 1 };
				legalMoves.add(move);
			}
			if (file - 1 >= 0) {
				int[] move = { rank + 2, file - 1 };
				legalMoves.add(move);
			}
		}
		if (rank - 2 >= 0) {
			if (file + 1 < path[0].length) {
				int[] move = { rank - 2, file + 1 };
				legalMoves.add(move);
			}
			if (file - 1 >= 0) {
				int[] move = { rank - 2, file - 1 };
				legalMoves.add(move);
			}
		}
		if (rank + 1 < path.length) {
			if (file + 2 < path[0].length) {
				int[] move = { rank + 1, file + 2 };
				legalMoves.add(move);
			}
			if (file - 2 >= 0) {
				int[] move = { rank + 1, file - 2 };
				legalMoves.add(move);
			}
		}
		if (rank - 1 >= 0) {
			if (file + 2 < path[0].length) {
				int[] move = { rank - 1, file + 2 };
				legalMoves.add(move);
			}
			if (file - 2 >= 0) {
				int[] move = { rank - 1, file - 2 };
				legalMoves.add(move);
			}
		}
		return legalMoves;
	}

	/**
	 * functions like findMoves() but sorts those moves in ascending order of how
	 * many options there are from the square you move to
	 * 
	 * @param rank the rank to find moves from
	 * @param file the file to find mvoes from
	 * @return a filelection of integer pairs represented as arrays
	 * @since 10/20/2022
	 * @author Evan Dreher
	 */
	public int[][] warnsdorffMoves(int rank, int file) {
		ArrayList<int[]> moves = findMoves(rank, file);

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
		
		return warnsdorffMoves;
	}

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm
	 * 
	 * @param rank the rank that the knight is on
	 * @param file the file that the knight is on
	 * @param fast set true to use warnsdorffRules for faster runtimes, false
	 *             otherwise
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void recTour(int rank, int file) {
		// mark current square
		visited[rank][file] = true;
		path[rank][file] = steps;
		steps++;

		// try all branches from current square
		int[][] moves = warnsdorffMoves(rank, file);

		for (int[] ia : moves) {
			if (!visited[ia[0]][ia[1]]) {
				recTour(ia[0], ia[1]);
			}
		}

		// check if problem is done
		if (tourCompleted()) {
			return;
		}

		visited[rank][file] = false;
		path[rank][file] = 0;
		steps--;
	}

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm in such a way that the knight well end on the square
	 * [endRank][endFile]
	 * 
	 * @param rank    the rank that the knight is on
	 * @param file    the file that the knight is on
	 * @param fast    set true to use warnsdorffRules for faster runtimes, false
	 *                otherwise
	 * @param endRank the ranks that the knight will end on
	 * @param endFile the fileumn that the knight will end on
	 * @since 10/22/2022
	 * @author Evan Dreher
	 */
	public void recTourEnd(int rank, int file, int endRank, int endFile) {
		// mark current square
		visited[rank][file] = true;
		path[rank][file] = steps;
		steps++;

		// check if problem is done
		if (rank == endRank && file == endFile && tourCompleted()) {
			return;
		}

		// try all branches from current square
		int[][] moves = warnsdorffMoves(rank, file);

		for (int[] ia : moves) {
			if (!visited[ia[0]][ia[1]]) {
				recTourEnd(ia[0], ia[1], endRank, endFile);
				if (tourCompleted()) {
					return;
				}
			}
		}

		// unmark square if no child paths works
		visited[rank][file] = false;
		path[rank][file] = 0;
		steps--;
	}

	/**
	 * @return a string representation of the board
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
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

	/**
	 * recursively splits a piece of size n into ideal chunks of 5, 7, and 9
	 * 
	 * @param n the size of the piece to be split
	 * @return an arrayList of ideal numbers such that the sum equals n
	 * @since 10/22/2022
	 * @author Evan Dreher
	 */
	public ArrayList<Integer> divide(int n) {
		ArrayList<Integer> chunks = new ArrayList<>();
		if (n >= 28) {
			chunks.addAll(divide((int) Math.floor(n / 2.0)));
			chunks.addAll(divide((int) Math.ceil(n / 2.0)));
		} else {
			if (n >= 23) {
				chunks.add(9);
				n -= 9;
			}
			if (n >= 21) {
				chunks.add(7);
				n -= 7;
			}
			if (n >= 19) {
				chunks.add(5);
				n -= 5;
			}
			if (n == 18) {
				chunks.add(9);
				chunks.add(9);
			} else if (n == 17) {
				chunks.add(7);
				chunks.add(5);
				chunks.add(5);
			} else if (n == 16) {
				chunks.add(9);
				chunks.add(7);
			} else if (n == 15) {
				chunks.add(5);
				chunks.add(5);
				chunks.add(5);
			} else if (n == 14) {
				chunks.add(7);
				chunks.add(7);
			} else if (n == 12) {
				chunks.add(7);
				chunks.add(5);
			} else if (n == 10) {
				chunks.add(5);
				chunks.add(5);
			} else {
				chunks.add(n);
			}
		}
		return chunks;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// --------------------------NOT WORKING CODE-----------------------------------
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// solves a smaller section of the larger board
	public void solvePart() {
		// TODO complete method
	}

	// divides the larger board into smaller sections then solves them all
	public void dncSolve() {
		// TODO complete method
	}

	// stores a dynamically solved solution
	public void dynamicStore() {
		// TODO complete method
	}

	// retrieves a dynamically solved solution
	public void dynamicSolve() {
		// TODO complete method
	}
}
