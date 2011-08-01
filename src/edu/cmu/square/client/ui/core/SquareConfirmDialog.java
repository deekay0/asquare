package edu.cmu.square.client.ui.core;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.ui.ManageSite.ManageSitePaneMessages;

public class SquareConfirmDialog extends DialogBox
{
	final ManageSitePaneMessages messages = (ManageSitePaneMessages) GWT.create(ManageSitePaneMessages.class);

	private VerticalPanel layout = new VerticalPanel();
	private HorizontalPanel descriptionLayout = new HorizontalPanel();
	private FlexTable buttonLayout = new FlexTable();

	private Image warningImage = new Image("images/warning-icon.png");
	private boolean confirmed = false;
	private Command caller;

	private Button confirmButton = new Button(messages.confirm());
	private Button cancelButton = new Button(messages.cancel());

	public SquareConfirmDialog(Command caller,String dialogMessage)
	{
		super();
		this.caller = caller;
		initializeDialog(dialogMessage);
	}
	
	public SquareConfirmDialog(Command caller,String dialogMessage,String okButtonText,String cancelButtonText)
		{
			super();
			this.caller = caller;
			this.confirmButton.setText(okButtonText);
			this.cancelButton.setText(cancelButtonText);
			initializeDialog(dialogMessage);
		}

	private void initializeDialog(String dialogMessage)
	{
		
		super.setModal(true);
		super.setAnimationEnabled(true);
		this.warningImage.setSize("60px", "60px");

		this.descriptionLayout.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		this.descriptionLayout.add(new HTML(dialogMessage));
		this.descriptionLayout.add(warningImage);
		this.descriptionLayout.setWidth("100%");
		this.descriptionLayout.setSpacing(15);

		this.confirmButton.setWidth("145px");
		this.cancelButton.setWidth("145px");

		this.buttonLayout.setWidget(0, 0, confirmButton);
		this.buttonLayout.setWidget(0, 1, cancelButton);
		this.buttonLayout.setWidth("100%");
		this.buttonLayout.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		this.buttonLayout.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		this.confirmButton.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					confirmed = true;
					caller.execute();
					hide();
				}
			});

		this.cancelButton.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					confirmed = false;
					hide();
				}
			});

		this.layout.setSpacing(6);
		this.layout.add(descriptionLayout);
		this.layout.add(buttonLayout);

		this.setWidget(layout);

	}

	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}

	public boolean isConfirmed()
	{
		return confirmed;
	}

}
