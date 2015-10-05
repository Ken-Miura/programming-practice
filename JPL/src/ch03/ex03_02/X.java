package ch03.ex03_02;

public class X {
	protected int xMask = 0x00ff;
	protected int fullMask;
	
	public X() {
		fullMask = xMask;
		System.out.println("class X field after constructor");
		System.out.printf("xMask: 0x%04x\n", xMask);
		System.out.printf("fullMask: 0x%04x\n", fullMask);
		System.out.println();
	}

	public int mask(int orig) {
		return (orig & fullMask);
	}
	
	{
		System.out.println("class X field after initialization");
		System.out.printf("xMask: 0x%04x\n", xMask);
		System.out.printf("fullMask: 0x%04x\n", fullMask);
		System.out.println();
	}
}
