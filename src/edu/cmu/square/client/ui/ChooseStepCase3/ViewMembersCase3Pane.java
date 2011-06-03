package edu.cmu.square.client.ui.ChooseStepCase3;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class ViewMembersCase3Pane extends BasePane
{
	GwtProject gwtProject = null;
	private VerticalPanel projetusersPanel = new VerticalPanel();
	private FlexTable projectUserTable = new FlexTable();
	private List<GwtUser> listOfUsers;

	State curentState = null;

	final ChooseStepCase3PaneMessages messages = (ChooseStepCase3PaneMessages) GWT.create(ChooseStepCase3PaneMessages.class);

	private ManageProjectServiceAsync mpsa = GWT.create(ManageProjectService.class);

	public ViewMembersCase3Pane(State stateInfo)
		{
			super(stateInfo);

			this.showLoadingStatusBar();
			this.curentState = stateInfo;
			this.gwtProject = new GwtProject();
			this.gwtProject.setId(this.curentState.getProjectID());

			this.projetusersPanel.add(projectUserTable);
			getUsersForProject(gwtProject);
			Label pageTitle = new Label(messages.pageTitle());
			pageTitle.setStyleName("square-title");
			this.getContent().add(pageTitle);
			this.getContent().add(projetusersPanel);
		}

	public void getUsersForProject(final GwtProject gwtProject)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		mpsa.getUserList(gwtProject, new AsyncCallback<List<GwtUser>>()
			{

				public void onFailure(Throwable caught)
				{
					Window.alert(messages.errorRoleRetrieval());
					Window.alert(caught.getMessage());
				}

				public void onSuccess(List<GwtUser> result)
				{
					listOfUsers = result;
					loadUserToTable(result);
					hideStatusBar();
				}
			});

	}

	public void loadUserToTable(final List<GwtUser> result)
	{
		FlexCellFormatter formatter = this.projectUserTable.getFlexCellFormatter();
		formatter.setWidth(0, 0, "300px");
		int numberOfRows = 0;
		this.projectUserTable.clear();
		
		formatter.setHorizontalAlignment(numberOfRows, 2, HasHorizontalAlignment.ALIGN_LEFT);
		this.projectUserTable.setCellSpacing(0);

		this.projectUserTable.setStyleName("square-flex");
		// header
		this.projectUserTable.setWidget(numberOfRows, 0, new Label(messages.memberTableTitle()));
		this.projectUserTable.setWidget(numberOfRows, 1, new Label(messages.roleTableTitle()));
		
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		numberOfRows++;
		
		// get users list from the gwtProject through getUsersForProject
		listOfUsers = result;

		for (final GwtUser currentUser : listOfUsers)
		{
			// Get the user name to the table
			String displayName = currentUser.getFullName();
			if (currentUser.getUserName().equals(this.curentState.getUserName())) 
			{
				displayName += messages.thisIsYou();
			}
			
			SquareHyperlink userNameLabel = new SquareHyperlink(displayName);
			this.projectUserTable.setWidget(numberOfRows, 0, userNameLabel);

			FlexTable userInfoTable = new FlexTable();
			userInfoTable.setStyleName("square-choose-step-membertable");

			FlexCellFormatter formatter1 = userInfoTable.getFlexCellFormatter();
			formatter1.setWidth(0, 0, "200px");

			userInfoTable.setWidget(0, 0, new Label(currentUser.getFullName()));
			userInfoTable.setWidget(1, 0, new Label(currentUser.getEmailAddress()));
			userInfoTable.setWidget(2, 0, new Label("" + currentUser.getPhone()));
			userInfoTable.setWidget(3, 0, new Label(" "));
			userInfoTable.setWidget(4, 0, new Label(currentUser.getOrganization()));
			userInfoTable.setWidget(5, 0, new Label(currentUser.getDepartment()));
			userInfoTable.setWidget(6, 0, new Label(currentUser.getLocation()));

			formatter1.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter1.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter1.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter1.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter1.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_LEFT);
			formatter1.setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_LEFT);

			// add a close icon
			Image imageLock = new Image("images//close-button.gif");
			userInfoTable.setWidget(0, 1, imageLock);
			formatter1.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
			
			final DecoratedPopupPanel userInfoPopup = new DecoratedPopupPanel(true);
			userInfoPopup.setStyleName("square-DecoratedPopupPanel.popupMiddleCenter");

			userInfoPopup.setWidth("200px");
			userInfoPopup.setWidget(userInfoTable);


			// add a close handler to the close icon
			imageLock.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						userInfoPopup.hide();
					}

				});
			
			userNameLabel.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{

						Widget source = (Widget) event.getSource();
						int left = source.getAbsoluteLeft() + 90;
						int top = source.getAbsoluteTop() + 10;
						userInfoPopup.setPopupPosition(left, top);
						userInfoPopup.show();

					}
				});

			// Get the user role to the table
			String role = currentUser.getRole();
			if (currentUser.getIsAdmin())
			{
				role += messages.administrator();
			}

			this.projectUserTable.setWidget(numberOfRows, 1, new Label(role));
			numberOfRows++;
		}
	}
}
