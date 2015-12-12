/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_08;

/**
 * @author Ken Miura
 *
 */
class FriendlyAvoidingDeadlock {

	private FriendlyAvoidingDeadlock partner;
	private String name;
	
	public FriendlyAvoidingDeadlock (String name) {
		this.name = name;
	}
	
	public void hug () {
		synchronized (FriendlyAvoidingDeadlock.class) {
			System.out.println(Thread.currentThread().getName() + " in " + name + ".hug() trying to invoke " + partner.name + ".hugback()");
			partner.hugBack();
		}
	}
	
	private synchronized void hugBack() {
		synchronized (FriendlyAvoidingDeadlock.class) {
			System.out.println(Thread.currentThread().getName()+ " in " + name + ".hugBack()");	
		}
	}

	public void becomeFriend  (FriendlyAvoidingDeadlock partner) {
		this.partner = partner;
	}
	
	public static void main(String[] args) {

		final FriendlyAvoidingDeadlock jareth = new FriendlyAvoidingDeadlock("jareth");
		final FriendlyAvoidingDeadlock cory = new FriendlyAvoidingDeadlock("cory");
		
		jareth.becomeFriend(cory);
		cory.becomeFriend(jareth);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;) // 繰り返し起動の手間を省くため無限ループ
					jareth.hug();
			}
		}, "Thread1").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (;;) // 繰り返し起動の手間を省くため無限ループ
					cory.hug();
			}
		}, "Thread2").start();
	}

}
