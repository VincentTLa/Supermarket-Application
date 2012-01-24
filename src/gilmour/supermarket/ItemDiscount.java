package gilmour.supermarket;

public class ItemDiscount implements Deal {

	/*
	 * Both types of Product can have a discount applied to them. Here the
	 * condition of the deal is expressed as an integer, so '25' means 25% off
	 */

	private final String dealName = "Discount";
	private int dealCondition;
	private Product mProduct;

	public ItemDiscount(Product p, int percentageDiscountAsInt) {
		mProduct = p;
		dealCondition = percentageDiscountAsInt;
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
		return mProduct;
	}

	@Override
	public double applyDiscount(int amountOfProduct) {
		WeighableProduct wProduct = null;
		UnitProduct uProduct = null;
		double total_before_discount = 0;
		// check mProduct's type + overall cost
		if (mProduct instanceof WeighableProduct) {
			wProduct = (WeighableProduct) mProduct;
			total_before_discount = wProduct.getPricePerGram()
					* amountOfProduct;
		} else {
			uProduct = (UnitProduct) mProduct;
			total_before_discount = uProduct.getProductPrice()
					* amountOfProduct;
		}
		// Apply discount
		return total_before_discount * this.dealCondition / 100;
	}
}
