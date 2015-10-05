package ch02.ex02_03;

import java.util.Objects;

/* ex02_01.Vehicleを修正 */
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

	/* ex02_03で追加したメソッド */
	public long getID() {
		return ID;
	}
}
