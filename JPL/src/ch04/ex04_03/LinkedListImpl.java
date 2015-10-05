package ch04.ex04_03;

import java.util.Objects;

/* 
 * ch03.ex03_10.LinkedListを修正。
 */
public class LinkedListImpl implements LinkedList {

	private Object element;
	private LinkedList next; /* 次の要素への参照がnullのときリスト構造の終端 */

	public LinkedListImpl(Object element, LinkedListImpl next) {
		this.element = element;
		this.next = next;
	}

	public LinkedListImpl(Object element) {
		this(element, null);
	}

	@Override
	public LinkedListImpl clone() {
		LinkedListImpl linkedList = null;
		try {
			linkedList = (LinkedListImpl) super.clone();
			linkedList.next = next == null ? null : next.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return linkedList;
	}

	@Override
	public Object getElement() {
		return element;
	}

	@Override
	public void setElement(Object element) {
		this.element = element;
	}

	@Override
	public LinkedList getNext() {
		return next;
	}

	@Override
	public void setNext(LinkedList next) {
		this.next = next;
	}

	@Override
	public String toString() {
		StringBuilder listContents = null;
		if (element != null) {
			listContents = new StringBuilder("[element: " + element.toString()
					+ "]");
		} else {
			listContents = new StringBuilder("[element: null]");
		}
		if (next != null) {
			listContents.append(next.toString());
		}
		return listContents.toString();
	}

	/**
	 * @param head
	 *            リスト構造の先頭を指す参照
	 * @return headの指すリスト構造の要素数
	 **/
	public static int size(LinkedList head) {
		Objects.requireNonNull(head, "head is null");
		int size = 1;
		LinkedList next = head.getNext();
		while (next != null) {
			size++;
			next = next.getNext();
		}
		return size;
	}
}
