package algorithms;

import java.util.ArrayList;

/**
 * Defines a chessboard and methods that allow the knight's tour to be performed
 * on said chessboard
 * 
 * @author Evan Dreher
 * @since 10/19/2022
 */
public class Tour {
	// tracking members
	private int[][] board;
	private int steps;

	// efficiency members
	private boolean solved;
	private long runtime;

	// size members
	private int length;
	private int ranks;
	private int files;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// -------------------------------WORKING
	// CODE--------------------------------------
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
		board = new int[size][size];
		steps = 1;
		solved = false;
		length = (size * size) + 1;
		ranks = size;
		files = size;

		for (int[] ia : board) {
			for (int i : ia) {
				i = 0;
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
		board = new int[ranks][files];
		steps = 1;
		solved = false;
		length = (ranks * files) + 1;
		this.ranks = ranks;
		this.files = files;

		for (int[] ia : board) {
			for (int i : ia) {
				i = 0;
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
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void start() {
		runtime = System.currentTimeMillis();
		structuredTour(0, 0);
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
		if (rank + 2 < board.length) {
			if (file + 1 < board[0].length) {
				int[] move = { rank + 2, file + 1 };
				legalMoves.add(move);
			}
			if (file - 1 >= 0) {
				int[] move = { rank + 2, file - 1 };
				legalMoves.add(move);
			}
		}
		if (rank - 2 >= 0) {
			if (file + 1 < board[0].length) {
				int[] move = { rank - 2, file + 1 };
				legalMoves.add(move);
			}
			if (file - 1 >= 0) {
				int[] move = { rank - 2, file - 1 };
				legalMoves.add(move);
			}
		}
		if (rank + 1 < board.length) {
			if (file + 2 < board[0].length) {
				int[] move = { rank + 1, file + 2 };
				legalMoves.add(move);
			}
			if (file - 2 >= 0) {
				int[] move = { rank + 1, file - 2 };
				legalMoves.add(move);
			}
		}
		if (rank - 1 >= 0) {
			if (file + 2 < board[0].length) {
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
	public void tour(int rank, int file) {
		// mark current square
		board[rank][file] = steps;
		steps++;

		// try all branches from current square
		int[][] moves = warnsdorffMoves(rank, file);

		for (int[] ia : moves) {
			if (board[ia[0]][ia[1]] == 0) {
				tour(ia[0], ia[1]);
			}
		}

		// check if problem is done
		if (tourCompleted()) {
			return;
		}

		// if not child nodes had a successful tour
		board[rank][file] = 0;
		steps--;
	}

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm in such a way that the tour will be "structured" ad descripbed by
	 * Dr. Ian Parberry
	 *
	 * @param rank the ranks that the knight is on
	 * @param file the file that the knight is on
	 * @since 11/3/2022
	 * @author Evan Dreher
	 */
	public void structuredTour(int rank, int file) {
		// eliminates incorrect routes before even trying them for an m x n board
		
		// case 1: trying to go to the end square before the tour is over
//		if ((rank == 1 && file == 2) || (rank == 2 && file == 1)) {
//			System.out.println(steps);
//			if (steps != 4 || steps != length) {
//				return;
//			}
//		}
		
		// case 2: [0][1] -> [2][0] do not connect and [0][2] -> [1][0] do not connect
		if(board[0][1] != 0 && board[2][0] != 0) {
			if(Math.abs(board[0][1] - board[2][0]) != 1) {
				return;
			}
		}
		
		if (board[0][2] != 0 && board[1][0] != 0) {
			if (Math.abs(board[0][2] - board[1][0]) != 1) {
				return;
			}
		}

		// case 3: [0][n-2] -> [2][n-1] and [0][n-3] -> [1][n-1]
		if (board[0][files - 2] != 0 && board[2][files - 1] != 0) {
			if (Math.abs(board[0][files - 2] - board[2][files - 1]) != 1) {
				return;
			}
		}

		if (board[0][files - 3] != 0 && board[1][files - 1] != 0) {
			if (Math.abs(board[0][files - 3] - board[1][files - 1]) != 1) {
				return;
			}
		}

		// case 4: [m-3][0] -> [m-1][1] and [m-2][0] -> [m-1][2] do not connect
		if (board[ranks - 3][0] != 0 && board[ranks - 1][1] != 0) {
			if (Math.abs(board[ranks - 3][0] - board[ranks - 1][1]) != 1) {
				return;
			}
		}

		if (board[ranks - 2][0] != 0 && board[ranks - 1][2] != 0) {
			if (Math.abs(board[ranks - 2][0] - board[ranks - 1][2]) != 1) {
				return;
			}
		}

		// case 5: [m-3][n-1] -> [m-1][n-2] and [m-2][n-1] -> [m-1][n-3] do not connect
		if (board[ranks - 3][files - 1] != 0 && board[ranks - 1][files - 2] != 0) {
			if (Math.abs(board[ranks - 3][files - 1] - board[ranks - 1][files - 2]) != 1) {
				return;
			}
		}

		if (board[ranks - 2][files - 1] != 0 && board[ranks - 1][files - 3] != 0) {
			if (Math.abs(board[ranks - 2][files - 1] - board[ranks - 1][files - 3]) != 1) {
				return;
			}
		}

		// mark current square
		board[rank][file] = steps;
		steps++;

		// try all branches from current square
		int[][] moves = warnsdorffMoves(rank, file);
		for (int[] ia : moves) {
			if (board[ia[0]][ia[1]] == 0) {
				structuredTour(ia[0], ia[1]);
			}
		}

		// check if problem is done
		if (tourCompleted()) {
			return;
		}

		// if not child nodes had a successful tour
		board[rank][file] = 0;
		steps--;
	}

	/**
	 * @return a string representation of the board
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	@Override
	public String toString() {
		String ts = "Runtime: " + runtime + "\nPath:\n";
		int maxDigits = Integer.toString(board[0].length * board.length).length() + 1;
		for (int[] ia : board) {
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
	 * an efficient divide and conquer algorithm for the knight's tour problem as
	 * described by Dr. Ian Parberry
	 * 
	 * @since TODO: write date of method completion here
	 * @author TODO: write your name here if you worked on this method
	 */
	public void DNCTour() {
		// TODO: complete this method
	}

	/**
	 * takes 2 structured knight's tours and combines them into 1 larger board
	 * 
	 * @param t1         a structured Knight's tour to be merged with t2
	 * @param t2         a structured Knight's tour to be merged with t1
	 * @param horizontal determines with t1 and t2 are merged by stacking them on
	 *                   top of each other or lining them up side by side
	 * @since TODO: write date of method completion here
	 * @author TODO: write your name here if you worked on this method
	 */
	public void connectTours(Tour t1, Tour t2, boolean horizontal) {
		// TODO: complete this method
	}
}
