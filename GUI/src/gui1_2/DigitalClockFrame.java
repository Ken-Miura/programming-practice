package gui1_2;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class DigitalClockFrame extends Frame {

	/**
	 * version 1.0
	 */
	private static final long serialVersionUID = -3945485273578642505L;
	private static final String TITLE = "DIGITAL CLOCK";
	private static final long INTERVAL = 500; /* ミリ秒単位 */
	private final DigitalClockCanvas digitalClockCanvasCanvas;
	private final Timer timer;
	private final Dialog propertyDialog;
	
	private final MenuBar menuBar;
	private final Menu menu;
	private final MenuItem menuItem;

	DigitalClockFrame () {
		super(TITLE);
		setResizable(false);
		menuBar = new MenuBar();
		menu = new Menu("menu");
		menuItem = new MenuItem(PropertyDialog.TITLE);
		propertyDialog = new PropertyDialog(this);
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
		setVisible(true);
		
		digitalClockCanvasCanvas = new DigitalClockCanvas(this);
		add(digitalClockCanvasCanvas);
		
		Insets insets = getInsets();
		setSize(insets.right + digitalClockCanvasCanvas.getCanvasWidth() + insets.right, 
				insets.top + digitalClockCanvasCanvas.getCanvasHeight() + insets.bottom);
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				digitalClockCanvasCanvas.repaint();
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
	
	DigitalClockCanvas getDigitalClockCanvas() {
		return digitalClockCanvasCanvas;
	}
}
