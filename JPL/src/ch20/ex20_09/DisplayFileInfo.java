/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_09;

import java.io.File;
import java.io.IOException;

/**
 * @author Ken Miura
 *
 */
public final class DisplayFileInfo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			throw new IllegalArgumentException("args length must be one or more");
		}
		for (String arg: args) {
			File file = new File(arg);
			System.out.println("AbsolutePath: " + file.getAbsolutePath());
			System.out.println("CanonicalPath: " + file.getCanonicalPath());
			System.out.println("FreeSpace: " + file.getFreeSpace());
			System.out.println("Name: " + file.getName());
			System.out.println("Parent: " + file.getParent());
			System.out.println("TotalSpace: " + file.getTotalSpace());
			System.out.println("UsableSpace: " + file.getUsableSpace());
			System.out.println("LastModified: " + file.lastModified());
			System.out.println("Length: " + file.length());
			System.out.println("CanExecute: " + file.canExecute());
			System.out.println("CanRead: " + file.canRead());
			System.out.println("CanWrite: " + file.canWrite());
			System.out.println("Exists: " + file.exists());
			System.out.println("IsAbsolute: " + file.isAbsolute());
			System.out.println("IsDirectory: " + file.isDirectory());
			System.out.println("IsFile: " + file.isFile());
			System.out.println("IsHidden: " + file.isHidden());
			System.out.println("List: " + file.list());
			System.out.println("ListRoots: " + File.listRoots()[0]);
		}
	}

}
