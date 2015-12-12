/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_07;

/**
 * @author Ken Miura
 *
 */
class Babble extends Thread {
	static boolean doYield;
	static int howOften;
	private String word;
	
	Babble (String whatToSay) {
		word = whatToSay;
	}
	
	@Override
	public void run() {
		for (int i=0; i<howOften; i++) {
			System.out.println(word);
			if (doYield)
				Thread.yield();
		}
	}
	
	/*
	 * Windows 8.1 64bit, Java version 8u40
	 * 引数trueのとき、10回中10回Did, DidNot, DidNot, Didの順
	 * 引数falseのとき、10回中8回Did, Did, DidNot, DidNotの順, 2回DidNot, DidNot, Did, Didの順
	 */
	public static void main(String[] args) {
		doYield = new Boolean(args[0]).booleanValue();
		howOften = Integer.parseInt(args[1]);
		
		for (int i=2; i<args.length; i++)
			new Babble (args[i]).start();
	}

}
