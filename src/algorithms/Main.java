package algorithms;

public class Main {

	public static void main(String[] args) {
		Tour kt = new Tour(5, 7);
		kt.start();
		System.out.println(kt);
		System.out.println(kt.runtime());
	}
}
