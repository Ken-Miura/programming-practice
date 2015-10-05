package ch06.ex06_04;

import static org.junit.Assert.*;

import org.junit.Test;

public class TrafficLightColorTest {

	@Test
	public void 定数ごとに正しいColorオブジェクトを保有しているかの確認() {
		TrafficLightColor t = TrafficLightColor.RED;
		assertEquals(Byte.MAX_VALUE, t.getColor().red, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().green, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().blue, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().alpha, 0);
		
		t = TrafficLightColor.GREEN;
		assertEquals(Byte.MIN_VALUE, t.getColor().red, 0);
		assertEquals(Byte.MAX_VALUE, t.getColor().green, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().blue, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().alpha, 0);
		
		t = TrafficLightColor.YELLOW;
		assertEquals(Byte.MAX_VALUE, t.getColor().red, 0);
		assertEquals(Byte.MAX_VALUE, t.getColor().green, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().blue, 0);
		assertEquals(Byte.MIN_VALUE, t.getColor().alpha, 0);
	}

}
