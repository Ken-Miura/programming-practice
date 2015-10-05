package ch03.ex03_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class PassengerVehicleTest {

	@Test
	public void cloneメソッドで正しく複製できていることの確認() {
		PassengerVehicle pv1 = new PassengerVehicle("test", new Battery(3), 4);
		PassengerVehicle pv2 = pv1.clone();
		assertEquals(pv1.getNumOfSittingPeople(), pv2.getNumOfSittingPeople(), 0);
		assertEquals(pv1.getSeatingCapacity(), pv2.getSeatingCapacity(), 0);
	}

}
