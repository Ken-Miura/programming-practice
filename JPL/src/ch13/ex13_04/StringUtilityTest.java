/* Copyright (C) 2015 Ken Miura */
package ch13.ex13_04;

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
	
	@Test
	public void 区切り文字を正しく生成できているかの確認() {
		String[] test = { "<a>", "<b>", "<c>" };
		String from = "aghha<a><b>aaaaaahaghreahaa<c>";
		String[] result = StringUtility.delimitedString(from, '<', '>');
		
		for (int i=0; i<test.length; i++) {
			if (!test[i].equals(result[i])) {
				fail();
			}
		}
	}
	
	@Test
	public void 区切り文字がないとき空配列を返すかの確認() {
		String from = "aghha<a><b>aaaaaahaghreahaa<c>";
		String[] result1 = StringUtility.delimitedString(from, '{', '>');
		assertEquals(0, result1.length, 0);
		
		String[] result2 = StringUtility.delimitedString(from, '<', '}');
		assertEquals(0, result2.length, 0);
		
		String[] result3 = StringUtility.delimitedString(from, '{', '}');
		assertEquals(0, result3.length, 0);
	}
	
	@Test(expected=java.text.ParseException.class)
	public void 入力行が１つ単語だけときParseExceptionをスローするかの確認() throws java.text.ParseException {
		String test = "Boolean ";
		StringUtility.displayValues(test);
	}
	
	@Test(expected=java.text.ParseException.class)
	public void 入力行が3つ以上の単語を含むときParseExceptionをスローするかの確認() throws java.text.ParseException {
		String test = "Boolean true false";
		StringUtility.displayValues(test);
	}
	
	@Test(expected=java.text.ParseException.class)
	public void 存在しない型名を入力したときParseExceptionをスローするかの確認() throws java.text.ParseException {
		String test = "boolean true";
		StringUtility.displayValues(test);
	}
}
