/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_03;

/**
 * @author Ken Miura
 *
 */
public final class ValueHolder {

	private long value = 0;
	private final Object lock = new Object();
	
	public ValueHolder () {
		System.out.println("value: " + value);
	}
	
	public void addAndDisplayValue () {
		synchronized (lock) {
			value++;
			System.out.println("value: " + value);			
		}
	}
	
	/* maxの値 × スレッド数の値が最後に表示されることの確認 */
	public static void main(String[] args) {
		final ValueHolder vh = new ValueHolder();
		final int max = 5000;
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i<max; i++) {
					vh.addAndDisplayValue();	
				}
			}
		};
		new Thread (r).start();
		new Thread (r).start();
	}

}
