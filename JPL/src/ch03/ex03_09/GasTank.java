package ch03.ex03_09;

/* ex03_06.GasTankを修正 */
public class GasTank extends EnergySource {

	private int amountOfGas;

	public GasTank(int energy) {
		if (energy < 0) {
			throw new IllegalArgumentException("energy must be 0 or positive");
		}
		amountOfGas = energy;
	}

	@Override
	public final boolean empty() {
		if (amountOfGas == 0) {
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
		if (energy <= amountOfGas) {
			amountOfGas = amountOfGas - energy;
			return energy;
		} else {
			int tempAmountOfGas = amountOfGas;
			amountOfGas = 0;
			return tempAmountOfGas;
		}
	}

	@Override
	public final void fill(int energy) {
		if (energy <= 0) {
			throw new IllegalArgumentException("energy must be positive");
		}
		amountOfGas += energy;
	}

	@Override
	public int observeRemainingEnergy() {
		return amountOfGas;
	}

	/* ex03_08で追加 */
	@Override
	protected GasTank clone() {
		return (GasTank) super.clone();
	}
}
