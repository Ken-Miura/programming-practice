/* Copyright (C) 2016 Ken Miura */
package ch20.ex20_07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public final class Attr {

	private static final String DELIMITER = ",";
	private final String name;
	private Object value = null;

	public Attr(String name) {
		this.name = name;
	}

	public Attr(String name, Object value) {
		if (name.contains(DELIMITER)) {
			throw new IllegalArgumentException(DELIMITER + " cannot be used for name");
		}
		this.name = name;
		this.setValue(value);
	}
	
	public Attr(InputStream in) throws IOException {
		Objects.requireNonNull(in, "in must not be null");
		try (DataInputStream dataInputStream = new DataInputStream(in)) {
			char[] c = new char[dataInputStream.readInt()];
			for (int i=0; i<c.length; i++) {
				c[i] = dataInputStream.readChar();
			}
			String s = new String(c);
			String[] nameAndValue = s.split(DELIMITER);
			name = nameAndValue[0];
			value = nameAndValue[1];
		}
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
	
	public void writeAttr(OutputStream out) throws IOException {
		Objects.requireNonNull(out, "out must not be null");
		if (!(value instanceof String)) {
			throw new IllegalStateException("Illegal value type");
		}
		try(DataOutputStream dataOutputStream = new DataOutputStream(out)) {
			String s = getName() + DELIMITER + getValue();
			dataOutputStream.writeInt(s.length());
			dataOutputStream.writeChars(s);
		}
	}
}
