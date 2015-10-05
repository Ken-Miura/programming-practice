package ch01.ex01_08;

import java.util.Objects;

public class Point {
	public double x, y;
	public static Point origin = new Point();

	public double distancePoint(Point that) {
		double xdiff = this.x - that.x;
		double ydiff = this.y - that.y;
		return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	public void clear() {
		this.x = 0.0;
		this.y = 0.0;
	}

	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/** ch01.ex01_08で追加したメソッド */
	public void setPoint(Point p){
		Objects.requireNonNull(p, "p must not be null");
		this.x = p.x;
		this.y = p.y;
	}
}
