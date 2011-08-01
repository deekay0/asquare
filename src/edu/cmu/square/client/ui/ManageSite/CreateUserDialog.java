package edu.cmu.square.client.ui.ManageSite;

import java.util.List;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.utils.SquareUtil;


public class CreateUserDialog extends DialogBox
{
	private final TextBox userNameTextBox = new TextBox();
    private final TextBox fullNameTextBox = new TextBox();
    private final TextBox emailTextBox = new TextBox();
    private final ListBox rolesComboBox = new ListBox(false);
    private final CheckBox lockCheckBox = new CheckBox();
    private final Label passwordLabel = new Label();
    
    private final int MAX_NAME_LENGTH = 48;
    private final int MAX_EMAIL_LENGTH = 48;
    
    private ManageSitePane caller = null;
    final ManageSitePaneMessages messages = (ManageSitePaneMessages)GWT.create(ManageSitePaneMessages.class);
    
    private List<GwtUser> users = null;
    
    private DisclosurePanel errorPanel = new DisclosurePanel();
    
    
	public CreateUserDialog(ManageSitePane manageSitePointer, List<GwtUser> users)
	{
		super();
		this.caller = manageSitePointer;
		this.users = users;
		this.initializeDialog(new GwtUser());
	}
	
	public CreateUserDialog(ManageSitePane manageSitePointer, GwtUser newUser, List<GwtUser> users)
	{
		super();
		this.caller = manageSitePointer;
		this.users = users;
		this.initializeDialog(newUser);
	}
	
