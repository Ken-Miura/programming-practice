package ch10.ex10_02;

import java.util.Objects;

public class EscapeSequenceReplacement {
	
	/**
	 * 
	 * @param source 特殊文字を含む可能性のある文字列
	 * @return 特殊文字をJava言語の表現で置き換えた文字列　例.sourceが"であれば\"がリターンされる
	 * @throws NullPointerException sourceがnullのときスローされる
	 */
	public static String replaceEscapeSequences(String source) {
		Objects.requireNonNull(source, "source must not be null");
		int length = source.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char target = source.substring(i, i + 1).charAt(0);
			switch (target) {
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			case '\'':
				sb.append("\\'");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			default:
				sb.append(target);
			}
		}
		return sb.toString();
	}
}
