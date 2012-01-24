package gilmour.supermarket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 * This class handles all button clicks, except those that work in
 * conjunction with lists of items (and must know list information to work properly
 * 'Add to cart', 'remove' and 'edit quantity' )
 */

public class ControlButtonHandler implements ActionListener {

	private ViewFrame mViewFrame;
	private ProductCatalogue mProductCatalogue;
	private CheckOut mCheckOut;

	/*
	 * MODEL and VIEW references recieved from the presenter class
	 */

	public void setModel(CheckOut checkOut, ProductCatalogue productCatalogue) {
		mProductCatalogue = productCatalogue;
		mCheckOut = checkOut;
	}

	public void setView(ViewFrame viewFrame) {
		mViewFrame = viewFrame;
	}

	/*
	 * Handle button clicks for START, VIEW DEALS, VIEW CART, BACK TO SHOP,
	 * CLEAR CART and CHECKOUT buttons
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// Only JButtons are registered to this listener
		String action = ((JButton) e.getSource()).getActionCommand();

		if (action.equals(ViewFrame.ACTION_START_SHOPPING)) {
			mViewFrame.setButtonsForShopping(true);
			mViewFrame.showProducts(mProductCatalogue);
		}

		if (action.equals(ViewFrame.ACTION_VIEW_DEALS)) {
			// get todays deals
			String message = CurrentDealsList.getDealsInstance().toString();
			JOptionPane.showMessageDialog(null, message, "***TODAY'S DEALS***",
					JOptionPane.OK_OPTION);
		}

		if (action.equals(ViewFrame.ACTION_VIEW_CART)) {
			mViewFrame.setButtonsForShopping(false);
			if (ShoppingBasket.getBasketInstance().size() < 1)
				mViewFrame.showCartEditButtons(false);
			else
				mViewFrame.showCartEditButtons(true);
			mViewFrame.showCart(mCheckOut);
			mViewFrame.showProductsPanelCard(false);
		}

		if (action.equals(ViewFrame.ACTION_BACK_TO_SHOP)) {
			mViewFrame.setButtonsForShopping(true);
			mViewFrame.showProductsPanelCard(true);
		}

		if (action.equals(ViewFrame.ACTION_CLEAR_CART)) {
			ShoppingBasket.getBasketInstance().clear();
			mViewFrame.showCartEditButtons(false);
			mViewFrame.showCart(mCheckOut);
		}

		if (action.equals(ViewFrame.ACTION_CHECKOUT)) {

			double subtotal = mCheckOut.getSubtotal();
			double discounts = mCheckOut.getDiscounts();
			String msg = "SUB-TOTAL = $" + subtotal + "\nDiscounts = $"
					+ discounts + "\nTOTAL $" + (subtotal - discounts);

			JOptionPane.showMessageDialog(null, msg, "SIMPLE TOTAL POPUP",
					JOptionPane.OK_OPTION);
		}

	}

}
