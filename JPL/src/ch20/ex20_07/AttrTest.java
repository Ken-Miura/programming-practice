/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_07;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class AttrTest {

	@Test
	public void testAttr() {
		String expectedName = "name";
		String expectedValue = "value";
		Attr attr1 = new Attr(expectedName, expectedValue);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			attr1.writeAttr(out);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
		try (InputStream in = new ByteArrayInputStream(out.toByteArray())) {
			Attr attr2 = new Attr(in);
			if (!(attr2.getName().equals(expectedName) 
					&& attr2.getValue().equals(expectedValue))) {
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
