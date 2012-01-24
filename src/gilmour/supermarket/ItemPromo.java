package gilmour.supermarket;

public class ItemPromo implements Deal {

	private final String dealName = "Promotion";
	private int dealCondition;
	private UnitProduct uProduct;

	/*
	 * The deal condition is the amount of this Product that must be bought
	 * before the next 'purchase' is free, only applicable to UnitProducts
	 */

	public ItemPromo(UnitProduct up, int condition) {
		uProduct = up;
		dealCondition = condition;
	}

	@Override
	public String getDealName() {
		return dealName;
	}

	@Override
	public int getDealCondition() {
		return dealCondition;
	}

	@Override
	public Product getProduct() {
		return uProduct;
	}

	@Override
	public double applyDiscount(int amountOfProduct) {
		/*
		 * condition will be buy x get one free, so, for buy 1 get 1 free the
		 * customer is eligible for a free one with 2, 4, 6 etc products
		 */
		int promo_condition = 1 + dealCondition;
		double savings = 0;
		for (int x = 1; x <= amountOfProduct; x++) {
			if (x % promo_condition == 0)
				savings += uProduct.getProductPrice();
		}
		return savings;
	}

}
