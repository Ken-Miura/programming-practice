package ch03.ex03_01;

import org.junit.Test;

public class PassengerVehicleTest {

	@Test(expected=IllegalArgumentException.class)
	public void コンストラクタで座席数より座っている数を多くセットしようとしたら例外が投げられることの確認() {
		new PassengerVehicle("A", 2, 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void オーバーロードされたコンストラクタで座席数より座っている数を多くセットしようとしたら例外が投げられることの確認() {
		new PassengerVehicle(10, 1.1, "A", 2, 3);
	}

	@Test(expected=IllegalArgumentException.class)
	public void 座席数より座っている数を多くセットしようとしたら例外が投げられることの確認() {
		PassengerVehicle pv = new PassengerVehicle("A", 2);
		pv.setNumOfSittingPeople(3);
	}
}
