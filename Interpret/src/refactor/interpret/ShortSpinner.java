/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
final class ShortSpinner extends JSpinner {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 8881810745637821332L;
	private final SpinnerNumberModel model = new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1);  
	
	public ShortSpinner () {
		setModel(model);
	}
	
	public short getShort () {
		Object i = getValue();
		if (!(i instanceof Integer)) {	
			throw new AssertionError("not to be passed.");
		}
		int temp = (Integer) i;
		return (short) temp;
	}
}
