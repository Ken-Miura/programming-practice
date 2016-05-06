package ch22.ex22_12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Attr {

	public static interface Attributed {
		void add(Attr newAttr);

		Attr find(String attrName);

		Attr remove(String attrName);

		java.util.Iterator<Attr> attrs();
	}
	
	public static class AttributedImpl implements Attributed {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((attrs == null) ? 0 : attrs.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AttributedImpl other = (AttributedImpl) obj;
			if (attrs == null) {
				if (other.attrs != null)
					return false;
			} else if (!attrs.equals(other.attrs))
				return false;
			return true;
		}

		private final List<Attr> attrs = new ArrayList<>();
		
		@Override
		public void add(Attr newAttr) {
			attrs.add(newAttr);
		}

		@Override
		public Attr find(String attrName) {
			for (final Attr attr: attrs) {
				if (attr.getName().equals(attrName)) {
					return attr;
				}
			}
			return null;
		}

		@Override
		public Attr remove(String attrName) {
			for (final Attr attr: attrs) {
				attrs.remove(attr);
				return attr;
			}
			return null;
		}

		@Override
		public Iterator<Attr> attrs() {
			return attrs.iterator();
		}
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attr other = (Attr) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
