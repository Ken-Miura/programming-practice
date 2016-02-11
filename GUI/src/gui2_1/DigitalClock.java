/* Copyright (C) 2016 Ken Miura */
package gui2_1;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * ウィンドウを作成し、デジタル時計を描画するためのクラス
 * @author Ken
 *
 */
/* シリアライズして利用することを想定しない */
@SuppressWarnings("serial")
public final class DigitalClock extends JFrame {

	private static final class DigitalClockCanvas extends JPanel {

		/**
		 * Ver 1.0
		 */
		private static final long serialVersionUID = -546560808092491620L;
		private final int frameWidth;
		private final int stringHeight;
		private final int frameHeight;
		
		DigitalClockCanvas (Insets insets, Graphics g) {
			setBackground(Color.WHITE);
			Font font = new Font(null, Font.PLAIN, FONT_SIZE);
			setFont(font);
			FontMetrics fm = g.getFontMetrics(font);
			frameWidth = insets.left + fm.stringWidth("00:00:00") + insets.right;
			stringHeight = fm.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
			int margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
			frameHeight = insets.top + stringHeight + margin + insets.bottom;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}
			LocalTime localTime = LocalTime.now();
			g.drawString(String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond()), 0, stringHeight);
		}

		public final int getFrameWidth() {
			return frameWidth;
		}

		public final int getFrameHeight() {
			return frameHeight;
		}
	}
	
	private static final String TITLE = "DIGITAL CLOCK";
	private static final int FONT_SIZE = 128;
	private static final long INTERVAL = 1000; /* ミリ秒単位 */
	private final DigitalClockCanvas canvas;
	private final Timer timer;

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
		setVisible(true); /* ボーダーの長さを取得するために先に可視化 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = getInsets();
		
		canvas = new DigitalClockCanvas(insets, getGraphics());
		add(canvas);		

		setSize(canvas.getFrameWidth(), canvas.getFrameHeight());
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				canvas.repaint();
			}
		}, new Date(), INTERVAL);		
	}
}
