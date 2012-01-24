package gilmour.supermarket;

public class UnitProduct extends Product {

	/*
	 * A UnitProduct cost is derived from the quanity of the items, rather than
	 * its weight e.g. a chocolate bar
	 */

	public UnitProduct(String id, String name, double cost) {
		super(id, name, cost);
	}
}
