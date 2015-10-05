package ch01.ex01_13;

/* ch01.ex01_07.ImprovedFibonacciを修正して作成  */
public class ImprovedFibonacci {

	static final int MIN_INDEX = 1;
	static final int MAX_INDEX = 9;
	static final String MARK = " *";

	public static void main(String[] args) {

		for (int i = MIN_INDEX; i <= MAX_INDEX; i++) {
			int result = calculateFibonacciNumber(i);
			if (result % 2 == 0) {
				System.out.printf("%d: %d%s%n", i, result, MARK);
			} else {
				System.out.printf("%d: %d%n", i, result);
			}
		}
	}

	private static int calculateFibonacciNumber(int index) {
		assert (index >= MIN_INDEX);
		double an = 0.0;
		final double root5 = Math.sqrt(5);

		an = (Math.pow((1 + root5) / 2.0, index) - Math.pow((1 - root5) / 2.0,
				index)) / root5; /* Fibonacci数列の一般項の公式 */

		return (int) an;
	}
}
