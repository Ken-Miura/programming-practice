/* Copyright (C) 2015 Ken Miura */
package interpret;

import javax.swing.JFrame;

/**
 * @author Ken Miura
 *
 */
public final class InterpretFrame extends JFrame {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 4537958849872139727L;
	private static final String TITLE = "Interpret";
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	public InterpretFrame() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
