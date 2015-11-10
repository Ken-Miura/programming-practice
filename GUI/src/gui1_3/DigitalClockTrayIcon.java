package gui1_3;

import java.awt.AWTException;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

final class DigitalClockTrayIcon {

	private static final String ICON_PATH = "image/DukeAsKeith-daylightSmall.jpg";
	private static final String TOOLTIP_DESCRIPTION = "DIGITAL CLOCK";
	private final Dialog propertyDialog;
	
	private DigitalClockTrayIcon(DigitalClockPropertyObserver observer) {
		
		PopupMenu popup = new PopupMenu();
		MenuItem propertyMenu = new MenuItem("open dialog...");
		propertyDialog = new DigitalClockPropertyDialog(observer);
		propertyMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				propertyDialog.setLocationRelativeTo(null);
				propertyDialog.setVisible(true);	
			}
		});
		MenuItem exitMenu = new MenuItem("exit");
		exitMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popup.add(propertyMenu);
		popup.addSeparator();
		popup.add(exitMenu);
		URL url = getClass().getClassLoader().getResource(ICON_PATH);
		Image image = Toolkit.getDefaultToolkit().createImage(url);
		TrayIcon trayIcon = new TrayIcon(image, TOOLTIP_DESCRIPTION, popup);
		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println(e);
		}

	}
	
	static void createTrayIcon(DigitalClockPropertyObserver observer) {
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported.");
			return;
		}
		new DigitalClockTrayIcon(observer);
	}
}
