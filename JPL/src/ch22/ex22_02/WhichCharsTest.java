/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_02;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class WhichCharsTest {

	/**
	 * {@link ch22.ex22_02.WhichChars#toString()} のためのテスト・メソッド。
	 */
	@Test
	public void testToString() {
		WhichChars wc = new WhichChars("Testing 1 2 3");
		final String actual = wc.toString();
		final String expected = "[ 123Teginst]";
		if (!actual.equals(expected)) {
			fail();
		}
	}

}
