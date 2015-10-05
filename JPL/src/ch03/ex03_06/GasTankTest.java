package ch03.ex03_06;

import static org.junit.Assert.*;

import org.junit.Test;

public class GasTankTest {


	@Test
	public void インスタンス生成時に設定した値が反映されていることの確認() {
		int expected = 1;
		EnergySource energySource = new GasTank(expected);
		assertEquals(expected, energySource.observeRemainingEnergy(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値でインスタンスが生成できないことの確認() {
		int negative = -1;
		new GasTank(negative);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値をセットできないことの確認() {
		int negative = -1;
		EnergySource energySource = new GasTank(0);
		energySource.fill(negative);
		fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void 違法な値を取得できないことの確認() {
		int negative = -1;
		EnergySource energySource = new GasTank(1);
		energySource.retrieve(negative);
		fail();
	}

	@Test
	public void amaountOfGasが0のときemptyがtrueを返すことの確認() {
		EnergySource energySource = new GasTank(0);
		if (energySource.empty()) {
			// success
		} else {
			fail();
		}
	}

	@Test
	public void amaountOfGasが正のときemptyがfalseを返すことの確認() {
		EnergySource energySource = new GasTank(1);
		if (energySource.empty()) {
			fail();
		} else {
			// success
		}
	}

	@Test
	public void fillを呼んだ後エネルギー量が増加していることの確認() {
		EnergySource energySource = new GasTank(0);
		assertEquals(0, energySource.observeRemainingEnergy(), 0);
		int expected = 5;
		energySource.fill(expected);
		assertEquals(expected, energySource.observeRemainingEnergy(), 0);
	}

	@Test
	public void retrieveを呼んだ後エネルギー量が減少していることの確認() {
		int energy = 3;
		EnergySource energySource = new GasTank(energy);
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
		EnergySource energySource = new GasTank(energy);
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
