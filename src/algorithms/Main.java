package algorithms;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Tour kt = new Tour(10, 8);
		kt.start();
		System.out.println(kt);
	}
}
