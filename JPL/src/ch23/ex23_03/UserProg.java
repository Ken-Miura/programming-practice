/* Copyright (C) 2016 Ken Miura */
package ch23.ex23_03;

import java.io.BufferedReader;
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

	private static final String FORBIDDEN_WORD = "test";

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
					BufferedReader reader = 
							new BufferedReader(new InputStreamReader(inputStream, "SHIFT-JIS")); // WindowsのプロセスからSHIFT-JISエンコーディングで来るバイトストリームをUTF16としてread
					String line = null;
					while ((line=reader.readLine()) != null) {
						if (line.contains(FORBIDDEN_WORD)) {
							System.out.println("\"" + line + "\" has forbidden word \"" + FORBIDDEN_WORD + "\"");
							System.exit(0);
						}
						out.println(line);
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
