package gilmour.supermarket;

import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/*
 * The main VIEW is the ViewFrame class and it uses a card layout to
 * display/switch between the customer's cart and products for sale. THIS
 * class is the cart panel
 */

@SuppressWarnings("serial")
public class ViewCartPanel extends JPanel {


	private ListSelectionListener mListSelectionListener;
	private JList list;
	private DefaultListModel listmodel;

	public ViewCartPanel() {
		listmodel = new DefaultListModel();
		list = new JList();
		mListSelectionListener = null;
	}

	public void setPresenter(Presenter presenter) {
		this.mListSelectionListener = presenter.getListSelectionListener();
	}

	public void showCart(CheckOut checkOut) {
		/*
		 * this method is invoked from the ListProductsHandler, and populates
		 * the list of cart items
		 */
		listmodel.clear();
		Iterator<UnitProduct> unitProducts = checkOut.getUnitBasketIterator();
		while (unitProducts.hasNext()) {
			UnitProduct up = unitProducts.next();
			int quantity = ShoppingBasket.getBasketInstance().getUnitBasket()
					.get(up);
			String total = String.format("%.2f", (up.getUnitCost() * quantity));
			String element = up.getProductId() + " " + up.getProductName()
					+ " x " + quantity + " = $" + total;
			listmodel.addElement(element);
		}

		Iterator<WeighableProduct> weighProducts = checkOut
				.getWeighablesBasketIterator();
		while (weighProducts.hasNext()) {
			WeighableProduct wp = weighProducts.next();
			double quantity = ShoppingBasket.getBasketInstance()
					.getWeighablesBasket().get(wp);
			String weight = String.format("%.3f", quantity);
			String total = String.format("%.2f", (wp.getCostPer100G()
					* quantity * 10));
			String element = wp.getProductId() + " " + wp.getProductName()
					+ " x " + weight + " kg" + " = $" + total;
			listmodel.addElement(element);
		}
		list.setModel(listmodel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		this.add(list);
		list.addListSelectionListener(mListSelectionListener);

	}

}
