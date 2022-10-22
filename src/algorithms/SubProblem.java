package algorithms;

/**
 * Defines a subproblem of the knight's tour so that larger problems may be solved dynamically
 * @author Evan Dreher
 */
public class SubProblem {
	private int[][] solution;
	private int size;
	
	public SubProblem(int[][] solution) {
		this.solution = solution;
	}
	
	public int[][] solveSubProblem(int currSteps, boolean flipped, int rotation) {
		for(int[] ia : solution) {
			for(int i : ia) {
				i += currSteps;
			}
		}
		currSteps += size;
		return solution;
	}
}
