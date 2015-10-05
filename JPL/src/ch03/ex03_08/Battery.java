package ch03.ex03_08;

/* ex03_06.Batteryを修正 */
public class Battery extends EnergySource {

	private int power;

	public Battery(int energy) {
		if (energy < 0) {
			throw new IllegalArgumentException("energy must be 0 or positive");
		}
		power = energy;
	}

	@Override
	public final boolean empty() {
		if (power == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final int retrieve(int energy) {
		if (energy <= 0) {
			throw new IllegalArgumentException("energy must be positive");
		}
		if (energy <= power) {
			power = power - energy;
			return energy;
		} else {
			int tempPower = power;
			power = 0;
			return tempPower;
		}
	}

	@Override
	public final void fill(int energy) {
		if (energy <= 0) {
			throw new IllegalArgumentException("energy must be positive");
		}
		power += energy;
	}

	@Override
	public int observeRemainingEnergy() {
		return power;
	}

	/* ex03_08で追加 */
	@Override
	public Battery clone() {
		return (Battery) super.clone();
	}
}
