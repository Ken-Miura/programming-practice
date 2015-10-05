package ch06.ex06_05;

public final class Color {
	
	public static final Color RED = new Color(Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE);
	public static final Color GREEN = new Color(Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE);
	public static final Color YELLOW = new Color(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE);	
	
	public final byte red;
	public final byte green;
	public final byte blue;
	public final byte alpha;

	public Color(byte red, byte green, byte blue, byte alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}
