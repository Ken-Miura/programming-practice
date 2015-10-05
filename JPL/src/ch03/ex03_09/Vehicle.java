package ch03.ex03_09;

import java.util.Objects;

/* メモリ以外のリソースを利用しているわけではなく、複製して使用しても問題は発生しないと思われるので
 * 複製の方針に関して一つ目（cloneをサポートすること）を選択する 
 * メンバに不変型でない参照型(EnergySource)を含んでいるので、Object#cloneによる単純なコピーでは不完全
 */
/* ex03_06.Vehicleを修正 */
public class Vehicle implements Cloneable {

	private int speed = 0; // 単位はkm/h
	private double direction = 0.0; // 単位はradian
	private String ownerName = "unknown";
	private long ID;
	private EnergySource energySource;
	private static long nextID = 0L;

	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;

	public Vehicle(EnergySource energySource) {
		Objects.requireNonNull(energySource, "energySource is null");
		this.energySource = energySource;
		this.ID = nextID;
		nextID++;
	}

	public Vehicle(String ownerName, EnergySource energySource) {
		this(energySource);
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.ownerName = ownerName;
	}

	public Vehicle(int speed, double direction, String ownerName,
			EnergySource energySource) {
		this(energySource);
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.speed = speed;
		this.direction = direction;
		this.ownerName = ownerName;
	}

	/* ex03_08で追加 */
	@Override
	public Vehicle clone() {
		Vehicle vehicle = null;
		try {
			vehicle = (Vehicle) super.clone();
			vehicle.energySource = energySource.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			assert false;
		}
		return vehicle;
	}

	/* ex03_08で追加 */
	public final EnergySource getEnergySource() {
		return energySource;
	}	
	
	/**
	 * 出発できるかどうかを確認するメソッド
	 * 
	 * @return 出発できる場合、trueを返す。出発できない場合、falseを返す
	 */
	public final boolean start() {
		return !(energySource.empty());
	}

	public final int getSpeed() {
		return speed;
	}

	public final void setSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("speed must be 0 or positive");
		}
		this.speed = speed;
	}

	public final double getDirection() {
		return direction;
	}

	public final void setDirection(double direction) {
		this.direction = direction;
	}

	public final String getOwnerName() {
		return ownerName;
	}

	public final void setOwnerName(String ownerName) {
		Objects.requireNonNull(ownerName, "ownerName is null");
		this.ownerName = ownerName;
	}

	public final long getID() {
		return ID;
	}

	/** 最後に使われたIDの値を返す。IDが使われていないときは-1を返す。 */
	public static final long getLastUsedID() {
		return nextID - 1;
	}

	@Override
	public String toString() {
		return "ID: " + ID + " Owner Name: " + ownerName + " Speed: " + speed
				+ " Direction: " + direction;
	}

	public final void changeSpeed(int speed) {
		this.speed = speed;
	}

	public final void stop() {
		this.speed = 0;
	}

	public final void turn(double direction) {
		this.direction = direction;
	}

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