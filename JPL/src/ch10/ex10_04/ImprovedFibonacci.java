package ch10.ex10_04;

/* ch01.ex01_13のものを修正 */
public class ImprovedFibonacci {

	static final int MIN_INDEX = 1;
	static final int MAX_INDEX = 9;
	static final String MARK = " *";

	public static void main(String[] args) {
		
		int i = MAX_INDEX;
		while (i >= MIN_INDEX) {
			int result = calculateFibonacciNumber(i);
			if (result % 2 == 0) {
				System.out.println(i + ": " + result + MARK);
			} else {
				System.out.println(i + ": " + result);
			}
			i--;
		}
				
//		int i = MAX_INDEX;
//		assert (MIN_INDEX < MAX_INDEX);
//		do {
//			int result = calculateFibonacciNumber(i);
//			if (result % 2 == 0) {
//				System.out.println(i + ": " + result + MARK);
//			} else {
//				System.out.println(i + ": " + result);
//			}
//			i--;
//		} while (i >= MIN_INDEX);
		
//		for (int i = MAX_INDEX; i >= MIN_INDEX; i--) {
//			int result = calculateFibonacciNumber(i);
//			if (result % 2 == 0) {
//				System.out.println(i + ": " + result + MARK);
//			} else {
//				System.out.println(i + ": " + result);
//			}
//		}
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
