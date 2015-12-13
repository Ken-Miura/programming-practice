/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_09;

import java.util.Objects;

/**
 * @author Ken Miura
 *
 */
public class DisplayThreadGroup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadGroup tg1 = new ThreadGroup("tg1");
		ThreadGroup tg2 = new ThreadGroup(tg1, "tg2");
		ThreadGroup tg3 = new ThreadGroup(tg1, "tg3");
		ThreadGroup tg4 = new ThreadGroup(tg2, "tg4");
		ThreadGroup tg5 = new ThreadGroup(tg1, "tg5");
		ThreadGroup tg6 = new ThreadGroup(tg5, "tg6");
		displayThreadGroup(tg1);
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
