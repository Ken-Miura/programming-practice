/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_10;

import static org.junit.Assert.fail;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class CommentRemovableParserTest {

	@Test
	public void test1() {
		String test = "token\n# This is a comment line\ntoken2";
		StringReader reader = new StringReader(test);
		
		List<String> actual = CommentRemovableParser.removeComments(reader);
		
		List<String> expected = new ArrayList<>();
		expected.add("token");
		expected.add("token2");
		
		if (actual.size() != expected.size()) {
			fail();
		}
		for (int i=0; i<actual.size(); i++) {
			String word1 = actual.get(i);
			String word2 = expected.get(i);
			if ((!word1.equals(word2))) {
				fail();	
			}
		}
	}
	
	@Test
	public void test2() {
		String test = "token\n# This is a comment line";
		StringReader reader = new StringReader(test);
		
		List<String> actual = CommentRemovableParser.removeComments(reader);
		
		List<String> expected = new ArrayList<>();
		expected.add("token");
		
		if (actual.size() != expected.size()) {
			fail();
		}
		for (int i=0; i<actual.size(); i++) {
			String word1 = actual.get(i);
			String word2 = expected.get(i);
			if ((!word1.equals(word2))) {
				fail();	
			}
		}
	}

	@Test
	public void test3() {
		String test = "# This is a comment line\ntoken2";
		StringReader reader = new StringReader(test);
		
		List<String> actual = CommentRemovableParser.removeComments(reader);
		
		List<String> expected = new ArrayList<>();
		expected.add("token2");
		
		if (actual.size() != expected.size()) {
			fail();
		}
		for (int i=0; i<actual.size(); i++) {
			String word1 = actual.get(i);
			String word2 = expected.get(i);
			if ((!word1.equals(word2))) {
				fail();	
			}
		}
	}
	
	@Test
	public void test4() {
		String test = "token0\ntoken1\n# This is a comment line\ntoken2\ntoken3";
		StringReader reader = new StringReader(test);
		
		List<String> actual = CommentRemovableParser.removeComments(reader);
		
		List<String> expected = new ArrayList<>();
		expected.add("token0\ntoken1");
		expected.add("token2\ntoken3");
		
		if (actual.size() != expected.size()) {
			fail();
		}
		for (int i=0; i<actual.size(); i++) {
			String word1 = actual.get(i);
			String word2 = expected.get(i);
			if ((!word1.equals(word2))) {
				fail();	
			}
		}
	}

}
