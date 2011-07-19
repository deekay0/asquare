package edu.cmu.square.client.ui.ManageProject;

import java.util.LinkedList;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRole;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteService;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class ManageProjectPane extends BasePane
{
	private List<GwtRole> gwtRoleNames = new LinkedList<GwtRole>();
	private List<GwtUser> gwtProjectUserList;
	private List<GwtUser> gwtUserSuggestionList;
	GwtProject gwtProject = null;
	
	State curentState = null;
	
	private ManageProjectServiceAsync mpsa = GWT.create(ManageProjectService.class);
	private ManageSiteServiceAsync mssa = GWT.create(ManageSiteService.class);
	
	private VerticalPanel projetusersPanel = new VerticalPanel();
	
	private ProjectDetailPane projectDetailPanel = null;
//	private VerticalPanel exportDataPanel = new VerticalPanel();
	
	private FlexTable projectUserTableHeader = new FlexTable();
	private FlexTable projectUserTable = new FlexTable();
	
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    final SuggestBox userSuggestBox = new SuggestBox(oracle);
	private ListBox rolesComboBox = new ListBox(false);
	
	final ManageProjectPaneMessages messages = (ManageProjectPaneMessages)GWT.create(ManageProjectPaneMessages.class);
	
	
	public ManageProjectPane(final State stateInfo)
	{	
		super(stateInfo);
		this.showLoadingStatusBar();
		this.curentState = stateInfo;
		this.gwtProject = new GwtProject();
		this.gwtProject.setId(this.curentState.getProjectID());
		
		this.projectDetailPanel = new ProjectDetailPane(this.getCurrentState().getProjectID(),
				                                        this.getCurrentState().getProjectName(),
				                                        this);
		
		DecoratedTabPanel mainTabs = new DecoratedTabPanel();
		mainTabs.add(this.projetusersPanel, messages.tab1Title());
		
		mainTabs.add(this.projectDetailPanel, messages.tab5Title());
//		mainTabs.add(this.exportDataPanel,  messages.tab6Title());

		mainTabs.selectTab(0);
		mainTabs.setSize("60%", "100%");
		
		
		this.setProjectUserPanel();
		this.projectDetailPanel.initializeTable();
		
		this.getContent().add(mainTabs);		
	}

	
	public void refreshProjectUserTable()
	{	
		getUsersForProject(gwtProject);
	}
	
	
	/* The project users from the selected project will be loaded to the _projectUserTable
	 * Only lead requirement engineer can see this page
	 */
	public void getUsersForProject(final GwtProject gwtProject)
	{	
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");	
		
		mpsa.getUserList(gwtProject, new AsyncCallback<List<GwtUser>>()
		{
			
			public void onFailure(Throwable caught)
            {
				ExceptionHelper.SquareRootRPCExceptionHandler(caught,"retrieving users");
            }

			
			public void onSuccess(List<GwtUser> result)
			{   
				gwtProjectUserList = result;
				// Add role names to the ListBox from the Role table in the DB
				getRoleNamesFromDB();
			}			
		});
		
	}
	
	public void createAndShowUserEditDialog(GwtUser gwtUser, List<GwtRole> list, ManageProjectServiceAsync mpsa2)
	{
		final EditUserInProjectDialog dialogBox = new EditUserInProjectDialog(this, gwtUser, gwtProject, list, mpsa);
	    dialogBox.setText(messages.editUserDialogTitle());
	    dialogBox.setAnimationEnabled(true);

	    dialogBox.center();
	    dialogBox.show();
	}
	
	
	
	public void loadUserToTable(final List<GwtUser> result)
	{	FlexCellFormatter formatter1 = this.projectUserTable.getFlexCellFormatter();
		int numberOfRows = 0;
		this.projectUserTable.clear();
		formatter1.setHorizontalAlignment(numberOfRows, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		

		//get users list from the gwtProject through getUsersForProject
		List<GwtUser> gwtUsers = null;
		gwtUsers = result;
		
		for(final GwtUser currentUser: gwtUsers)
		{	
			HorizontalPanel links = new HorizontalPanel(); //holds links so that they do not spread out in the flex table
			links.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			links.setStyleName("flex-link-bar");
			
			SquareHyperlink editUserLink = new SquareHyperlink(messages.edit());
			editUserLink.addClickHandler(new ClickHandler()
			{
				
				public void onClick(ClickEvent event)
				{
					//this should pass a handle to the role data being updated
					//so that it can be updated from within the popup.
					
					createAndShowUserEditDialog(currentUser, gwtRoleNames, mpsa);
				}
			});
			
			
			SquareHyperlink removeUserLink = new SquareHyperlink(messages.remove());
			removeUserLink.addClickHandler(new DeleteUserFromProjectHandler(this, currentUser, gwtProject, mpsa,messages));
			links.add(editUserLink);
			links.add(removeUserLink);
			
			String displayName = currentUser.getFullName();
			if (currentUser.getUserName().equals(this.curentState.getUserName()))  //We know this is the user currently logged in, tell them in the table.
			{
				displayName += "  (This is you)";
			}
			
			String role = currentUser.getRole();
			
			Hyperlink userNameLink = new Hyperlink(displayName, currentUser.getFullName() + "userLink");
			///////////////////////////////////////////////
			//this.projectUserTable.setBorderWidth(1);
			/////////////////////////////////////////////////
			// If the user role is lead requirement engineer then don't show the following link.
			if(role.equals("Lead Requirements Engineer"))
			{
				this.projectUserTable.setWidget(numberOfRows, 0, userNameLink);
				this.projectUserTable.setWidget(numberOfRows, 1, new Label(role));	
				this.projectUserTable.setWidget(numberOfRows, 2, new HorizontalPanel());
			}

			else
			{
				this.projectUserTable.setWidget(numberOfRows, 0, userNameLink);
				this.projectUserTable.setWidget(numberOfRows, 1, new Label(role));
				this.projectUserTable.setWidget(numberOfRows, 2, links);
				this.projectUserTable.getFlexCellFormatter().setHorizontalAlignment(numberOfRows, 2, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			
			numberOfRows++;
		}
	}
	
	
	void getRoleNamesFromDB(){
		
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()
				+ "manageProject.rpc");	
		
		mpsa.getAllRoles(new AsyncCallback<List<GwtRole>>() {
        
			
			public void onFailure(Throwable caught)
            {
				ExceptionHelper.SquareRootRPCExceptionHandler(caught,"retrieving roles");
            }

			
			public void onSuccess(List<GwtRole> result) {
				initializeUserTable();
				
				loadUserToTable(gwtProjectUserList);
				gwtRoleNames.clear();
				rolesComboBox.clear();
				for(GwtRole r: result){
					gwtRoleNames.add(r);
					rolesComboBox.addItem(r.getName(), r.getId()+"");
				}
				hideStatusBar();
			}
					
		});
	}

	
	void getUsersToSuggestionBox(){
		
		ServiceDefTarget endpoint = (ServiceDefTarget) mssa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL()
				+ "manageSite.rpc");
		
		mssa.getAllUsers(new AsyncCallback<List<GwtUser>>() {
        
			
			public void onFailure(Throwable caught)
	        {
				ExceptionHelper.SquareRootRPCExceptionHandler(caught,"retrieving users");
	        }
	
			
			public void onSuccess(List<GwtUser> result) {
				gwtUserSuggestionList = result;
				loadUserToSuggestionBox(result);
			}
		});
	}
	
	public void loadUserToSuggestionBox(final List<GwtUser> result){
		oracle.clear();
		
		for(GwtUser u: result){
			String suggestion  = u.getFullName()+ "<"+u.getUserName()+">";
			oracle.add(suggestion);
		}
	}
	
	// ******* Handlers for the buttons *************
	private class AddUserToProjectHandler implements ClickHandler
	{	
		private GwtProject gwtProject = null;
	
		public AddUserToProjectHandler(GwtProject project)
		{
			super();
			this.gwtProject = project;		
		}
			
		
		public void onClick(ClickEvent event)
		{
			GwtUser gwtUser = new GwtUser();
			GwtRole gwtRole = new GwtRole();
			String inputText = null;
			String subInputText = null;
			inputText = userSuggestBox.getText();
		
			// get the unique user name from the input Text
			if (inputText.trim().length() == 0)
			{
				// if the input Text is null -- alert
				Window.alert(messages.invalidInput());				
			}
			else
			{
				if(inputText.indexOf('<') == -1 || inputText.indexOf('>') == -1)
				{
					subInputText = inputText;
				}
				else
				{
					subInputText = inputText.substring(inputText.indexOf('<')+1, inputText.indexOf('>'));
				}

				boolean siteUser = false;
				boolean addToProject = false;
				for(GwtUser u: gwtUserSuggestionList)
				{
					if(subInputText.equalsIgnoreCase(u.getUserName()))
					{
						siteUser = true;
						gwtUser.setUserId(u.getUserId());
						gwtUser.setUserName(u.getUserName());
						gwtUser.setEmailAddress(u.getEmailAddress());
						gwtUser.setFullName(u.getFullName());
						break;
					}
				}
				if(!siteUser)
				{
					siteUser = false;
					Window.alert(messages.accessAlert());
				}
				else
				{
					//boolean addToProject = false;
					for(GwtUser pu: gwtProjectUserList)
					{
						if(subInputText.equalsIgnoreCase(pu.getUserName()))
						{
							addToProject = false;
							Window.alert(messages.existingProjectUser());
							break;
						}							
						else{
							addToProject = true;
						}
					}
				}
				if(addToProject)
				{
					//add user to the project
					gwtRole.setId(Integer.parseInt(rolesComboBox.getValue(rolesComboBox.getSelectedIndex())));
					gwtRole.setName(rolesComboBox.getItemText(rolesComboBox.getSelectedIndex()));
					addUserToProject(gwtProject, gwtUser, gwtRole);
				}
				
			}
		}
	}
	void addUserToProject(GwtProject project, GwtUser user, GwtRole role)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");	
		
		mpsa.addUserToProject(project, user, role, new AsyncCallback<Void>()
		{
			
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught,"adding users");
			}

			
			public void onSuccess(Void result)
			{
				refreshProjectUserTable();
			}
		});
	}

	
	
	private void setProjectUserPanel()
	{
		
		this.getUsersForProject(gwtProject);
		
	}
	private void initializeUserTable()
	{
///////////////////////////////////////////////
		//this.projectUserTableHeader.setBorderWidth(1);
		/////////////////////////////////////////////////
		
		// load users from the DB to the suggestion list
		this.getUsersToSuggestionBox();

	    userSuggestBox.ensureDebugId(messages.userSuggestBox());
	    userSuggestBox.setAnimationEnabled(true);

		Button addUserButton = new Button(messages.addUserButton(), new AddUserToProjectHandler(gwtProject));

		this.projectUserTableHeader.setWidget(0, 0, userSuggestBox);
		this.projectUserTableHeader.setWidget(0, 1, rolesComboBox);
		this.projectUserTableHeader.setWidget(0, 2, addUserButton);
		
		FlexCellFormatter formatter1 = this.projectUserTableHeader.getFlexCellFormatter();
		formatter1.setStyleName(0, 0, "square-TableHeader");
		formatter1.setStyleName(0, 1, "square-TableHeader");
		formatter1.setStyleName(0, 2, "square-TableHeader");
		this.projectUserTableHeader.setWidth("100%");
		this.projectUserTableHeader.setCellSpacing(0);
		this.projectUserTableHeader.setStyleName("square-flex");
		formatter1.setWidth(0, 0, "30%");
		formatter1.setWidth(0, 1, "40%");
		formatter1.setWidth(0, 2, "30%");
		formatter1.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter1.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		FlexCellFormatter formatter = this.projectUserTable.getFlexCellFormatter();
		this.projectUserTable.setWidth("100%");
		this.projectUserTable.setCellSpacing(0);
		this.projectUserTable.setStyleName("square-flex");
		formatter.setWidth(0, 0, "30%");
		formatter.setWidth(0, 1, "40%");
		formatter.setWidth(0, 2, "30%");
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_LEFT);
		
		
		this.projetusersPanel.add(this.projectUserTableHeader);
		this.projetusersPanel.add(this.projectUserTable);
	}

}



