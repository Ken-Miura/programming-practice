/* Copyright (C) 2016 Ken Miura */
package ch21.ex21_06;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Ken Miura
 *
 */
class Concat {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InputStream in;
		if (args.length == 0) {
			in = System.in;
		} else {
			InputStream fileIn, bufIn;
			List<InputStream> inputs = new ArrayList<>(args.length);
			for (String arg: args) {
				fileIn = new FileInputStream(arg);
				bufIn = new BufferedInputStream(fileIn);
				inputs.add(bufIn);
			}
			//Enumeration<InputStream> files = Collections.enumeration(inputs);
			Enumeration<InputStream> files = new ISEnumeration(inputs);
			in= new SequenceInputStream(files);
		}
		int ch;
		while ((ch=in.read())!=-1) {
			System.out.println(ch);
		}
	}

	private static class ISEnumeration implements Enumeration<InputStream> {

		private final ListIterator<InputStream> listIterator;
		private InputStream usedInputStream = null;
		
		ISEnumeration(final List<InputStream> list) {
			listIterator = list.listIterator();
		}
		
		@Override
		public boolean hasMoreElements() {
			return listIterator.hasNext();
		}

		@Override
		public InputStream nextElement() {
			if (usedInputStream != null) {
				try {
					usedInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			usedInputStream = listIterator.next();
			return usedInputStream;
		}
		
	}
}
