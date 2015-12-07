/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_06;

import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
public class TimerThread extends Thread {
	
	private final MessageDisplay messageDisplay;
	public static final long INTERVAL = 1000;
	
	public TimerThread(MessageDisplay messageDisplay) {
		this.messageDisplay = Objects.requireNonNull(messageDisplay, "messageDisplay must not be null.");
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		for (;;) {
			System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms");
			try {
				Thread.sleep(INTERVAL);
				messageDisplay.countUp();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}

}
