package ch02.ex02_18;

import static org.junit.Assert.*;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;

import ch02.ex02_18.Vehicle;

/* ch02.ex02_17.VehicleTestを修正 */
public class VehicleTest {

	@Test
	public void Vehicleインスタンス同士が異なるIDを保持することの確認() {
		Vehicle v1 = new Vehicle(1, 1.0, "test1");
		Vehicle v2 = new Vehicle(2, 2.0, "test2");

		Assert.assertNotEquals(v1.getID(), v2.getID());
	}

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

	@Test
	public void 最後に使ったIDの値を正しく取得できているかの確認() {

		try {
			Class<?> clazz = Class.forName("ch02.ex02_18.Vehicle");
			Field field = clazz.getDeclaredField("nextID");
			field.setAccessible(true);
			field.set(null, 0L);
		} catch (Exception e) {
			fail();
		}

		Assert.assertEquals(-1, Vehicle.getLastUsedID(), 0);

		new Vehicle("test1");
		Assert.assertEquals(0, Vehicle.getLastUsedID(), 0);

		new Vehicle("test2");
		Assert.assertEquals(1, Vehicle.getLastUsedID(), 0);
	}

	@Test
	public void toStringが正しい値を返すかの確認() {
		long ID = Vehicle.getLastUsedID() + 1;
		String ownerName = "test1";
		int speed = 1;
		double direction = 1.0;

		Vehicle v1 = new Vehicle(speed, direction, ownerName);
		String expected = "ID: " + ID + " Owner Name: " + ownerName
				+ " Speed: " + speed + " Direction: " + direction;

		Assert.assertEquals(expected, v1.toString());
	}

	@Test
	public void セッターが正しい値を設定しているか確認() {
		Vehicle v = new Vehicle();

		double expectedDirection = 1.2;
		v.setDirection(expectedDirection);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);

		int expectedSpeed = 40;
		v.setSpeed(expectedSpeed);
		Assert.assertEquals(expectedSpeed, v.getSpeed(), 0);

		String expectedName = "test";
		v.setOwnerName(expectedName);
		Assert.assertEquals(expectedName, v.getOwnerName());
	}

	@Test
	public void スピードの変更ができているかの確認() {
		Vehicle v = new Vehicle();

		int expected = 60;
		v.changeSpeed(expected);
		Assert.assertEquals(expected, v.getSpeed(), 0);
	}

	@Test
	public void スピードが0にできているかの確認() {
		Vehicle v = new Vehicle();
		v.changeSpeed(20);

		v.stop();
		Assert.assertEquals(0, v.getSpeed(), 0);
	}

	@Test
	public void 引数の型がdoubleのときのturnメソッドが正しい値を設定できていることの確認() {
		Vehicle v = new Vehicle();
		double expectedDirection = 1.2;
		v.turn(expectedDirection);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);
	}

	@Test
	public void 引数の型がintのときのturnメソッドが正しい値を設定できていることの確認() {
		Vehicle v = new Vehicle();
		double expectedDirection = Math.PI / 2;
		v.turn(Vehicle.TURN_LEFT);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);

		expectedDirection = 0.0;
		v.turn(Vehicle.TURN_RIGHT);
		Assert.assertEquals(expectedDirection, v.getDirection(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 引数の型がintのときのturnメソッドがIllegalArgumentExceptionをスローすることの確認() {
		Vehicle v = new Vehicle();
		v.turn(100);
	}

	@Test
	public void オーナーの名前を含んだVehicleのtoStringが出力することの確認() {
		String actual = null;
		String expected = "ID: 0 Owner Name: test Speed: 0 Direction: 0.0";
		try {
			Class<?> clazz = Class.forName("ch02.ex02_18.Vehicle");

			Field field = clazz.getDeclaredField("nextID");
			field.setAccessible(true);
			field.set(null, 0L);

			Method method = clazz.getDeclaredMethod("vehicleToString",
					String.class);
			method.setAccessible(true);
			actual = (String) method.invoke(null, "test");
		} catch (Exception e) {
			fail();
		}
		Assert.assertEquals(expected, actual);
	}
}
