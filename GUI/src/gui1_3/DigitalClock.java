/* Copyright 2015 Ken Miura */
package gui1_3;

import java.awt.EventQueue;

public class DigitalClock {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new DigitalClockWindow();
		});
	}

}
