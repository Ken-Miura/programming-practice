/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_14;


/**
 * @author Ken Miura
 *
 */
public class Sum {

	public static double sum (String input) {
		//String[] tokens = input.split("\\p{javaWhitespace}");
		String[] tokens = input.split("\\s");
		double sum = 0.0;
		for (final String token: tokens) {
			sum+=Double.valueOf(token);
		}
		return sum;
	}
}
