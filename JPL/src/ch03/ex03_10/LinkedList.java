package ch03.ex03_10;

import java.util.Objects;

/* 
 * ch02.ex02_16.LinkedListを修正。
 */
/**
 * リスト構造の一つの要素を示すクラス。次の要素への参照がnullのときリスト構造の終端。
 */
public class LinkedList implements Cloneable {

	private Object element;
	private LinkedList next;

	public LinkedList(Object element, LinkedList next) {
		this.element = element;
		this.next = next;
	}

	public LinkedList(Object element) {
		this(element, null);
	}

	/* ex03_10で追加 */
	@Override
	protected LinkedList clone() {
		LinkedList linkedList = null;
		try {
			linkedList = (LinkedList) super.clone();
			linkedList.next = next == null ? null : next.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return linkedList;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public LinkedList getNext() {
		return next;
	}

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
