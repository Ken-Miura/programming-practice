/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_04;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class LineReaderTest {

	/**
	 * {@link ch20.ex20_04.LineReader#readLine(char[], int)} のためのテスト・メソッド。
	 */
	@Test
	public void testReadLine() {
		String testData = "test\ntest";
		Reader reader = new StringReader(testData);
		LineReader lineReader = new LineReader(reader);
		
		char[] output = new char[20];
		char[] expected = new char[20];
		expected[0] = 't';
		expected[1] = 'e';
		expected[2] = 's';
		expected[3] = 't';
		try {
			lineReader.readLine(output, 0);
			for (int i=0; i<20; i++) {
				if (expected[i] != output[i]) {
					fail();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (lineReader != null) {
				try {
					lineReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
