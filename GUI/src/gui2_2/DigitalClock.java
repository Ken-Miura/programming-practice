/* Copyright (C) 2016 Ken Miura */
package gui2_2;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

	private static final class DigitalClockCanvas extends JPanel {

		private int canvasWidth;
		private int stringHeight;
		private int canvasHeight;
		
		private Font font = new Font(null, Font.PLAIN, FONT_SIZE);
		private Color fontColor = Color.BLACK;
		private Color backgroungColor = Color.WHITE;
		private final Graphics graphics;
		
		DigitalClockCanvas (Graphics graphics) {
			assert graphics != null;
			setFont(font);
			setBackground(backgroungColor);
			this.graphics = graphics;
			FontMetrics fm = this.graphics.getFontMetrics(font);
			canvasWidth = fm.stringWidth("00:00:00");
			stringHeight = fm.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
			int margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
			canvasHeight = stringHeight + margin;
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

		public final Font getFont() {
			return font;
		}

		public final Color getFontColor() {
			return fontColor;
		}

		public final Color getBackgroungColor() {
			return backgroungColor;
		}
	}
	
	private static final String TITLE = "DIGITAL CLOCK";
	private static final int FONT_SIZE = 128;
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
		
		setVisible(true); /* Graphics取得のためとボーダーの長さを取得するために先に可視化 */		
		canvas = new DigitalClockCanvas(getGraphics());
		add(canvas);		

		insets = getInsets();
		setSize(insets.left + canvas.canvasWidth() + insets.right, 
				insets.top + canvas.canvasHeight() + insets.bottom);
		
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
		return canvas.getBackgroungColor();
	}	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
