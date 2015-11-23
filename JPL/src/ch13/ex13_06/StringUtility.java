/* Copyright (C) 2015 Ken Miura */
package ch13.ex13_06;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
public class StringUtility {

	private StringUtility() {
		throw new AssertionError();
	}

	/**
	 * 
	 * @param target
	 *            出現回数を調べたい文字
	 * @param str
	 *            targetの出現回数を調べる文字列
	 * @return str内にあるtargetの数
	 * @throws NullPointerException
	 *             strがnullのときスローされる
	 */
	public static int countChar(char target, String str) {
		Objects.requireNonNull(str, "str must not be null.");
		int count = 0;
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (target == str.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 
	 * @param target
	 *            出現回数を調べたい文字列
	 * @param str
	 *            targetの出現回数を調べる文字列
	 * @return str内にあるtargetの数
	 * @throws NullPointerException
	 *             targetまたはstrがnullのときスローされる
	 */
	public static int countString(String target, String str) {
		Objects.requireNonNull(target, "target must not be null.");
		Objects.requireNonNull(str, "str must not be null.");
		int count = 0;
		int head = str.indexOf(target);
		while (head != -1) {
			count++;
			head = str.indexOf(target, head + 1);
		}
		return count;
	}

	/**
	 * 
	 * @param from 区切り文字で囲まれた文字列を含む文字列
	 * @param start 区切りの始まりを意味する区切り文字
	 * @param end  区切りの終わりを意味する区切り文字
	 * @return　startとendで囲まれた文字列の配列（startとendを含む）<br>
	 * 			区切り文字で囲まれた文字列が一つも見つからないとき、空配列を返す。
	 * @throws NullPointerException
	 *             fromがnullのときスローされる
	 */
	public static String[] delimitedString(String from, char start, char end) {
		Objects.requireNonNull(from, "from must not be null.");
		List<String> list = new ArrayList<>();
		int startPos = from.indexOf(start);
		int endPos = -1;
		while (startPos != -1) {
			endPos = from.indexOf(end, startPos + 1);
			if (endPos == -1) {
				break;
			}
			list.add(from.substring(startPos, endPos + 1));
			startPos = from.indexOf(start, endPos + 1);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	/**
	 * "type value"の形式の文字列を受け取り、valueの値を表示する。"type value"を一行として複数行（行の区切りは\n）の入力を受け付ける<br>
	 * typeとして許容される文字列は以下の通り<br>
	 * Boolean, Character, Byte, Short, Integer, Long, Float, Double<br>
	 * typeがCharacterのときvalueに' '(空白)は許容されない。
	 * @param typesAndValues "type value"の形式を1行として、それを0行以上保持する文字列
	 * @throws NullPointerException typesAndValuesがnullのときスローされる
	 * @throws ParseException<br>
	 * 入力行が空白を区切りとして2単語にならないときスローされる。<br>
	 * typeとして許容されない文字列を入力したときスローされる。<br>
	 * typeがCharacterでvalueが' '(空白)のときスローされる。
	 */
	public static void displayValues (String typesAndValues) throws ParseException {
		Objects.requireNonNull(typesAndValues, "typesAndValues must not be null.");
		List<Object> list = new ArrayList<>();
		String[] splitTypesAndValues = typesAndValues.split(String.valueOf('\n'));
		int length = splitTypesAndValues.length;
		for (int i=0; i<length; i++) {
			String trimmedTypesAndValues = splitTypesAndValues[i].trim();
			String[] splitTypeAndValue = trimmedTypesAndValues.split(String.valueOf(' '));
			if (splitTypeAndValue.length != 2) {
				throw new ParseException("line number " + (i+1) + ": is illegal format. Correct format is \"type value\".", 0);
			}
			switch (splitTypeAndValue[0]) {
			case "Boolean":
				list.add(new Boolean(splitTypeAndValue[1]));
				break;
			case "Character":
				char[] cArray = splitTypeAndValue[1].toCharArray();
				if (cArray.length != 1) {
					throw new ParseException("line number " + (i+1) + ": is illegal format. One character must be input for value of Character.", 0);
				}
				list.add(new Character(cArray[0]));
				break;
			case "Byte":
				list.add(new Byte(splitTypeAndValue[1]));
				break;
			case "Short":
				list.add(new Short(splitTypeAndValue[1]));
				break;
			case "Integer":
				list.add(new Integer(splitTypeAndValue[1]));
				break;
			case "Long":
				list.add(new Long(splitTypeAndValue[1]));
				break;
			case "Float":
				list.add(new Float(splitTypeAndValue[1]));
				break;
			case "Double":
				list.add(new Double(splitTypeAndValue[1]));
				break;
			default:
				throw new ParseException("line number " + (i+1) + " has illegal type name.", 0);	
			}
		}
		for (Object o: list) {
			System.out.println(o.toString());
		}
	}
	
	/**
	 * addDelimiterToNumber (number, ',', 3)と同じ
	 */
	public static String addDelimiterToNumber (String number) {
		return addDelimiterToNumber (number, ',', 3);
	}
	
	/**
	 * 10進数を示す文字列を指定した桁数毎に指定した区切り文字を加えた文字列を返す。(1543729は、区切り文字に','で区切り間隔に3としたとき、1,543,729となる)
	 * @param number　10進数を示す文字列
	 * @param delimiter 区切り文字
	 * @param betweenDelimiter 区切り文字を入れる間隔
	 * @return 区切り文字を追加された10進数を示す文字列
	 * @throws NullPointerException numberがnullのときスローされる。
	 * @throws IllegalArgumentException betweenDelimiterが0以下のときスローされる。
	 * @throws NumberFormatException numberが10進数を示す文字列でないとき、numberがLong型に収まらない数を示す文字列のときスローされる。
	 */
	public static String addDelimiterToNumber (String number, char delimiter, int betweenDelimiter) {
		Objects.requireNonNull(number, "number must not be null.");
		if (betweenDelimiter <= 0) {
			throw new IllegalArgumentException("betweenDelimiter must be positive.");
		}
		long num = Long.parseLong(number);
		String format = "%" + delimiter + betweenDelimiter +"d";
		return String.format(format, num);
	}
	
	/* addDelimiterToNumberの表示テスト */
	public static void main (String args[]) throws ParseException, UnsupportedEncodingException {
		System.out.println(addDelimiterToNumber("1543729"));
	}
}
