/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author Ken Miura
 *
 */
public final class ReadCSVTable {

	public static List<String[]> readCSVTable(Readable source, int numOfCells) 
		throws IOException {
		
		@SuppressWarnings("resource") // スキャナは勝手に閉じないようにする
		Scanner in = new Scanner(source);
		List<String[]> vals = new ArrayList<>();
		StringBuilder sb = new StringBuilder("^(.*)");
		for (int i=0; i<(numOfCells-1); i++) {
			sb.append(",(.*)");
		}
		Pattern pat = Pattern.compile(sb.toString(), Pattern.MULTILINE);
		while (in.hasNext()) {
			String line = in.findInLine(pat);
			if (line != null) {
				String[] cells = new String[numOfCells];
				MatchResult match = in.match();
				for (int i=0; i<numOfCells; i++) {
					cells[i] = match.group(i+1);
				}
				vals.add(cells);
				in.nextLine();
			} else {
				throw new IOException("input format error");
			}
		}
		
		IOException ex = in.ioException();
		if (ex != null) {
			throw ex;
		}
		return vals;
	}
}
