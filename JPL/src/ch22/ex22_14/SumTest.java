/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_14;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class SumTest {

	@Test
	public void test() {
		String test = "1.1 2.2 3.3";
		double actual = Sum.sum(test);
		double expected = 6.6;
		if (actual != expected) {
			fail();
		}
	}

}
