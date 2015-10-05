package ch02.ex02_05;

import java.util.Objects;

/* ex02_03.Vehicleを修正 */
public class Vehicle {

	private int speed; // 単位はkm/h
	private double direction; // 単位はradian
	private String ownerName;
	private final long ID;
	private static long nextID = 0L;

	public Vehicle(int speed, double direction, String ownerName) {
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.speed = speed;
		this.direction = direction;
		this.ownerName = ownerName;
		this.ID = nextID;
		nextID++;
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

	/* ex02_05.Vehicleで追加したメソッド */
	public static void main(String[] args) {
		Vehicle[] vehicles = { new Vehicle(30, Math.PI / 4, "A"),
				new Vehicle(40, Math.PI / 3, "B"),
				new Vehicle(50, Math.PI / 2, "C") };
		
		//　テストコード。Vehicleインスタンスのフィールドにコンストラクタでセットした値が設定されているか目視確認。
		for (Vehicle v : vehicles) {
			System.out.println("--");
			System.out.println("ID: " + v.getID());
			System.out.println("Owner Name: " + v.getOwnerName());
			System.out.println("Speed (km/s): " + v.getSpeed());
			System.out.println("Direction (radian): " + v.getDirection());
		}
	}

}
