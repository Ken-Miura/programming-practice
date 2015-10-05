package ch04.ex04_03;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void リスト要素が新しく複製されるが内包しているオブジェクトは同じ参照値を持っていることの確認() {
		Object element1 = new Object();
		Object element2 = new Object();
		LinkedListImpl l1 = new LinkedListImpl(element1);
		LinkedListImpl l2 = new LinkedListImpl(element2);
		l1.setNext(l2);
		LinkedListImpl l3 = l1.clone();
		assertNotSame(l1, l3);
		assertNotSame(l2, l3.getNext());
		assertSame(l1.getElement(), l3.getElement());
		assertSame(l2.getElement(), l3.getNext().getElement());
	}

}
