package edu.cmu.square.client.ui.FinalProductSelection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.utils.SquareUtil;

public class AddRationaleDialog extends DialogBox
{
	
	private final TextArea tradeoffReasonTextBox = new TextArea();

	private FinalProductSelectionPane updateRationaleCommand;
	final FinalProductSelectionMessages messages = (FinalProductSelectionMessages) GWT.create(FinalProductSelectionMessages.class);
	private Button saveButton;
	

	public AddRationaleDialog(FinalProductSelectionPane command, GwtProject project, GwtSoftwarePackage spackage)
		{
			super();
			this.updateRationaleCommand = command;
			this.initializeDialog(project, spackage);
		}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param SoftwarePackage
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtProject project, GwtSoftwarePackage spackage)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		this.setText(messages.editTradeoffReasonDialogBoxTitle());
		nameLayout.add(new Label(messages.whatareyoudoing()));
		nameLayout.add(new Label(messages.editTradeoffReasonDialogBoxName()));
		nameLayout.add(this.tradeoffReasonTextBox);

		this.tradeoffReasonTextBox.setWidth("500px");
		this.tradeoffReasonTextBox.setSize("500px", "80px");

		// Set up the buttons
		saveButton = new Button(messages.editTradeoffReasonDialogBoxSave(), new SaveHandler(this, project, spackage));
		Button cancelButton = new Button(messages.editTradeoffReasonDialogBoxCancel(), new CancelHandler(this));
		
		this.tradeoffReasonTextBox.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event)
			{
				 configureButton();
			}
			
		});
		
		this.tradeoffReasonTextBox.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		this.tradeoffReasonTextBox.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				 configureButton();
				
			}
			
		});
		
		
		saveButton.setWidth("100px");
		cancelButton.setWidth("100px");
		
		buttonsLayout.setSpacing(10);
		buttonsLayout.add(saveButton);
		buttonsLayout.add(cancelButton);

		// set the base layout
		baseLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		baseLayout.add(nameLayout);
		baseLayout.add(descriptionLayout);
		baseLayout.add(buttonsLayout);
		baseLayout.setSpacing(5);

		this.setWidget(baseLayout);
	}
	private void configureButton()
	{
		if(tradeoffReasonTextBox.getText().trim().equalsIgnoreCase(""))
		{
			saveButton.setEnabled(false);
		}
		else
		{
			saveButton.setEnabled(true);
		}
	}

	private class SaveHandler implements ClickHandler
	{
		private AddRationaleDialog dialog = null;
		private GwtRationale localRationale = null;
		
		public SaveHandler(AddRationaleDialog dialogPointer, GwtProject project, GwtSoftwarePackage spackage)
			{
				super();
				
				this.dialog = dialogPointer;
				this.localRationale = new GwtRationale();
				this.localRationale.setProject(project);
				this.localRationale.setPackage(spackage);
			}

		public void onClick(ClickEvent event)
		{
			this.dialog.hide();

			localRationale.setRationale(SquareUtil.firstCharacterToUpperCase(tradeoffReasonTextBox.getText().trim()));
			updateRationaleCommand.updateCommand(this.localRationale);
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private AddRationaleDialog dialog = null;

		public CancelHandler(AddRationaleDialog dialog)
			{
				super();
				this.dialog = dialog;
			}

		public void onClick(ClickEvent event)
		{
			this.dialog.hide(true);
		}
	}
}
