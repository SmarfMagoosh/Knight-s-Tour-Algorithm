package algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("fastOutput.txt");
		PrintWriter writer = new PrintWriter(f);

		for (int i = 16; i <= 256; i+=16) {
			int total = 0;
			for(int j = 0; j < 5; j++) {
				Tour t = new Tour(i);
				try {
					t.start(true);
				} catch (NoSuchElementException nsee) {
					System.out.println("uh oh you found an edge case!");
				}
				total += t.runtime();
				System.out.println(i + " x " + i + " board number " + (j + 1) + " complete");
			}
			int avg = total / 5;
			writer.println("average run time for " + i + " x " + i + " board: " + avg);
			writer.flush();
		}
		writer.close();
		
		File f2 = new File("slowOutput.txt");
		PrintWriter writer2 = new PrintWriter(f2);
		
		for(int i = 5; i < 12; i++) {
			int total = 0;
			for(int j = 0; j < 5; j++) {
				Tour t = new Tour(i);
				t.start(false);
				total += t.runtime();
				System.out.println(i + " x " + i + " board number " + (j + 1) + " complete");
			}
			int avg = total / 5;
			writer2.println("average run time for " + i + " x " + i + " board: " + avg);
			writer2.flush();
		}
		writer2.close();
	}
}
