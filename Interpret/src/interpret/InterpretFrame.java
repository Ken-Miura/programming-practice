/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Ken Miura
 *
 */
public final class InterpretFrame extends JFrame {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 776059298091831885L;
	private static final String TITLE = "Interpret";
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	private static final int MARGIN = 5;
	
	private final JPanel buttonArea = new JPanel(new GridBagLayout());
	private final JButton instanceCreationButton = new JButton ("インスタンスを生成する");
	private final JButton arrayCreationButton = new JButton ("配列を生成する");
	
	public InterpretFrame() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		instanceCreationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InstanceHoldingDialog instanceDialog = new InstanceCreationDialog(null);
				instanceDialog.setLocationRelativeTo(null);
				instanceDialog.setVisible(true);
			}
		});
		
		arrayCreationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InstanceHoldingDialog arrayDialog = new ArrayCreationDialog(null);
				arrayDialog.setLocationRelativeTo(null);
				arrayDialog.setVisible(true);
			}
		});
		
		GridBagConstraints componetConstraints = new GridBagConstraints();
		componetConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componetConstraints.gridx = 0;
		componetConstraints.anchor = GridBagConstraints.CENTER;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		componetConstraints.gridy = 0;
		buttonArea.add(instanceCreationButton, componetConstraints);
		
		componetConstraints.gridy = 1;
		buttonArea.add(arrayCreationButton, componetConstraints);
		
		add(buttonArea, "Center");
	}
}
