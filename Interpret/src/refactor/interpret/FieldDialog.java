/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.util.Objects;

import javax.swing.JDialog;

/**
 * @author Ken Miura
 *
 */
public final class FieldDialog extends JDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -5625858125672229888L;

	public FieldDialog (Object instance) {
		Objects.requireNonNull(instance, "instance must not be null.");
	}
}
