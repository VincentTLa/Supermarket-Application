package gilmour.supermarket;

public class ItemDiscount implements Deal {

	/*
	 * Both types of Product can have a discount applied to them. Here the
	 * condition of the deal is expressed as an integer, so '25' means 25% off
	 */

	private final String DEAL_TYPE = "Discount";
	private int dealCondition;
	private Product mProduct;

	public ItemDiscount(Product p, int percentageDiscountAsInt) {
		mProduct = p;
		dealCondition = percentageDiscountAsInt;
	}

	@Override
	public String getDealType() {
		return DEAL_TYPE;
	}

	@Override
	public int getDealCondition() {
		return dealCondition;
	}

	@Override
	public Product getProduct() {
		return mProduct;
	}
}
