package ch01.ex01_15;

public interface Lookup {
	/** nameと関連付けられた値を返す。
	 *  そのような値がなければnullを返す  
	 */
	Object find (String name);
}
