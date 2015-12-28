/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_11;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Ken Miura
 *
 */
public class PlayerLoader extends ClassLoader {

	//最後のセパレータはなしで
	private static final String playersDirectory = "/C:/workspace_programming_practice/JPL/ch16_ex16_11_players";
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] buf = byteForClass(name);
			return defineClass(name, buf, 0, buf.length);
		} catch (IOException e) {
			throw new ClassNotFoundException(e.toString());
		}
	}

	private byte[] byteForClass(String name) throws IOException, ClassNotFoundException {
		String simpleClassName = null;
		int found = name.lastIndexOf('$');
		if (found == -1) {
			found = name.lastIndexOf('.');
		}
		if (found == -1) {
			simpleClassName = name;
		} else {
			simpleClassName = name.substring(found + 1);	
		}
		try (FileInputStream in = new FileInputStream(playersDirectory + File.separator + simpleClassName + ".class")) {
			int length = in.available();
			if (length == 0) {
				throw new ClassNotFoundException(name);
			}
			byte[] buf = new byte[length];
			in.read(buf);
			return buf;
		}
	}
}
