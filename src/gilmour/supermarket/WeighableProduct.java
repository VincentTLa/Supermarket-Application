package gilmour.supermarket;

public class WeighableProduct extends Product {

	/*
	 * This class is a more specific type of Product, whose cost is calculated
	 * based on weight
	 */

	private int price_metric;// weight at which price is based

	public WeighableProduct(String id, String name, double cost, int metric) {
		super(id, name, cost);
		price_metric = metric;
	}

	public int getPriceMetric() {
		return price_metric;
	}

	public void setPriceMetric(int newPriceMetric) {
		price_metric = newPriceMetric;
	}
	
	public double getPricePerGram (){
		return (super.getProductPrice()/price_metric); 
	}

}
