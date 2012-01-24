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

		Iterator<Product> i = checkOut.getBasketIterator();
		while (i.hasNext()) {
			Product product = i.next();
			String total = null;
			String element = null;
			int amount = checkOut.getAmount(product);

			if (product instanceof UnitProduct) {
				total = String.format("%.2f", product.getProductPrice()
						* amount);
				element = product.getProductId() + " "
						+ product.getProductName() + " @$"
						+ product.getProductPrice() + " each." + " Quantity = "
						+ amount + " = $" + total;

			} else if (product instanceof WeighableProduct) {
				total = String.format("%.2f",
						((WeighableProduct) product).getPricePerGram()
								* checkOut.getAmount(product));
				element = product.getProductId() + " "
						+ product.getProductName() + " @$"
						+ product.getProductPrice() + " per "
						+ ((WeighableProduct) product).getPriceMetric()
						+ " grams. Quantity = " + amount + " grams = $" + total;
			} else
				total = "error";
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
