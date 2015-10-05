package ch03.ex03_08;

/* メモリ以外のリソースを利用しているわけではなく、複製して使用しても問題は発生しないと思われるので
 * 複製の方針に関して一つ目（cloneをサポートすること）を選択する 
 * メンバに参照型を含んでいないので、Object#cloneによる単純なコピーで十分
 */
/* ex03_01.PassengerVehicleを修正 */
public class PassengerVehicle extends Vehicle {

	private int seatingCapacity;
	private int numOfSittingPeople;

	public PassengerVehicle(String ownerName, EnergySource energySource,
			int seatingCapacity) {
		this(ownerName, energySource, seatingCapacity, 0);
	}

	public PassengerVehicle(String ownerName, EnergySource energySource,
			int seatingCapacity, int numOfSittingPeople) {
		super(ownerName, energySource);
		if (seatingCapacity <= 0) {
			throw new IllegalArgumentException(
					"seatingCapacity must be positive");
		}
		if (numOfSittingPeople < 0) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be 0 or positive");
		}
		if (numOfSittingPeople > seatingCapacity) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be seatingCapacity or less");
		}
		this.seatingCapacity = seatingCapacity;
		this.setNumOfSittingPeople(numOfSittingPeople);
	}

	public PassengerVehicle(int speed, double direction, String ownerName,
			EnergySource energySource, int seatingCapacity) {
		this(speed, direction, ownerName, energySource, seatingCapacity, 0);
	}

	public PassengerVehicle(int speed, double direction, String ownerName,
			EnergySource energySource, int seatingCapacity,
			int numOfSittingPeople) {
		super(speed, direction, ownerName, energySource);
		if (seatingCapacity <= 0) {
			throw new IllegalArgumentException(
					"seatingCapacity must be positive");
		}
		if (numOfSittingPeople < 0) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be 0 or positive");
		}
		if (numOfSittingPeople > seatingCapacity) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be seatingCapacity or less");
		}
		this.seatingCapacity = seatingCapacity;
		this.setNumOfSittingPeople(numOfSittingPeople);
	}

	/* ex03_08で追加 */
	@Override
	public PassengerVehicle clone() {
		return (PassengerVehicle) super.clone();
	}

	public final int getSeatingCapacity() {
		return seatingCapacity;
	}

	public final int getNumOfSittingPeople() {
		return numOfSittingPeople;
	}

	public final void setNumOfSittingPeople(int numOfSittingPeople) {
		if (numOfSittingPeople > seatingCapacity) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be seatingCapacity or less");
		}
		this.numOfSittingPeople = numOfSittingPeople;
	}
}
