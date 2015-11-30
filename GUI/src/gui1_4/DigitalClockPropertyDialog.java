/* Copyright 2015 Ken Miura */
package gui1_4;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

final class DigitalClockPropertyDialog extends Dialog {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = 7491464556518502928L;

	public static final String TITLE = "Property";
	private static final int HEIGHT = 300;
	private static final int WIDTH = 500;
	private static final int MARGIN = 5;
	private static final int FONT_SIZE = 17;

	private final Panel propertyArea = new Panel();
	private final Label fontLabel = new Label("font");
	private final Label fontSizeLabel = new Label("font size");
	private final Label fontColorLabel = new Label("font color");
	private final Label backgroungColorLabel = new Label("background color");
	private final Choice fontChoice = new Choice();
	private final Choice fontSizeChoice = new Choice();
	private final Choice fontColorChoice = new Choice();
	private final Choice backgroungColorChoice = new Choice();

	private final Panel okCancelArea = new Panel();
	private final Button okButton = new Button("OK");
	private final Button cancelButton = new Button("Cancel");

	private final Font[] fontSet;
	private final static int NUM_OF_FONT_SIZE_SET = 30;
	private final int[] fontSizeSet = new int[NUM_OF_FONT_SIZE_SET];
	
	/* キャンセル押したとき用にダイアログ開いた時点のプロパティ保存用 */
	private String fontNameOnOpening;
	private int fontSizeOnOpening;
	private Color fontColorOnOpening;
	private Color backgroundColorOnOpening;
	
	private final Font DIALOG_FONT = new Font("Monospace", Font.PLAIN, FONT_SIZE);
	private static final Frame frame = new Frame();
	private final DigitalClockPropertyObserver observer;
	
	/* 絵文字やプラットフォーム固有等のフォントを除外対象に */
	public static final String[] EXCLUDED_FONTS = { "Bookshelf Symbol 7", "MT Extra", "Wide Latin", "Viner Hand ITC", "Vladimir Script", "Webdings", "Wingdings", "Wingdings 2", "Wingdings 3" };
	
