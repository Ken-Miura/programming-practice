/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_09;

import java.util.Objects;
import java.util.Random;

/**
 * @author Ken Miura
 *
 */
public class DisplayThreadGroup {

	private static final Runnable shortTask = new Runnable() {
		@Override
		public void run () {
			for (int i=0; i<3; i++) {
				try {
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	};
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		ThreadGroup tg1 = new ThreadGroup("tg1");
		ThreadGroup tg2 = new ThreadGroup(tg1, "tg2");
		ThreadGroup tg3 = new ThreadGroup(tg1, "tg3");
		ThreadGroup tg4 = new ThreadGroup(tg2, "tg4");
		ThreadGroup tg5 = new ThreadGroup(tg1, "tg5");
		ThreadGroup tg6 = new ThreadGroup(tg5, "tg6");
		displayThreadGroup(tg1);
		
		for (;;) {
			new Thread (tg1, shortTask, "thread 1").start();
			new Thread (tg2, shortTask, "thread 2").start();
			new Thread (tg3, shortTask, "thread 3").start();
			new Thread (tg4, shortTask, "thread 4").start();
			new Thread (tg5, shortTask, "thread 5").start();
			new Thread (tg6, shortTask, "thread 6").start();
			Thread.sleep(5000);
		}
	}

	public static void displayThreadGroup(final ThreadGroup threadGroup) {
		Objects.requireNonNull(threadGroup);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					for(;;) {
						displayHierarchically(threadGroup, 0);
						Thread.sleep(1000);	
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}).start();
	}
	
	private static void displayHierarchically (final ThreadGroup threadGroup, int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<indent; i++) {
			sb.append('	');
		}
		System.out.print(sb.toString() + "ThreadGroup name: " + threadGroup.getName());
		Thread[] threads = new Thread[threadGroup.activeCount()];
		int threadsNum = threadGroup.enumerate(threads, false);
		System.out.print(" has {");
		for (int i=0; i<threadsNum; i++) {
			System.out.print("Thread name: " + threads[i].getName() + ", ");
		}
		System.out.println("}");
		
		ThreadGroup[] threadGroups = new ThreadGroup[threadGroup.activeGroupCount()];
		int threadGroupNum = threadGroup.enumerate(threadGroups, false);
		for (int i=0; i<threadGroupNum; i++) {
			displayHierarchically(threadGroups[i], indent + 1);
		}
	}
}
