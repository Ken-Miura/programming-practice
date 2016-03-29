/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_04;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Ken Miura
 *
 */
public final class ShortStrings implements ListIterator<String> {

	private final ListIterator<String> strings;
	private final int maxLen;
	private int index;
	private int innerIndex;
	private int lastSelectedIndex;
	private String nextShort;
	private String previousShort;
	
	public ShortStrings(ListIterator<String> strings, int maxLen){
		if (strings.nextIndex() == 0) {
			throw new IllegalArgumentException();
		}
		this.strings = strings;
		this.maxLen = maxLen;
		nextShort = null;
		previousShort = null;
		innerIndex = strings.nextIndex();
		lastSelectedIndex = innerIndex;
		index = 0;
	}
	
	@Override
	public boolean hasNext() {
		if (nextShort != null) {
			return true;
		}
		while (strings.hasNext()) {
			nextShort = strings.next();
			if (nextShort.length() <= maxLen) {
				lastSelectedIndex = strings.previousIndex();
				return true;
			}
		}
		nextShort = null;
		return false;
	}

	@Override
	public String next() {
		if (nextShort == null && !hasNext()) {
			throw new NoSuchElementException();
		}
		String n = nextShort;
		nextShort = null;
		index++;
		return n;
	}

	@Override
	public boolean hasPrevious() {
		if (previousShort != null) {
			return true;
		}
		while (strings.hasPrevious()) {
			previousShort = strings.previous();
			if (previousShort.length() <= maxLen) {
				lastSelectedIndex = strings.nextIndex();
				return true;
			}
		}
		previousShort = null;
		return false;
	}

	@Override
	public String previous() {
		if (previousShort == null && !hasPrevious()) {
			throw new NoSuchElementException();
		}
		String n = previousShort;
		previousShort = null;
		index--;
		return n;
	}

	@Override
	public int nextIndex() {
		return index;
	}

	@Override
	public int previousIndex() {
		return index--;
	}

	@Override
	public void remove() {
		int diff = Math.abs(lastSelectedIndex - innerIndex);
		if (lastSelectedIndex < innerIndex) {
			for (int i=0; i<diff; i++) {
				strings.previous();
			}
		} else {
			for (int i=0; i<diff; i++) {
				strings.next();
			}
		}
		remove();
	}

	@Override
	public void set(String e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(String e) {
		throw new UnsupportedOperationException();
	}

}
