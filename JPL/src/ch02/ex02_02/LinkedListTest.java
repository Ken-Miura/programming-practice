package ch02.ex02_02;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListTest {

	@Test
	public void null要素を許容することの確認() {
		try {
			LinkedList list = new LinkedList(null, null);
			list.setElement(null);
			list.setNext(null);
		} catch (NullPointerException e) {
			fail("NullPointerException has occurred");
		}
	}
}
