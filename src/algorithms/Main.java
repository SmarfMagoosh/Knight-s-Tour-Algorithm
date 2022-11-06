package algorithms;

import java.io.FileNotFoundException;

public class Main {
<<<<<<< Updated upstream
	public static void main(String[] args) throws FileNotFoundException {
		Tour kt = new Tour(10, 8);
		kt.start();
		System.out.println(kt);
=======
	public static void main(String[] args) {
		Tour t1 = new Tour(6);
		Tour t2 = new Tour(6);
		Tour t3 = new Tour(6);
		Tour t4 = new Tour(6);
		t1.solveBoard();
		t2.solveBoard();
		t3.solveBoard();
		t4.solveBoard();
		Tour merged = Tour.joinTours(t1, t2, t3, t4);
		System.out.println(merged);
>>>>>>> Stashed changes
	}
}
