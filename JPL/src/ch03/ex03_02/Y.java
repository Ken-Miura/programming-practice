package ch03.ex03_02;

public class Y extends X {

	protected int yMask = 0xff00;

	public Y() {
		fullMask |= yMask;
		System.out.println("class Y field after constructor");
		System.out.printf("xMask: 0x%04x\n", xMask);
		System.out.printf("fullMask: 0x%04x\n", fullMask);
		System.out.printf("yMask: 0x%04x\n", yMask);
		System.out.println();
	}
	
	{
		System.out.println("class Y field after initialization");
		System.out.printf("xMask: 0x%04x\n", xMask);
		System.out.printf("fullMask: 0x%04x\n", fullMask);
		System.out.printf("yMask: 0x%04x\n", yMask);
		System.out.println();
	}		
	
	public static void main (String... args) {
		new Y();
	}
}
