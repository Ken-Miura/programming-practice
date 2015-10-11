package gui1_2;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public final class PropertyDialog extends Dialog {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = 7491464556518502928L;
	
	public static final String TITLE = "property";
	private static final int HEIGHT = 300;
	private static final int WIDTH = 500;
	private static final int MARGIN = 3;

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
	
	PropertyDialog(Frame owner) {
		super(owner,TITLE, true);
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontSet = ge.getAllFonts();
		for (Font f: fontSet) {
			fontChoice.add(f.getName());
		}
		
		for (int i=0; i<NUM_OF_FONT_SIZE_SET; i++) {
			fontSizeSet[i] = (i+1) * 10;
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
		
		for (String s :colorSet.keySet()) {
			fontColorChoice.add(s);
		}
		
		for (String s :colorSet.keySet()) {
			backgroungColorChoice.add(s);
		}
				
		propertyArea.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		propertyArea.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		propertyArea.add(fontLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontChoice, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		propertyArea.add(fontSizeLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontSizeChoice, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		propertyArea.add(fontColorLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(fontColorChoice, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.NONE;
		propertyArea.add(backgroungColorLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		propertyArea.add(backgroungColorChoice, gbc);
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graphics g = owner.getGraphics();
				int selectedFontSize =  fontSizeSet[fontSizeChoice.getSelectedIndex()];
				Font selectedFont = new Font(fontChoice.getSelectedItem(), Font.PLAIN, selectedFontSize);
				FontMetrics fm = g.getFontMetrics(selectedFont);
				
				Insets insets = owner.getInsets();
				int stringHeight = fm.getAscent();
				int margin = stringHeight/3;
				int canvasHeight = stringHeight + margin;
				int canvaswidth = fm.stringWidth("00:00:00");
				owner.setSize(insets.left + canvaswidth + insets.right, insets.top + canvasHeight + insets.bottom);
				if (owner instanceof DigitalClock) {
					((DigitalClock)owner).setCanvasWidth(canvaswidth);
					((DigitalClock)owner).setCanvasHeight(canvasHeight);;
					((DigitalClock)owner).setStringHeight(stringHeight);
					((DigitalClock)owner).setMargin(margin);
					((DigitalClock)owner).setFontColor(colorSet.get(fontColorChoice.getSelectedItem()));
				}
				Component[] allComponents = owner.getComponents();
				for (Component c: allComponents) {
					if (c instanceof Canvas) {
						c.setBackground(colorSet.get(backgroungColorChoice.getSelectedItem()));
						c.setFont(selectedFont);
					}
				}
				g.dispose();
				dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		okCancelArea.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		okCancelArea.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okCancelArea.add(cancelButton);
		okCancelArea.add(okButton);
		
		add(propertyArea, "Center");
		add(okCancelArea, "South");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

}
