/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_07;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ken Miura
 *
 */
public final class Stack<E> {

	/* 継承だとスタックの使い方にそぐわないメソッドが公開されるので、内部で保持すべき */
	private final List<E> list = new ArrayList<E>();
	private int pointer = 0;

	public void push(E element){
		list.add(element);
		pointer++;
	}
	
	public E pop() {
		if (list.size() == 0) {
			throw new IllegalStateException("stack size is zero");
		}
		pointer--;
		return list.remove(pointer);
	}
	
	public int size() {
		return list.size();
	}
}
