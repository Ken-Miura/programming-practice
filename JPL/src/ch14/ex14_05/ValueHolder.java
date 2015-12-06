/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_05;

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
	
	public static void decrementValue () {
		synchronized (ValueHolder.class) {
			value--;
		}
	}
	
	/* 加算と減算をそれぞれ別スレッドで同じ回数繰り返して、最終的に0になることの確認 */
	public static void main(String[] args) {
		final int max = 10000;
		Runnable increase = new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i<max; i++) {
					ValueHolder.addAndDisplayValue();	
				}
			}
		};
		Runnable decrease = new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i<max; i++) {
					ValueHolder.decrementValue();;	
				}
			}
		};
		new Thread (increase).start();
		new Thread (decrease).start();
	}

}
