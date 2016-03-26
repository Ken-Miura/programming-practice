/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_08;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public final class DisplayEntry {

	/**
	 * @param args
	 * @throws IOException 
	 */
	// 表示テスト
	public static void main(String[] args) throws IOException {
		String filePath = System.getProperty("user.dir")
				+ File.separator + "src" + File.separator + "ch20"+ File.separator + "ex20_08" + File.separator;
		File in = new File(filePath + "entry.txt");
		File out = new File(filePath + "entryTable.txt");
		readAndWrite(in, out);
		readAndRandomDisplay(out, in);
	}

	private static void readAndWrite(File in, File out) throws IOException {
		try (RandomAccessFile inFile = new RandomAccessFile(in, "r");
				RandomAccessFile outFile = new RandomAccessFile(out, "rw")) {
			long pointer = inFile.getFilePointer();
			String data = inFile.readLine();
			while (data != null) {
				if (data.startsWith("%%")) {
					outFile.writeLong(pointer);
				}
				pointer = inFile.getFilePointer();
				data = inFile.readLine();
			}
		}
	}
	
	private static void readAndRandomDisplay (File table, File in) throws FileNotFoundException, IOException {
		try (RandomAccessFile tableFile = new RandomAccessFile(table, "r");
				RandomAccessFile inFile = new RandomAccessFile(in, "r")) {
			List<Long> pointers = new ArrayList<>();
			for (;;) {
				try {
					pointers.add(tableFile.readLong());					
				} catch (EOFException e) {
					break;
				}
			}
			int selected = new Random().nextInt(pointers.size());
			long pointer = pointers.get(selected);
			inFile.seek(pointer);
			System.out.println(inFile.readLine());
		}
	}
}
