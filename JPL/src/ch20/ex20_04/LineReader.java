/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_04;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Ken Miura
 *
 */
public final class LineReader extends FilterReader {

	/**
	 * @param in
	 */
	protected LineReader(Reader in) {
		super(in);
	}

	public int readLine(char[] output, int offset) throws IOException {
		int ch;
		int count = 0;
		while ((ch = read()) != -1 && ch != '\n'){
			output[offset] = (char) ch;
			offset++;
			count++;
		}
		return count;
	}
}
