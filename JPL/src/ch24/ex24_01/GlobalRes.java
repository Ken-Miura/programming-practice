package ch24.ex24_01;
/* Copyright (C) 2016 Ken Miura */


import java.util.ListResourceBundle;

/**
 * @author Ken Miura
 *
 */
public class GlobalRes extends ListResourceBundle {

	private static final Object[][] contents = { 
		{"hello", "hello"}, 
		{"goodbye", "good bye"} };

	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
