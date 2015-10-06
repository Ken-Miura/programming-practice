package ch02.ex02_07;

import org.junit.Assert;
import org.junit.Test;

import ch02.ex02_07.Vehicle;

/* ex02_03.VehicleTestを修正 */
public class VehicleTest {

	@Test
	public void Vehicleインスタンス同士が異なるIDを保持することの確認() {
		Vehicle v1 = new Vehicle(1, 1.0, "test1");
		Vehicle v2 = new Vehicle(2, 2.0, "test2");
		
		Assert.assertNotEquals(v1.getID(), v2.getID());
	}
	
	/* ex02_07.VehicleTestで追加したメソッド */
	@Test
	public void デフォルトコンストラクタでの初期値の確認() {
		Vehicle v = new Vehicle();
		
		Assert.assertEquals(0, v.getSpeed(), 0);
		Assert.assertEquals(0.0, v.getDirection(), 0.0);
		Assert.assertEquals("unknown", v.getOwnerName());
	}

	@Test
	public void オーナーの名前がセットされているかの確認() {
		Vehicle v = new Vehicle("test");
		
		Assert.assertEquals("test", v.getOwnerName());
	}

}
