/* Copyright 2015 Ken Miura */
package gui1_4;

import java.awt.Button;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ErrorMessageDialog extends Dialog {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = -6097507768569228753L;
	
	static final String TITLE = "error";
	private static final int HEIGHT = 150;
	private static final int WIDTH = 700;
	private static final int FONT_SIZE = 15;

	private final Panel messageArea = new Panel();
	private final Panel okArea = new Panel();
	
	private ErrorMessageDialog(Frame owner, String message) {
		super(owner, TITLE, true);
		setResizable(false);
		Font font = new Font("Monospace", Font.PLAIN, FONT_SIZE);
		setFont(font);
		setSize(WIDTH, HEIGHT);

		messageArea.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		messageArea.setLayout(new GridBagLayout());
		GridBagConstraints componetConstraints = new GridBagConstraints();
		componetConstraints.gridx = 0;
		componetConstraints.gridy = 0;
		componetConstraints.anchor = GridBagConstraints.CENTER;
		componetConstraints.fill = GridBagConstraints.NONE;
		messageArea.add(new Label(message), componetConstraints);

		Button okButton = new Button("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okButton.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// Do nothing				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// Do nothing
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
		        if (key == KeyEvent.VK_ENTER) {
		        	dispose();    
		        }
			}
		});
		okArea.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		okArea.setLayout(new FlowLayout(FlowLayout.CENTER));
		okArea.add(okButton);

		add(messageArea, "Center");
		add(okArea, "South");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	static void showErrorMessage (String message) {
		Frame f = new Frame();
		ErrorMessageDialog emd = new ErrorMessageDialog(f, message);
		emd.setLocationRelativeTo(null);
		emd.setVisible(true);
		f.dispose();
	}
}
