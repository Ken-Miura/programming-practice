/* Copyright (C) 2016 Ken Miura */
package ch24.ex24_01;

import java.util.ResourceBundle;

/**
 * @author Ken Miura
 *
 */
public class GlobalHello {

	static final String KEY_HELLO = "hello";
	static final String KEY_GOODBYE = "goodbye";

	/*
	 * リソースをクラスパスにおいて表示テスト
	 */
	public static void main(String[] args) {
		ResourceBundle res = ResourceBundle.getBundle("ch24.ex24_01.GlobalRes");
		String msg;
		if (args.length > 0) {
			msg = res.getString(GlobalHello.KEY_HELLO);
		} else {
			msg = res.getString(GlobalHello.KEY_GOODBYE);
		}
		System.out.println(msg);
	}

}
