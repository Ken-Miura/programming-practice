package ch02.ex02_07;

import java.util.Objects;

/* ex02_05.Vehicleを修正 */
public class Vehicle {

	private int speed = 0; // 単位はkm/h
	private double direction = 0.0; // 単位はradian
	private String ownerName = "unknown";
	private final long ID;
	private static long nextID = 0L;

	/* ex02_07.Vehicleで追加したコンストラクタ */
	public Vehicle() {
		this.ID = nextID;
		nextID++;
	}

	/* ex02_07.Vehicleで追加したコンストラクタ */
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

	/* ex02_07.Vehicleで修正したメソッド */
	public static void main(String[] args) {
		Vehicle[] vehicles = { new Vehicle("A"), new Vehicle("B"),
				new Vehicle("C") };
		
		vehicles[0].setSpeed(30); 
		vehicles[0].setDirection(Math.PI / 4);
		
		vehicles[1].setSpeed(40); 
		vehicles[1].setDirection(Math.PI / 3);
		
		vehicles[2].setSpeed(50); 
		vehicles[2].setDirection(Math.PI / 2);

		// 目視確認でテスト
		for (Vehicle v : vehicles) {
			System.out.println("--");
			System.out.println("ID: " + v.getID());
			System.out.println("Owner Name: " + v.getOwnerName());
			System.out.println("Speed (km/s): " + v.getSpeed());
			System.out.println("Direction (radian): " + v.getDirection());
		}
	}

}
