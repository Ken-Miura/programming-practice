package ch05.ex05_01;

/* Attributed（Attrのコレクション）はAttrなしでは無意味な型なので
 * AttributedをAttrのネスとした型にすることは有用だと考えられる。 
 */
public class Attr {

	public static interface Attributed {
		void add(Attr newAttr);

		Attr find(String attrName);

		Attr remove(String attrName);

		java.util.Iterator<Attr> attrs();
	}

	private final String name;
	private Object value = null;

	public Attr(String name) {
		this.name = name;
	}

	public Attr(String name, Object value) {
		this.name = name;
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public Object setValue(Object newValue) {
		Object oldVal = value;
		value = newValue;
		return oldVal;
	}

	@Override
	public String toString() {
		return name + "='" + value + "'";
	}
}
