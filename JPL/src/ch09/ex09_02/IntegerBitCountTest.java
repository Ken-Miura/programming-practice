package ch09.ex09_02;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerBitCountTest {

	@Test
	public void int型の整数のビットカウントが正しく得られていることの確認() {
		assertEquals(0, IntegerBitCount.countIntegerBit(0), 0);
		assertEquals(1, IntegerBitCount.countIntegerBit(1), 0);
		assertEquals(1, IntegerBitCount.countIntegerBit(2), 0);
		assertEquals(2, IntegerBitCount.countIntegerBit(3), 0);
		assertEquals(1, IntegerBitCount.countIntegerBit(4), 0);
		assertEquals(2, IntegerBitCount.countIntegerBit(5), 0);
		assertEquals(2, IntegerBitCount.countIntegerBit(6), 0);
		assertEquals(3, IntegerBitCount.countIntegerBit(7), 0);
		assertEquals(1, IntegerBitCount.countIntegerBit(8), 0);
	}

}
