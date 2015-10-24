package ch10.ex10_01;

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
			String target = source.substring(i, i + 1);
			if (target.equals("\b")) {
				sb.append("\\b");
			} else if (target.equals("\t")) {
				sb.append("\\t");
			} else if (target.equals("\n")) {
				sb.append("\\n");
			} else if (target.equals("\f")) {
				sb.append("\\f");
			} else if (target.equals("\r")) {
				sb.append("\\r");
			} else if (target.equals("\"")) {
				sb.append("\\\"");
			} else if (target.equals("\'")) {
				sb.append("\\'");
			} else if (target.equals("\\")) {
				sb.append("\\\\");
			} else {
				sb.append(target);
			}
		}
		return sb.toString();
	}
}
