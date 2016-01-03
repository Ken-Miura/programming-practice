/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
final class ByteSpinner extends JSpinner {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 1369867053796890428L;
	private final SpinnerNumberModel model = new SpinnerNumberModel(0, Byte.MIN_VALUE, Byte.MAX_VALUE, 1);  
	
	public ByteSpinner () {
		setModel(model);
	}
	
	public byte getByte () {
		Object i = getValue();
		if (!(i instanceof Integer)) {	
			throw new AssertionError("not to be passed.");
		}
		int temp = (Integer) i;
		return (byte) temp;
	}
}
