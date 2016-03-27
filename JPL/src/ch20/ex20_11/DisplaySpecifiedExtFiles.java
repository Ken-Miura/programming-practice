/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_11;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Ken Miura
 *
 */
public final class DisplaySpecifiedExtFiles implements FilenameFilter {

	private final String suffix;
	
	private DisplaySpecifiedExtFiles (String suffix) {
		this.suffix = suffix;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		File file = new File(dir, name);
		String fileName = file.getName();
		return fileName.endsWith(suffix);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("illegal input");
		}
		File dir = new File(args[0]);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("illegal input");
		}
		String[] files = dir.list(new DisplaySpecifiedExtFiles(args[1]));
		for (String file: files) {
			System.out.println(file);
		}
	}

}
