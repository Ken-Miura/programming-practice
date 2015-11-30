/* Copyright (C) 2015 Ken Miura */
package gui1_4;

import java.awt.EventQueue;

public final class DigitalClock {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new DigitalClockFrame();
		});
	}
}
