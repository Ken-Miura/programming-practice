package ch03.ex03_04;

/* ex03_01.PassengerVehicle */
public class PassengerVehicle extends Vehicle {

	/* 隠ぺいされる場合に備えて、finalをつける必要がある */
	public static final void main(String... args) {
		PassengerVehicle[] passengerVehicles = { new PassengerVehicle("A", 4),
				new PassengerVehicle("B", 6), new PassengerVehicle("C", 7) };

		passengerVehicles[0].setSpeed(30);
		passengerVehicles[0].setDirection(Math.PI / 4);
		passengerVehicles[0].setNumOfSittingPeople(1);

		passengerVehicles[1].setSpeed(40);
		passengerVehicles[1].setDirection(Math.PI / 3);
		passengerVehicles[1].setNumOfSittingPeople(2);

		passengerVehicles[2].setSpeed(50);
		passengerVehicles[2].setDirection(Math.PI / 2);
		passengerVehicles[2].setNumOfSittingPeople(3);

		for (PassengerVehicle v : passengerVehicles) {
			System.out.println("--");
			System.out.println("ID: " + v.getID());
			System.out.println("Owner Name: " + v.getOwnerName());
			System.out.println("Speed (km/s): " + v.getSpeed());
			System.out.println("Direction (radian): " + v.getDirection());
			System.out.println("Seating capacity: " + v.getSeatingCapacity());
			System.out.println("Number of sitting people: "
					+ v.getNumOfSittingPeople());
		}

	}

	private final int seatingCapacity;
	private int numOfSittingPeople;

	public PassengerVehicle(String ownerName, int seatingCapacity) {
		this(ownerName, seatingCapacity, 0);
	}

	public PassengerVehicle(String ownerName, int seatingCapacity,
			int numOfSittingPeople) {
		super(ownerName);
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
			int seatingCapacity) {
		this(speed, direction, ownerName, seatingCapacity, 0);
	}

	public PassengerVehicle(int speed, double direction, String ownerName,
			int seatingCapacity, int numOfSittingPeople) {
		super(speed, direction, ownerName);
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

	/* オーバーライドされたときに意図しない値（seatingCapacity以外の値）を返さないようにfinalをつけるべき */
	public final int getSeatingCapacity() {
		return seatingCapacity;
	}

	/* オーバーライドされたときに意図しない値（numOfSittingPeople以外の値）を返さないようにfinalをつけるべき */
	public final int getNumOfSittingPeople() {
		return numOfSittingPeople;
	}

	/* オーバーライドされたときに意図しない動作（numOfSittingPeopleに値を設定しない）をさせないようにfinalをつけるべき */
	public final void setNumOfSittingPeople(int numOfSittingPeople) {
		if (numOfSittingPeople > seatingCapacity) {
			throw new IllegalArgumentException(
					"numOfSittingPeople must be seatingCapacity or less");
		}
		this.numOfSittingPeople = numOfSittingPeople;
	}
}
