/* Copyright (C) 2015 Ken Miura */
package ch14.ex14_02;

/**
 * @author Ken Miura
 *
 */
class SingleLinkQueue<E> {
	class Cell {
		private Cell next;
		private E element;
		public Cell (E element) {
			this.element = element;
		}
		public Cell (E element, Cell next) {
			this.element = element;
			this.next = next;
		}
		public E getElement () {
			return element;
		}
		public Cell getNext() {
			return next;
		}
		public void setNext(Cell next) {
			this.next = next;
		}
		public void setElement(E element) {
			this.element = element;
		}
	}
	
	protected Cell head;
	protected Cell tail;
	
	public void add (E item) {
		Cell cell = new Cell(item);
		if (tail==null) 
			head = tail = cell;
		else {
			tail.setNext(cell);
			tail = cell;
		}
	}
	
	public E remove() {
		if (head == null)
			return null;
		Cell cell = head;
		head = head.getNext();
		if (head == null)
			tail = null;
		return cell.getElement();
	}
	
	public int size () {
		Cell head = this.head;
		int num = 0;
		while (head != null) {
			num++;
			head = head.getNext();
		}
		return num;
	}
}
