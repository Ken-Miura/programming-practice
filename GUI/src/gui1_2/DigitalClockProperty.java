package gui1_2;

import java.awt.Color;
import java.awt.Font;

public class DigitalClockProperty {

	private Font font = null;
	private Color fontColor = null;
	private Color backgroungColor = null;
	
	Font getFont() {
		return font;
	}
	void setFont(Font font) {
		this.font = font;
	}
	Color getFontColor() {
		return fontColor;
	}
	void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	Color getBackgroungColor() {
		return backgroungColor;
	}
	void setBackgroungColor(Color backgroungColor) {
		this.backgroungColor = backgroungColor;
	}
}
