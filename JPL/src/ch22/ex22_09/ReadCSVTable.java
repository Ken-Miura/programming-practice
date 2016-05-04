/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_09;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author Ken Miura
 *
 */
public final class ReadCSVTable {

	private static final String[] CSV_FIELD_PATTERNS
		= { "(.*)", "([^,]*)", "(.*?)", "([^,]*?)" };
	
	public static void main (String... args) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		final int numOfCells = 3;
		sb.append('a');
		sb.append(',');
		for (int j=0; j<100000; j++) {
			sb.append('a');
		}
		sb.append(',');
		sb.append('a');
		String testData1 = sb.toString();
		System.out.println("long word between commas");
		for (int i=0; i<CSV_FIELD_PATTERNS.length; i++) {
			calcParsingTime(testData1, numOfCells, i);
		}
		
		System.out.println();
		
		String testData2 = "a,a,a";
		System.out.println("short word between commas");
		for (int i=0; i<CSV_FIELD_PATTERNS.length; i++) {
			calcParsingTime(testData2, numOfCells, i);
		}
	}
	
	private static void calcParsingTime(String record, int numOfCells, int parseNum) throws IOException {
		String expression = CSV_FIELD_PATTERNS[parseNum];
		long start = System.nanoTime();
		readCSVTable(new StringReader(record), numOfCells, parseNum);
		System.out.printf("result: %+10d ns, field expression: %s%n", (System.nanoTime() - start), expression);
	}

	public static List<String[]> readCSVTable(Readable source, int numOfCells, int patternNum) 
		throws IOException {
		
		@SuppressWarnings("resource") // スキャナは勝手に閉じないようにする
		Scanner in = new Scanner(source);
		List<String[]> vals = new ArrayList<>();
		StringBuilder sb = new StringBuilder("^" + CSV_FIELD_PATTERNS[patternNum]);
		for (int i=0; i<(numOfCells-1); i++) {
			sb.append("," + CSV_FIELD_PATTERNS[patternNum]);
		}
		sb.append("$");
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
		return vals;
	}
}
