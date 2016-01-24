/* Copyright (C) 2016 Ken Miura */
package interpret;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
final class IntSpinner extends JSpinner {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 9197382091961633958L;
	private final SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);  
	
	public IntSpinner () {
		setModel(model);
	}
	
	public int getInt () {
		Object i = getValue();
		if (i instanceof Integer) {
			return (Integer) i;	
		}
		throw new AssertionError("not to be passed.");
	}
}
