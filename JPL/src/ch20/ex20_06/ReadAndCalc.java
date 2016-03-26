/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_06;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken Miura
 *
 */
public final class ReadAndCalc {

	private final Map<String, Double> nameAndValue = new HashMap<>();
	
	private static final String WORD1 = "abc";
	private static final String WORD2 = "def";
	private static final String WORD3 = "ghi";
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	//表示テスト
	public static void main(String[] args) throws IOException {
		String s = WORD1 + " + 1.1 " + WORD2 + " - 1.2 " + WORD3 + " = 1.3";
		StringReader reader = new StringReader(s);
		ReadAndCalc rac = new ReadAndCalc();
		rac.readAndCalc(reader);
	}

	private void readAndCalc (Reader source) throws IOException {
		StreamTokenizer in = new StreamTokenizer(source);
		in.wordChars('a', 'z');
		in.wordChars('A', 'Z');
		in.wordChars(128+32, 255);
		in.parseNumbers();
		
		String name = null;
		int op = 0;
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			if (in.ttype == StreamTokenizer.TT_WORD) {
				nameAndValue.putIfAbsent(in.sval, 0.0);
				name = in.sval;
			} else if (in.ttype == '+' || in.ttype == '-' || in.ttype == '=') {
				op = in.ttype;
			} else {
				Double value = nameAndValue.get(name);
				if (value == null) {
					throw new IOException("bad name");
				}
				switch (op) {
				case '+':
					nameAndValue.put(name, value + Double.valueOf(in.nval));
					break;
				case '-':
					nameAndValue.put(name, value - Double.valueOf(in.nval));
					break;
				case '=':
					nameAndValue.put(name, Double.valueOf(in.nval));
					break;
				default:
					throw new IOException("bad op");
				}
			}
		}
		System.out.println(nameAndValue);
	}
}
