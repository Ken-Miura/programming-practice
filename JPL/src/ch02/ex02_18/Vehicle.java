package ch02.ex02_18;

import java.util.Objects;

/* ex02_17.Vehicleを修正 */
public class Vehicle {

	/* ex02_18.Vehicleで追加したメソッド */
	public static void main (String... args) {
		if (args.length != 1) {
			System.err.println("Please input owner's name as a argument.");			
			return;
		}
		System.out.println(vehicleToString(args[0]));
	}

	/* ex02_18.Vehicleで追加したメソッド */
	private static String vehicleToString(String ownerName) {
		assert (ownerName == null);
		return new Vehicle(ownerName).toString();
	}
	
	private int speed = 0; // 単位はkm/h
	private double direction = 0.0; // 単位はradian
	private String ownerName = "unknown";
	private final long ID;
	private static long nextID = 0L;

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
	public Vehicle() {
		this.ID = nextID;
		nextID++;
	}
	
	public Vehicle(String ownerName) {
		this();
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.ownerName = ownerName;
	}

	public Vehicle(int speed, double direction, String ownerName) {
		this();
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.speed = speed;
		this.direction = direction;
		this.ownerName = ownerName;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		this.speed = speed;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.ownerName = ownerName;
	}

	public long getID() {
		return ID;
	}

	/** 最後に使われたIDの値を返す。IDが使われていないときは-1を返す。 */
	public static long getLastUsedID() {
		return nextID - 1;
	}

	@Override
	public String toString() {
		return "ID: " + ID + " Owner Name: " + ownerName + " Speed: " + speed
				+ " Direction: " + direction;
	}

	public void changeSpeed(int speed) {
		this.speed = speed;
	}

	public void stop() {
		this.speed = 0;
	}

	public void turn(double direction) {
		this.direction = direction;
	}

	public void turn(int turn) {
		switch (turn) {
		case TURN_LEFT:
			this.direction = this.direction + Math.PI / 2;
			break;
		case TURN_RIGHT:
			this.direction = this.direction - Math.PI / 2;
			break;
		default:
			throw new IllegalArgumentException("turn must be TURN_LEFT or TURN_RIGHT");
		}
	}
}