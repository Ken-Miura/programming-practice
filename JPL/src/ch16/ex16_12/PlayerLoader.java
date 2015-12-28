/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_12;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;


/**
 * @author Ken Miura
 *
 */
public class PlayerLoader extends ClassLoader {

	// Webで見つけたクラスパス取得方法。動くけどなんか信用できないのでとりあえず固定値で。
	//private static final String CLASS_PATH;
	//static {
	//	CLASS_PATH = ClassLoader.getSystemClassLoader().getResource("").getPath();
	//}
	private static final String CLASS_PATH = "/C:/workspace_programming_practice/JPL/bin/";
	
	//最後のセパレータはなしで + クラスパス以外を指定してみる
	private static final String playersDirectory;
	static {
		String parentDirectoryOfClassPath = CLASS_PATH;
		for (int i=0; i<2; i++) {
			int index = parentDirectoryOfClassPath.lastIndexOf('/');
			parentDirectoryOfClassPath = parentDirectoryOfClassPath.substring(0, index);
		}
		playersDirectory = parentDirectoryOfClassPath + "/ch16_ex16_12_players";
	}
	
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
		try (FileInputStream in = new FileInputStream(playersDirectory + "/" + simpleClassName + ".class")) {
			int length = in.available();
			if (length == 0) {
				throw new ClassNotFoundException(name);
			}
			byte[] buf = new byte[length];
			in.read(buf);
			return buf;
		}
	}
	
	@Override
	protected URL findResource(String name) {
		File f = fileFor(name);
		if (!f.exists()) {
			return null;
		}
		try {
			return f.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private File fileFor(String name) {
		return new File(playersDirectory + "/" + name);
	}
	
	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Vector<URL> v = new Vector<>();
		v.add(findResource(name));
		return v.elements();
	}
}
