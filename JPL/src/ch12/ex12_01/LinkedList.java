/* Copyright 2015 Ken Miura */
package ch12.ex12_01;

import java.util.Objects;

/**
 * リスト構造の一つの要素を示すクラス。次の要素への参照がnullのときリスト構造の終端。
 */
public class LinkedList<E> {

	private E element;
	private LinkedList<E> next;

	public LinkedList(E element, LinkedList<E> next) {
		this.element = element;
		this.next = next;
	}

	public LinkedList(E element) {
		this(element, null);
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public LinkedList<E> next() {
		return next;
	}

	public void setNext(LinkedList<E> next) {
		this.next = next;
	}

	@Override
	public String toString() {
		StringBuilder listContents = null;
		if (element != null) {
			listContents = new StringBuilder("[element: " + element.toString() + "]");
		} else {
			listContents = new StringBuilder("[element: null]");
		}
		if (next != null) {
			listContents.append(next.toString());
		}
		return listContents.toString();
	}
	

	/**
	 * @return 自身をヘッドとしたときのリスト構造の長さ
	 */
	public final int size() {
		int size = 0;
		LinkedList<E> next = this;
		while (next != null) {
			size++;
			next = next.next();
		}
		return size;
	}
	
	/**
	 * 自身をヘッドとしたときのリスト内に特定のオブジェクトが存在するか検索する。
	 * @param seachTarget リスト内から検索したいオブジェクト
	 * @return seachTargetを含有するLinkedList
	 * @throws NullPointerException 
	 * 		seachTargetがnullのときスローされる。
	 * @throws ObjectNotFoundException
	 * 		seachTargetがリスト内に存在しないときスローされる。
	 */
	public LinkedList<E> find (E seachTarget) throws ObjectNotFoundException {
		Objects.requireNonNull(seachTarget, "seachTarget must not be null.");
		LinkedList<E> next = this;
		while (next != null) {
			if (seachTarget.equals(next.getElement())) {
				return next;
			}
			next = next.next();
		}
		/* オブジェクトが見つからなかったときは、nullではなく例外をスローする（nullを返して使う側に例外的状況を伝えると、使う側に例外的状況の対処を強制できないので） */
		throw new ObjectNotFoundException("No such a object[" + seachTarget + "] found in " + this +".", seachTarget);
	}
}
