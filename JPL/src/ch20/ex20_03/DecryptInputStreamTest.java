/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_03;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class DecryptInputStreamTest {

	/**
	 * {@link ch20.ex20_03.DecryptInputStream#read()} のためのテスト・メソッド。
	 */
	@Test
	public void testRead() {
		byte[] data = new byte[1];
		data[0] = 0;
		InputStream in = new ByteArrayInputStream(data);
		final byte key = 0x01;
		DecryptInputStream dec = new DecryptInputStream(in, key);

		try {
			int actual = dec.read();
			int expected = 1;
			if (actual != expected) {
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (dec != null) {
				try {
					dec.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
