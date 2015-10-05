package ch01.ex01_15;

public interface ExtendedLookup extends Lookup {

	/**
	 * nameとvalueを関連付けて保存する。 すでにnameに関連付けられた値があった場合、valueを返り値とする。
	 * nameに関連付けられた値がない場合、nullを返す
	 */
	Object add(String name, Object value);

	/**
	 * nameと関連付けられたvalueを取り除く。 取り除くことに成功するとtrueを返す
	 */
	boolean remove(String name, Object value);
}
