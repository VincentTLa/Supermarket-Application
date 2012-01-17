package gilmour.supermarket;

public class UnitProduct extends Product {

	/*
	 * A UnitProduct cost is derived from the quanity of the items, rather than
	 * its weight e.g. a chocolate bar
	 */

	private double unitCost;

	public UnitProduct(String id, String name, double cost) {
		super(id, name);
		unitCost = cost;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double newUnitCost) {
		unitCost = newUnitCost;
	}

}
