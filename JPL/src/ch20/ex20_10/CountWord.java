/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_10;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken Miura
 *
 */
public final class CountWord {
	
	private final Map<String, Integer> wordAndCount = new HashMap<>();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filePath = System.getProperty("user.dir")
				+ File.separator + "src" + File.separator + "ch20"+ File.separator + "ex20_10" + File.separator;
		File file = new File(filePath + "word.txt");
		Reader source = new FileReader(file);
		new CountWord().readAndCount(source);
	}
	
	private void readAndCount (Reader source) throws IOException {
		StreamTokenizer in = new StreamTokenizer(source);
		in.wordChars('a', 'z');
		in.wordChars('A', 'Z');
		in.wordChars(128+32, 255);
		
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			if (in.ttype == StreamTokenizer.TT_WORD) {
				Integer count = wordAndCount.get(in.sval);
				if (count == null) {
					wordAndCount.put(in.sval, 1);
				} else {
					wordAndCount.put(in.sval, ++count);
				}
			}
		}
		System.out.println(wordAndCount);
	}

}
