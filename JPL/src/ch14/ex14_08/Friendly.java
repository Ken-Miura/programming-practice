/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_08;

/**
 * @author Ken Miura
 *
 */
class Friendly {

	private Friendly partner;
	private String name;
	
	public Friendly (String name) {
		this.name = name;
	}
	
	public synchronized void hug () {
		System.out.println(Thread.currentThread().getName() + " in " + name + ".hug() trying to invoke " + partner.name + ".hugback()");
		partner.hugBack();
	}
	
	private synchronized void hugBack() {
		System.out.println(Thread.currentThread().getName()+ " in " + name + ".hugBack()");
	}

	public void becomeFriend  (Friendly partner) {
		this.partner = partner;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final Friendly jareth = new Friendly("jareth");
		final Friendly cory = new Friendly("cory");
		
		jareth.becomeFriend(cory);
		cory.becomeFriend(jareth);
		あ
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;)
				jareth.hug();
			}
		}, "Thread1").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;)
				cory.hug();
			}
		}, "Thread2").start();
	}

}
