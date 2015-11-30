/* Copyright (C) 2015 Ken Miura */
package gui1_4;

import java.awt.Color;
import java.util.prefs.Preferences;

enum DigitalClockProperty {

	PROPERTY;
	
	private final Preferences preferences = Preferences.userNodeForPackage(getClass());
	private static final String PREFIX = "ken_miura_1102_";
	private static final String FONT_NAME_KEY = PREFIX + "font_name_key";
	private static final String FONT_SIZE_KEY = PREFIX + "font_size_key";
	private static final String FONT_COLOR_KEY = PREFIX + "font_color_key";
	private static final String BACKGROUND_COLOR_KEY = PREFIX + "background_color_key";
	private static final String X_COORDINATE_KEY = PREFIX + "x_coordinate_key";
	private static final String Y_COORDINATE_KEY = PREFIX + "y_coordinate_key";
	
	// DEFAULT PROPERTY
	private String fontName = preferences.get(FONT_NAME_KEY, "Arial");
	private int fontSize = Integer.parseInt(preferences.get(FONT_SIZE_KEY, "60"));
	private Color fontColor = ColorNameConverter.convertNameToColor(preferences.get(FONT_COLOR_KEY, "BLACK"));
	private Color backgroungColor = ColorNameConverter.convertNameToColor(preferences.get(BACKGROUND_COLOR_KEY, "WHITE"));
	private int xCoodinate = Integer.parseInt(preferences.get(X_COORDINATE_KEY, "0"));;
	private int yCoodinate = Integer.parseInt(preferences.get(Y_COORDINATE_KEY, "0"));;
	
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
	int getXCoodinate() {
		return xCoodinate;
	}
	void setXCoodinate(int xCoodinate) {
		this.xCoodinate = xCoodinate;
	}
	int getYCoodinate() {
		return yCoodinate;
	}
	void setYCoodinate(int yCoodinate) {
		this.yCoodinate = yCoodinate;
	}
	
	void save () {
		preferences.put(FONT_NAME_KEY, getFontName());
		preferences.put(FONT_SIZE_KEY, String.valueOf(getFontSize()));
		preferences.put(FONT_COLOR_KEY, ColorNameConverter.convertColorToName(getFontColor()));
		preferences.put(BACKGROUND_COLOR_KEY, ColorNameConverter.convertColorToName(getBackgroungColor()));
		preferences.put(X_COORDINATE_KEY, String.valueOf(getXCoodinate()));
		preferences.put(Y_COORDINATE_KEY, String.valueOf(getYCoodinate()));
	}
}
