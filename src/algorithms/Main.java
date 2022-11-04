package algorithms;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> results = new ArrayList<String>();
		long tests = 10;

		// 3 x 10 boards
		long total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(3, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		long avg = total / tests;
		results.add("Average runtime for 5 x 6 board: " + avg + " ms");

		// 5 x 6 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(5, 6);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 5 x 6 board: " + avg + " ms");

		// 5 x 8 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(5, 8);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 5 x 8 board: " + avg + " ms");

		// 5 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(5, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 5 x 10 board: " + avg + " ms");

		// 6 x 6 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(6);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 6 board: " + avg + " ms");

		// 6 x 7 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(6, 7);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 7 board: " + avg + " ms");

		// 6 x 8 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(6, 8);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 8 board: " + avg + " ms");

		// 6 x 9 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(6, 9);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 9 board: " + avg + " ms");

		// 6 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(6, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 10 board: " + avg + " ms");

		// 7 x 8 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(7, 8);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 7 x 8 board: " + avg + " ms");

		// 7 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(7, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 7 x 10 board: " + avg + " ms");

		// 8 x 8 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(8);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 8 x 8 board: " + avg + " ms");

		// 8 x 9 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(8, 9);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 8 x 9 board: " + avg + " ms");

		// 8 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(8, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 6 x 7 board: " + avg + " ms");

		// 9 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(9, 10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 9 x 10 board: " + avg + " ms");

		// 10 x 10 boards
		total = 0;
		for (int i = 0; i < tests; i++) {
			Tour kt = new Tour(10);
			kt.start();
			total += kt.runtime();
			System.out.println(kt);
		}
		avg = total / tests;
		results.add("Average runtime for 10 x 10 board: " + avg + " ms");

		// print results
		for (String s : results) {
			System.out.println(s);
		}
	}
}
