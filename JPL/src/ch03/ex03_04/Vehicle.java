package ch03.ex03_04;

import java.util.Objects;

/* ex03_01.Vehicle */
public class Vehicle {

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

	/* オーバーライドされたときに意図しない値（speed以外の値）を返さないようにfinalをつけるべき */
	public final int getSpeed() {
		return speed;
	}

	/* オーバーライドされたときに意図しない動作（speedに値を設定しない）をさせないようにfinalをつけるべき */
	public final void setSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		this.speed = speed;
	}

	/* オーバーライドされたときに意図しない値（direction以外の値）を返さないようにfinalをつけるべき */
	public final double getDirection() {
		return direction;
	}

	/* オーバーライドされたときに意図しない動作（directionに値を設定しない）をさせないようにfinalをつけるべき */
	public final void setDirection(double direction) {
		this.direction = direction;
	}

	/* オーバーライドされたときに意図しない値（ownerName以外の値）を返さないようにfinalをつけるべき */
	public final String getOwnerName() {
		return ownerName;
	}

	/* オーバーライドされたときに意図しない動作（ownerNameに値を設定しない）をさせないようにfinalをつけるべき */
	public final void setOwnerName(String ownerName) {
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.ownerName = ownerName;
	}

	/* オーバーライドされたときに意図しない値（ID以外の値）を返さないようにfinalをつけるべき */
	public final long getID() {
		return ID;
	}

	/* 隠ぺいされたときに意図しない値を返さないようにfinalをつけるべき */
	/** 最後に使われたIDの値を返す。IDが使われていないときは-1を返す。 */
	public static final long getLastUsedID() {
		return nextID - 1;
	}

	/* オーバーライドされたときに、そのクラス特有の表現をさせるためにfinalをつけるべきではない */
	@Override
	public String toString() {
		return "ID: " + ID + " Owner Name: " + ownerName + " Speed: " + speed
				+ " Direction: " + direction;
	}

	/* オーバーライドされたときに意図しない動作（speedに値を設定しない）をさせないようにfinalをつけるべき */	
	public final void changeSpeed(int speed) {
		this.speed = speed;
	}

	/* オーバーライドされたときに意図しない動作（speedの値を0に設定しない）をさせないようにfinalをつけるべき */	
	public final void stop() {
		this.speed = 0;
	}

	/* オーバーライドされたときに意図しない動作（directionに値を設定しない）をさせないようにfinalをつけるべき */	
	public final void turn(double direction) {
		this.direction = direction;
	}

	/* オーバーライドされたときに意図しない動作（directionに値を設定しない）をさせないようにfinalをつけるべき */
	public final void turn(int turn) {
		switch (turn) {
		case TURN_LEFT:
			this.direction = this.direction + Math.PI / 2;
			break;
		case TURN_RIGHT:
			this.direction = this.direction - Math.PI / 2;
			break;
		default:
			throw new IllegalArgumentException(
					"turn must be TURN_LEFT or TURN_RIGHT");
		}
	}
}