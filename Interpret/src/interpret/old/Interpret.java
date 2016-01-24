/* Copyright (C) 2015 Ken Miura */
package interpret.old;

import javax.swing.SwingUtilities;

/**
 * @author Ken Miura
 *
 */
public final class Interpret {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable () {

			@Override
			public void run() {
				new InterpretFrame();
			}
			
		});
	}

}
