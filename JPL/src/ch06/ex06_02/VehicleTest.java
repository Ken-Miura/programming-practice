package ch06.ex06_02;

import org.junit.Assert;
import org.junit.Test;

public class VehicleTest {

	@Test
	public void 引数の型がTURNのときturnメソッドが正しい値を設定できていることの確認() {
		Vehicle v = new Vehicle();
		double expectedDirection = Math.PI / 2;
		v.turn(Vehicle.TURN.LEFT);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);

		expectedDirection = 0.0;
		v.turn(Vehicle.TURN.RIGHT);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);
	}

}
