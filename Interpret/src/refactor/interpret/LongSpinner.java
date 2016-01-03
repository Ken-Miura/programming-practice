/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
public class LongSpinner extends JSpinner {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 7040483528112226952L;
	private final SpinnerNumberModel model = new SpinnerNumberModel(0, Long.MIN_VALUE, Long.MAX_VALUE, 1);  
	
	public LongSpinner () {
		setModel(model);
	}
	
	public long getLong () {
		Object d = getValue();
		if (!(d instanceof Double)) {	
			throw new AssertionError("not to be passed.");
		}
		double temp = (Double) d;
		return (long) temp;
	}
}
