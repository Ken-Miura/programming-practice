/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_05;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author Ken Miura
 *
 */
public final class FindWord {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length != 2) {
			throw new IllegalArgumentException("need word and file");
		}

		final String word = args[0];
		try (LineNumberReader reaader = new LineNumberReader(new FileReader(args[1]))) {
			String line = null;
			while ((line = reaader.readLine()) != null) {
				if (line.contains(word)) {
					System.out.println(reaader.getLineNumber() + ": " + line);
				}
			}
		}
	}

}
