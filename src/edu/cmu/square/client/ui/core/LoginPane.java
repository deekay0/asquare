package edu.cmu.square.client.ui.core;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.LogInService;
import edu.cmu.square.client.remoteService.interfaces.LogInServiceAsync;
import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.content.LoginPaneMessages;


public class LoginPane extends BasePane 
{
	private final LoginPaneMessages messages = (LoginPaneMessages)GWT.create(LoginPaneMessages.class);
	
	private DisclosurePanel errorPanel = new DisclosurePanel();
	 
	public LoginPane(State stateInfo)
	{
		super(stateInfo);
		
				
		final PasswordTextBox password = new PasswordTextBox();
		password.setWidth("200px");
		
		final TextBox username = new TextBox();
		username.setWidth("200px");
		
		VerticalPanel mainLayout = new VerticalPanel();
		mainLayout.setWidth("400px");
		mainLayout.setStyleName("square-login");
		mainLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		// user name
		HorizontalPanel usernameLayout = new HorizontalPanel();
		usernameLayout.setSpacing(4);
		usernameLayout.add(new Label(messages.userNameLabel()));
		usernameLayout.add(username);
		
		// password
		HorizontalPanel passwordLayout = new HorizontalPanel();
		passwordLayout.setSpacing(4);
		passwordLayout.add(new Label(messages.passwordLabel()));
		passwordLayout.add(password);
		
		//login button...
		final Button loginButton = new Button(messages.loginButton());
		
		//error panel
		errorPanel.setAnimationEnabled(true);
		errorPanel.setContent(new Label(""));
		errorPanel.setOpen(false);
		errorPanel.setStyleName("square-error");

		//instructions
		Label instructions = new Label(messages.instructions());
		instructions.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		mainLayout.add(usernameLayout);
		mainLayout.add(passwordLayout);
		mainLayout.add(loginButton);
		mainLayout.add(errorPanel);
		
		VerticalPanel bigLayout = new VerticalPanel();
		bigLayout.add(instructions);
		bigLayout.add(mainLayout);
		
		username.setFocus(true);
		loginButton.setTabIndex(0);
		
		// When the button is clicked, try to log the user in.
		loginButton.addClickHandler(new ClickHandler()
		{	
			public void onClick(ClickEvent event)
			{
				
				logIn(username.getText(), password.getText());
				password.setText("");
			}
		});
		

		//handles the enter event from the user name text box
		username.addKeyPressHandler(new KeyPressHandler()
		{
			public void onKeyPress(KeyPressEvent event)
			{
				if (event.getCharCode() == KeyCodes.KEY_ENTER)
				{    
					logIn(username.getText(),password.getText());
			   	}
			}
		});
		
		//handles the enter event from the password text box
		password.addKeyPressHandler(new KeyPressHandler()
		{	
			public void onKeyPress(KeyPressEvent event)
			{
				if (event.getCharCode() == KeyCodes.KEY_ENTER)
				{
					logIn(username.getText(),password.getText());
				}
			}
		});
		
		
		this.getContent().add(bigLayout);
		
	}

	
	public void logIn(String userName, String password)
	{	showStatusBar("Verifying information...");
		errorPanel.setOpen(false);
		
		//Validation of emptyfields
		if (userName.trim().length() == 0 || password.trim().length() == 0)
		{
			showError(messages.emptyValidator());
		}
		
		else
		{
			LogInServiceAsync logInService = GWT.create(LogInService.class);
			ServiceDefTarget endpoint = (ServiceDefTarget) logInService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "logIn.rpc");	
		
			logInService.logIn(userName, password, new AsyncCallback<GwtUser>()
			{
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException)
					{
						SquareException se = (SquareException) caught;
						switch (se.getType())
						{
							case constraintViolated :
								showError(messages.passwordIllegalError());
								break;
							case duplicateName :
								showError(messages.duplicateUsernameError());
								break;
							default :
								showError(messages.generalLoginError());
								break;
						}
					}
					else
					{
						showError(messages.generalLoginError());
					}
				}

				public void onSuccess(GwtUser result)
				{
					if (result.isAuthenticated())
					{
						currentState.setSessionID(result.getSessionID());
						currentState.setUserName(result.getUserName());
						currentState.setUserId(result.getUserId());
						currentState.setFullName(result.getFullName());
						currentState.setSiteAdministrator(result.getIsAdmin());
						currentState.setSkipTeachStep(result.isSkipTeachStep());
						currentState.setAccountLocked(result.isLocked());
						currentState.setAuthenticated(result.isAuthenticated());
						
						History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.home));
					}
					else
					{
						if (result.isLocked())
						{
							showError(messages.accountLocked());
						}
						else
						{
							showError(messages.loginFailed());
						}
					}
					hideStatusBar();
				}
			});
		}	
	}


	private void showError(String errorMessage)
	{
		this.errorPanel.setContent(new Label(errorMessage));
		this.errorPanel.setOpen(true);
	}
}

