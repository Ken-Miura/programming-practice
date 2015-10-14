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
	private static final String STRING_WIDTH_MESUREMENT = "00:00:00";
	
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
		stringWidth = fontMetrics.stringWidth(STRING_WIDTH_MESUREMENT);
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

	int getCanvasWidth () {
		return stringWidth;
	}
	
	int getCanvasHeight () {
		// 表示する数字の下のマージンを追加。値は見た目を調整しながら適当に。Fontの種類によってはマージンをfontMetrics.getDescent()に設定しないと足りないかも。
		return (stringHeight + stringHeight/3); 
	}
	
	void changeProperty (Font font, Color fontColor, Color backgroundColor, FontMetrics fontMetrics) {
		setFont(font);
		this.fontColor = fontColor;
		setBackground(backgroundColor);
		stringHeight = fontMetrics.getAscent();
		stringWidth = fontMetrics.stringWidth(STRING_WIDTH_MESUREMENT);
	}
}
