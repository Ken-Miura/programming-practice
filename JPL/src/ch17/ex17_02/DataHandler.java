/* Copyright (C) 2016 Ken Miura */
package ch17.ex17_02;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author Ken Miura
 *
 */
class DataHandler {
	private WeakReference<File> lastFile;
	private WeakReference<byte[]> lastData;
	
	byte[] readFile(File file) {
		byte[] data;
		
		if (file.equals(lastFile.get())) {
			data = lastData.get();
			if (data != null) {
				return data;
			}
		}
		
		data = readBytesFromFile(file);
		lastFile = new WeakReference<File>(file);
		lastData = new WeakReference<byte[]>(data);
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
