/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_03;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class TestEncryptOutputStream {

	/**
	 * {@link ch20.ex20_03.EncryptOutputStream#write(int)} のためのテスト・メソッド。
	 */
	@Test
	public void testWriteInt() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte key = 0x01;
		EncryptOutputStream enc = new EncryptOutputStream(out, key);
		
		int data = 1;
		int expected = 0;
		try {
			enc.write(data);
			byte actual = out.toByteArray()[0];
			if (actual != expected) {
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (enc != null) {
				try {
					enc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
