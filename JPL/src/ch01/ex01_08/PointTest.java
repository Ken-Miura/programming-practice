package ch01.ex01_08;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {

	@Test
	public void setPointで引数のPointのxとyの値が自身のxとyコピーされている() {
		double expectedX = 1.0;
		double expectedY = 2.0;
		Point p1 = new Point();
		p1.x = expectedX;
		p1.y = expectedY;
		
		Point p2 = new Point();
		p2.setPoint(p1);
		
		Assert.assertEquals(expectedX, p2.x, 0.0);
		Assert.assertEquals(expectedY, p2.y, 0.0);
	}

	@Test (expected = NullPointerException.class)
	public void setPointの引数がnullのときNullPointerExceptionがスローされる() {
		Point p = new Point();
		p.setPoint(null);
	}
}
