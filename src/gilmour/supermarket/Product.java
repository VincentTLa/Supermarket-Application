package gilmour.supermarket;

public class Product {

	/* This is the base concrete class for products at the supermarket */

	private String product_id;
	private String product_name;
	private double product_price;

	public Product(String id, String name, double price) {
		product_id = id;
		product_name = name;
		product_price = price;
	}

	public String getProductId() {
		return product_id;
	}

	public void setProductId(String newProductId) {
		product_id = newProductId;
	}

	public String getProductName() {
		return product_name;
	}

	public void setProductName(String newName) {
		product_name = newName;
	}

	public double getProductPrice() {
		return product_price;
	}

	public void setProductPrice(double price) {
		product_price = price;
	}

}
