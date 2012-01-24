package gilmour.supermarket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
 * This class processes the contents of the shopping basket. It requires a
 * reference to the ShoppingBasket and CurrentDealsList, which can be done
 * statically, but I've added field variables for legibility
 */

public class CheckOut {

	private ShoppingBasket mShoppingBasket;
	private CurrentDealsList mCurrentDealsList;

	public CheckOut() {
		mShoppingBasket = ShoppingBasket.getBasketInstance();
		mCurrentDealsList = CurrentDealsList.getDealsInstance();
	}

	/*
	 * this method does a simple calculation (number of product*cost), and takes
	 * no consideration of applicable deals
	 */

	public double getSubtotal() {
		double subtotal = 0;
		Iterator<Product> i = this.getBasketIterator();
		while (i.hasNext()) {
			Product product = i.next();
			if (product instanceof UnitProduct) {
				int quantity = mShoppingBasket.getBasket().get(product);
				subtotal += product.getProductPrice() * quantity;
			}

			if (product instanceof WeighableProduct) {
				int weight = mShoppingBasket.getBasket().get(product);
				subtotal += ((WeighableProduct) product).getPricePerGram()
						* weight;
			}

			// if other type of product...
		}
		return subtotal;
	}

	/*
	 * this method returns the total amount of applicable discounts as a
	 * positive, double value. overall total = getSubTotal() - getDiscounts() It
	 * goes through each item present in the Basket and assesses whether there
	 * is a deal pertaining to that product
	 */

	public double getDiscounts() {
		double discounts = 0;
		Deal deal = null;
		Iterator<Product> i = this.getBasketIterator();
		while (i.hasNext()) {
			Product product = i.next();
			// check if this product has a deal against it
			if (mCurrentDealsList.containsDeal(product)) {
				deal = mCurrentDealsList.getDeal(product);
				int amount = mShoppingBasket.getBasket().get(product);
				discounts += deal.applyDiscount(amount);
			}
		}

		return discounts;
	}

	/*
	 * Helper methods for obtaining iterators, and retrieve quantity/weight of
	 * specific product
	 */

	public Iterator<Product> getBasketIterator() {
		HashMap<Product, Integer> basket_map = mShoppingBasket.getBasket();
		Set<Product> basket_set = basket_map.keySet();
		return basket_set.iterator();
	}

	public int getAmount(Product product) {
		return (mShoppingBasket.getAmount(product));
	}
}
