/* Copyright (C) 2016 Ken Miura */
package gui2_4;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
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

	private static class ColorTip implements Icon {

		private static final int ICON_SIZE = 24;
		private final Color color;
		
		ColorTip(Color color) {
			this.color = color;
		}
		
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, getIconWidth(), getIconHeight());
		}

		@Override
		public int getIconWidth() {
			return ICON_SIZE;
		}

		@Override
		public int getIconHeight() {
			return ICON_SIZE;
		}
		
	}
	
	private final Icon BLACK_ICON = new ColorTip(Color.BLACK);
	private final Icon BLUE_ICON = new ColorTip(Color.BLUE);
	private final Icon CYAN_ICON = new ColorTip(Color.CYAN);
	private final Icon DARK_GRAY_ICON = new ColorTip(Color.DARK_GRAY);
	private final Icon GRAY_ICON = new ColorTip(Color.GRAY);
	private final Icon GREEN_ICON = new ColorTip(Color.GREEN);
	private final Icon LIGHT_GRAY_ICON = new ColorTip(Color.LIGHT_GRAY);
	private final Icon MAGENTA_ICON = new ColorTip(Color.MAGENTA);
	private final Icon ORANGE_ICON = new ColorTip(Color.ORANGE);
	private final Icon PINK_ICON = new ColorTip(Color.PINK);
	private final Icon RED_ICON = new ColorTip(Color.RED);
	private final Icon WHITE_ICON = new ColorTip(Color.WHITE);
	private final Icon YELLOW_ICON = new ColorTip(Color.YELLOW);
	
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
