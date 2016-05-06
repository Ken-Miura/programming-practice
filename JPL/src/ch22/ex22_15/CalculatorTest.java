/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_15;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

/**
 * @author Ken Miura
 *
 */
public class CalculatorTest {

	@Test
	public void test() throws IOException, ParseException {
		double actual = Calculator.calculate("1/2+3*4-5%6");
		double expected = 7.5;
		if (actual!=expected) {
			fail();
		}
	}
}
