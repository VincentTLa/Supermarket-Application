package gilmour.supermarket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
 * This class processes the contents of the shopping basket. It requires a
 * reference to the ShoppingBasket and CurrentDealsList, which can be done
 * statically but I've added field variables for legibility
 */

public class CheckOut {

	private ShoppingBasket mShoppingBasket;
	private CurrentDealsList mCurrentDealsList;
	private final double HUNDRED_GRAMS = 0.100;
	private final double HUNDRED = 100;

	public CheckOut() {
		mShoppingBasket = ShoppingBasket.getBasketInstance();
		mCurrentDealsList = CurrentDealsList.getDealsInstance();
	}

	/*
	 * this method does a simple calculation (number of product*cost), and takes
	 * no consideration of applicable deals
	 */

	public double getSubTotal() {
		// subtotal of the 2 types of products, initialized to 0
		double uSubTotal = 0;
		double wSubTotal = 0;
		/* iterate through UNIT products and calculate subtotal */
		Iterator<UnitProduct> i_unit = this.getUnitBasketIterator();
		while (i_unit.hasNext()) {
			UnitProduct uProduct = i_unit.next();
			int current_quantity = mShoppingBasket.getUnitBasket()
					.get(uProduct);
			uSubTotal += (uProduct.getUnitCost() * current_quantity);
		}

		/* iterate through WEIGHABLE products and calculate subtotal */

		Iterator<WeighableProduct> i_weighable = this
				.getWeighablesBasketIterator();
		while (i_weighable.hasNext()) {
			WeighableProduct wProduct = i_weighable.next();
			double current_weight = mShoppingBasket.getWeighablesBasket().get(
					wProduct);
			wSubTotal += ((current_weight / HUNDRED_GRAMS) * wProduct
					.getCostPer100G());
		}
		return (uSubTotal + wSubTotal);

	}

	/*
	 * this method returns the total amount of applicable discounts as a
	 * positive, double value. overall total = getSubTotal() - getDiscounts() It
	 * goes through each item present in the Basket and assesses whether there
	 * is a deal pertaining to that product
	 */

	public double getDiscounts() {
		double reductions = 0;
		Iterator<UnitProduct> i_unit = this.getUnitBasketIterator();
		Iterator<WeighableProduct> i_weigh = this.getWeighablesBasketIterator();
		Deal d;
		/*
		 * TODO REVIEW NOTE: There are 3 separate calculations here - should
		 * they be refactored into methods or another class?
		 */

		/* first we look for discounts on UNIT products */
		while (i_unit.hasNext()) {
			UnitProduct up = i_unit.next();
			if (mCurrentDealsList.containsDeal(up)) {
				d = mCurrentDealsList.getDeal(up);
				if (d instanceof ItemDiscount) {
					double discountAsPercentage = (double) d.getDealCondition()
							/ HUNDRED;// e.g. converts '25' to '0.25'
					reductions += (discountAsPercentage * up.getUnitCost() * mShoppingBasket
							.getUnitBasket().get(up));
				}
			}
		}
		/* Then we look for discounts on WEIGHABLE products */
		while (i_weigh.hasNext()) {
			WeighableProduct wp = i_weigh.next();
			if (mCurrentDealsList.containsDeal(wp)) {
				d = mCurrentDealsList.getDeal(wp);
				double current_weight = mShoppingBasket.getWeighablesBasket()
						.get(wp);
				double discountAsPercentage = (double) d.getDealCondition()
						/ HUNDRED;
				reductions += (discountAsPercentage * wp.getCostPer100G() * (current_weight / HUNDRED_GRAMS));
			}
		}
		/*
		 * Then we look for promotions (e.g. 2 for 1s) on WEIGHABLE items only
		 * if an item is on 2 for 1, its deal condition is '2'. In other words,
		 * the third item is free
		 */
		i_unit = this.getUnitBasketIterator();
		while (i_unit.hasNext()) {
			UnitProduct up = i_unit.next();
			if (mCurrentDealsList.containsDeal(up)) {
				d = mCurrentDealsList.getDeal(up);
				if (d instanceof ItemPromo) {
					int promo_condition = 1 + d.getDealCondition();
					int quantity = mShoppingBasket.getUnitBasket().get(up);
					for (int x = 1; x <= quantity; x++) {
						if (x % promo_condition == 0)
							reductions += up.getUnitCost();
					}
				}
			}
		}
		return reductions;
	}

	/*
	 * TODO REVIEW NOTE: Helper methods for obtaining iterators, promoting code
	 * re-use. These are also used by Presenter classes/handlers, hence public
	 */

	public Iterator<UnitProduct> getUnitBasketIterator() {
		HashMap<UnitProduct, Integer> units = mShoppingBasket.getUnitBasket();
		Set<UnitProduct> unitsSet = units.keySet();
		return unitsSet.iterator();
	}

	public Iterator<WeighableProduct> getWeighablesBasketIterator() {
		HashMap<WeighableProduct, Double> weighables = mShoppingBasket
				.getWeighablesBasket();
		Set<WeighableProduct> weighSet = weighables.keySet();
		return weighSet.iterator();
	}
}
