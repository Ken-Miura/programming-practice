/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.GridBagLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

/**
 * @author Ken Miura
 *
 */
abstract class InstanceHoldingDialog extends JDialog implements PropertyChangeListener {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -2680753238560627111L;
	protected static final int TOP_BOTTOM_MARGIN = 34;
	protected static final int LEFT_RIGHT_MARGIN = 4;
	
	private Object instance = null;
	
	public InstanceHoldingDialog () {
		setModal(true);
		setResizable(false);
		setLayout(new GridBagLayout());
	}

	public final Object getInstance() {
		return instance;
	}

	public final void setInstance(Object instance) {
		this.instance = instance;
	}
}
