/* Copyright (C) 2015 Ken Miura */
package ch11.ex11_01;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import ch11.ex11_01.LinkedList;

/**
 * @author Ken
 *
 */
public class LinkedListTest {

	@Test
	public void null要素を許容することの確認() {
		try {
			LinkedList<Object> list = new LinkedList<>(null, null);
			list.setElement(null);
			list.setNext(null);
		} catch (NullPointerException e) {
			fail("NullPointerException has occurred");
		}
	}
	
	@Test
	public void toStringが正しい値を返すかの確認() {
		LinkedList<String> list1 = new LinkedList<>("1");
		LinkedList<String> list2 = new LinkedList<>(null);
		LinkedList<String> list3 = new LinkedList<>("2");
		list1.setNext(list2);
		list2.setNext(list3);

		String expected = "[element: 1][element: null][element: 2]";
		Assert.assertEquals(expected, list1.toString());
	}

	@Test
	public void セッターが正しい値を設定しているか確認() {
		LinkedList<Object> list = new LinkedList<>(null);

		Object expectedObject = new Object();
		list.setElement(expectedObject);
		Assert.assertEquals(expectedObject, list.getElement());

		LinkedList<Object> expectedLinkedList = new LinkedList<>(null);
		list.setNext(expectedLinkedList);
		Assert.assertEquals(expectedLinkedList, list.next());
	}

	@Test
	public void sizeメソッドがリストの要素数を正しく返しているかの確認() {
		LinkedList<String> list1 = new LinkedList<>("1");
		int expected = 1;
		int actual = list1.size();
		Assert.assertEquals(expected, actual, 0);

		LinkedList<String> list2 = new LinkedList<>(null);
		list1.setNext(list2);
		expected = 2;
		actual = list1.size();
		Assert.assertEquals(expected, actual, 0);

		LinkedList<String> list3 = new LinkedList<>("2");
		list2.setNext(list3);
		expected = 3;
		actual = list1.size();
		Assert.assertEquals(expected, actual, 0);
	}
}
