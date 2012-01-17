package gilmour.supermarket;

public class ItemPromo implements Deal {
	
	private final String DEAL_TYPE = "Promotion";
	private int dealCondition;
	private UnitProduct uProduct;
	
	/*
	 * The deal condition is the amount of this Product that must be bought
	 * before the next 'purchase' is free, only applicable to UnitProducts
	 */
	
	public ItemPromo(UnitProduct up, int condition){
		uProduct = up;
		dealCondition = condition;
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
		return uProduct;
	}

}
