/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ken Miura
 *
 */
public final class ReadAndSort {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filePath = System.getProperty("user.dir")
				+ File.separator + "src" + File.separator + "ch21"+ File.separator + "ex21_01" + File.separator;
		File file = new File(filePath + "name.txt");
		try(BufferedReader bufReader = new BufferedReader(new FileReader(file))) {
			List<String> words = new ArrayList<>();
			String word;
			while ((word = bufReader.readLine()) != null) {
				words.add(word);
			}
			Collections.sort(words);
			// 表示テスト
			System.out.println(words);			
		}
	}

}
