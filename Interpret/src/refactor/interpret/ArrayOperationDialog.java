/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.Objects;

import javax.swing.JLabel;

/**
 * @author Ken Miura
 *
 */
public final class ArrayOperationDialog extends InstanceHoldingDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 1513068164944039662L;

	private static final String TITLE = "配列要素の操作";
	
	private final JLabel caption = new JLabel("要素番号を指定して下さい");
	
	public ArrayOperationDialog (Object instance) {
		Objects.requireNonNull(instance, "instance must not be null");
		if (!(instance.getClass().isArray())) {
			throw new IllegalArgumentException("instance is not array");
		}
		setInstance(instance);
		setTitle(TITLE);
		
		
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
