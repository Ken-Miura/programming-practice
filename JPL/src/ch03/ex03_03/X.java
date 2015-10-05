package ch03.ex03_03;

public class X {
	protected int xMask = 0x00ff;
	protected int fullMask;
	
	public X() {
		System.out.println("class X constructor");
		System.out.printf("yMask: 0x%04x\n", Y.yMask);
		System.out.println();
		fullMask = xMask;
	}

	public int mask(int orig) {
		return (orig & fullMask);
	}
}
