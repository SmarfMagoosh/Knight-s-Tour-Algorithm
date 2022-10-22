package algorithms;

public class Main {

	public static void main(String[] args) {
		Tour kt = new Tour(8);
		kt.start(true);
		System.out.println(kt);
		System.out.println(kt.runtime());
	}
}
