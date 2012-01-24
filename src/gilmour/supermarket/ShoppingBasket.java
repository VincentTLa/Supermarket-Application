package gilmour.supermarket;

import java.util.HashMap;

/*
 * This class models the customer's shopping basket, of which they should
 * have only one instance - hence this class is a singleton. Quantities of 
 * products are measured differently, for wProducts int is number of grams, for uProducts int is number of items
 */

public final class ShoppingBasket {

	private static final ShoppingBasket shoppingBasket = new ShoppingBasket();

	private HashMap<Product, Integer> basket;

	private ShoppingBasket() {
		basket = new HashMap<Product, Integer>();
	}

	public static ShoppingBasket getBasketInstance() {
		return shoppingBasket;
	}

	// add to cart
	public void addProduct(Product product, int amount) {
		if (!basket.containsKey(product))
			basket.put(product, amount);
		else {
			int current_amount = basket.get(product);
			basket.put(product, current_amount + amount);
		}
	}

	// remove by quantity
	public void removeProduct(Product product, int amount) {
		if (basket.containsKey(product)) {
			int current_amount = basket.get(product);
			if (current_amount > amount) {
				basket.put(product, current_amount - amount);
			}
		}

	}

	// remove by product type

	public void removeProductByType(Product product) {
		if (basket.containsKey(product))
			basket.remove(product);
	}

	// remove all contents

	public void clear() {
		basket.clear();
	}

	public int size() {
		return (basket.size());
	}
	
	public int getAmount (Product product){
		//returns either the quantity or weight of selected product in basket
		return basket.get(product);		
	}

	public HashMap<Product, Integer> getBasket() {
		return basket;
	}
}
