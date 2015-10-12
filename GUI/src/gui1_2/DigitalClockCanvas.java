package gui1_2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.time.LocalTime;

final class DigitalClockCanvas extends Canvas {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = -7241239036627526757L;
	private Image buffer;
	private static int DEFAULT_FONT_SIZE = 120;
	private int stringWidth;
	private int stringHeight;
	private Color fontColor;
	
	DigitalClockCanvas (Frame digitalClockFrame) {
		Font font = new Font("Monospace", Font.PLAIN, DEFAULT_FONT_SIZE);
		setFont(font);
		Graphics graphics = digitalClockFrame.getGraphics();
		if (graphics == null) {
			digitalClockFrame.setVisible(true);
			graphics = digitalClockFrame.getGraphics();
			digitalClockFrame.setVisible(false);
		}
		FontMetrics fontMetrics = graphics.getFontMetrics(font);
		stringWidth = fontMetrics.stringWidth("00:00:00");
		stringHeight = fontMetrics.getAscent();
		fontColor = Color.BLACK;
	}
	
	@Override
	public void paint(Graphics graphics) {
		LocalTime localTime = LocalTime.now();
		buffer = createImage(getCanvasWidth(), getCanvasHeight());
		Graphics bufferGraphics = buffer.getGraphics();
		if (bufferGraphics instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) bufferGraphics;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		}
		bufferGraphics.setColor(fontColor);
		bufferGraphics.drawString(String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond()), 0, stringHeight);
		graphics.drawImage(buffer, 0, 0, this);
		bufferGraphics.dispose();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	int getStringWidth() {
		return stringWidth;
	}

	void setStringWidth(int stringWidth) {
		this.stringWidth = stringWidth;
	}

	int getStringHeight() {
		return stringHeight;
	}

	void setStringHeight(int stringHeight) {
		this.stringHeight = stringHeight;
	}

	Color getFontColor() {
		return fontColor;
	}

	void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	int getCanvasWidth () {
		return stringWidth;
	}
	
	int getCanvasHeight () {
		return (stringHeight + stringHeight/3);
	}
}
