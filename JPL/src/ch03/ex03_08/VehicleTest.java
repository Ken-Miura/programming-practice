package ch03.ex03_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class VehicleTest {

	@Test
	public void cloneメソッドで正しく複製されていることの確認() {
		Vehicle v1 = new Vehicle("test", new GasTank(5));
		Vehicle v2 = v1.clone();
		assertEquals(v1.getDirection(), v2.getDirection(), 0);
		assertEquals(v1.getID(), v2.getID(), 0);
		assertEquals(v1.getOwnerName(), v2.getOwnerName());
		assertEquals(v1.getSpeed(), v2.getSpeed(), 0);
		assertEquals(v1.getEnergySource().observeRemainingEnergy(), 
						v2.getEnergySource().observeRemainingEnergy(), 0);
	}

}
