/* Copyright (C) 2015 Ken Miura */
package ch13.ex13_01;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class StringUtilityTest {

	@Test
	public void 文字列中の文字を正しく数えられているかの確認() {
		String test1 = "abcdgawsg";
		int expected1 = 2;
		assertEquals(expected1, StringUtility.countChar('a', test1), 0);
		
		String test2 = "aaaaaaaaaaaaa";
		int expected2 = 0;
		assertEquals(expected2, StringUtility.countChar('b', test2), 0);
	}

}
