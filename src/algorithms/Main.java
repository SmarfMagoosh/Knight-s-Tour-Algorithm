package algorithms;

public class Main {
	public static void main(String[] args) {
		int threeTen = testBoard(10, 10, 10);
		System.out.println(threeTen);
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
