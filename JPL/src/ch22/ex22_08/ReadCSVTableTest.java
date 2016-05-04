/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_08;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class ReadCSVTableTest {

	@Test
	public void test3() throws IOException {
		// 入力の終わりに空行なし
		String testData = "1, 2, 3\n4, 5, 6";
		StringReader reader = new StringReader(testData);
		
		List<String[]> result = null;
		try {
			result = ReadCSVTable.readCSVTable(reader, 3);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
		List<String[]> expected = new ArrayList<>();
		expected.add(new String[]{ "1", " 2", " 3" });
		expected.add(new String[]{ "4", " 5", " 6" });
		
		for (int i=0; i<result.size(); i++) {
			String[] record = result.get(i);
			String[] expectedRecord = expected.get(i);
			for (int j=0; j<record.length; j++) {
				if (!(record[j].equals(expectedRecord[j]))) {
					fail();
				}
			}
		}
	}
	
	
	@Test(expected=IOException.class)
	public void test2() throws IOException {
		// 入力フィールド4つにつきカンマ4つのデータ
		String testData = "1, 2, 3, 4,\n";
		StringReader reader = new StringReader(testData);
		
		ReadCSVTable.readCSVTable(reader, 4);
	}
	
	@Test
	public void test1() {
		// 入力の終わりに空行
		String testData = "1, 2, 3\n4, 5, 6\n";
		StringReader reader = new StringReader(testData);
		
		List<String[]> result = null;
		try {
			result = ReadCSVTable.readCSVTable(reader, 3);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
		List<String[]> expected = new ArrayList<>();
		expected.add(new String[]{ "1", " 2", " 3" });
		expected.add(new String[]{ "4", " 5", " 6" });
		
		for (int i=0; i<result.size(); i++) {
			String[] record = result.get(i);
			String[] expectedRecord = expected.get(i);
			for (int j=0; j<record.length; j++) {
				if (!(record[j].equals(expectedRecord[j]))) {
					fail();
				}
			}
		}
	}
}
