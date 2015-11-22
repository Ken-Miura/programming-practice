/* Copyright (C) 2015 Ken Miura */
package ch13.ex13_02;

import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
public class StringUtility {

	private StringUtility() {
		throw new AssertionError();
	}

	/**
	 * 
	 * @param target 出現回数を調べたい文字
	 * @param str targetの出現回数を調べる文字列
	 * @return str内にあるtargetの数
	 * @throws NullPointerException strがnullのときスローされる
	 */
	public static int countChar (char target, String str) {
		Objects.requireNonNull(str, "str must not be null.");
		int count = 0;
		int length = str.length();
		for (int i=0; i<length; i++) {
			if (target == str.charAt(i)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param target 出現回数を調べたい文字列
	 * @param str targetの出現回数を調べる文字列
	 * @return str内にあるtargetの数
	 * @throws NullPointerException targetまたはstrがnullのときスローされる
	 */
	public static int countString (String target, String str) {
		Objects.requireNonNull(target, "target must not be null.");
		Objects.requireNonNull(str, "str must not be null.");
		int count = 0;
		int head = str.indexOf(target);
		while (head != -1) {
			count++;
			head = str.indexOf(target, head + 1);
		}
		return count;
	}
	
}
