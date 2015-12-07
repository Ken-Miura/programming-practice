/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_06;

import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
class MessageDisplayThread extends Thread {

	private final String message;
	private final int maxCount;
	private final MessageDisplay messageDisplay;
	
	public MessageDisplayThread (String message, int maxCount, MessageDisplay messageDisplay) {
		if (maxCount < 1) {
			throw new IllegalArgumentException("maxCount must be positive.");
		}
		this.message = Objects.requireNonNull(message, "message must not be null.");
		this.maxCount = maxCount;
		this.messageDisplay = Objects.requireNonNull(messageDisplay, "messageDisplay must not be null.");
	}

	String getMessage() {
		return message;
	}

	int getMaxCount() {
		return maxCount;
	}
	
	@Override
	public void run() {
		for (;;) {
			try {
				messageDisplay.displayMessage(maxCount, message);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
