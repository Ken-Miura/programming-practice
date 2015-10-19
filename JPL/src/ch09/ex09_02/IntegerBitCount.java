package ch09.ex09_02;

public final class IntegerBitCount {

	private IntegerBitCount() {
		throw new AssertionError("must not instanciate this class");
	}
	
	/**
	 * 
	 * @param input int型の整数
	 * @return　inputをバイナリで表したとき、1を含んでいる数
	 */
	public static int countIntegerBit(int input) {
		int count = 0;
		while (input != 0) {
			int mask = input & 0x00000001;
			if (mask == 1) {
				count++;	
			}
			input = input >>> 1;
		}
		return count;
	}
}
