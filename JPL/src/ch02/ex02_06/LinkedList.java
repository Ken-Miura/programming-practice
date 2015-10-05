package ch02.ex02_06;

import ch02.ex02_05.Vehicle;

/* ex02_02.LinkedListを修正 */
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

	/* ex02_06.LinkedListで追加したメソッド */
	public static void main(String[] args) {
		LinkedList list1 = new LinkedList(new Vehicle(30, Math.PI / 4, "A"));
		LinkedList list2 = new LinkedList(new Vehicle(40, Math.PI / 3, "B"));
		LinkedList list3 = new LinkedList(new Vehicle(50, Math.PI / 2, "C"));
		
		list1.setNext(list2);
		list2.setNext(list3);
		
		//　テストコード。Vehicleインスタンスのフィールドにコンストラクタでセットした値が設定されているか目視確認。
		LinkedList next = list1;
		while (next != null) {
			if (next.getElement() instanceof Vehicle) {
				Vehicle v = (Vehicle)next.getElement();
				System.out.println("--");
				System.out.println("ID: " + v.getID());
				System.out.println("Owner Name: " + v.getOwnerName());
				System.out.println("Speed (km/s): " + v.getSpeed());
				System.out.println("Direction (radian): " + v.getDirection());
			}
			next = next.getNext();
		}
	}
}
