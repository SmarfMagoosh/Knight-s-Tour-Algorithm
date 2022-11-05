package algorithms;

public class Main {
	public static void main(String[] args) {
		Tour t1 = new Tour(5,6);
		Tour t2 = new Tour(5,6);
		Tour t3 = new Tour(5,6);
		Tour t4 = new Tour(5,6);
		t1.start();
		t2.start();	
		t3.start();
		t4.start();
		Tour big = Tour.joinTours(t1, t2, t3, t4);
		System.out.println(big);
	}
	
	public static int testBoard(int length, int width, int tests) {
		int total = 0;
		for(int i = 0; i < tests; i++) {
			Tour kt = new Tour(length, width);
			kt.start();
			System.out.println(kt);
			total += kt.runtime();
			System.out.println("Test " + (i + 1) + " complete.");
		}
		return total / tests;
	}
}
