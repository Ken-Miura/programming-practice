/* Copyright (C) 2015 Ken Miura */
package ch11.ex11_02;

/**
 * @author Ken Miura
 * Attrクラスのコレクション
 */
public interface Attributed<E> {

	void add(Attr<E> newAttr);

	Attr<E> find(String attrName);

	Attr<E> remove(String attrName);

	java.util.Iterator<Attr<E>> attrs();
}
