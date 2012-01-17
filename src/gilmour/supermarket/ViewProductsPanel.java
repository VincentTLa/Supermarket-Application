package gilmour.supermarket;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/*
 * The main VIEW is the ViewFrame class and it uses a card layout to
 * display/switch between the customer's cart and products for sale. THIS
 * class is the products panel
 */

@SuppressWarnings("serial")
public class ViewProductsPanel extends JPanel {

	private ListSelectionListener mListSelectionListener;
	private JList list;
	private DefaultListModel listmodel;

	public ViewProductsPanel() {
		listmodel = new DefaultListModel();
		list = new JList();
		mListSelectionListener = null;
	}

	public void setPresenter(Presenter presenter) {
		// register a ListSelectionListener to this panel which
		// responds to list selections
		mListSelectionListener = presenter.getListSelectionListener();
	}

	public void showProducts(ProductCatalogue pc) {
		// The PRESENTER knows about the VIEW.
		// This method is called from the PRESENTER, which provides the data
		for (int x = 0; x < pc.getCatalogue().size(); x++) {
			if (pc.getCatalogue().get(x) instanceof UnitProduct) {
				UnitProduct up = (UnitProduct) pc.getCatalogue().get(x);
				String cost = String.format("%.2f", up.getUnitCost());
				listmodel.addElement(up.getProductName() + " $" + cost);
			} else {
				WeighableProduct wp = (WeighableProduct) pc.getCatalogue().get(
						x);
				String cost = String.format("%.2f", wp.getCostPer100G());
				listmodel.addElement(wp.getProductName() + " $" + cost
						+ "/100g");
			}
		}
		list.setModel(listmodel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		this.add(list);
		list.addListSelectionListener(mListSelectionListener);
	}
}
