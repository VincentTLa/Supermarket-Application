package gilmour.supermarket;

/*
 * Interface from which different types of Deal are made, namely
 * ItemDiscount and ItemPromo
 */

public interface Deal {

	public String getDealType();

	public int getDealCondition();

	public Product getProduct();

}
