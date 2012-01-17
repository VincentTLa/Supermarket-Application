package gilmour.supermarket;

public class WeighableProduct extends Product {

	/*
	 * This class is a more specific type of Product, whose cost is calculated
	 * based on weight
	 */

	private double costPer100Grams;

	public WeighableProduct(String id, String name, double cost) {
		super(id, name);
		costPer100Grams = cost;
	}

	public double getCostPer100G() {
		return costPer100Grams;
	}

	public void setCostPerWeight(double newCostPerWeight) {
		costPer100Grams = newCostPerWeight;
	}

}
