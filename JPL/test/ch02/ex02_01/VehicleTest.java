package ch02.ex02_01;

import org.junit.Test;

public class VehicleTest {

	@Test(expected = NullPointerException.class)
	public void オーナーの名前がnullのときコンストラクタからヌルポが投げられることの確認() {
		new Vehicle(10, 10, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void スピードが負の値のときコンストラクタからIllegalArgumentExceptionが投げられることの確認() {
		new Vehicle(-1, 10, "test");
	}
	
	@Test(expected = NullPointerException.class)
	public void setOwnerNameでオーナーの名前がnullのときヌルポが投げられることの確認() {
		Vehicle v = new Vehicle(10, 10, "test");
		v.setOwnerName(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setSpeedでスピードが負の値のときIllegalArgumentExceptionが投げられることの確認() {
		Vehicle v = new Vehicle(1, 10, "test");
		v.setSpeed(-1);
	}
}
