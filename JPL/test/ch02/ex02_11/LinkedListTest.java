package ch02.ex02_11;

import static org.junit.Assert.*;
import org.junit.Assert;

import org.junit.Test;

/* ch02.ex02_02.LinkedListTestを修正 */
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
	
	/* ch02.ex02_11.LinkedListTestで追加したメソッド*/
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
}