	private void initializeDialog(GwtUser user)
	{	
		if (user.getUserId() == -1)
		{
			passwordLabel.setText(SquareUtil.generatePassword());
		}
		
		this.rolesComboBox.addItem(this.messages.squareAdministratorRoleTitle());
		this.rolesComboBox.addItem(this.messages.squareUserRoleTitle());
		this.userNameTextBox.setText(user.getUserName());
		this.emailTextBox.setText(user.getEmailAddress());
	    this.emailTextBox.setMaxLength(MAX_EMAIL_LENGTH);
	    
	    this.fullNameTextBox.setText(user.getFullName());
	    this.fullNameTextBox.setMaxLength(MAX_NAME_LENGTH);
	    this.lockCheckBox.setText(this.messages.lockCheck());
    	this.lockCheckBox.setValue(user.isLocked());
	    
    	this.fullNameTextBox.setWidth("150px");
    	this.emailTextBox.setWidth("150px");
    	this.rolesComboBox.setWidth("160px");
    	
    	this.errorPanel.setAnimationEnabled(true);
    	this.errorPanel.setContent(new Label(""));
    	this.errorPanel.setOpen(false);
    	
	    if (user.getIsAdmin())
	    {
	    	this.rolesComboBox.setSelectedIndex(0);
	    	
	    }
	    else
	    {
	    	this.rolesComboBox.setSelectedIndex(1);
	    }
	    
	    this.fullNameTextBox.addFocusHandler(new FocusHandler()
		{
			public void onFocus(FocusEvent event)
			{
				errorPanel.setOpen(false);				
			}
			
		});
	    
	    this.emailTextBox.addFocusHandler(new FocusHandler()
		{
			public void onFocus(FocusEvent event)
			{
				errorPanel.setOpen(false);			
			}
		});
	    
	    
	    // layout the controls for user input in the dialog
	    FlexTable layout = new FlexTable();
	    layout.setWidth("100%");
	    layout.setCellSpacing(10);
	   
	    layout.setWidget(0, 0, new Label(this.messages.username()));
	    layout.setWidget(0, 1, this.userNameTextBox);
	    
	    int tableOffset = 1;
	    if (user.getUserId() == -1)
		{
	    	layout.setWidget(1, 0, new Label(this.messages.passwordLabelText()));
	    	layout.setWidget(1, 1, passwordLabel);
	    	tableOffset = 0;
		}
	    
	    layout.setWidget(2 - tableOffset, 0, new Label(this.messages.nameLabelText()));
	    layout.setWidget(2 - tableOffset, 1, this.fullNameTextBox);
	    
	    layout.setWidget(3 - tableOffset, 0, new Label(this.messages.emailLabelText()));
	    layout.setWidget(3 - tableOffset, 1, this.emailTextBox);

	    layout.setWidget(4 - tableOffset, 0, new Label(this.messages.siteRoleLabelText()));
	    layout.setWidget(4 - tableOffset, 1, this.rolesComboBox);
	    
	    layout.setWidget(5 - tableOffset, 1, this.lockCheckBox);

	    // Set up the buttons
	    Button saveButton = new Button(messages.save(), new SaveHandler(user));
	    Button cancelButton = new Button(messages.cancel(), new CancelHandler());
	    
	    saveButton.setWidth("100px");
	    cancelButton.setWidth("100px");
	    
	    HorizontalPanel buttons = new HorizontalPanel();
	    buttons.setSpacing(10);
	    buttons.add(saveButton);
	    buttons.add(cancelButton);
	    layout.setWidget(6 - tableOffset, 0, buttons);
	    
	    FlexCellFormatter formatter = layout.getFlexCellFormatter();
	    formatter.setHorizontalAlignment(6 - tableOffset, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    formatter.setColSpan(6 - tableOffset, 0, 2);
	    
	    layout.setWidget(7 - tableOffset, 0, this.errorPanel);
	    layout.getFlexCellFormatter().setColSpan(7 - tableOffset, 0, 2);
	    layout.getFlexCellFormatter().setHorizontalAlignment(7 - tableOffset, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    
	    this.setWidget(layout);
	}
	
	private void showError(String message)
	{
		errorPanel.setOpen(false);
		errorPanel.clear();
		
		Label errorLabel = new Label(message);
		errorLabel.setStyleName("square-error");
		errorPanel.add(errorLabel);
		errorPanel.setOpen(true);
	}
	
	
	
	// ******* Handlers for the buttons *************
	
	private class SaveHandler implements ClickHandler
	{
		private GwtUser user = null;
		
		public SaveHandler(GwtUser newUser)
		{
			super();
			this.user = newUser;
		}
		
		
		public void onClick(ClickEvent event)
		{
    		this.user.setIsLocked(lockCheckBox.getValue());
    		errorPanel.setOpen(false);
    		
    		String email = emailTextBox.getText().trim();
    		
    		for (GwtUser currentUser : users)
    		{
    			if (currentUser.getEmailAddress().equalsIgnoreCase(email) &&
    				currentUser.getUserId() != this.user.getUserId())
    			{
    				showError(messages.duplicateEmail());
    				return;
    			}
    			
    			if (currentUser.getUserName().equalsIgnoreCase(userNameTextBox.getText().trim()) &&
        				currentUser.getUserId() != this.user.getUserId())
        		{
        			showError(messages.duplicateUserName());
        			return;
        		}
    			
    			if (userNameTextBox.getText().trim().length() == 0)
    			{
    				showError(messages.invalidNameOrEmail());
    				return;
    			}
    			
    		}
    		
    		// Check that they are following at least a nominal email address
    	    if (!email.matches(SquareUtil.getEmailRegex()))
    	    {
    	    	showError(messages.invalidEmailAddress());
    	    	return;
    	    }

    	    // check to make sure something has been entered.
			if (fullNameTextBox.getText().trim().length() == 0 || email.length() == 0)
			{
				showError(messages.invalidNameOrEmail());
				return;
			}


			this.user.setUserName(userNameTextBox.getText().trim());
			this.user.setFullName(fullNameTextBox.getText().trim());
			this.user.setEmailAddress(email);
				
			if(rolesComboBox.getSelectedIndex() == 0)
			{
				this.user.setIsAdmin(true);
			}
			else
			{
				this.user.setIsAdmin(false);
			}
	    		
	    	if (!this.user.isSavedInDb())  //create a new user, pass it back to the caller
	    	{
	    		caller.addUser(this.user, passwordLabel.getText());
	    		hide();
	    	}
	    	else  //update the old user, pass it back to the caller
	    	{   
	    		caller.updateUser(this.user);
	    		hide();
	    	}    	
		}
	}
	
	private class CancelHandler implements ClickHandler
	{
		public void onClick(ClickEvent event)
		{
    		hide(true);
		}
	}
}
