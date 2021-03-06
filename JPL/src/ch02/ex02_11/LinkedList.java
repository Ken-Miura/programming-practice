package ch02.ex02_11;

/* ex02_06.LinkedListを修正。mainメソッドは必要ないので削除 */
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
}
