package ch02.ex02_02;

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

}
