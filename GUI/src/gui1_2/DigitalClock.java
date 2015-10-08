package gui1_2;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
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
	private static final long INTERVAL = 500; /* ミリ秒単位 */
	private final int frameTop;
	private final int frameBottom;
	private final int frameLeft;
	private final int frameRight;
	private final Canvas canvas;
	private final Timer timer;
	private int canvasWidth;
	private int canvasHeight;
	private Font font;
	private FontMetrics fontMetrics;
	private Image buffer;

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
		Insets insets = getInsets();
		frameTop = insets.top;
		frameBottom = insets.bottom;
		frameLeft = insets.left;
		frameRight = insets.right;
		font = new Font(null, Font.PLAIN, FONT_SIZE);
		setFont(font);
		fontMetrics = getGraphics().getFontMetrics(font);
		canvasWidth = fontMetrics.stringWidth("00:00:00");
		int frameWidth = frameLeft + canvasWidth + frameRight;
		int stringHeight = fontMetrics.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
		int margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
		canvasHeight = stringHeight + margin;
		int frameHeight = frameTop + canvasHeight + frameBottom;
		setSize(frameWidth, frameHeight);
		
		canvas = new Canvas() {
			@Override
			public void paint(Graphics graphics) {
				LocalTime localTime = LocalTime.now();
				buffer = createImage(canvasWidth, canvasHeight);
				Graphics bufferGraphics = buffer.getGraphics();
				bufferGraphics.drawString(String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond()), 0, stringHeight);
				graphics.drawImage(buffer, 0, 0, canvas);
				bufferGraphics.dispose();
			}
			
			@Override
			public void update(Graphics g) {
				paint(g);
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
