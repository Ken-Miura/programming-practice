/* Copyright (C) 2016 Ken Miura */
package ch24.ex24_03;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Ken Miura
 *
 */
public final class DateParser {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// 日本語ロケールでそれぞれ
		// yy/MM/dd
		// yyyy/MM/dd
		// yyyy/MM/dd
		// yyyy年M月d日
		displayDate("6/-45/513", DateFormat.SHORT);
		displayDate("-1000/99/9999", DateFormat.MEDIUM);
		displayDate("200/9999999/-1", DateFormat.LONG);
		displayDate("271641016年35月13日", DateFormat.FULL);
	}

	/**
	 * @param date
	 * @param medium
	 * @throws ParseException 
	 */
	private static void displayDate(String date, int style) throws ParseException {
		SimpleDateFormat dateFmt = (SimpleDateFormat) DateFormat.getDateInstance(style);
		dateFmt.setLenient(true);
		System.out.println(dateFmt.format(dateFmt.parse(date)));		
	}
}
