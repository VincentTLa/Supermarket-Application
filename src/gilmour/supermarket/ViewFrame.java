package gilmour.supermarket;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ViewFrame extends JFrame {

	private Container mContainer;
	private JButton btn_view_deals, btn_add_cart, btn_view_cart, btn_back,
			btn_edit, btn_remove, btn_start, btn_clear_cart, btn_checkout;
	private ViewProductsPanel pnl_products;
	private ViewCartPanel pnl_cart;
	private JPanel pnl_card_holder, pnl_buttons;
	private CardLayout cardLayout;

	public static String ACTION_ADD_TO_CART = "ADD TO CART";
	public static String ACTION_BACK_TO_SHOP = "BACK TO SHOP";
	public static String ACTION_EDIT_QUANTITY = "EDIT QUANTITY";
	public static String ACTION_REMOVE_ALL = "REMOVE_ALL";
	public static String ACTION_VIEW_CART = "VIEW CART";
	public static String ACTION_VIEW_DEALS = "VIEW DEALS";
	public static String ACTION_START_SHOPPING = "START SHOPPING";
	public static String ACTION_CLEAR_CART = "CLEAR CART";
	public static String ACTION_CHECKOUT = "ACTION_CHECKOUT";
	private final String PRODUCTS = "PRODUCTS";
	private final String CART = "CART";

	public ViewFrame() {
		// set up the frame
		this.setSize(500, 250);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SuperMarket Application");
		this.setLocationRelativeTo(null);

		mContainer = this.getContentPane();
		mContainer.setLayout(new BorderLayout());
		createButtons();// helper method

		// add an instance of Products Panel
		pnl_products = new ViewProductsPanel();
		// add instance of Cart Panel
		pnl_cart = new ViewCartPanel();
		// create a card layout and add the panels
		cardLayout = new CardLayout();
		pnl_card_holder = new JPanel(cardLayout);
		pnl_card_holder.add(pnl_products, PRODUCTS);
		pnl_card_holder.add(pnl_cart, CART);
		mContainer.add(pnl_card_holder, BorderLayout.CENTER);
	}

	private void createButtons() {
		btn_add_cart = new JButton("Add to Cart");
		btn_back = new JButton("Back to Shop");
		btn_edit = new JButton("Remove (quanity)");
		btn_remove = new JButton("Remove (this product only)");
		btn_start = new JButton("Start Shopping");
		btn_view_cart = new JButton("View Cart");
		btn_view_deals = new JButton("View Today's Deals");
		btn_clear_cart = new JButton("Clear Cart");
		btn_checkout = new JButton("Check Out");

		pnl_buttons = new JPanel();
		mContainer.add(pnl_buttons, BorderLayout.SOUTH);// add buttons panel to
														// container
		pnl_buttons.add(btn_add_cart);
		pnl_buttons.add(btn_back);
		pnl_buttons.add(btn_edit);
		pnl_buttons.add(btn_remove);
		pnl_buttons.add(btn_view_cart);
		pnl_buttons.add(btn_view_deals);
		pnl_buttons.add(btn_start);
		pnl_buttons.add(btn_clear_cart);
		pnl_buttons.add(btn_checkout);

		btn_add_cart.setActionCommand(ACTION_ADD_TO_CART);
		btn_back.setActionCommand(ACTION_BACK_TO_SHOP);
		btn_edit.setActionCommand(ACTION_EDIT_QUANTITY);
		btn_remove.setActionCommand(ACTION_REMOVE_ALL);
		btn_view_cart.setActionCommand(ACTION_VIEW_CART);
		btn_view_deals.setActionCommand(ACTION_VIEW_DEALS);
		btn_start.setActionCommand(ACTION_START_SHOPPING);
		btn_clear_cart.setActionCommand(ACTION_CLEAR_CART);
		btn_checkout.setActionCommand(ACTION_CHECKOUT);

		// default visability
		btn_back.setVisible(false);
		btn_edit.setVisible(false);
		btn_remove.setVisible(false);
		btn_add_cart.setVisible(false);
		btn_view_cart.setVisible(false);
		btn_view_deals.setVisible(false);
		btn_clear_cart.setVisible(false);
		btn_checkout.setVisible(false);
		// should only see start button at startup
		btn_start.setVisible(true);

	}

	public void setPresenter(Presenter presenter) {

		// The VIEW learns about the PRESENTER here
		// From the presenter, get the corresponding ActionListeners for
		// each UI component
		ActionListener buttonHandler = presenter.getControlButtonsHandler();
		btn_back.addActionListener(buttonHandler);
		btn_start.addActionListener(buttonHandler);
		btn_view_cart.addActionListener(buttonHandler);
		btn_view_deals.addActionListener(buttonHandler);
		btn_clear_cart.addActionListener(buttonHandler);
		btn_checkout.addActionListener(buttonHandler);

		ActionListener listButtonHandler = presenter.getListActionHandler();
		btn_add_cart.addActionListener(listButtonHandler);
		btn_edit.addActionListener(listButtonHandler);
		btn_remove.addActionListener(listButtonHandler);
		// children VIEWS of this Frame also need to know the PRESENTER
		pnl_products.setPresenter(presenter);
		pnl_cart.setPresenter(presenter);

	}

	public void setButtonsForShopping(boolean shopping) {
		/*
		 * When shopping (selecting items and adding to cart), BACK, EDIT,
		 * REMOVE & CLEAR buttons are not visible
		 */
		btn_back.setVisible(!shopping);
		btn_edit.setVisible(!shopping);
		btn_remove.setVisible(!shopping);
		btn_clear_cart.setVisible(!shopping);
		/* When shopping, only ADD, VIEW, and DEALS should be visible */
		btn_add_cart.setVisible(shopping);
		btn_view_cart.setVisible(shopping);
		btn_view_deals.setVisible(shopping);
		btn_start.setVisible(false);
	}

	public void showProducts(ProductCatalogue pc) {
		pnl_products.showProducts(pc);
	}

	public void showCart(CheckOut checkOut) {
		pnl_cart.showCart(checkOut);
	}

	public void showProductsPanelCard(boolean show) {
		if (show)
			cardLayout.show(pnl_card_holder, PRODUCTS);
		else
			cardLayout.show(pnl_card_holder, CART);

		this.pack();
	}

	public void showCartEditButtons(boolean show) {
		/*
		 * this method is called from the presenter after the user clicks on
		 * CLEAR CART or VIEW CART, and if there are items in the cart, the
		 * corresponding buttons will become visible
		 */
		btn_clear_cart.setVisible(show);
		btn_remove.setVisible(show);
		btn_edit.setVisible(show);
		/*
		 * If there are items in the cart, the option to checkout should be
		 * available
		 */
		btn_checkout.setVisible(show);
		this.pack();
	}

}
