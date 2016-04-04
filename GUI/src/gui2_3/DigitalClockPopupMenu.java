/* Copyright 2015 Ken Miura */
package gui2_3;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

final class DigitalClockPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7914779750101277674L;
	private final PropertyChangeSupport notifier;
	private final Font POPUP_MENU_FONT = new Font("Monospace", Font.PLAIN, 12);
	private final Map<String, Color> colorSet = new HashMap<>();
	
	DigitalClockPopupMenu(PropertyChangeListener digitalClockPropertyObserver) {
		notifier = new PropertyChangeSupport(this);
		notifier.addPropertyChangeListener(digitalClockPropertyObserver);
		
		setFont(POPUP_MENU_FONT);
		
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
		
		initFontMenu();
		initFontSizeMenu();
		initFontColorMenu();
		initBackgroungColorMenu();
		
		addSeparator();
		JMenuItem exitMenu = new JMenuItem("exit");
		exitMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(exitMenu);
	}

	private void initFontMenu() {
		JMenu fontMenu = new JMenu("font");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fontSet = ge.getAllFonts();
		List<String> list = new ArrayList<>();
		for (Font f : fontSet) {
				list.add(f.getName());
		}
		for (final String fontName: list) {
			JMenuItem mi = new JMenuItem(fontName);
			mi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectedFontString = fontName;
					for (final Font f: fontSet) {
						if (selectedFontString.equals(f.getFontName())) {
							notifier.firePropertyChange(DigitalClock.FONT_EVT, null, f);
							break;
						}
					}
				}
			});
			fontMenu.add(mi);
		}
		add(fontMenu);
	}

	private void initFontSizeMenu() {
		JMenu fontSizeMenu = new JMenu("font size");
		int numOfFontSize = 30;
		for (int i = 0; i<numOfFontSize; i++) {
			int fontSize = i * 10 + 10;
			JMenuItem mi = new JMenuItem(Integer.valueOf(fontSize).toString());
			mi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedFontSize = fontSize;
					notifier.firePropertyChange(DigitalClock.FONT_SIZE_EVT, null, selectedFontSize);
				}
			});
			fontSizeMenu.add(mi);
			
		}
		add(fontSizeMenu);
	}

	private void initFontColorMenu() {
		JMenu fontColorMenu = new JMenu("font color");
		for (final Entry<String, Color> colorEntry: colorSet.entrySet()) {
			JMenuItem mi = new JMenuItem(colorEntry.getKey());
			mi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Color selectedColor = colorEntry.getValue();
					notifier.firePropertyChange(DigitalClock.FONT_COLOR_EVT, null, selectedColor);
				}
			});
			fontColorMenu.add(mi);
		}
		add(fontColorMenu);
	}
	
	private void initBackgroungColorMenu() {
		JMenu bgColorMenu = new JMenu("background color");
		for (final Entry<String, Color> colorEntry: colorSet.entrySet()) {
			JMenuItem mi = new JMenuItem(colorEntry.getKey());
			mi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Color selectedColor = colorEntry.getValue();
					notifier.firePropertyChange(DigitalClock.BACKGROUND_COLOR_EVT, null, selectedColor);
				}
			});
			bgColorMenu.add(mi);
		}
		add(bgColorMenu);		
	}
}
