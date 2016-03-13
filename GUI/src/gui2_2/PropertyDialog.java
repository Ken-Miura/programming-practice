/* Copyright (C) 2016 Ken Miura */
package gui2_2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author Ken Miura
 *
 */
/* シリアライズして利用することを想定しない */
@SuppressWarnings("serial")
final class PropertyDialog extends JDialog {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int FONT_SIZE = 24;
	
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
		final int FONT_SIZE_NUM = 30;
		fontSizeSet = new int[FONT_SIZE_NUM];
		for (int i=0; i<FONT_SIZE_NUM; i++) {
			fontSizeSet[i] = 10 + (i * 10);
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
		
		GridBagConstraints componentConstraints = new GridBagConstraints();
		// TODO
		
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
		fontSizeCombo.setSelectedIndex(fontOnOpening.getSize());
		fontColorCombo.setSelectedItem(ColorNameConverter.convertColorToName(fontColorOnOpening));
		backgroundColorCombo.setSelectedItem(ColorNameConverter.convertColorToName(backgroundColorOnOpening));
	}
}
