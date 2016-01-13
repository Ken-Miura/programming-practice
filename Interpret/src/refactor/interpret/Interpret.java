/* Copyright (C) 2015 Ken Miura */
package refactor.interpret;

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