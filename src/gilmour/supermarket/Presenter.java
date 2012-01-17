package gilmour.supermarket;

import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionListener;

/*
 * The presenter is the 'middle-man' between the VIEW and the MODEL. The
 * PRESENTER has a reference to both V and M. UI events affect the model
 * through the presenter (the view has no reference to the model and vice-versa)
 * The handler classes are part of the view
 */

public class Presenter {

	@SuppressWarnings("unused")
	private ViewFrame mViewFrame;
	@SuppressWarnings("unused")
	private CheckOut mCheckOut;
	@SuppressWarnings("unused")
	private ProductCatalogue mProductCatalogue;
	private ControlButtonHandler mControlButtonHandler;
	private ListProductsHandler mListHandler;

	public Presenter() {
		// creates instances of the handlers
		this.mControlButtonHandler = new ControlButtonHandler();
		this.mListHandler = new ListProductsHandler();
		this.mCheckOut = null;
		this.mProductCatalogue = null;

	}

	public void setView(ViewFrame viewFrame) {
		this.mViewFrame = viewFrame;
		// pass on the view to the handlers
		mControlButtonHandler.setView(viewFrame);
		mListHandler.setView(viewFrame);
	}

	public void setModel(CheckOut checkOut, ProductCatalogue productCatalogue) {
		this.mCheckOut = checkOut;
		this.mProductCatalogue = productCatalogue;
		// pass on the model to the handlers
		mControlButtonHandler.setModel(checkOut, productCatalogue);
		mListHandler.setModel(checkOut, productCatalogue);
	}

	public ActionListener getControlButtonsHandler() {
		return this.mControlButtonHandler;
	}

	/*
	 * TODO REVIEW NOTE: Using the same object to return 2 different
	 * Listeners.Should this be done differently?
	 */
	
	public ActionListener getListActionHandler() {
		return this.mListHandler;
	}

	public ListSelectionListener getListSelectionListener() {
		return this.mListHandler;
	}

}
