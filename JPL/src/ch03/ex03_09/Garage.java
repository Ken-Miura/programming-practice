package ch03.ex03_09;

public final class Garage implements Cloneable {

	private Vehicle[] vehicles;

	public Garage(Vehicle... vehicles) {
		this.vehicles = vehicles;
	}

	public Vehicle[] getVehicles() {
		return vehicles;
	}

	@Override
	public Garage clone() {
		Garage garage = null;
		try {
			garage = (Garage) super.clone();
			garage.vehicles = vehicles.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return garage;
	}

	/* Garage#cloneのテスト用メソッド */
	public static void main(String[] args) {
		Garage g1 = new Garage(new Vehicle("test1", new Battery(3)),
				new Vehicle("test2", new GasTank(3)));
		Garage g2 = g1.clone();

		for (int i = 0; i < 2; i++) {
			assert (g1.getVehicles()[i].getDirection() == g2.getVehicles()[i]
					.getDirection());
			assert (g1.getVehicles()[i].getEnergySource()
					.observeRemainingEnergy() == g2.getVehicles()[i]
					.getEnergySource().observeRemainingEnergy());
			assert (g1.getVehicles()[i].getID() == g2.getVehicles()[i].getID());
			assert (g1.getVehicles()[i].getOwnerName()
					.equals(g2.getVehicles()[i].getOwnerName()));
			assert (g1.getVehicles()[i].getSpeed() == g2.getVehicles()[i]
					.getSpeed());
		}

		System.out.println("succeeded");
	}
}
