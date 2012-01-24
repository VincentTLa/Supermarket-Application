package gilmour.supermarket;

/*
 * Interface from which different types of Deal are made, namely
 * ItemDiscount and ItemPromo
 */

public interface Deal {

	public String getDealName();

	public int getDealCondition();

	public Product getProduct();
	
	/*
	 * applyDiscount returns the amount of savings, by applying the deal to this
	 * product
	 */
	
	public double applyDiscount(int amountOfProduct);

}
