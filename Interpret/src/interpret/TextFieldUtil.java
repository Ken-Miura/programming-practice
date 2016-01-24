/* Copyright (C) 2016 Ken Miura */
package interpret;

import javax.swing.JTextField;

/**
 * @author Ken Miura
 *
 */
final class TextFieldUtil {

	private static final int col = 10;
	
	private TextFieldUtil() {
		throw new AssertionError("not to be passed.");
	}
	
	public static JTextField createStringTextField () {
		return new JTextField("", col);
	}
	
	public static JTextField createFloatingPointNumberTextField () {
		JTextField t = new JTextField("0.0", col);
		t.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
		return t;
	}
}
