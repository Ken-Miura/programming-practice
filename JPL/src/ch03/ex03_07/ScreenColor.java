package ch03.ex03_07;

/* ColorAttrでequalsとhashCodeを実装するために内容を独自に追加 */
public class ScreenColor {

	private final byte red;
	private final byte green;
	private final byte blue;
	private final byte alpha;

	public ScreenColor(Object value) {
		/* valueの定義が増えたとき、else ifで値の設定処理を追加 */
		if ((value instanceof String) && ((String) value).equals("transparent")) {
			red = Byte.MIN_VALUE;
			green = Byte.MIN_VALUE;
			blue = Byte.MIN_VALUE;
			alpha = Byte.MIN_VALUE;
		} else {
			// 黒
			red = Byte.MIN_VALUE;
			green = Byte.MIN_VALUE;
			blue = Byte.MIN_VALUE;
			alpha = Byte.MAX_VALUE;
		}
	}

	public byte getRed() {
		return red;
	}

	public byte getGreen() {
		return green;
	}

	public byte getBlue() {
		return blue;
	}

	public byte getAlpha() {
		return alpha;
	}
}
