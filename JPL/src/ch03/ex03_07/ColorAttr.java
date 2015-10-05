package ch03.ex03_07;

public class ColorAttr extends Attr {

	private ScreenColor myColor;

	public ColorAttr(String name, Object value) {
		super(name, value);
		decodeColor();
	}

	public ColorAttr(String name) {
		this(name, "transparent");
	}

	public ColorAttr(String name, ScreenColor value) {
		this(name, value.toString());
		myColor = value;
	}

	@Override
	public Object setValue(Object newValue) {
		Object retval = super.setValue(newValue);
		decodeColor();
		return retval;
	}

	public Object setValue(ScreenColor newValue) {
		super.setValue(newValue.toString());
		ScreenColor oldValue = myColor;
		myColor = newValue;
		return oldValue;
	}

	public ScreenColor getColor() {
		return myColor;
	}

	protected void decodeColor() {
		if (getValue() == null)
			myColor = null;
		else
			myColor = new ScreenColor(getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof ColorAttr) {
			ScreenColor screenColor = this.getColor();
			ScreenColor targetScreenColor = ((ColorAttr) obj).getColor();
			if (screenColor.getRed() == targetScreenColor.getRed()
					&& screenColor.getGreen() == targetScreenColor.getGreen()
					&& screenColor.getBlue() == targetScreenColor.getBlue()
					&& screenColor.getAlpha() == targetScreenColor.getAlpha()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.getColor().getRed();
		result = 31 * result + this.getColor().getGreen();
		result = 31 * result + this.getColor().getBlue();
		result = 31 * result + this.getColor().getAlpha();
		return result;  
	}
}
