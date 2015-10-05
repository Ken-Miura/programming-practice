package ch02.ex02_16;

import java.util.Objects;

/* 
 * ex02_14.LinkedListを修正。
 */
/**
 * リスト構造の一つの要素を示すクラス。次の要素への参照がnullのときリスト構造の終端。
 */
public class LinkedList {

	private Object element;
	private LinkedList next;

	public LinkedList(Object element, LinkedList next) {
		this.element = element;
		this.next = next;
	}

	public LinkedList(Object element) {
		this(element, null);
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

	/* ex02_11.LinkedListで追加したメソッド */
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
	
	/* ex02_16.LinkedListで追加したメソッド */
	/**
	 * @param head リスト構造の先頭を指す参照
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
