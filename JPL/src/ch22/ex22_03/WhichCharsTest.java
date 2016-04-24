/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_03;

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
		WhichChars wc = new WhichChars("あ");
		final String actual = wc.toString();
		final String expected = "[あ]";
		if (!actual.equals(expected)) {
			fail();
		}
	}

}
