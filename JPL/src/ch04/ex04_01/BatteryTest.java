package ch04.ex04_01;

import static org.junit.Assert.*;

import org.junit.Test;

public class BatteryTest {

	@Test
	public void インスタンス生成時に設定した値が反映されていることの確認() {
		int expected = 1;
		EnergySource energySource = new Battery(expected);
		assertEquals(expected, energySource.observeRemainingEnergy(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値でインスタンスが生成できないことの確認() {
		int negative = -1;
		new Battery(negative);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値をセットできないことの確認() {
		int negative = -1;
		EnergySource energySource = new Battery(0);
		energySource.fill(negative);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値を取得できないことの確認() {
		int negative = -1;
		EnergySource energySource = new Battery(1);
		energySource.retrieve(negative);
		fail();
	}

	@Test
	public void powerが0のときemptyがtrueを返すことの確認() {
		EnergySource energySource = new Battery(0);
		if (energySource.empty()) {
			// success
		} else {
			fail();
		}
	}

	@Test
	public void powerが正のときemptyがfalseを返すことの確認() {
		EnergySource energySource = new Battery(1);
		if (energySource.empty()) {
			fail();
		} else {
			// success
		}
	}

	@Test
	public void fillを呼んだ後エネルギー量が増加していることの確認() {
		EnergySource energySource = new Battery(0);
		assertEquals(0, energySource.observeRemainingEnergy(), 0);
		int expected = 5;
		energySource.fill(expected);
		assertEquals(expected, energySource.observeRemainingEnergy(), 0);
	}

	@Test
	public void retrieveを呼んだ後エネルギー量が減少していることの確認() {
		int energy = 3;
		EnergySource energySource = new Battery(energy);
		assertEquals(energy, energySource.observeRemainingEnergy(), 0);
		int value = 2;
		energySource.retrieve(value);
		assertEquals(energy - value, energySource.observeRemainingEnergy(), 0);
		energySource.retrieve(value);
		assertEquals(0, energySource.observeRemainingEnergy(), 0);
	}

	@Test
	public void retrieveの戻り値確認() {
		int energy = 3;
		EnergySource energySource = new Battery(energy);
		assertEquals(energy, energySource.observeRemainingEnergy(), 0);
		int value = 2;
		if (energySource.retrieve(value) == value) {
			// success
		} else {
			fail();
		}
		if (energySource.retrieve(value) == (energy - value)) {
			// success
		} else {
			fail();
		}
	}

}
