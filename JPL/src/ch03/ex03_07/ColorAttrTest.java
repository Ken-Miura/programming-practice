package ch03.ex03_07;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColorAttrTest {

	@Test
	public void 同一のオブジェクトを指しているときequalsメソッドがtrueを返す() {
		ColorAttr colorAttr1 = new ColorAttr("test");
		ColorAttr colorAttr2 = colorAttr1;
		if (colorAttr1.equals(colorAttr2)) {
			// success
		} else {
			fail();
		}
	}

	@Test
	public void 同値のオブジェクト同士に対してequalsメソッドがtrueを返す() {
		ColorAttr colorAttr1 = new ColorAttr("test1", "other");
		ColorAttr colorAttr2 = new ColorAttr("test2", "other");
		if (colorAttr1.equals(colorAttr2)) {
			// success
		} else {
			fail();
		}
	}

	@Test
	public void 同値でないオブジェクト同士に対してequalsメソッドがfalseを返す() {
		ColorAttr colorAttr1 = new ColorAttr("test1", "transparent");
		ColorAttr colorAttr2 = new ColorAttr("test2", "other");
		if (colorAttr1.equals(colorAttr2)) {
			fail();
		} else {
			// success
		}
	}

	@Test
	public void 同一のオブジェクトを指しているときhashCodeメソッドが同じ値を返す() {
		ColorAttr colorAttr1 = new ColorAttr("test");
		ColorAttr colorAttr2 = colorAttr1;
		if (colorAttr1.hashCode() == colorAttr2.hashCode()) {
			// success
		} else {
			fail();
		}
	}

	@Test
	public void 同値のオブジェクト同士に対してhashCodeメソッドが同じ値を返す() {
		ColorAttr colorAttr1 = new ColorAttr("test1", "other");
		ColorAttr colorAttr2 = new ColorAttr("test2", "other");
		if (colorAttr1.hashCode() == colorAttr2.hashCode()) {
			// success
		} else {
			fail();
		}
	}
}
