/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_06;

/**
 * @author Ken Miura
 *
 */
final class MessageDisplay {

	private int count = 0;
	private boolean hasAlreadyDisplayed = false;
	
	public synchronized void displayMessage (int maxCount, String message) throws InterruptedException {
		while (count == 0 || (count%maxCount != 0) || hasAlreadyDisplayed) {
			wait();
		}
		System.out.println(message);
		hasAlreadyDisplayed = true;
	}
	
	public synchronized void countUp () throws InterruptedException {
		count++;
		hasAlreadyDisplayed = false;
		notifyAll();
	}
	
	/* 指定した間隔毎に表示されることを確認 */
	public static void main (String... args) {

		MessageDisplay messageDisplay = new MessageDisplay();
		new TimerThread(messageDisplay).start();
		new MessageDisplayThread("15秒間隔", 15, messageDisplay).start();
		new MessageDisplayThread("7秒間隔", 7, messageDisplay).start();
		

	}
}
