/* Copyright 2015 Ken Miura */
package gui2_3;

import javax.swing.SwingUtilities;

public class DigitalClock {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DigitalClockWindow();
		});
	}

}
