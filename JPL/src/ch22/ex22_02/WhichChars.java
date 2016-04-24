/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_02;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Ken Miura
 *
 */
public class WhichChars {

	SortedSet<Character> characterSortedSet = new TreeSet<>();
	
	public WhichChars (String str) {
		for (int i=0; i<str.length(); i++) {
			characterSortedSet.add(str.charAt(i));
		}
	}
	
	@Override
	public String toString(){
		StringBuilder desc = new StringBuilder("[");
		for (char c: characterSortedSet) {
			desc.append(c);
		}
		return desc.append("]").toString();
	}
}
