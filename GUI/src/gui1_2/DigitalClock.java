package gui1_2;

import java.awt.EventQueue;

public final class DigitalClock {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new DigitalClockFrame();
		});
	}
}
