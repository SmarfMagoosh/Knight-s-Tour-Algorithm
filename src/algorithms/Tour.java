package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

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
	private LinkedList<Move> path;

	// efficiency members
	private boolean solved;
	private long runtime;

	// size members
	private int length;
	private int ranks;
	private int files;

	// Constructors

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
		path = new LinkedList<>();

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
		path = new LinkedList<>();

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

	// Getters
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

	// Methods for algorithm
	public void start() {
		runtime = System.currentTimeMillis();
		Tour t = this.DNCTour();
		runtime = System.currentTimeMillis() - runtime;
		this.board = t.board;
		this.files = t.files;
		this.ranks = t.ranks;
		this.path = t.path;
	}
	
	/**
	 * makes the first call to the recursive solution finder and tracks the time to
	 * find a solution
	 * 
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void solveBoard() {
		if(ranks % 2 == 1 && files % 2 == 1) {
			oddTour(ranks - 1, files - 1);
		} else {
			structuredTour(0, 0);
		}
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
	 * determines if a partial solution has been found for an odd tour
	 * 
	 * @return true if every square except 1 has been visited, false otherwise
	 * @since 11/4/2022
	 * @author Evan Dreher
	 */
	public boolean oddTourCompleted() {
		return steps == length - 1;
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
	 * calls findMoves() and sorts those moves in ascending order of how many
	 * options there are from the square you move to
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
	 * algorithm, solution is an open knight's tour
	 * 
	 * @param rank the rank that the knight is on
	 * @param file the file that the knight is on
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void tour(int rank, int file) {
		// mark current square
		board[rank][file] = steps;
		steps++;
		path.add(new Move(rank, file));

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
		path.removeLast();
	}

	/**
	 * recursively solves the knight's tour problem using a simple backtracking
	 * algorithm in such a way that the tour will be "structured" ad described by
	 * Dr. Ian Parberry
	 *
	 * @param rank the ranks that the knight is on
	 * @param file the file that the knight is on
	 * @since 11/3/2022
	 * @author Evan Dreher
	 */
	public void structuredTour(int rank, int file) {
		// eliminates incorrect routes before even trying them for an m x n board

		// make sure knight's tour is closed
		if ((rank == 1 && file == 2) || (rank == 2 && file == 1)) {
			if (!(steps == 2 || steps == length - 1)) {
				return;
			}
		}

		// case 1: [0][1] -> [2][0] do not connect and [0][2] -> [1][0] do not connect
		if (board[0][1] != 0 && board[2][0] != 0) {
			if (Math.abs(board[0][1] - board[2][0]) != 1) {
				return;
			}
		}

		if (board[0][2] != 0 && board[1][0] != 0) {
			if (Math.abs(board[0][2] - board[1][0]) != 1) {
				return;
			}
		}

		// case 2: [0][n-2] -> [2][n-1] and [0][n-3] -> [1][n-1]
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

		// case 3: [m-3][0] -> [m-1][1] and [m-2][0] -> [m-1][2] do not connect
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

		// case 4: [m-3][n-1] -> [m-1][n-2] and [m-2][n-1] -> [m-1][n-3] do not connect
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
		path.add(new Move(rank, file));

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
		path.removeLast();
	}
	
	/**
	 * completes a structured closed tour missing only the top left corner on a
	 * board where both height and width are odd
	 * 
	 * @param rank the current rank that the knight is on
	 * @param file the current file that the knight is on
	 * @return a structured closed tour missing only the top left corner.
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public void oddTour(int rank, int file) {

		// top left square should be blank
		if (rank == 0 && file == 0) {
			return;
		}

		// eliminates incorrect routes before even trying them for an m x n board

		// make sure knight's tour is closed
		if ((rank == ranks - 2 && file == files - 3) || (rank == ranks - 3 && file == files - 2)) {
			if (!(steps == 2 || steps == length - 2)) {
				return;
			}
		}
		// case 1: [0][1] -> [2][0] do not connect and [0][2] -> [1][0] do not connect
		if (board[0][1] != 0 && board[2][0] != 0) {
			if (Math.abs(board[0][1] - board[2][0]) != 1) {
				return;
			}
		}

		if (board[0][2] != 0 && board[1][0] != 0) {
			if (Math.abs(board[0][2] - board[1][0]) != 1) {
				return;
			}
		}

		// case 2: [0][n-2] -> [2][n-1] and [0][n-3] -> [1][n-1]
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

		// case 3: [m-3][0] -> [m-1][1] and [m-2][0] -> [m-1][2] do not connect
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

		// case 4: [m-3][n-1] -> [m-1][n-2] and [m-2][n-1] -> [m-1][n-3] do not connect
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
		path.add(new Move(rank, file));

		// try all branches from current square
		int[][] moves = warnsdorffMoves(rank, file);
		for (int[] ia : moves) {
			if (board[ia[0]][ia[1]] == 0) {
				oddTour(ia[0], ia[1]);
			}
		}

		// check if problem is done
		if (oddTourCompleted()) {
			return;
		}

		// if not child nodes had a successful tour
		board[rank][file] = 0;
		steps--;
		path.removeLast();
	}

	/**
	 * takes 4 structured knight's tours and combines them into 1 larger structured
	 * knight's tour in the following patter 
	 * t1 | t2 
	 * ------- 
<<<<<<< Updated upstream
	 * t3 | t4
=======
	 * t4 | t3
>>>>>>> Stashed changes
	 * 
	 * @param t1 a structured Knight's tour to be merged with the others
	 * @param t2 a structured Knight's tour to be merged with the others
	 * @param t3 a structured Knight's tour to be merged with the others
	 * @param t4 a structured Knight's tour to be merged with the others
	 * @return the combined knight's tour
	 * @since 11/5/2022
	 * @author Evan Dreher
	 */
	public static Tour joinTours(Tour t1, Tour t2, Tour t3, Tour t4) {
		// create new board the size of all params combined
		int mergedFiles = t1.files + t2.files;
		int mergedRanks = t1.ranks + t3.ranks;
		Tour bigger = new Tour(mergedRanks, mergedFiles);

		// partially fill in top left

		// build path until we get to exit square
		t1.path.add(t1.path.remove());
		
		// reorder array if its an odd board
		if(t1.ranks % 2 == 1 && t1.files % 2 == 1) {
			while (!(t1.path.get(0).getX() == 2 && t1.path.get(0).getY() == 1)) {
				t1.path.add(t1.path.remove());
			}
			t1.path.add(t1.path.remove());
		}
		
		Move current = t1.path.removeLast();
		while (!(current.getX() == t1.ranks - 2 && current.getY() == t1.files - 1)) {
			bigger.path.add(current);
			current = t1.path.removeLast();
		}
		bigger.path.add(current);
		
		// fill in top right

		// reorder array so entry square is at the beginning
		while (!(t2.path.get(0).getX() == t2.ranks - 3 && t2.path.get(0).getY() == 1)) {
			t2.path.add(t2.path.remove());
		}

		// add all the elements in reverse order adjusted for relative position
		while (t2.path.size() > 0) {
			current = t2.path.remove();
			current.setY(current.getY() + t1.files);
			bigger.path.add(current);
		}

		// fill in bottom right

		// reorder array so entry square is at the end
		while (!(t3.path.get(0).getX() == 0 && t3.path.get(0).getY() == 2)) {
			t3.path.add(t3.path.remove());
		}

		// add all the elements in reverse order adjusted for relative position
		while (t3.path.size() > 0) {
			current = t3.path.remove();
			current.setY(current.getY() + t1.files);
			current.setX(current.getX() + t1.ranks);
			bigger.path.add(current);
		}

		// fill in bottom left

		// reorder array so entry square at the end
		while (!(t4.path.get(0).getX() == 2 && t4.path.get(0).getY() == t4.files - 2)) {
			t4.path.add(t4.path.remove());
		}

		// add all the elements adjusted for relative position
		while (t4.path.size() > 0) {
			current = t4.path.remove();
			current.setX(current.getX() + t1.ranks);
			bigger.path.add(current);
		}

		// complete filling in top left
		while (t1.path.size() > 0) {
			bigger.path.add(t1.path.removeLast());
		}
		
		// add first move if t1 was an odd board
		if(bigger.path.size() == bigger.length - 2) {
			bigger.path.addFirst(new Move(0, 0));
		}

		// print out steps based on path
		ListIterator<Move> it = bigger.path.listIterator(0);
		int count = 1;
		while (it.hasNext()) {
			Move toAdd = it.next();
			bigger.board[toAdd.getX()][toAdd.getY()] = count;
			count++;
		}
		return bigger;
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
	public Tour DNCTour() {
<<<<<<< Updated upstream
		if(ranks <= 13 && files <= 13) {
			solveBoard();
			return this;
		} else {
			// divide into ideal base cases here.
		}
=======
		if(ranks < 10 && files < 10) {
			solveBoard();
			return this;
		}
		// TODO: complete this method
>>>>>>> Stashed changes
		return null;
	}
}
