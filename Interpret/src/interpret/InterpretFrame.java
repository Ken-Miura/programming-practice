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
	private static final long serialVersionUID = 4537958849872139727L;
	private static final String TITLE = "Interpret";
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	private static final int MARGIN = 5;
	
	private final JPanel buttonArea = new JPanel(new GridBagLayout());
	private final JButton instanceCreationButton = new JButton ("インスタンスの生成に関する操作");
	private final JButton arrayCreationButton = new JButton ("配列の生成に関する操作");
	
	private final InstanceCreationDialog instanceCreationDialog = new InstanceCreationDialog(null);

	public InterpretFrame() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		instanceCreationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instanceCreationDialog.setLocation(getLocation());
				instanceCreationDialog.setVisible(true);
			}
		});
		
		GridBagConstraints componetConstraints = new GridBagConstraints();
		componetConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		componetConstraints.gridx = 0;
		componetConstraints.gridy = 0;
		componetConstraints.anchor = GridBagConstraints.CENTER;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonArea.add(instanceCreationButton, componetConstraints);
		
		componetConstraints.gridx = 0;
		componetConstraints.gridy = 1;
		componetConstraints.anchor = GridBagConstraints.CENTER;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonArea.add(arrayCreationButton, componetConstraints);
		
		add(buttonArea, "Center");
		
		setVisible(true);
	}
}