	DigitalClockPropertyDialog(DigitalClockPropertyObserver observer) {
		super(frame, TITLE, true);
		this.observer = observer;
		setResizable(false);
		setFont(DIALOG_FONT);
		setSize(WIDTH, HEIGHT);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontSet = ge.getAllFonts();
		List<String> list = new LinkedList<>();
		for (Font f : fontSet) {
				list.add(f.getName());
		}
		int num = EXCLUDED_FONTS.length;
		for (int i=0; i<num; i++) {
			list.remove(EXCLUDED_FONTS[i]);
		}
		for (String s : list) {
			fontChoice.add(s);
		}
		fontChoice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				onPropertyChanged();
			}
		});
		
		for (int i = 0; i < NUM_OF_FONT_SIZE_SET; i++) {
			fontSizeSet[i] = (i + 1) * 10;
			fontSizeChoice.add(Integer.toString(fontSizeSet[i]));
		}
		fontSizeChoice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				onPropertyChanged();
			}
		});

		for (String s : ColorNameConverter.getColorNameSet()) {
			fontColorChoice.add(s);
		}
		fontColorChoice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (fontColorChoice.getSelectedItem().equals(backgroungColorChoice.getSelectedItem())) {
					String colorName = fontColorChoice.getSelectedItem();
					fontColorChoice.select(ColorNameConverter.convertColorToName(DigitalClockProperty.PROPERTY.getFontColor()));
					ErrorMessageDialog.showErrorMessage(colorName + " is same color of font. Select different color for background.");
					return;
				}
				onPropertyChanged();
			}
		});

		for (String s : ColorNameConverter.getColorNameSet()) {
			backgroungColorChoice.add(s);
		}
		backgroungColorChoice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (fontColorChoice.getSelectedItem().equals(backgroungColorChoice.getSelectedItem())) {
					String colorName = backgroungColorChoice.getSelectedItem();
					backgroungColorChoice.select(ColorNameConverter.convertColorToName(DigitalClockProperty.PROPERTY.getBackgroungColor()));
					ErrorMessageDialog.showErrorMessage(colorName + " is same color of font. Select different color for background.");
					return;
				}
				onPropertyChanged();
			}
		});

		propertyArea.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		propertyArea.setLayout(new GridBagLayout());
		GridBagConstraints componetConstraints = new GridBagConstraints();
		componetConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);

		componetConstraints.gridx = 0;
		componetConstraints.gridy = 0;
		componetConstraints.anchor = GridBagConstraints.EAST;
		componetConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(fontLabel, componetConstraints);
		componetConstraints.gridx = 1;
		componetConstraints.gridy = 0;
		componetConstraints.anchor = GridBagConstraints.WEST;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontChoice, componetConstraints);

		componetConstraints.gridx = 0;
		componetConstraints.gridy = 1;
		componetConstraints.anchor = GridBagConstraints.EAST;
		componetConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(fontSizeLabel, componetConstraints);
		componetConstraints.gridx = 1;
		componetConstraints.gridy = 1;
		componetConstraints.anchor = GridBagConstraints.WEST;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontSizeChoice, componetConstraints);

		componetConstraints.gridx = 0;
		componetConstraints.gridy = 2;
		componetConstraints.anchor = GridBagConstraints.EAST;
		componetConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(fontColorLabel, componetConstraints);
		componetConstraints.gridx = 1;
		componetConstraints.gridy = 2;
		componetConstraints.anchor = GridBagConstraints.WEST;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontColorChoice, componetConstraints);

		componetConstraints.gridx = 0;
		componetConstraints.gridy = 3;
		componetConstraints.anchor = GridBagConstraints.EAST;
		componetConstraints.fill = GridBagConstraints.NONE;
		propertyArea.add(backgroungColorLabel, componetConstraints);
		componetConstraints.gridx = 1;
		componetConstraints.gridy = 3;
		componetConstraints.anchor = GridBagConstraints.WEST;
		componetConstraints.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(backgroungColorChoice, componetConstraints);

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
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelSelected();
			}
		});
		cancelButton.addKeyListener(new KeyListener() {
			
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
		        	cancelSelected();   
		        }
			}
		});
		
		okCancelArea.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		okCancelArea.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okCancelArea.add(okButton);
		okCancelArea.add(cancelButton);

		add(propertyArea, "Center");
		add(okCancelArea, "South");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			setAndSavePropertyValues();			
		}
		super.setVisible(b);
	};
	
	private void setAndSavePropertyValues() {
		if (DigitalClockProperty.PROPERTY.getFontName().equals(Font.DIALOG)) { // 物理フォントがなかった場合、論理フォント”DIALOG”になっている
			System.err.println("Selected font is incorrect.");
		}
		fontChoice.select(DigitalClockProperty.PROPERTY.getFontName());
		fontSizeChoice.select(Integer.valueOf(DigitalClockProperty.PROPERTY.getFontSize()).toString());
		fontColorChoice.select(ColorNameConverter.convertColorToName(DigitalClockProperty.PROPERTY.getFontColor()));
		backgroungColorChoice.select(ColorNameConverter.convertColorToName(DigitalClockProperty.PROPERTY.getBackgroungColor()));
		
		fontNameOnOpening = DigitalClockProperty.PROPERTY.getFontName();
		fontSizeOnOpening = DigitalClockProperty.PROPERTY.getFontSize();
		fontColorOnOpening = DigitalClockProperty.PROPERTY.getFontColor();
		backgroundColorOnOpening = DigitalClockProperty.PROPERTY.getBackgroungColor();
	}
	
	private void cancelSelected() {
		DigitalClockProperty.PROPERTY.setFontName(fontNameOnOpening);
		DigitalClockProperty.PROPERTY.setFontSize(fontSizeOnOpening);
		DigitalClockProperty.PROPERTY.setFontColor(fontColorOnOpening);
		DigitalClockProperty.PROPERTY.setBackgroungColor(backgroundColorOnOpening);
		observer.notifyPropertyChanged(DigitalClockProperty.PROPERTY);	
		dispose();
	}

	private void onPropertyChanged() {
		
		int selectedFontSize = fontSizeSet[fontSizeChoice.getSelectedIndex()];
		String selectedFontName = fontChoice.getSelectedItem();
		Color selectedFontColor = ColorNameConverter.convertNameToColor(fontColorChoice.getSelectedItem());
		Color selectedBackgroundColor = ColorNameConverter.convertNameToColor(backgroungColorChoice.getSelectedItem());
		
		DigitalClockProperty.PROPERTY.setFontName(selectedFontName);
		DigitalClockProperty.PROPERTY.setFontSize(selectedFontSize);
		DigitalClockProperty.PROPERTY.setFontColor(selectedFontColor);
		DigitalClockProperty.PROPERTY.setBackgroungColor(selectedBackgroundColor);
		
		observer.notifyPropertyChanged(DigitalClockProperty.PROPERTY);			
	}
}
