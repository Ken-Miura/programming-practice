package ch01.ex01_09;

public class Fibonacci2 {
	private static final int MAX = 50;
	private static final String TITLE = "Fibonacci number";

	/** 値がMAX未満のフィボナッチ数列を表示する */
	public static void main(String[] args) {
		System.out.println(TITLE);
		int max_index = 10;
		int[] fibonacciNumbers = new int[max_index];

		int i = 0;
		int result = calculateFibonacciNumber(i + 1); /* 初項 */
		while (result < MAX) {
			if (i < max_index) {
				fibonacciNumbers[i] = result;
			} else {
				/* 要素数が足りない場合、要素数を一つ拡張した配列に置き換える */
				fibonacciNumbers = copyAndStretchArray(fibonacciNumbers);
				fibonacciNumbers[max_index] = result;
				max_index++;
			}
			i++;
			result = calculateFibonacciNumber(i + 1);
		}

		for (int n : fibonacciNumbers) {
			if (n != 0) { /* Fibonacci数列に0はあり得ないので除外 */
				System.out.println(n);
			}
		}
	}

	private static int calculateFibonacciNumber(int index) {
		assert (index >= 1); // ここでは数列の初項の添え字は1からに設定
		double an = 0.0;
		final double root5 = Math.sqrt(5);

		an = (Math.pow((1 + root5) / 2.0, index) - Math.pow((1 - root5) / 2.0,
				index)) / root5; /* Fibonacci数列の一般項の公式 */

		return (int) an;
	}

	private static int[] copyAndStretchArray(int[] array) {
		int[] tempArray = new int[array.length + 1];
		System.arraycopy(array, 0, tempArray, 0, array.length);
		return tempArray;
	}
}
