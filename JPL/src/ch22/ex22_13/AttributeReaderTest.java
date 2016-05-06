/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_13;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import ch22.ex22_13.Attr.Attributed;
import ch22.ex22_13.Attr.AttributedImpl;

/**
 * @author Ken Miura
 *
 */
public class AttributeReaderTest {

	@Test(expected=IOException.class)
	public void test2() throws IOException {
		String test = "name1=value1.1=value1.2\n";
		StringReader reader = new StringReader(test);
		AttributeReader.readAttrs(reader);
	}
	
	@Test
	public void test1() {
		String test = "name1=value1\nname2=value2\nname3=value3\n";
		StringReader reader = new StringReader(test);
		Attributed actual = null;
		try {
			actual = AttributeReader.readAttrs(reader);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
		Attributed expected = new AttributedImpl();
		expected.add(new Attr("name1", "value1"));
		expected.add(new Attr("name2", "value2"));
		expected.add(new Attr("name3", "value3"));
		
		if (!actual.equals(expected)) {
			fail();
		}
	}
}
