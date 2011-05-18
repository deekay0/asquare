package edu.cmu.square.client.ui.ManageSite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.utils.SquareUtil;

public class ResetPasswordDialog extends DialogBox
{
	private final PasswordTextBox newPasswordTextBox = new PasswordTextBox();
	private final PasswordTextBox confirmPasswordTextBox = new PasswordTextBox();
	
	private DisclosurePanel errorPanel = new DisclosurePanel();
	private ManageSitePane manageSite = null;
	final ManageSitePaneMessages messages = (ManageSitePaneMessages)GWT.create(ManageSitePaneMessages.class);
	
	public ResetPasswordDialog(ManageSitePane manageSitePointer, GwtUser user)
	{
		super();
		this.manageSite = manageSitePointer;
		this.initializeDialog(user);
	}
	
	
	
	private void initializeDialog(GwtUser gwtUser)
	{
		final CheckBox autoGenerateYesNo = new CheckBox(messages.autogeneratePassword());
		final CheckBox emailYesNo = new CheckBox(messages.email());
		this.newPasswordTextBox.setSize("200px", "15px");
		this.confirmPasswordTextBox.setSize("200px", "15px");
		
		this.newPasswordTextBox.setMaxLength(25);
		this.confirmPasswordTextBox.setMaxLength(25);
		
		
		this.errorPanel.setAnimationEnabled(true);
    	this.errorPanel.setContent(new Label(""));
    	this.errorPanel.setOpen(false);
		autoGenerateYesNo.addClickHandler(
				new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						errorPanel.clear();
						errorPanel.setOpen(false);
						if (autoGenerateYesNo.getValue()) 
						{
							String password = SquareUtil.generatePassword();
							newPasswordTextBox.setValue(password);
							confirmPasswordTextBox.setValue(password);
							emailYesNo.setValue(true);
							emailYesNo.setEnabled(false);
						}
						else 
						{
							newPasswordTextBox.setValue("");
							confirmPasswordTextBox.setValue("");
							emailYesNo.setValue(false);
							emailYesNo.setEnabled(true);
						}
						
					}
					
				}
		);
		this.newPasswordTextBox.addFocusHandler(new FocusHandler()
		{
			public void onFocus(FocusEvent event)
			{
				errorPanel.setOpen(false);					
			}
			
		});
		
		this.confirmPasswordTextBox.addFocusHandler(new FocusHandler()
		{
			public void onFocus(FocusEvent event)
			{
				errorPanel.setOpen(false);					
			}
			
		});
		
		FlexTable layout = new FlexTable();
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		
	    layout.setCellSpacing(10);
	    layout.setWidget(0, 0, autoGenerateYesNo);
	    layout.setWidget(0, 1, emailYesNo);
//	    cellFormatter.setColSpan(0, 0, 2);
	    layout.setWidget(1, 0, new Label(this.messages.newPasswordLabelText()));
	    layout.setWidget(1, 1, this.newPasswordTextBox);
	    
	    layout.setWidget(2, 0, new Label(this.messages.confirmPasswordLabelText()));
	    layout.setWidget(2, 1, this.confirmPasswordTextBox);
	    
	    this.setWidget(layout);
	    
	    layout.setWidget(3, 0, this.errorPanel);
	    cellFormatter.setColSpan(3, 0, 2);
	    cellFormatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    // Set up the buttons
	    Button saveButton = new Button(messages.save(), new SaveHandler(gwtUser, emailYesNo));
	    Button cancelButton = new Button(messages.cancel(), new ClickHandler()
	    {

			@Override
			public void onClick(ClickEvent event)
			{
				hide(true);
			}
	    	
	    });
	    
	    saveButton.setWidth("100px");
	    cancelButton.setWidth("100px");
	    
	    HorizontalPanel buttonPanel = new HorizontalPanel();
	    buttonPanel.setSpacing(10);
	    buttonPanel.add(saveButton);
	    buttonPanel.add(cancelButton);
	    layout.setWidget(4, 0, buttonPanel);
	    
	   
	    cellFormatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    cellFormatter.setColSpan(4, 0, 2);
	}
	
	private class SaveHandler implements ClickHandler
	{
		private CheckBox emailYesNo;
		private GwtUser user;
		public SaveHandler(GwtUser user, CheckBox emailYesNo)
		{
			super();
			this.user = user;
			this.emailYesNo = emailYesNo;
		}
		
		public void onClick(ClickEvent event)
		{
			errorPanel.setOpen(false);
				
			if(newPasswordTextBox.getText().trim().length() == 0 || confirmPasswordTextBox.getText().trim().length() == 0)
			{
				showError(messages.emptyPassword());
			}
			else if(!newPasswordTextBox.getText().equals(confirmPasswordTextBox.getText()))
			{
				showError(messages.passwordDoesNotMatch());
			}
			else
			{
				manageSite.updatePassword(user, newPasswordTextBox.getText().trim(), emailYesNo.getValue());
				hide();
			}
		
		}
		
	}
	private void showError(String message)
	{
		errorPanel.setOpen(false);
		errorPanel.clear();
		errorPanel.add(new Label(message));
		errorPanel.setOpen(true);
	}
		
}
