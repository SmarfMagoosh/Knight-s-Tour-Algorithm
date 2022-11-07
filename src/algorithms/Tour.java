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
	}

	// Getters
	/**
	 * simple getter method for determining if the board is solved from outside the class
	 * 
	 * @return solvable class member
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public boolean isSolved() {
		return solved;
	}

	/**
	 * simple getter method for getting the time to solve the board from outside the class
	 * 
	 * @return runtime class member
	 * @since 10/20/2022
	 * @author Evan Dreher
	 */
	public long runtime() {
		return runtime;
	}

	// Methods for algorithm
	
	/**
	 * starts solving the board using either divide and conquer method or standard method
	 * @param divide true if you want to use divide and conquer false if you want to use backtracking
	 * @since 11/6/2022
	 * @author Evan Dreher
	 */
	public void start(boolean divide) {
		if(divide) {
			runtime = System.currentTimeMillis();
			Tour t = DNCTour();
			runtime = System.currentTimeMillis() - runtime;
			this.path = t.path;
			this.board = t.board;
			this.solved = t.solved;
		} else {
			runtime = System.currentTimeMillis();
			tour(0, 0);
			runtime = System.currentTimeMillis() - runtime;
		}
	}

	/**
	 * solves the board with backtracking using either structuredTour() or oddTour() based
	 * on the board's number of ranks and files. oddBoard() will only be called if both the 
	 * number of ranks and files are both odd
	 * 
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public void solveBoard() {
		if (ranks % 2 == 1 && files % 2 == 1) {
			oddTour(ranks - 1, files - 1);
		} else {
			structuredTour(0, 0);
		}
	}

	/**
	 * determines if a solution has been found by comparing the number of steps the knight
	 * has made to the total number of squares on the board.
	 * 
	 * @return true if every square has been visited, false otherwise.
	 * @since 10/19/2022
	 * @author Evan Dreher
	 */
	public boolean tourCompleted() {
		return steps == length;
	}

	/**
	 * determines if a partial solution has been found for an odd tour by seeing if the knight
	 * has made a move on each square except one
	 * 
	 * @return true if every square except 1 has been visited, false otherwise
	 * @since 11/4/2022
	 * @author Evan Dreher
	 */
	public boolean oddTourCompleted() {
		return steps == length - 1;
	}

	/**
	 * finds all legal knight-moves from [rank][file]. The following are the legal squares
	 * to which the knigth can hop
	 * [rank + 2][file + 1]
	 * [rank + 2][file - 1]
	 * [rank + 1][file + 2]
	 * [rank + 1][file - 2]
	 * [rank - 1][file + 2]
	 * [rank - 1][file - 2]
	 * [rank - 2][file + 1]
	 * [rank - 2][file - 1]
	 * assuming each of those squares is contained within the board
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
	 * algorithm in such a way that the tour will be "structured" as described by
	 * Dr. Ian Parberry
	 * 
	 * A structured knight's tour has the following formation in each corner
	 * 
	 * 1 |A |X |
	 * ---------
	 * 2 |X |C |
	 * ---------
	 * B |1 |2 |
	 * 
	 * where the two 1s connect by a knight's move, the two 2s connect by a knight's move,
	 * ABC connect by two knight's move in that order or the opposite order and the "B"
	 * square is a corner, and the X's are arbitrary knight's moves. 
	 * The formation here show the bottom left corner, but it is rotated
	 * to fit other corners.
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
	 * board where both height and width are odd. see structureTour() for a description of
	 * a structured knight's tour.
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
	 * an efficient algorithm for solving the knight's tours the recursivle splits
	 * the board into smaller subproblems that can be solved and merged as described
	 * by Dr. Ian Parberry.
	 * 
	 * @return a solved knight's tour
	 * @since 11/6/2022
	 * @author Christian Previtali
	 * @author Evan Dreher
	 */
	public Tour DNCTour() {
		Tour kt = null;
		int file1 = 0;
		int file2 = 0;
		int rank1 = 0;
		int rank2 = 0;
		// Base Cases
		if (this.ranks < 10 && this.files < 10) {
			solveBoard();
			return this;
		} else {
			if (this.ranks % 2 != 0 && this.files % 2 != 0) {
				rank2 = ((ranks - 1) / 2) + 1;
				rank1 = (ranks - 1) / 2;
				file2 = ((files - 1) / 2) + 1;
				file1 = (files - 1) / 2;
				if (rank1 % 2 != 0) {
					int temp = rank1;
					rank1 = rank2;
					rank2 = temp;
					temp = file1;
					file1 = file2;
					file2 = temp;
				}
			} else if (ranks % 2 != 0) {
				rank1 = ((ranks - 1) / 2) + 1;
				rank2 = (ranks - 1) / 2;
				file1 = (files) / 2;
				file2 = (files) / 2;
			} else if (files % 2 != 0) {
				file1 = ((files - 1) / 2) + 1;
				file2 = (files - 1) / 2;
				rank1 = (ranks) / 2;
				rank2 = (ranks) / 2;
			} else {
				file1 = (files) / 2;
				file2 = (files) / 2;
				rank1 = (ranks) / 2;
				rank2 = (ranks) / 2;
			}

			Tour k1 = new Tour(rank2, file2);
			Tour k2 = new Tour(rank2, file1);
			Tour k3 = new Tour(rank1, file1);
			Tour k4 = new Tour(rank1, file2);
			k1 = k1.DNCTour();
			k2 = k2.DNCTour();
			k3 = k3.DNCTour();
			k4 = k4.DNCTour();
			kt = joinTours(k1, k2, k3, k4);
		}
		return kt;
	}

	/**
	 * takes 4 structured knight's tours and combines them into 1 larger structured
	 * knight's tour in the following pattern 
	 * t1 | t2 
	 * ------- 
	 * t4 | t3
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
		if (t1.ranks % 2 == 1 && t1.files % 2 == 1) {
			while (!(t1.path.get(0).getX() == 2 && t1.path.get(0).getY() == 1)) {
				t1.path.add(t1.path.remove());
			}
			t1.path.add(t1.path.remove());
		}

		Move current = t1.path.removeLast();
		while (current.getX() != t1.ranks - 2 || current.getY() != t1.files - 1) {
			bigger.path.add(current);
			current = t1.path.removeLast();
		}
		bigger.path.add(current);

		// fill in top right

		// reorder list so entry square is in front
		while (t2.path.get(0).getX() != t2.ranks - 3 || t2.path.get(0).getY() != 1) {
			t2.path.add(t2.path.remove());
		}

		// add path to larger board
		if (t2.board[t2.ranks - 3][1] < t2.board[t2.ranks - 1][0]) {
			t2.path.add(t2.path.remove());
			while (t2.path.size() > 0) {
				current = t2.path.removeLast();
				current.setY(current.getY() + t1.files);
				bigger.path.add(current);
			}
		} else {
			while (t2.path.size() > 0) {
				current = t2.path.remove();
				current.setY(current.getY() + t1.files);
				bigger.path.add(current);
			}
		}

		// fill in bottom right

		// reorder list so entry square is in front
		while (t3.path.get(0).getX() != 0 || t3.path.get(0).getY() != 2) {
			t3.path.add(t3.path.remove());
		}

		// add path to larger board
		if (t3.board[0][2] > t3.board[1][0]) {
			while (t3.path.size() > 0) {
				current = t3.path.remove();
				current.setY(current.getY() + t1.files);
				current.setX(current.getX() + t1.ranks);
				bigger.path.add(current);
			}
		} else {
			t3.path.add(t3.path.remove());
			while (t3.path.size() > 0) {
				current = t3.path.removeLast();
				current.setY(current.getY() + t1.files);
				current.setX(current.getX() + t1.ranks);
				bigger.path.add(current);
			}
		}
		
		// fill in bottom left
		
		// reorder path so entry square is at front
		while (t4.path.get(0).getX() != 2 || t4.path.get(0).getY() != t4.files - 2) {
			t4.path.add(t4.path.remove());
		}
		
		// add path to larger board
		if (t4.board[2][t4.files - 2] > t4.board[0][t4.files - 1]) {
			while (t4.path.size() > 0) {
				current = t4.path.remove();
				current.setX(current.getX() + t1.ranks);
				bigger.path.add(current);
			}
		} else {
			t4.path.add(t4.path.remove());
			while (t4.path.size() > 0) {
				current = t4.path.removeLast();
				current.setX(current.getX() + t1.ranks);
				bigger.path.add(current);
			}
		}
		
		// fill in remaining top left
		while(t1.path.size() > 0) {
			bigger.path.add(t1.path.removeLast());
		}
		
		// fill in top left corner if there was an oddboard in the board
		if(bigger.path.get(0).getX() != 0 || bigger.path.get(0).getY() != 0) {
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
		String ts = "Sixe: " + ranks + " x " + files + "\nRuntime: " + runtime + "\nPath:\n";
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
}
