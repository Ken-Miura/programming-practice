/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_02;

/**
 * @author Ken Miura
 *
 */
class PrintServer implements Runnable {
	
	/* 外部のスレッドからアクセスできないことのテスト */
	public static void main (String... args) {
		PrintServer printServer = new PrintServer();
		printServer.run();
	}	
	
	private final PrintQueue requests = new PrintQueue();
	private final long threadId ;
	public PrintServer() {
		Thread thread = new Thread(this);
		threadId = thread.getId();
		thread.start();
	}
	public void print (PrintJob job) {
		requests.add(job);
	}
	@Override
	public void run() {
		if (Thread.currentThread().getId() != threadId) {
			System.err.println("Only inner thread can be access.");
			return;
		}
		for (;;)
			try {
				realPrint(requests.remove());
			} catch (InterruptedException e) {
				return;
			}
	}
	private void realPrint (PrintJob job) {
		// do Print.
	}
}
