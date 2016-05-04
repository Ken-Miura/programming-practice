/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_10;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Ken Miura
 *
 */
public final class CommentRemovableParser {

	private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\n0085]";
	
	public static List<String> removeComments (Readable reader) {
		@SuppressWarnings("resource") // スキャナは勝手に閉じないようにする
		Scanner in = new Scanner(reader);
		Pattern pat = Pattern.compile("("+LINE_SEPARATOR_PATTERN+"?)#(.*)("+LINE_SEPARATOR_PATTERN+"?)");
		in.useDelimiter(pat);
		List<String> list = new ArrayList<>();
		while (in.hasNext()) {
			list.add(in.next());
			try {
				in.nextLine();				
			} catch (NoSuchElementException e) {
				break;
			}
		}
		return list;
	}
}
