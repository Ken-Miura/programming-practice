/* Copyright 2015 Ken Miura */
package gui2_3;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

final class DigitalClockWindow extends Window implements DigitalClockPropertyObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1330034521147739183L;
	
	private final PopupMenu digitalClockPopupMenu;
	
	private int xOnClicked = 0;
	private int yOnClicked = 0;
	
	private Image buffer;
	private int stringWidth;
	private int stringHeight;
	private Color fontColor;
	private static final String STRING_WIDTH_MESUREMENT = "00:00:00";

	private static final long INTERVAL = 500;

	/* 絵文字やプラットフォーム固有等のフォントを除外対象に */
	public static final String[] EXCLUDED_FONTS = { "Bookshelf Symbol 7", "MT Extra", "Wide Latin", "Viner Hand ITC", "Vladimir Script", "Webdings", "Wingdings", "Wingdings 2", "Wingdings 3" };
	
	DigitalClockWindow() throws HeadlessException {
		super(new Frame());
		setVisible(true);
		
		Font font = new Font(DigitalClockProperty.PROPERTY.getFontName(), Font.PLAIN, DigitalClockProperty.PROPERTY.getFontSize());
		setFont(font);
		FontMetrics fontMetrics = getFontMetrics(font);
		stringWidth = fontMetrics.stringWidth(STRING_WIDTH_MESUREMENT);
		stringHeight = fontMetrics.getAscent();
		fontColor = DigitalClockProperty.PROPERTY.getFontColor();		

		digitalClockPopupMenu = new DigitalClockPopupMenu(this);
		add(digitalClockPopupMenu);
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				xOnClicked = e.getX();
				yOnClicked = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// Do nothing
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					digitalClockPopupMenu.show(DigitalClockWindow.this, e.getX(), e.getY());	
				}
			}
		});
				
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - xOnClicked, e.getYOnScreen() - yOnClicked);
			}
		});
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, new Date(), INTERVAL);
		
		setSize(getWindowWidth(), getWindowHeight());
	}
	
	
	@Override
	public void paint(Graphics graphics) {
		LocalTime localTime = LocalTime.now();
		buffer = createImage(getWindowWidth(), getWindowHeight());
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

	int getWindowWidth () {
		return stringWidth;
	}
	
	int getWindowHeight () {
		// 表示する数字の下のマージンを追加。値は見た目を調整しながら適当に。Fontの種類によってはマージンをfontMetrics.getDescent()に設定しないと足りないかも。
		return (stringHeight + stringHeight/3); 
	}

	@Override
	public void notifyPropertyChanged(DigitalClockProperty newProperty) {
		Graphics graphics = getGraphics();
		Font font = Font.decode(newProperty.getFontName() + " " + newProperty.getFontSize());
		setFont(font);
		fontColor = newProperty.getFontColor();
		setBackground(newProperty.getBackgroungColor());
		FontMetrics fontMetrics = graphics.getFontMetrics(font);
		stringHeight = fontMetrics.getAscent();
		stringWidth = fontMetrics.stringWidth(STRING_WIDTH_MESUREMENT);	
		graphics.dispose();
		setSize(getWindowWidth(), getWindowHeight());
		toFront();
	}

}
