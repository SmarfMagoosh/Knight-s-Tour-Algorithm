package algorithms;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> results = new ArrayList<String>();
		long tests = 1;
		
		// 3 x 10 boards
		long total = 0;
		for(int i = 0; i < tests; i++) {
			Tour kt = new Tour(5, 6);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		long avg = total / tests;
		results.add("Average runtime for 5 x 6 board: " + avg + " ms");
		
//		// 5 x 6 boards
//		total = 0;
//		for(int i = 0; i < tests; i++) {
//			Tour kt = new Tour(5, 6);
//			kt.start();
//			total += kt.runtime();
//			System.out.println(kt);
//		}
//		avg = total / tests;
//		results.add("Average runtime for 5 x 6 board: " + avg + " ms");
//		
//		// 5 x 8 boards
//		total = 0;
//		for(int i = 0; i < tests; i++) {
//			Tour kt = new Tour(5, 8);
//			kt.start();
//			total += kt.runtime();
//			System.out.println(kt);
//		}
//		avg = total / tests;
//		results.add("Average runtime for 5 x 8 board: " + avg + " ms");
//		
//		// 5 x 10 boards
//		total = 0;
//		for(int i = 0; i < tests; i++) {
//			Tour kt = new Tour(5, 10);
//			kt.start();
//			total += kt.runtime();
//			System.out.println(kt);
//		}
//		avg = total / tests;
//		results.add("Average runtime for 5 x 10 board: " + avg + " ms");
//		
//		// 6 x 6 boards
//		total = 0;
//		for(int i = 0; i < tests; i++) {
//			Tour kt = new Tour(6);
//			kt.start();
//			total += kt.runtime();
//			System.out.println(kt);
//		}
//		avg = total / tests;
//		results.add("Average runtime for 6 x 6 board: " + avg + " ms");
//		
//		// 6 x 7 boards
//		total = 0;
//		for(int i = 0; i < tests; i++) {
//			Tour kt = new Tour(6, 7);
//			kt.start();
//			total += kt.runtime();
//			System.out.println(kt);
//		}
//		avg = total / tests;
//		results.add("Average runtime for 6 x 7 board: " + avg + " ms");
//		
//		// print results
//		for(String s : results) {
//			System.out.println(s);
//		}
	}
}
