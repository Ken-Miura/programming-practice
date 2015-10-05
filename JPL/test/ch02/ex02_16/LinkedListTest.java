package ch02.ex02_16;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import ch02.ex02_16.LinkedList;

/* ch02.ex02_14.LinkedListTestを修正 */
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

	@Test
	public void toStringが正しい値を返すかの確認() {
		LinkedList list1 = new LinkedList("1");
		LinkedList list2 = new LinkedList(null);
		LinkedList list3 = new LinkedList("2");
		list1.setNext(list2);
		list2.setNext(list3);

		String expected = "[element: 1][element: null][element: 2]";
		Assert.assertEquals(expected, list1.toString());
	}

	@Test
	public void セッターが正しい値を設定しているか確認() {
		LinkedList list = new LinkedList(null);

		Object expectedObject = new Object();
		list.setElement(expectedObject);
		Assert.assertEquals(expectedObject, list.getElement());

		LinkedList expectedLinkedList = new LinkedList(null);
		list.setNext(expectedLinkedList);
		Assert.assertEquals(expectedLinkedList, list.getNext());
	}

	/* ch02.ex02_16.LinkedListTestで追加したメソッド */
	@Test
	public void sizeメソッドがリストの要素数を正しく返しているかの確認() {
		LinkedList list1 = new LinkedList("1");
		int expected = 1;
		int actual = LinkedList.size(list1);
		Assert.assertEquals(expected, actual, 0);

		LinkedList list2 = new LinkedList(null);
		list1.setNext(list2);
		expected = 2;
		actual = LinkedList.size(list1);
		Assert.assertEquals(expected, actual, 0);

		LinkedList list3 = new LinkedList("2");
		list2.setNext(list3);
		expected = 3;
		actual = LinkedList.size(list1);
		Assert.assertEquals(expected, actual, 0);
	}

	/* ch02.ex02_16.LinkedListTestで追加したメソッド */
	@Test(expected = NullPointerException.class)
	public void sizeメソッドで引数がnullのときヌルポをスローすることの確認() {
		LinkedList.size(null);
	}
}
