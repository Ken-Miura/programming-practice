/* Copyright (C) 2016 Ken Miura */
package gui2_4;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * ウィンドウを作成し、デジタル時計を描画するためのクラス
 * @author Ken
 *
 */
/* シリアライズして利用することを想定しない */
@SuppressWarnings("serial")
public final class DigitalClock extends JFrame implements PropertyChangeListener {
	
	private static final Preferences preferences = Preferences.userNodeForPackage(DigitalClock.class);
	private static final String PREFIX = "ken_miura_1102_swing_";
	private static final String FONT_NAME_KEY = PREFIX + "font_name_key";
	private static final String FONT_SIZE_KEY = PREFIX + "font_size_key";
	private static final String FONT_COLOR_KEY = PREFIX + "font_color_key";
	private static final String BACKGROUND_COLOR_KEY = PREFIX + "background_color_key";
	private static final String X_COORDINATE_KEY = PREFIX + "x_coordinate_key";
	private static final String Y_COORDINATE_KEY = PREFIX + "y_coordinate_key";
	
	// DEFAULT PROPERTY
	private static final String FONT_NAME = preferences.get(FONT_NAME_KEY, "Arial");
	private static final int FONT_SIZE = Integer.parseInt(preferences.get(FONT_SIZE_KEY, "100"));
	private static final Color FONT_COLOR = ColorNameConverter.convertNameToColor(preferences.get(FONT_COLOR_KEY, "BLACK"));
	private static final Color BACKGROUND_COLOR = ColorNameConverter.convertNameToColor(preferences.get(BACKGROUND_COLOR_KEY, "WHITE"));
	private static final int X_CORDINATE = Integer.parseInt(preferences.get(X_COORDINATE_KEY, "0"));
	private static final int Y_CORDINATE = Integer.parseInt(preferences.get(Y_COORDINATE_KEY, "0"));		
	
	private static final class DigitalClockCanvas extends JPanel {

		private int canvasWidth;
		private int stringHeight;
		private int canvasHeight;
		
		private Font font = Font.decode(FONT_NAME + " " + FONT_SIZE);
		private Color fontColor = FONT_COLOR;
		private final Graphics graphics;
		
		DigitalClockCanvas (Graphics graphics) {
			assert graphics != null;
			this.graphics = graphics;
			setFont(font);
			setBackground(BACKGROUND_COLOR);
			setCanvasWidthAndHeight(font);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}
			g.setColor(fontColor);
			LocalTime localTime = LocalTime.now();
			g.drawString(String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond()), 0, stringHeight);
		}

		public final int canvasWidth() {
			return canvasWidth;
		}

		public final int canvasHeight() {
			return canvasHeight;
		}

		public final Color getFontColor() {
			return fontColor;
		}
		
		public final void setFontColor (Color fontColor) {
			this.fontColor = fontColor;
		}
		
		@Override
		public void setFont(Font font) {
			this.font = font;
			super.setFont(font);
		}
		
		public final void setCanvasWidthAndHeight (Font font) {
			FontMetrics fm = graphics.getFontMetrics(font);
			canvasWidth = fm.stringWidth("00:00:00");
			stringHeight = fm.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
			int margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
			canvasHeight = stringHeight + margin;
		}
	}
	
	static final String FONT_EVT = "FONT_EVT";
	static final String FONT_SIZE_EVT = "FONT_SIZE_EVT";
	static final String FONT_COLOR_EVT = "FONT_COLOR_EVT";
	static final String BACKGROUND_COLOR_EVT = "BACKGROUND_COLOR_EVT";
	
	private static final String TITLE = "DIGITAL CLOCK";
	private static final long INTERVAL = 1000; /* ミリ秒単位 */
	private final PropertyDialog propertyDialog;
	private final DigitalClockCanvas canvas;
	private final Insets insets;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DigitalClock();
		});
	}

	/**
	 * ウィンドウを作成し、デジタル時計を描画する。<br>
	 * 時刻の更新のために、バックグラウンドで動くタイマースレッド（デーモンスレッド）を起動する。<br>
	 * ウィンドウのxボタンを押下すると、アプリケーションが終了する。
	 */
	private DigitalClock() {
		super(TITLE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		JMenuItem menuItem = new JMenuItem("property");
		propertyDialog = new PropertyDialog(this);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				propertyDialog.setLocation(getLocation());
				propertyDialog.setVisible(true);
			}
		});
		menu.add(menuItem);
		menubar.add(menu);
		setJMenuBar(menubar);
		
		setLocation(X_CORDINATE, Y_CORDINATE);
		
		setVisible(true); /* Graphics取得のためとボーダーの長さを取得するために先に可視化 */		
		canvas = new DigitalClockCanvas(getGraphics());
		add(canvas);		

		insets = getInsets();
		setSize(insets.left + canvas.canvasWidth() + insets.right, 
				insets.top + canvas.canvasHeight() + insets.bottom);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Font f = canvas.getFont();
				preferences.put(FONT_NAME_KEY, f.getName());
				preferences.put(FONT_SIZE_KEY, String.valueOf(f.getSize()));
				preferences.put(FONT_COLOR_KEY, ColorNameConverter.convertColorToName(canvas.getFontColor()));
				preferences.put(BACKGROUND_COLOR_KEY, ColorNameConverter.convertColorToName(canvas.getBackground()));
				preferences.put(X_COORDINATE_KEY, String.valueOf(getX()));
				preferences.put(Y_COORDINATE_KEY, String.valueOf(getY()));
			}
		});
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				canvas.repaint();
			}
		}, new Date(), INTERVAL);
	}
	
	public final Font canvasFont() {
		return canvas.getFont();
	}

	public final Color canvasFontColor() {
		return canvas.getFontColor();
	}

	public final Color canvasBackgroungColor() {
		return canvas.getBackground();
	}	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(FONT_EVT)) {
			Font selectedFont = (Font) evt.getNewValue();
			Font newFont = selectedFont.deriveFont((float) canvas.getFont().getSize());
			canvas.setFont(newFont);
			canvas.setCanvasWidthAndHeight(newFont);
		} else if (evt.getPropertyName().equals(FONT_SIZE_EVT)) {
			int selectedFontSize = (int) evt.getNewValue();
			Font newFont = canvas.getFont().deriveFont((float)selectedFontSize);
			canvas.setFont(newFont);
			canvas.setCanvasWidthAndHeight(newFont);
		} else if (evt.getPropertyName().equals(FONT_COLOR_EVT)) {
			Color selectedFontColor = (Color) evt.getNewValue();
			canvas.setFontColor(selectedFontColor);
		} else if (evt.getPropertyName().equals(BACKGROUND_COLOR_EVT)) {
			Color selectedBackgroundColor = (Color) evt.getNewValue();
			canvas.setBackground(selectedBackgroundColor);
		} else {
			throw new AssertionError("not to be passed");
		}
		setSize(insets.left + canvas.canvasWidth() + insets.right, 
				insets.top + canvas.canvasHeight() + insets.bottom);
	}
}
