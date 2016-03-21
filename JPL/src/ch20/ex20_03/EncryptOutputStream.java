/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_03;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Ken Miura
 *
 */
public final class EncryptOutputStream extends FilterOutputStream {

	private final byte key;
	
	/**
	 * @param out
	 */
	public EncryptOutputStream(OutputStream out, byte key) {
		super(out);
		this.key = key;
	}

	@Override
	public void write(int b) throws IOException {
		int encrypted = b ^ key;
		super.write(encrypted);
	}
}
