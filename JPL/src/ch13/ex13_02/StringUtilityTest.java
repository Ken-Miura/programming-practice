/* Copyright (C) 2015 Ken Miura */
package ch13.ex13_02;

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

	@Test
	public void 文字列中の文字列を正しく数えられているかの確認() {
		String test1 = "abcdgawsg";
		int expected1 = 2;
		assertEquals(expected1, StringUtility.countString("a", test1), 0);
		
		String test2 = "aaaaaaaaaaaaa";
		int expected2 = 0;
		assertEquals(expected2, StringUtility.countString("b", test2), 0);
		
		String test3 = "abcdefg";
		int expected3 = 1;
		assertEquals(expected3, StringUtility.countString("cde", test3), 0);
				
	}
}
