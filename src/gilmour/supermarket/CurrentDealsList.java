package gilmour.supermarket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
 * this Singleton maps Deals to Products in a HashMap (which won't allow
 * duplicates - there can only be one deal per product at any one time), and is used to
 * keep track of active deals.
 */

public final class CurrentDealsList {

	private HashMap<Product, Deal> deals_list;

	private static final CurrentDealsList cdl = new CurrentDealsList();

	private CurrentDealsList() {
		deals_list = new HashMap<Product, Deal>();
	}

	public static CurrentDealsList getDealsInstance() {
		return cdl;
	}

	/* activate a deal */
	public void addDealToList(Deal d) {
		Product p = d.getProduct();
		removeDeal(p);
		deals_list.put(p, d);
	}

	/* deactivate a deal */
	public void removeDeal(Product p) {
		if (deals_list.containsKey(p))
			deals_list.remove(p);
	}

	/* Check if there is a Deal applicable to a Product */
	public boolean containsDeal(Product p) {
		return deals_list.containsKey(p);
	}

	/* return a Deal object for a specific product */

	public Deal getDeal(Product p) {
		if (deals_list.containsKey(p)) {
			Object o = deals_list.get(p);
			return (Deal) o;
		} else
			return null;

	}
	
	/*
	 * toString method over-ridden. Used to display todays current deals in the
	 * UI
	 */

	public String toString() {
		Set<Product> set = deals_list.keySet();
		Iterator<Product> i = set.iterator();
		String list = "";
		String message;
		while (i.hasNext()) {
			Product p = i.next();
			Deal d = deals_list.get(p);
			if (p instanceof UnitProduct && d instanceof ItemPromo) {
				message = ("Buy " + d.getDealCondition() + " "
						+ p.getProductName() + " get one free!\n");
				list += message;
			}
			if ((p instanceof WeighableProduct | p instanceof UnitProduct)
					&& d instanceof ItemDiscount) {
				message = ("Get " + d.getDealCondition() + "% off "
						+ p.getProductName() + "!\n");
				list += message;

			}
		}

		return list;

	}

}
