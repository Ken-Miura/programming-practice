package gui1_2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static final int FONT_SIZE = 120;
	private static final long INTERVAL = 500; /* ミリ秒単位 */
	private final int frameTop;
	private final int frameBottom;
	private final int frameLeft;
	private final int frameRight;
	private final Canvas canvas;
	private final Timer timer;
	private int canvasWidth;
	private int stringHeight;
	private int margin;
	private int canvasHeight;
	private Font font;
	private FontMetrics fontMetrics;
	private Image buffer;
	private Color fontColor;

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
		
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("menu");
		MenuItem menuItem = new MenuItem(PropertyDialog.TITLE);
		Dialog propertyDialog = new PropertyDialog(this);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				propertyDialog.setLocation(getLocation());
				propertyDialog.setVisible(true);
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		setMenuBar(menuBar);
		
		setVisible(true); /* ボーダーの長さを取得するために先に可視化 */
		Insets insets = getInsets();
		frameTop = insets.top;
		frameBottom = insets.bottom;
		frameLeft = insets.left;
		frameRight = insets.right;
		font = new Font(null, Font.PLAIN, FONT_SIZE);
		Graphics graphics = getGraphics();
		fontMetrics = graphics.getFontMetrics(font);
		graphics.dispose();
		canvasWidth = fontMetrics.stringWidth("00:00:00");
		int frameWidth = frameLeft + canvasWidth + frameRight;
		stringHeight = fontMetrics.getAscent(); /* 数字のみの表示で、ベースラインから上端までで十分なのでgetAscent */
		margin = stringHeight / 3; /* 数字の下側のマージン。値は見た目を調整しながら適当に */
		canvasHeight = stringHeight + margin;
		int frameHeight = frameTop + canvasHeight + frameBottom;
		setSize(frameWidth, frameHeight);
		fontColor = Color.BLACK;
		
		canvas = new Canvas() {
			
			{
				setFont(font);
			}
			@Override
			public void paint(Graphics graphics) {
				LocalTime localTime = LocalTime.now();
				buffer = createImage(canvasWidth, canvasHeight);
				Graphics bufferGraphics = buffer.getGraphics();
				if (bufferGraphics instanceof Graphics2D) {
					Graphics2D g2d = (Graphics2D) bufferGraphics;
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				}
				bufferGraphics.setColor(fontColor);
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
	
	int getMargin() {
		return margin;
	}

	void setMargin(int margin) {
		this.margin = margin;
	}

	int getCanvasWidth() {
		return canvasWidth;
	}

	void setCanvasWidth(int canvasWidth) {
		this.canvasWidth = canvasWidth;
	}

	int getStringHeight() {
		return stringHeight;
	}

	void setStringHeight(int stringHeight) {
		this.stringHeight = stringHeight;
	}

	int getCanvasHeight() {
		return canvasHeight;
	}

	void setCanvasHeight(int canvasHeight) {
		this.canvasHeight = canvasHeight;
	}
	
	Color getFontColor() {
		return fontColor;
	}

	void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
}
