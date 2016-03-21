/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_03;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ken Miura
 *
 */
public final class DecryptInputStream extends FilterInputStream {

	private final byte key;
	
	/**
	 * @param in
	 */
	protected DecryptInputStream(InputStream in, byte key) {
		super(in);
		this.key = key;
	}

	@Override
	public int read() throws IOException {
		int encrypted = super.read();
		return encrypted ^ key;
	}
}
