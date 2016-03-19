/* Copyright (C) 2016 Ken Miura */
package gui2_2;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Ken Miura
 *
 */
@SuppressWarnings("serial") // シリアライズすることは想定しない
public final class ItemWithColorTip extends JLabel implements
		ListCellRenderer<Color> {

	private final Icon BLACK_ICON = new ImageIcon(getURL("colors/Black.png"));
	private final Icon BLUE_ICON = new ImageIcon(getURL("colors/Blue.png"));
	private final Icon CYAN_ICON = new ImageIcon(getURL("colors/Cyan.png"));
	private final Icon DARK_GRAY_ICON = new ImageIcon(getURL("colors/DarkGray.png"));
	private final Icon GRAY_ICON = new ImageIcon(getURL("colors/Gray.png"));
	private final Icon GREEN_ICON = new ImageIcon(getURL("colors/Green.png"));
	private final Icon LIGHT_GRAY_ICON = new ImageIcon(getURL("colors/LightGray.png"));
	private final Icon MAGENTA_ICON = new ImageIcon(getURL("colors/Magenta.png"));
	private final Icon ORANGE_ICON = new ImageIcon(getURL("colors/Orange.png"));
	private final Icon PINK_ICON = new ImageIcon(getURL("colors/Pink.png"));
	private final Icon RED_ICON = new ImageIcon(getURL("colors/Red.png"));
	private final Icon WHITE_ICON = new ImageIcon(getURL("colors/White.png"));
	private final Icon YELLOW_ICON = new ImageIcon(getURL("colors/Yellow.png"));
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Color> list,
			Color value, int index, boolean isSelected, boolean cellHasFocus) {
		
		if (value.equals(Color.BLACK)) {
			setIcon(BLACK_ICON);
		} else if (value.equals(Color.BLUE)) {
			setIcon(BLUE_ICON);
		} else if (value.equals(Color.CYAN)) {
			setIcon(CYAN_ICON);
		} else if (value.equals(Color.DARK_GRAY)) {
			setIcon(DARK_GRAY_ICON);
		} else if (value.equals(Color.GRAY)) {
			setIcon(GRAY_ICON);
		} else if (value.equals(Color.GREEN)) {
			setIcon(GREEN_ICON);
		} else if (value.equals(Color.LIGHT_GRAY)) {
			setIcon(LIGHT_GRAY_ICON);
		} else if (value.equals(Color.MAGENTA)) {
			setIcon(MAGENTA_ICON);
		} else if (value.equals(Color.ORANGE)) {
			setIcon(ORANGE_ICON);
		} else if (value.equals(Color.PINK)) {
			setIcon(PINK_ICON);
		} else if (value.equals(Color.RED)) {
			setIcon(RED_ICON);
		} else if (value.equals(Color.WHITE)) {
			setIcon(WHITE_ICON);
		} else if (value.equals(Color.YELLOW)) {
			setIcon(YELLOW_ICON);
		}
		
		setText(ColorNameConverter.convertColorToName(value));
		
		return this;
	}

	private URL getURL (String fileName) {
		URL url = getClass().getClassLoader().getResource(fileName);
		return url;
	}
}
