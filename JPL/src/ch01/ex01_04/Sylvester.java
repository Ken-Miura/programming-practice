package ch01.ex01_04;

public class Sylvester {
	private static final int MAX = 10000;
	private static final String TITLE = "Sylvester's sequence";

	/** 値がMAX未満のシルベスターの数列を表示する */
	public static void main(String[] args) {
		System.out.println(TITLE);
		int a1 = 2; /* 初項 */
		int an = a1; /* 一般項 */
		while (an < MAX) {
			System.out.println(an);
			an = (an * an) - an + 1;
		}
	}
}
