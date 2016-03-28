/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_02;

import java.io.File;
import java.util.WeakHashMap;

/**
 * @author Ken Miura
 *
 */
class DataHandler {
	private final WeakHashMap<File, byte[]> fileAndLastData = new WeakHashMap<>();
	
	byte[] readFile(File file) {
		byte[] data;
		
		data = fileAndLastData.get(file);
		if (data != null) {
			return data;
		}
		
		data = readBytesFromFile(file);
		fileAndLastData.put(file, data);
		return data;
	}

	/**
	 * @param file
	 * @return
	 */
	private byte[] readBytesFromFile(File file) {
		// 実際の読み込み処理
		return new byte[0];
	}
}
