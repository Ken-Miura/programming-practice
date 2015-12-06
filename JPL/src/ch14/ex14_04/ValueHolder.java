/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_04;

/**
 * @author Ken Miura
 *
 */
public final class ValueHolder {

	private static long value = 0;
	static {
		System.out.println("value: " + value);
	}
	
	public static synchronized void addAndDisplayValue () {
		value++;
		System.out.println("value: " + value);			
	}
	
	public static void main(String[] args) {
		final int max = 5000;
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i<max; i++) {
					ValueHolder.addAndDisplayValue();	
				}
			}
		};
		new Thread (r).start();
		new Thread (r).start();
	}

}
