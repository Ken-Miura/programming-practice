/* Copyright (C) 2015 Ken Miura */
package gui1_4;

import java.awt.EventQueue;
import java.lang.Thread.UncaughtExceptionHandler;

public final class DigitalClock {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				EventQueue.invokeLater(() -> {
					ErrorMessageDialog.showErrorMessage("Exception has occured in Thread: " + t.getName() + ". Cause: " + e.toString());
				});	
			}
		});
		
		EventQueue.invokeLater(() -> {
			Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler () {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					ErrorMessageDialog.showErrorMessage("Exception has occured in EDT. Cause: " + e.toString());
				}
				
			});
			new DigitalClockFrame();
		});
	}
}
