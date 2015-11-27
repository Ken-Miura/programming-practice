package gui1_4;

import java.awt.Color;

enum DigitalClockProperty {

	PROPERTY;
	
	// DEFAULT PROPERTY
	private String fontName = "Arial";
	private int fontSize = 60;
	private Color fontColor = Color.BLACK;
	private Color backgroungColor = Color.WHITE;
	
	String getFontName() {
		return fontName;
	}
	void setFontName(String fontName) {
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
