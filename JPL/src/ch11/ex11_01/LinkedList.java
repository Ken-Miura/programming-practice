/* Copyright 2015 Ken Miura */
package ch11.ex11_01;

/**
 * リスト構造の一つの要素を示すクラス。次の要素への参照がnullのときリスト構造の終端。
 */
public class LinkedList<E> {

	private E element;
	private LinkedList<E> next;

	public LinkedList(E element, LinkedList<E> next) {
		this.element = element;
		this.next = next;
	}

	public LinkedList(E element) {
		this(element, null);
	}

	public Object getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public LinkedList<E> next() {
		return next;
	}

	public void setNext(LinkedList<E> next) {
		this.next = next;
	}

	@Override
	public String toString() {
		StringBuilder listContents = null;
		if (element != null) {
			listContents = new StringBuilder("[element: " + element.toString() + "]");
		} else {
			listContents = new StringBuilder("[element: null]");
		}
		if (next != null) {
			listContents.append(next.toString());
		}
		return listContents.toString();
	}
	

	/**
	 * @return 自身をヘッドとしたときのリスト構造の長さ
	 */
	public final int size() {
		int size = 0;
		LinkedList<E> next = this;
		while (next != null) {
			size++;
			next = next.next();
		}
		return size;
	}
}
