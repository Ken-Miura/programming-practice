/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_07;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class StackTest {

	@Test
	public void testPop() {
		Stack<Object> stack = new Stack<>();
		stack.push(new Object());
		stack.push(new Object());
		stack.push(new Object());
		stack.pop();
		stack.pop();
		stack.pop();
		int expected = 0;
		if (expected != stack.size()) {
			fail();
		}

	}

	@Test
	public void testSize() {
		Stack<Object> stack = new Stack<>();
		stack.push(new Object());
		stack.push(new Object());
		stack.push(new Object());
		int expected = 3;
		if (expected != stack.size()) {
			fail();
		}
	}

}
