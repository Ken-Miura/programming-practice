/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;

/**
 * @author Ken Miura
 *
 */
public final class OperationDialog extends InstanceHoldingDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 3485186338834021751L;
	private static final String TITLE = "インスタンスの操作";
	private static final int MARGIN = 3;
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	private final FieldPanel fieldPanel;
	
	public OperationDialog (Object instance) {
		setTitle(TITLE);
		setInstance(instance);
		fieldPanel = FieldPanel.createFieldPanel(instance, this);
		
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		add(fieldPanel, componentConstraints);
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(FieldPanel.FIELD_CHANGE)) {
			revalidate();
			Dimension d = getPreferredSize();
			setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
			repaint();
		}
	}

}