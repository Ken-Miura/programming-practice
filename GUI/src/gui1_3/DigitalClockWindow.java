package gui1_3;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public final class DigitalClockWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1330034521147739183L;
	
	private int x = 0;
	private int y = 0;
	
	private Image buffer;
	private Color fontColor;

	private int stringWidth;

	private int stringHeight;
	private static final String STRING_WIDTH_MESUREMENT = "00:00:00";
	
	public DigitalClockWindow() throws HeadlessException {
		super(new Frame());
		
		PopupMenu pm = new PopupMenu("test");
		
		pm.add(new MenuItem("menu item test"));
		pm.addSeparator();
		MenuItem exit = new MenuItem("exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pm.add(exit);
		add(pm);
	
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// Do nothing
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
				if (e.getButton()==MouseEvent.BUTTON3) {
					pm.show(DigitalClockWindow.this, e.getPoint().x, e.getPoint().y);	
				}
			}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
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
				// Do nothing
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
			}
		});
		
		setVisible(true);
		int DEFAULT_FONT_SIZE = 60;
		Font font = new Font("Monospace", Font.PLAIN, DEFAULT_FONT_SIZE );
		setFont(font);
		Graphics graphics = getGraphics();
		FontMetrics fontMetrics = graphics.getFontMetrics(font);
		stringWidth = fontMetrics.stringWidth(STRING_WIDTH_MESUREMENT);
		stringHeight = fontMetrics.getAscent();
		fontColor = Color.BLACK;
		
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, new Date(), 1000);
		
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
	
	private int getWindowWidth() {
		return stringWidth;
	}

	private int getWindowHeight() {
		return (stringHeight + stringHeight/3); 
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
