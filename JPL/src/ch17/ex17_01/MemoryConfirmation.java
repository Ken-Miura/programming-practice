/* Copyright (C) 2016 Ken Miura */
package ch17.ex17_01;

import javax.swing.JFrame;

/**
 * @author Ken Miura
 *
 */
public class MemoryConfirmation {

	private static final int NUM = 10000;
	
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		System.out.println("起動直後: " + rt.freeMemory());
		
		JFrame[] frames = new JFrame[NUM];
		for (int i=0; i<NUM; i++) {
			frames[i] = new JFrame();
		}
		System.out.println("オブジェクト作成後: " + rt.freeMemory());
		
		for (int i=0; i<NUM; i++) {
			frames[i] = null;
		}
		System.gc();
		System.out.println("gc呼び出し後: " + rt.freeMemory());
	}
}
