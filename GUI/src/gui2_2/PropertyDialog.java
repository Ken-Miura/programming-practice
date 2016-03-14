/* Copyright (C) 2016 Ken Miura */
package gui2_2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Ken Miura
 *
 */
/* シリアライズして利用することを想定しない */
@SuppressWarnings("serial")
final class PropertyDialog extends JDialog {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	private static final int FONT_SIZE = 60;
	
	private final JPanel propertyArea = new JPanel(new GridBagLayout());
	private final JPanel okCancelArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	private final DigitalClock digitalClock;
	private final PropertyChangeSupport notifier;
	
	private final JComboBox<String> fontCombo = new JComboBox<>();
	private final JComboBox<Integer> fontSizeCombo = new JComboBox<>();
	private final JComboBox<String> fontColorCombo = new JComboBox<>();
	private final JComboBox<String> backgroundColorCombo = new JComboBox<>();
	
	private final Font[] fontSet;
	private final int[] fontSizeSet;
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontSet = ge.getAllFonts();
		final int FONT_SIZE_NUM = 31;
		fontSizeSet = new int[FONT_SIZE_NUM];
		for (int i=0; i<FONT_SIZE_NUM; i++) {
			fontSizeSet[i] = 100 + (i * 10);
		}
		
		for (Font f: fontSet) {
			fontCombo.addItem(f.getFontName());
		}
		for (int n: fontSizeSet) {
			fontSizeCombo.addItem(n);
		}
		for (String color: ColorNameConverter.getColorNameSet()) {
			fontColorCombo.addItem(color);
		}
		for (String color: ColorNameConverter.getColorNameSet()) {
			backgroundColorCombo.addItem(color);
		}
	}
	
	private final JButton okButton;
	private final JButton cancelButton;
	
	/* キャンセル押したとき用にダイアログ開いた時点のプロパティ一時保存用 */
	private Font fontOnOpening;
	private Color fontColorOnOpening;
	private Color backgroundColorOnOpening;	
	
	PropertyDialog(PropertyChangeListener listener) {
		assert listener != null;
		if (!(listener instanceof DigitalClock)) {
			throw new AssertionError("not to be passed");
		}
		digitalClock = (DigitalClock) listener;
		notifier = new PropertyChangeSupport(listener);
		
		setResizable(false);
		setModal(true);
		setTitle("property");
		setSize(WIDTH, HEIGHT);
		setFont(new Font(null, Font.PLAIN, FONT_SIZE));
		propertyArea.setFont(getFont());
		okCancelArea.setFont(getFont());
		
		GridBagConstraints componentConstraints = new GridBagConstraints();
		final int MARGIN = 5;
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		componentConstraints.anchor = GridBagConstraints.EAST;
		componentConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(new JLabel("font"), componentConstraints);
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 0;
		componentConstraints.anchor = GridBagConstraints.WEST;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontCombo, componentConstraints);

		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.EAST;
		componentConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(new JLabel("font size"), componentConstraints);
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.WEST;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontSizeCombo, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 2;
		componentConstraints.anchor = GridBagConstraints.EAST;
		componentConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(new JLabel("font color"), componentConstraints);
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 2;
		componentConstraints.anchor = GridBagConstraints.WEST;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontColorCombo, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 3;
		componentConstraints.anchor = GridBagConstraints.EAST;
		componentConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(new JLabel("background color"), componentConstraints);
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 3;
		componentConstraints.anchor = GridBagConstraints.WEST;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(backgroundColorCombo, componentConstraints);
		
		add(propertyArea, "Center");
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		okCancelArea.add(okButton);
		okCancelArea.add(cancelButton);
		add(okCancelArea, "South");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			saveAndSetPropertyValues();						
		}
		super.setVisible(b);
	}

	private void saveAndSetPropertyValues() {
		fontOnOpening = digitalClock.canvasFont();
		fontColorOnOpening = digitalClock.canvasFontColor();
		backgroundColorOnOpening = digitalClock.canvasBackgroungColor();
		
		fontCombo.setSelectedItem(fontOnOpening.getFontName());
		fontSizeCombo.setSelectedItem(fontOnOpening.getSize());
		fontColorCombo.setSelectedItem(ColorNameConverter.convertColorToName(fontColorOnOpening));
		backgroundColorCombo.setSelectedItem(ColorNameConverter.convertColorToName(backgroundColorOnOpening));
	}
}
