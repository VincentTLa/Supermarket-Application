package gilmour.supermarket;

public class Test {

	public static void main(String[] args) {
		// create some products
		UnitProduct coke = new UnitProduct("U001", "Coke (Can)", 0.50);
		WeighableProduct carrots = new WeighableProduct("W002", "Carrots", 1.00);
		WeighableProduct apples = new WeighableProduct("W001", "Apples", 0.67);
		UnitProduct crisps = new UnitProduct("U002",
				"Walker's Crisps (Family Pack)", 1.68);
		// add products to catalogue
		ProductCatalogue catalogue = new ProductCatalogue();
		catalogue.addProduct(coke);
		catalogue.addProduct(carrots);
		catalogue.addProduct(apples);
		catalogue.addProduct(crisps);
		// create a new deals list
		CurrentDealsList dl = CurrentDealsList.getDealsInstance();
		// create a deal
		Deal id = new ItemDiscount(carrots, 25);
		Deal ip = new ItemPromo(coke, 4);
		// Add deals to List
		dl.addDealToList(id);
		dl.addDealToList(ip);
		// create instance of CheckOut class
		CheckOut checkOut = new CheckOut();
		// The presenter needs to know the model and the view
		// The view needs to know the presenter
		Presenter presenter = new Presenter();
		ViewFrame view = new ViewFrame();
		view.setPresenter(presenter);
		presenter.setView(view);
		// model is ProductCatalogue and CheckOut (ShoppingBasket and
		// CurrentDealsList are Singleton/Statically accessed)
		presenter.setModel(checkOut, catalogue);
	}

}
