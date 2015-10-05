package ch03.ex03_06;

import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {

	@Test
	public void 動力源が空の時にstartがfalseを返すことの確認() {
		Vehicle v1 = new Vehicle("test", new Battery(0));
		if (v1.start()) {
			fail();
		} else {
			// success
		}
		Vehicle v2 = new Vehicle("test", new GasTank(0));
		if (v2.start()) {
			fail();
		} else {
			// success
		}
	}

	@Test
	public void 動力源が空でない時にstartがtrueを返すことの確認() {
		Vehicle v1 = new Vehicle("test", new Battery(1));
		if (v1.start()) {
			// success
		} else {
			fail();
		}
		Vehicle v2 = new Vehicle("test", new GasTank(1));
		if (v2.start()) {
			// success
		} else {
			fail();
		}
	}
}
