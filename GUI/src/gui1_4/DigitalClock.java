package gui1_4;

import java.awt.EventQueue;

public final class DigitalClock {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new DigitalClockFrame();
		});
	}
}
