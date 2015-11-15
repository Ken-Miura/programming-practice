/* Copyright (C) 2015 Ken Miura */
package ch11.ex11_02;

public class Attr <E> {
	
	private final String name;
	private E value = null;

	public Attr(String name) {
		this.name = name;
	}

	public Attr(String name, E value) {
		this.name = name;
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public E getValue() {
		return value;
	}

	public final E setValue(E newValue) {
		E oldVal = value;
		value = newValue;
		return oldVal;
	}

	@Override
	public String toString() {
		return name + "='" + value + "'";
	}
}
