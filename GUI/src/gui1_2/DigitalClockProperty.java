package gui1_2;

import java.awt.Color;

public class DigitalClockProperty {

	private String fontName = null;
	private int fontSize = 0;
	private Color fontColor = null;
	private Color backgroungColor = null;
	
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	int getFontSize() {
		return fontSize;
	}
	void setFontSize(int fontSize) {
		this.fontSize = fontSize;
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
