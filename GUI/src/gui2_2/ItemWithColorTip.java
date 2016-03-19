/* Copyright (C) 2016 Ken Miura */
package gui2_2;

import java.awt.Color;
import java.awt.Component;

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

	private static final Icon BLACK_ICON = new ImageIcon("./colors/Black.png");
	private static final Icon BLUE_ICON = new ImageIcon("./colors/Blue.png");
	private static final Icon CYAN_ICON = new ImageIcon("./colors/Cyan.png");
	private static final Icon DARK_GRAY_ICON = new ImageIcon("./colors/DarkGray.png");
	private static final Icon GRAY_ICON = new ImageIcon("./colors/Gray.png");
	private static final Icon GREEN_ICON = new ImageIcon("./colors/Green.png");
	private static final Icon LIGHT_GRAY_ICON = new ImageIcon("./colors/LightGray.png");
	private static final Icon MAGENTA_ICON = new ImageIcon("./colors/Magenta.png");
	private static final Icon ORANGE_ICON = new ImageIcon("./colors/Orange.png");
	private static final Icon PINK_ICON = new ImageIcon("./colors/Pink.png");
	private static final Icon RED_ICON = new ImageIcon("./colors/Red.png");
	private static final Icon WHITE_ICON = new ImageIcon("./colors/White.png");
	private static final Icon YELLOW_ICON = new ImageIcon("./colors/Yellow.png");
	
	
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

}
