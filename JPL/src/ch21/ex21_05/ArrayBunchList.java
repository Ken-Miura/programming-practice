/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_05;

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;



/**
 * @author Ken Miura
 *
 */
public class ArrayBunchList<E> extends AbstractList<E> {

	private final E[][] arrays;
	private final int size;
	
	public ArrayBunchList(E[][] arrays) {
		this.arrays = arrays.clone();
		int s = 0;
		for (E[] array: arrays) {
			s += array.length;
		}
		size = s;
	}
	
	@Override
	public E get(int index) {
		int off = 0;
		for (int i=0; i<arrays.length; i++) {
			if(index < off+arrays[i].length) {
				return arrays[i][index-off];
			}
			off += arrays[i].length;
		}
		throw new ArrayIndexOutOfBoundsException(index);
	}

	@Override
	public E set (int index, E value) {
		int off = 0;
		for (int i = 0; i<arrays.length; i++) {
			if (index<off+arrays[i].length) {
				E ret = arrays[i][index-off];
				arrays[i][index-off] = value;
				return ret;	
			}			
			off += arrays[i].length;
		}
		throw new ArrayIndexOutOfBoundsException(index);
	}
	
	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unused")
	private class ABLListIterator implements ListIterator<E> {

		private int off;
		private int array;
		private int pos;
		private boolean calledNext = false;
		private boolean calledPrevious = false;
		
		public ABLListIterator() {
			off = 0;
			array = 0;
			pos = 0;
			for (array=0; array<arrays.length; array++) {
				if(arrays[array].length > 0) {
					break;
				}
			}
		}
		
		@Override
		public boolean hasNext() {
			return off + pos < size();
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E ret = arrays[array][pos++];
			
			while (pos >= arrays[array].length) {
				off += arrays[array++].length;
				pos = 0;
				if (array >= arrays.length) {
					break;
				}
			}
			calledNext = true;
			calledPrevious = false;
			return ret;
		}

		@Override
		public boolean hasPrevious() {
			return off + pos > 0;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			E ret = arrays[array][--pos];
			
			while (pos < 0) {
				off -= arrays[array--].length;
				pos = 0;
				if (array < 0) {
					break;
				}
			}
			calledPrevious = true;
			calledNext = false;
			return ret;
		}

		@Override
		public int nextIndex() {
			return off + pos;
		}

		@Override
		public int previousIndex() {
			return off + pos -1;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e) {
			if (!(calledNext == calledPrevious)) {
				arrays[array][pos] = e;
			}
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();			
		}

	}
}
