package gui1_3;

import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public final class DigitalClockWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1330034521147739183L;
	
	public DigitalClockWindow() throws HeadlessException {
		super(new Frame());
		
		PopupMenu pm = new PopupMenu("test");
		
		pm.add(new MenuItem("menu item test"));
		
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
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// Do nothing
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen(), e.getYOnScreen());
			}
		});
		
		setBackground(Color.BLACK);
		setSize(100, 100);
		setVisible(true);
		toFront();
	}
}
