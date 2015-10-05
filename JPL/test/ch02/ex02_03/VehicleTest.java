package ch02.ex02_03;

import org.junit.Assert;
import org.junit.Test;

public class VehicleTest {

	@Test
	public void Vehicleインスタンス同士が異なるIDを保持することの確認() {
		Vehicle v1 = new Vehicle(1, 1.0, "test1");
		Vehicle v2 = new Vehicle(2, 2.0, "test2");
		
		Assert.assertNotEquals(v1.getID(), v2.getID());
	}

}
