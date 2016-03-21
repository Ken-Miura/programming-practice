/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_02;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class TestTranslateByte {

	/**
	 * {@link ch20.ex20_02.TranslateByte#read()} のためのテスト・メソッド。
	 */
	@Test
	public void testRead() {
		byte from = 65;
		byte to = 97;
		byte[] buf = new byte[1];
		buf[0] = from;
		InputStream in = new ByteArrayInputStream(buf);
		TranslateByte tb = new TranslateByte(in, from, to);
		
		byte expected = to;
		try {
			byte actual = (byte) tb.read();
			if (expected != actual) {
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (tb != null) {
				try {
					tb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
