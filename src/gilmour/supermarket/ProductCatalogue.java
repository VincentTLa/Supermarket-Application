package gilmour.supermarket;

import java.util.List;
import java.util.Vector;

public class ProductCatalogue {

	/*
	 * After a Product object is created, it is added to the Product Catalogue -
	 * represented by this class
	 */

	//private Vector<Product> product_catalogue = new Vector<Product>();
	private List<Product> product_catalogue = new Vector<Product>();
	

	public void addProduct(Product p) {
		if (!product_catalogue.contains(p))
			product_catalogue.add(p);
	}

	public Vector<Product> getCatalogue() {
		return (Vector<Product>) this.product_catalogue;
	}

	// removeProduct()

}
