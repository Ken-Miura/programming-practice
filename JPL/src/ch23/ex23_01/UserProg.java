/* Copyright (C) 2016 Ken Miura */
package ch23.ex23_01;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * @author Ken Miura
 *
 */
public final class UserProg {

	// 表示テスト
	public static void main(String[] args) throws IOException {
		//String cmd = "ipconfig";
		String cmd = "echo test";
		userProg(cmd);
	}

	public static Process userProg (String cmd) throws IOException {
		Process proc = Runtime.getRuntime().exec(cmd);
		plugTogether(System.in, proc.getOutputStream());
		plugTogether(System.out, proc.getInputStream());
		plugTogether(System.err, proc.getErrorStream());
		return proc;
	}

	/**
	 * @param out
	 * @param inputStream
	 */
	private static void plugTogether(PrintStream out, InputStream inputStream) {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					InputStreamReader reader = new InputStreamReader(inputStream, "SHIFT-JIS"); // WindowsのプロセスからSHIFT-JISエンコーディングで来るバイトストリームをUTF16としてread
					int input = 0;
					while ((input=reader.read()) != -1) {
						out.print((char)input);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}, "bridge thread to receive data from other process").start();
	}

	/**
	 * @param in
	 * @param outputStream
	 */
	private static void plugTogether(InputStream in, OutputStream outputStream) {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					int input = 0;
					InputStreamReader reader = new InputStreamReader(in); // プラットフォームからデフォルトエンコーディングで来るバイトストリームをUTF16としてread
					OutputStreamWriter out = new OutputStreamWriter(outputStream, "SHIFT-JIS");
					while ((input=reader.read()) != -1) {
						out.write(input);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}, "bridge thread to send data to other process").start();
	}
}
