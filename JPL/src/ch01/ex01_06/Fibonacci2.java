package ch01.ex01_06;

public class Fibonacci2 {
	static final int MAX = 50;
	static final String TITLE = "Fibonacci number";

	/** 値がMAX未満のフィボナッチ数列を表示する */
	public static void main(String[] args) {
		System.out.println(TITLE);
		int lo = 1;
		int hi = 1;
		System.out.println(lo);
		while (hi < MAX) {
			System.out.println(hi);
			hi = lo + hi;
			lo = hi - lo;
		}
	}
}
