package ch01.ex01_10;

public class Element {

	private int number = 0;
	private boolean even = true;

	public void setNumber(int number) {
		this.number = number;
		if (number % 2 == 0) {
			this.even = true;
		} else {
			this.even = false;
		}
	}

	public int getNumber() {
		return number;
	}

	public boolean isEven() {
		return even;
	}
}
