package gui1_2;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

final class DigitalClockPropertyDialog extends Dialog {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = 7491464556518502928L;

	public static final String TITLE = "property";
	private static final int HEIGHT = 300;
	private static final int WIDTH = 500;
	private static final int MARGIN = 5;
	private static final int FONT_SIZE = 15;
	private static final String[] EXCLUDED_FONTS = new String [] { "Wingdings", "Wingdings 2", "Wingdings 3" };

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
	private final Map<String, Color> colorSet = new HashMap<>();
	
	private final Frame owner;
	
	DigitalClockPropertyDialog(Frame owner) {
		super(owner, TITLE, true);
		this.owner = owner;
		setResizable(false);
		Font font = new Font("Monospace", Font.PLAIN, FONT_SIZE);
		setFont(font);
		setSize(WIDTH, HEIGHT);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontSet = ge.getAllFonts();
		for (Font f : fontSet) {
			fontChoice.add(f.getName());
		}
		
		int num = EXCLUDED_FONTS.length;
		for (int i=0; i<num; i++) {
			fontChoice.remove(EXCLUDED_FONTS[i]);
		}

		for (int i = 0; i < NUM_OF_FONT_SIZE_SET; i++) {
			fontSizeSet[i] = (i + 1) * 10;
			fontSizeChoice.add(Integer.toString(fontSizeSet[i]));
		}

		colorSet.put("BLACK", Color.BLACK);
		colorSet.put("BLUE", Color.BLUE);
		colorSet.put("CYAN", Color.CYAN);
		colorSet.put("DARK GRAY", Color.DARK_GRAY);
		colorSet.put("GRAY", Color.GRAY);
		colorSet.put("GREEN", Color.GREEN);
		colorSet.put("LIGTHT GRAY", Color.LIGHT_GRAY);
		colorSet.put("MAGENTA", Color.MAGENTA);
		colorSet.put("ORANGE", Color.ORANGE);
		colorSet.put("PINK", Color.PINK);
		colorSet.put("RED", Color.RED);
		colorSet.put("WHITE", Color.WHITE);
		colorSet.put("YELLOW", Color.YELLOW);

		for (String s : colorSet.keySet()) {
			fontColorChoice.add(s);
		}

		for (String s : colorSet.keySet()) {
			backgroungColorChoice.add(s);
		}

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
				okSeleceted ();
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
		        	okSeleceted ();   
		        }
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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
		        	dispose();   
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
	
	private void okSeleceted () {
		int selectedFontSize = fontSizeSet[fontSizeChoice.getSelectedIndex()];
		Font selectedFont = new Font(fontChoice.getSelectedItem(), Font.PLAIN, selectedFontSize);
		Color selectedFontColor = colorSet.get(fontColorChoice.getSelectedItem());
		Color selectedBackgroundColor = colorSet.get(backgroungColorChoice.getSelectedItem());
		
		DigitalClockProperty property = new DigitalClockProperty();
		property.setFont(selectedFont);
		property.setFontColor(selectedFontColor);
		property.setBackgroungColor(selectedBackgroundColor);
		
		if (owner instanceof DigitalClockPropertyObserver) {
			DigitalClockPropertyObserver observer = (DigitalClockPropertyObserver) owner;
			observer.notifyPropertyChanged(property);	
		}

		dispose();
	}
}
