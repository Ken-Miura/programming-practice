/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_11;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ken Miura
 *
 */
public final class ReadCSVTable {

	public static List<String[]> readCSVTable(Reader source, int numOfCells)
			throws IOException {
		
		StreamTokenizer tokenizer = new StreamTokenizer(source);
		tokenizer.ordinaryChars('0', '9');
		tokenizer.ordinaryChar('.');
		tokenizer.ordinaryChar('-');
		tokenizer.ordinaryChar(' ');
		tokenizer.wordChars(0, 0x7F);
		tokenizer.ordinaryChar(',');
		tokenizer.eolIsSignificant(true);
		tokenizer.whitespaceChars(',', ',');
		tokenizer.whitespaceChars('\n', '\n');
		tokenizer.whitespaceChars('\r', '\r');
		List<String[]> cells = new ArrayList<>();
		int nextToken;
		while ((nextToken=tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {
			String[] record = new String[numOfCells];
			for (int i=0; 
				(i<numOfCells) && (nextToken!=StreamTokenizer.TT_EOL); 
				i++, nextToken=tokenizer.nextToken()) {
					record[i] = tokenizer.sval;
			}
			cells.add(record);
		}
		return cells;
	}
}
