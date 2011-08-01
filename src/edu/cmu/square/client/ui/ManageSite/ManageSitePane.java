package edu.cmu.square.client.ui.ManageSite;


import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteService;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class ManageSitePane extends BasePane
{	
	private ManageSiteServiceAsync manageSiteService = GWT.create(ManageSiteService.class);
		
	final ManageSitePaneMessages messages = (ManageSitePaneMessages)GWT.create(ManageSitePaneMessages.class);
	ServiceDefTarget endpoint = (ServiceDefTarget) manageSiteService;
	
	private VerticalPanel usersPanel = new VerticalPanel();
	
	private VerticalPanel projectsPanel = new VerticalPanel();
	
	private FlexTable userTable = new FlexTable();
	private List<GwtUser> users = null;
	private int lastRowClicked = -1;
	
	
	public ManageSitePane(State stateInfo)
	{
		super(stateInfo);

		//super.cleanProjectContext();
		super.showLoadingStatusBar();
		
		this.currentState = stateInfo;
		
		
		DecoratedTabPanel mainTabs = new DecoratedTabPanel();
		mainTabs.add(this.usersPanel, messages.tab1Title());
		mainTabs.add(this.projectsPanel, messages.tab3Title());
			
		mainTabs.selectTab(0);
		mainTabs.setSize("60%", "100%");
		
		//Get the users from the database and list them in the table
		this.getUsersForSite();
		this.setProjectsPanel();
		
		this.getContent().add(mainTabs);
	}

	
	public void addUser(GwtUser newUser, String password)
	{
	endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");
		
		this.manageSiteService.createUser(newUser, password, true, new AsyncCallback<GwtUser>()
		{
			@Override
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "creating user");
			}

			@Override
			public void onSuccess(GwtUser result)
			{
				addUserToTable(result);
				yellowFadeHandler.add(userTable, userTable.getRowCount() - 1);
				
				users.add(result);
			}
		});
	}

	
	public void updatePassword(final GwtUser gwtUser, String password, boolean resetPassword)
	{
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");	
		
		manageSiteService.updateUserPassword(gwtUser, password, resetPassword, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException)caught;
					switch (se.getType())
					{
						case invalidEmail:
							Window.alert(messages.invalidEmailAddress());
							break;
						case mailError:
							Window.alert(messages.errorSendingMail());
							break;
						case other:
							Window.alert(messages.errorUpdatingPassword());
							break;
						default:
							ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.updatePwdSessionTimeOut());
						break;
					} 
				}
			}

			public void onSuccess(Void result)
			{
				showStatusBarWithoutAnimation(messages.passwordUpdated());
				yellowFadeHandler.add(userTable, lastRowClicked);
			}
		});
	}
	
	
	public void updateUser(final GwtUser userToUpdate)
	{
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");	
		
		manageSiteService.updateUserNameEmailRole(userToUpdate, new AsyncCallback<Void>(){

			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.updateSessionTimeOut());
				}
			}

			public void onSuccess(Void result)
			{				
				userTable.setWidget(lastRowClicked, 0, new Label(userToUpdate.getFullName()));
				
				addLockImage(userToUpdate, lastRowClicked);
				
				if(userToUpdate.getIsAdmin())
				{
					userTable.setWidget(lastRowClicked, 2, new Label("Administrator"));
				}
				else
				{
					userTable.setWidget(lastRowClicked, 2, new Label("Square User"));
				}
				
				users.set(lastRowClicked - 1, userToUpdate);
				
				yellowFadeHandler.add(userTable, lastRowClicked);
				
				lastRowClicked = -1;
			}
		});
	}
	
	
	
	
	
	
	
	/* ----------------------------------
	 * Private Stuff
	 * ----------------------------------
	 */
		
	
	/**
	 * Initializes the widgets of the user panel of the main tab panel.
	 * This is the main grid in the middle of the screen.
	 */
	private void setUserPanel()
	{
		FlexCellFormatter formatter = this.userTable.getFlexCellFormatter();
		this.userTable.setWidth("100%");
		this.userTable.setCellSpacing(0);
		this.userTable.setStyleName("square-flex");
		
		//populate the flex table with default values
		Button createUserButton = new Button(messages.createButton());
		createUserButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				createAndShowUserDialog();			
			}	
		});
				
		this.userTable.setWidget(0, 0, createUserButton);
		this.userTable.setWidget(0, 2, new Label(messages.siteRoleTitle()));

		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		formatter.setStyleName(0, 2, "square-TableHeader");
		formatter.setStyleName(0, 3, "square-TableHeader");
	
		this.usersPanel.add(this.userTable);
	}
	
	private void addLockImage(GwtUser gwtUser, int rowID)
	{
		if (gwtUser.isLocked())
		{
			VerticalPanel lockPanel = new VerticalPanel();
			Image imageLock = new Image("images//LockIcon.gif");
			lockPanel.setStyleName("lock-icon");
			lockPanel.add(imageLock);
			this.userTable.setWidget(rowID, 1, lockPanel);
		}
		else
		{
			VerticalPanel lockPanel = new VerticalPanel();
			lockPanel.setStyleName("lock-icon");
			this.userTable.setWidget(rowID, 1, lockPanel);
		}
				
	}
	

	private void addUserToTable(final GwtUser newUser)
	{
		HorizontalPanel links = new HorizontalPanel(); //holds links so that they do not spread out in the flex table
		links.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		links.setStyleName("flex-link-bar");
		
		final int row = this.userTable.getRowCount();
		
		this.userTable.getFlexCellFormatter().setHorizontalAlignment(row, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		Label name = new Label(newUser.getFullName());
		if (newUser.getUserName().equals(this.currentState.getUserName()))  //We know this is the user currently logged in, tell them in the table.
		{
			name.setText(newUser.getFullName() + " " + messages.thisIsYou());
		}
		
		this.userTable.setWidget(row, 0, name);
		String siteWideRoleString = messages.squareUserRoleTitle();
		
		addLockImage(newUser, row);
		
		if (newUser.getIsAdmin())
		{
			siteWideRoleString = messages.squareAdministratorRoleTitle();
		}
	
		SquareHyperlink changeUserLink = new SquareHyperlink(messages.change());
		
		changeUserLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				createAndShowUserDialog(newUser);
				lastRowClicked = userTable.getCellForEvent(event).getRowIndex();
			}
		});
		
		SquareHyperlink resetPasswordLink = new SquareHyperlink(messages.resetPasswordLinkTitle());
		
		resetPasswordLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				lastRowClicked = userTable.getCellForEvent(event).getRowIndex();
				createAndShowPasswordDialog(newUser, row);
			}
		});
		links.add(resetPasswordLink);
		
		links.add(changeUserLink);
		
		//if the username of the current user is the same as the newUser, we cannot remove it.
		if (!this.currentState.getUserName().equals(newUser.getUserName()))
		{
			SquareHyperlink removeUserLink = new SquareHyperlink(messages.remove());
			links.add(removeUserLink);
			
			removeUserLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					removeUser(newUser);
					lastRowClicked = userTable.getCellForEvent(event).getRowIndex();
				}
			});
		}
		else
		{
			links.add(getSpacer(12));
		}
		
		this.userTable.setWidget(row, 2, new Label(siteWideRoleString));
		this.userTable.setWidget(row, 3, links);
	}
	

	private void createAndShowPasswordDialog(GwtUser user, int numberOfRows)
	{
		final ResetPasswordDialog dialogBox = new ResetPasswordDialog(this, user);
		dialogBox.setText(messages.resetPasswordDialogTitle());
		dialogBox.setAnimationEnabled(true);
		
	    dialogBox.center();
	    dialogBox.show();
	}
		
	private void createAndShowUserDialog()
	{
		final CreateUserDialog dialogBox = new CreateUserDialog(this, this.users);
	    dialogBox.setText(messages.createUserDialogTitle());
	    dialogBox.setAnimationEnabled(true);

	    dialogBox.center();
	    dialogBox.show();
	}
	
	
	private void createAndShowUserDialog(GwtUser user)
	{
		final CreateUserDialog dialogBox = new CreateUserDialog(this, user, users);
	    dialogBox.setText(messages.editUserDialogTitle());
	    dialogBox.setAnimationEnabled(true);
		
	    dialogBox.center();
	    dialogBox.show();
	}
		
	
	private void getUsersForSite()
	{
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");	
	
		manageSiteService.getAllUsers(new AsyncCallback<List<GwtUser>>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.loadUserSessionTimeOut());
			}
	
			public void onSuccess(List<GwtUser> result)
			{
				hideStatusBar();
				setUserPanel();
				
				users = result;
				for(GwtUser gwtUser: result)
				{
					addUserToTable(gwtUser);
				}
			}
		});
	}
	
	
	private void removeUser(final GwtUser userToRemove)
	{
		boolean response = Window.confirm(messages.confirmDelete(userToRemove.getFullName()));
		if (response)
		{
			this.manageSiteService.removeUser(userToRemove, new AsyncCallback<Boolean>()
			{
				public void onFailure(Throwable caught)
				{
					SquareException se = (SquareException)caught;
					switch(se.getType())
					{
						case constraintViolated:
							Window.alert(messages.errorDeletingLeadRequirementsEngineer());
							break;
						default:
							ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.removeSessionTimeOut());
					}
					
				}
				
				public void onSuccess(Boolean result)
				{
					if (result)
					{
						userTable.removeRow(lastRowClicked);
						users.remove(lastRowClicked - 1);
						
						lastRowClicked = -1;
					}
					else
					{
						Window.alert("Error deleting user!");
					}
				}
			});
		}
	}
	

	
	private void setProjectsPanel()
	{
		this.projectsPanel.add(new ProjectGrid(this));
	}
	
	
	
	private static HTML getSpacer(int width)
	{
		String spaces = "";
		for (int i = 0; i < width; i++)
		{
			spaces += "&nbsp;";
		}
		
		return new HTML(spaces);
	}
	
}
