package gui1_1;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ウィンドウを作成し、デジタル時計を描画するためのクラス
 * @author Ken
 *
 */
/* シリアライズして利用することを想定しない */
@SuppressWarnings("serial")
public final class DigitalClock extends Frame {

	private static final String TITLE = "DIGITAL CLOCK";
	private static final int FONT_SIZE = 128;
	private static final long INTERVAL = 1000; /* ミリ秒単位 */
	private final int stringHeight;
	private final Canvas canvas;
	private final Timer timer;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
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
		Font font = new Font(null, Font.PLAIN, FONT_SIZE);
		setFont(font);
		FontMetrics fm = getGraphics().getFontMetrics(font);
		Insets insets = getInsets();
		int frameWidth = insets.left + fm.stringWidth("00:00:00") + insets.right;
		stringHeight = fm.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
		int margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
		int frameHeight = insets.top + stringHeight + margin + insets.bottom;
		setSize(frameWidth, frameHeight);
		
		canvas = new Canvas() {
			@Override
			public void paint(Graphics graphics) {
				LocalTime localTime = LocalTime.now();
				graphics.drawString(String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond()), 0, stringHeight);
			}
		};
		add(canvas);
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				canvas.repaint();
			}
		}, new Date(), INTERVAL);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.cancel();
				dispose();
				System.exit(0);
			}
		});
	}
}
