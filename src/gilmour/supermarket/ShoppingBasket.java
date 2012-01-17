package gilmour.supermarket;

import java.util.HashMap;

/*
 * This class models the customer's shopping basket, of which they should
 * have only one instance - hence this class is a singleton. Quantities of 
 * products are measured differently (1 of Coke Can means 1 item, 1 of Carrots means 1.000kg).
 * Items are stored in a hashmap corresponding to their product type 
 */

public final class ShoppingBasket {

	private static final ShoppingBasket shoppingBasket = new ShoppingBasket();

	private HashMap<UnitProduct, Integer> basketOfUnitProducts;
	private HashMap<WeighableProduct, Double> basketOfWeighables;

	private ShoppingBasket() {
		basketOfUnitProducts = new HashMap<UnitProduct, Integer>();
		basketOfWeighables = new HashMap<WeighableProduct, Double>();
	}

	public static ShoppingBasket getBasketInstance() {
		return shoppingBasket;
	}

	// add to cart
	public void addProduct(UnitProduct product, int quantity) {
		// check if product exists in HashMap, alter quantity if necessary
		if (!basketOfUnitProducts.containsKey(product))
			basketOfUnitProducts.put(product, quantity);
		else {
			Object key = basketOfUnitProducts.get(product);
			int current_quantity = (Integer) key;
			basketOfUnitProducts.put(product, (current_quantity + quantity));
		}
	}

	public void addProduct(WeighableProduct product, double weight) {
		// chcek if product is in hashmap, alter quantity if necessary
		if (!basketOfWeighables.containsKey(product))
			basketOfWeighables.put(product, weight);
		else {
			Object key = basketOfWeighables.get(product);
			double current_weight = (Double) key;
			basketOfWeighables.put(product, (weight + current_weight));
		}
	}

	public void removeProduct(UnitProduct up, int quantity) {
		if (basketOfUnitProducts.containsKey(up)) {
			int current_quantity = (int) basketOfUnitProducts.get(up);
			if (current_quantity > quantity) {
				basketOfUnitProducts.put(up, (current_quantity - quantity));
			}
		}
	}

	public void removeProduct(WeighableProduct wp, double weight) {
		if (basketOfWeighables.containsKey(wp)) {
			double current_weight = (double) basketOfWeighables.get(wp);
			if (current_weight > weight)
				basketOfWeighables.put(wp, (current_weight - weight));
		}
	}

	// remove product type

	public void removeProductByType(UnitProduct up) {
		if (basketOfUnitProducts.containsKey(up)) {
			basketOfUnitProducts.remove(up);
		}
	}

	public void removeProductByType(WeighableProduct wp) {
		if (basketOfWeighables.containsKey(wp))
			basketOfWeighables.remove(wp);
	}

	public void clear() {
		basketOfUnitProducts.clear();
		basketOfWeighables.clear();
	}

	public int size() {
		return (basketOfUnitProducts.size() + basketOfWeighables.size());
	}

	public HashMap<UnitProduct, Integer> getUnitBasket() {
		return basketOfUnitProducts;
	}

	public HashMap<WeighableProduct, Double> getWeighablesBasket() {
		return basketOfWeighables;
	}
}
