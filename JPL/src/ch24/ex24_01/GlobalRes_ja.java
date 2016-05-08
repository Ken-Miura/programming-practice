package ch24.ex24_01; /* 無名パッケージでコンパイルして、クラスパス（無名パッケージと同一階層）上に配置 */
/* Copyright (C) 2016 Ken Miura */


import java.util.ListResourceBundle;

/**
 * @author Ken Miura
 *
 */
public class GlobalRes_ja extends ListResourceBundle {

	private static final Object[][] contents = { 
		{"hello", "こんにちは"}, 
		{"goodbye", "さようなら"} };

	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
