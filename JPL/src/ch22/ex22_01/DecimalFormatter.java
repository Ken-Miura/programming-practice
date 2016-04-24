/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_01;

import java.util.Objects;
import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public final class DecimalFormatter {

	private static final int MAX_LINE_NUM = 80;

	/**
	 * 表示テスト
	 */
	public static void main(String[] args) {
		double[] decimals = new double[10];
		Random r = new Random();
		for (int i=0; i<decimals.length; i++) {
			decimals[i] = r.nextDouble();
		}
		
		displayDecimal(decimals, 20);
	}

	private static void displayDecimal (double[] decimals, int width) {
		Objects.requireNonNull(decimals, "decimals must not be null");
		if (width < 0 || width > MAX_LINE_NUM) {
			throw new IllegalArgumentException("Illegal width");
		}
		
		final String format = "%" + width + "f%n";
		for (double decimal: decimals) {
			System.out.printf(format, decimal);
		}
	}
}
