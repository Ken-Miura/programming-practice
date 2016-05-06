/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_13;

import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import ch22.ex22_13.Attr.Attributed;
import ch22.ex22_13.Attr.AttributedImpl;

/**
 * @author Ken Miura
 *
 */
public final class AttributeReader {

	public static Attributed readAttrs (Reader source) throws IOException {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(source);
		Pattern pat = Pattern.compile("^(.*)=(.*)$", Pattern.MULTILINE);
		Attributed attrs = new AttributedImpl();
		while (in.hasNextLine()) {
			String line = in.findInLine(pat);
			if (line != null) {
				if (line.split("=").length != 2) {
					throw new IOException("input format error");
				}
				MatchResult result = in.match();
				attrs.add(new Attr(result.group(1), result.group(2)));
				try {
					in.nextLine();					
				} catch (NoSuchElementException e) {
					break;
				}
			} else {
				throw new IOException("input format error");
			}
		}
		IOException ex = in.ioException();
		if (ex != null) {
			throw ex;
		}
		return attrs;
	}
}
