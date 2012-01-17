package gilmour.supermarket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * In the main UI there are two lists (products on sale, and products in the
 * customer's cart). This class handles UI events from lists and their associated buttons e.g.
 * user has selected an item from the list, and has then clicked 'add to cart'
 */

public class ListProductsHandler implements ActionListener,
		ListSelectionListener {
	private static final String USE_LIST_WARNING = "Please select an item from the list";
	private int product_panel_choice, cart_panel_choice;
	private CheckOut mCheckOut;
	private ProductCatalogue mProductCatalogue;
	private ViewFrame mViewFrame;
	private JList list;

	// TODO REVIEW NOTE: Is this bad design?
	// The list selection and buttons are separate entities that need to work
	// together
	public void setModel(CheckOut checkOut, ProductCatalogue productCatalogue) {
		this.mCheckOut = checkOut;
		this.mProductCatalogue = productCatalogue;
	}

	public void setView(ViewFrame viewFrame) {
		this.mViewFrame = viewFrame;
	}

	@Override
	/*
	 * When the user selects an item in either list, this method keeps track of
	 * the item's index
	 */
	public void valueChanged(ListSelectionEvent e) {
		list = (JList) e.getSource();
		if (list.getParent() instanceof ViewProductsPanel)
			product_panel_choice = list.getSelectedIndex();
		if (list.getParent() instanceof ViewCartPanel)
			cart_panel_choice = list.getSelectedIndex();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String response;
		int iQuantity;
		double dWeight;
		UnitProduct uProduct = null;
		WeighableProduct wProduct = null;
		Product product = null;

		String action = ((JButton) e.getSource()).getActionCommand();

		/* EDIT QUANTITY of items in the shopping cart */

		if (action.equals(ViewFrame.ACTION_EDIT_QUANTITY)) {
			// resolve the choice to a Product
			product = resolveCartChoiceToProduct();
			if (product == null)
				JOptionPane.showMessageDialog(null, USE_LIST_WARNING, "Error",
						JOptionPane.WARNING_MESSAGE);
			// edit the quantity of that Product
			else if (product instanceof UnitProduct) {
				uProduct = (UnitProduct) product;
				this.editQuantity(uProduct);
			} else {
				// TODO REVIEW NOTE: Is this lazy coding?
				// Future versions could add more Product types, causing bugs
				// here?
				wProduct = (WeighableProduct) product;
				this.editQuantity(wProduct);
			}
			// refresh the Cart
			mViewFrame.showCart(mCheckOut);
		}
		/* REMOVE ALL ITEMS (of selected product type) from the shopping cart */
		if (action.equals(ViewFrame.ACTION_REMOVE_ALL)) {
			// resolve the choice to a product
			product = resolveCartChoiceToProduct();
			if (product == null)
				JOptionPane.showMessageDialog(null, USE_LIST_WARNING, "Error",
						JOptionPane.WARNING_MESSAGE);
			// remove Product from appropriate Basket
			else if (product instanceof UnitProduct)
				ShoppingBasket.getBasketInstance().removeProductByType(
						(UnitProduct) product);
			if (product instanceof WeighableProduct)
				ShoppingBasket.getBasketInstance().removeProductByType(
						(WeighableProduct) product);
			// refresh the cart as displayed in the UI
			mViewFrame.showCart(mCheckOut);
			// remove 'edit cart' buttons if basket is empty
			if (ShoppingBasket.getBasketInstance().size() < 1)
				mViewFrame.showCartEditButtons(false);
		}
		/* ADD ITEMS TO CART */
		if (action.equals(ViewFrame.ACTION_ADD_TO_CART)) {
			product = mProductCatalogue.getCatalogue()
					.get(product_panel_choice);
			boolean nothing_entered = false;
			// create the appropriate input dialog + validate response
			do {

				/*
				 * TODO REVIEW NOTE: Is there a more elegant way to validate
				 * 'reponse' and have a persitent entry UI but still allowing
				 * user to CANCEL - without using this 'nothing_entered' flag?
				 */
				response = JOptionPane
						.showInputDialog(createAddProductPrompt(product));
				if (response == null | response == "")
					nothing_entered = true;
			} while (!validateAlterCartQuantity(response, product));

			/*
			 * add the items to the cart if !nothing_entered (i.e. user HAS
			 * entered a valid quantity/weight to add)
			 */
			
			if (product instanceof UnitProduct && !nothing_entered) {
				iQuantity = Integer.parseInt(response);
				uProduct = (UnitProduct) product;
				ShoppingBasket.getBasketInstance().addProduct(uProduct,
						iQuantity);
			}
			if (product instanceof WeighableProduct && !nothing_entered) {
				dWeight = Double.parseDouble(response);
				wProduct = (WeighableProduct) product;
				ShoppingBasket.getBasketInstance()
						.addProduct(wProduct, dWeight);
			}
		}
	}

	/*
	 * *** HELPER METHODS ***
	 */

	private boolean validateAlterCartQuantity(String response, Product p) {
		if (response == "" || response == null)
			return true;
		else if (p instanceof UnitProduct) {
			try {
				Integer.parseInt(response);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Please enter a whole number", "Error",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			try {
				Double.parseDouble(response);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Please use decimal places, e.g. 0.1 is 100 grams",
						"Error", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private String createAddProductPrompt(Product product) {
		String msg;
		if (product instanceof UnitProduct) {
			msg = "How many " + product.getProductName()
					+ "s would you like to add to your cart?";
		} else {
			msg = "How many kg of " + product.getProductName()
					+ " would you like to add to your cart?";
		}
		return msg;

	}

	private void editQuantity(WeighableProduct wp) {
		String msg = "Enter how many kg's of " + wp.getProductName()
				+ " you want to remove";
		String answer;
		boolean nothing_entered = false;
		do {
			answer = JOptionPane.showInputDialog(msg);
			if (answer == null | answer == "")
				nothing_entered = true;
		} while (!validateAlterCartQuantity(answer, wp));
		if (!nothing_entered) {
			double nEdit = Double.valueOf(answer);
			ShoppingBasket.getBasketInstance().removeProduct(wp, nEdit);
		}

	}

	private void editQuantity(UnitProduct up) {
		String msg = "Enter how many " + up.getProductName()
				+ "s you want to remove";
		String answer;
		boolean nothing_entered = false;
		do {
			answer = JOptionPane.showInputDialog(msg);
			if (answer == null | answer == "")
				nothing_entered = true;
		} while (!validateAlterCartQuantity(answer, up));
		if (!nothing_entered) {
			int nEdit = Integer.parseInt(answer);
			ShoppingBasket.getBasketInstance().removeProduct(up, nEdit);
		}
	}

	private Product resolveCartChoiceToProduct() {
		Product tmp = null;
		Product product = null;
		ListModel lm = null;
		String detail;
		String product_code;
		try {
			lm = list.getModel();
			detail = (String) lm.getElementAt(cart_panel_choice);
			product_code = detail.substring(0, 4);
			for (int x = 0; x < mProductCatalogue.getCatalogue().size(); x++) {
				tmp = mProductCatalogue.getCatalogue().elementAt(x);
				if (tmp.getProductId().equals(product_code))
					product = tmp;
			}
		} catch (Exception e) {
			return null;
		}

		return product;
	}
}
