package ch01.ex01_10;

/* ch01.ex01_07.ImprovedFibonacciを修正して作成 */
public class ImprovedFibonacci {

	static final int MIN_INDEX = 1;
	static final int MAX_INDEX = 9;
	static final String MARK = " *";

	public static void main(String[] args) {

		int elemNum = MAX_INDEX - MIN_INDEX + 1;
		Element[] elements = new Element[elemNum];
		for (int i = 0; i < elemNum; i++) {
			elements[i] = new Element();
		}

		for (int i = MAX_INDEX; i >= MIN_INDEX; i--) {
			int result = calculateFibonacciNumber(i);
			if (result % 2 == 0) {
				elements[i - MIN_INDEX].setNumber(result);
			} else {
				elements[i - MIN_INDEX].setNumber(result);
			}
		}

		for (int i = 0; i < elemNum; i++) {
			if (elements[i].isEven()) {
				System.out.println((i + MIN_INDEX) + ": " + elements[i].getNumber()
						+ MARK);
			} else {
				System.out.println((i + MIN_INDEX) + ": " + elements[i].getNumber());
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
