package edu.cmu.square.client.ui.performTradeoffAnalysis;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.GwtTerm;

import edu.cmu.square.client.utils.SquareUtil;

public class EditTradeoffReasonDialog extends DialogBox
{
	
	private final TextArea tradeoffReasonTextBox = new TextArea();

	private GwtTradeoffReason current;
	private PerformTradeoffAnalysisPane updateTradeoffReasonCommand;
	private List<GwtTradeoffReason> listOfTradeoffReasons = new ArrayList<GwtTradeoffReason>();
	final PerformTradeoffAnalysisMessages messages = (PerformTradeoffAnalysisMessages) GWT.create(PerformTradeoffAnalysisMessages.class);
	private Button saveButton;
	

	public EditTradeoffReasonDialog(GwtTradeoffReason currentTradeoffReason, List<GwtTradeoffReason> tradeoffReasons, PerformTradeoffAnalysisPane command)
		{
			super();
		
			current = currentTradeoffReason;
			this.listOfTradeoffReasons = tradeoffReasons;
			this.updateTradeoffReasonCommand = command;

			this.initializeDialog(currentTradeoffReason);
		}

	/**
	 * Sets up the controls in the dialog
	 * 
	 * @param SoftwarePackage
	 *            The category to be updated in this dialog.
	 */
	private void initializeDialog(GwtTradeoffReason tradeoffReason)
	{

		VerticalPanel baseLayout = new VerticalPanel();
		VerticalPanel nameLayout = new VerticalPanel();
		VerticalPanel descriptionLayout = new VerticalPanel();
		HorizontalPanel buttonsLayout = new HorizontalPanel();
		this.setText(messages.editTradeoffReasonDialogBoxTitle());
		nameLayout.add(new Label(messages.editTradeoffReasonDialogBoxName()));
		nameLayout.add(this.tradeoffReasonTextBox);

		this.tradeoffReasonTextBox.setWidth("500px");
		this.tradeoffReasonTextBox.setSize("500px", "80px");
		this.tradeoffReasonTextBox.setText(tradeoffReason.getTradeoffreason());

		// Set up the buttons
		saveButton = new Button(messages.editTradeoffReasonDialogBoxSave(), new SaveHandler(this, tradeoffReason));
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
		private EditTradeoffReasonDialog dialog = null;
		private GwtTradeoffReason localTradeoffReason = null;
		String currentTradeoffReason = "";
		public SaveHandler(EditTradeoffReasonDialog dialogPointer, GwtTradeoffReason newTradeoffReason)
			{
				super();
				
				System.out.println("edit tradeoff dialog........."+newTradeoffReason.getPackageId());
				
				this.dialog = dialogPointer;
				this.localTradeoffReason = new GwtTradeoffReason();
				this.localTradeoffReason.setProjectId(newTradeoffReason.getProjectId());
				this.localTradeoffReason.setPackageId(newTradeoffReason.getPackageId());
				this.localTradeoffReason.setTradeoffreason(newTradeoffReason.getTradeoffreason());
				currentTradeoffReason = newTradeoffReason.getTradeoffreason();
			}

		public void onClick(ClickEvent event)
		{
			List<GwtTradeoffReason> list = listOfTradeoffReasons;
			this.dialog.hide();

			localTradeoffReason.setTradeoffreason(SquareUtil.firstCharacterToUpperCase(tradeoffReasonTextBox.getText().trim()));
			updateTradeoffReasonCommand.updateCommand(this.localTradeoffReason);
		}
	}

	private class CancelHandler implements ClickHandler
	{
		private EditTradeoffReasonDialog dialog = null;

		public CancelHandler(EditTradeoffReasonDialog dialog)
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
