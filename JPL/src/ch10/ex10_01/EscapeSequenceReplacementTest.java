package ch10.ex10_01;

import static org.junit.Assert.*;

import org.junit.Test;

public class EscapeSequenceReplacementTest {

	@Test
	public void 特殊文字がJava言語での表現に置き換えられていることの確認() {
		String input = "\n\t\b\r\f\\\'\"";
		String expected = "\\n\\t\\b\\r\\f\\\\\\'\\\"";
		String actual = EscapeSequenceReplacement.replaceEscapeSequences(input);
		if (!expected.equals(actual)) {
			fail();
		}
	}
}
