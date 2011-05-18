package edu.cmu.square.client.ui.core;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GWTAppProperties;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.LogInService;
import edu.cmu.square.client.remoteService.interfaces.LogInServiceAsync;
import edu.cmu.square.client.ui.ManageSite.ManageSitePilot;
import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.content.HeaderPaneMessages;


public class HeaderPane extends Composite
{ 
	private DockPanel baseLayout;
	private final HeaderPaneMessages messages = (HeaderPaneMessages)GWT.create(HeaderPaneMessages.class);
	LogInServiceAsync logInService = GWT.create(LogInService.class);
	HorizontalPanel linksPanel = null;
	GWTAppProperties properties = null;
	public HeaderPane(GWTAppProperties properties, State currentState)
	{
		this.properties = properties;
		ServiceDefTarget endpoint = (ServiceDefTarget) logInService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "logIn.rpc");
		this.baseLayout = new DockPanel();
		this.baseLayout.setSize("100%", "100%");
		
		this.linksPanel = new HorizontalPanel();
		HorizontalPanel titlePanel = new HorizontalPanel();
		;
		this.baseLayout.add(linksPanel, DockPanel.EAST);
	
		this.baseLayout.setCellWidth(linksPanel, "75%");
		this.baseLayout.setCellHorizontalAlignment(linksPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.baseLayout.setCellVerticalAlignment(linksPanel, HasVerticalAlignment.ALIGN_TOP);
	
		this.baseLayout.add(titlePanel, DockPanel.WEST);
		this.baseLayout.setCellHeight(titlePanel, "25px");
		this.baseLayout.setCellWidth(titlePanel, "45%");
		this.baseLayout.setCellHorizontalAlignment(titlePanel, HasHorizontalAlignment.ALIGN_LEFT);
		this.baseLayout.setCellVerticalAlignment(linksPanel, HasVerticalAlignment.ALIGN_TOP);
		
   		initWidget(baseLayout);

   		this.setStyleName("square-header");
   		
   		addNavigationLinks(currentState);
		addImageAndTitle(titlePanel, properties);
	}
	
	
	public void refresh(State currentState)
	{
		this.linksPanel.clear();
		this.addNavigationLinks(currentState);
	}
	
	
	
	private void addImageAndTitle(HorizontalPanel hp, GWTAppProperties properties) 
	{
		Image logo = new Image(properties.getHeaderLogo());
		logo.setStyleName("square-header-LogoImage");
		logo.addClickHandler(new ClickHandler()
		{
			
			public void onClick(ClickEvent event)
			{
				//when you click on the image, go to the home page.
				History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.home));
			}
		});
		
		logo.setPixelSize(50, 50);
		hp.add(logo);
		
		VerticalPanel titleVerticalPane = new VerticalPanel();
		titleVerticalPane.setSize("100%", "100%");
		Label title = new Label(properties.getHeaderTitle1());
		Label subTitle = new Label(properties.getHeaderTitle2());
		title.setStyleName("square-headerpane-title");
		subTitle.setStyleName("square-headerpane-tagline");
		
		titleVerticalPane.add(title);
		titleVerticalPane.add(subTitle);
		
		hp.add(titleVerticalPane);
	
	}

	
	private void addNavigationLinks(State currentState)
	{
		// If the user is not authenticated we're not going to bother setting up the link bar
		// they shouldn't have access to any of the stuff anyway.
		String role = "";
		if (!currentState.isAuthenticated())
		{	
			return;
		}

		if (!currentState.getUserProjectRole().equals(ProjectRole.None))
		{
			role=" ("+currentState.getUserProjectRole().getLabel()+")";

		}
		
		Label userName = new Label(currentState.getUserName()+role);
		
		this.linksPanel.add(userName);
		
		this.linksPanel.add(new Label(messages.delimiter()));
		
		// Only the admin can mange the site settings.
		if (currentState.isSiteAdministrator())
		{
			Hyperlink adminLink = new Hyperlink(messages.adminLinkText(), ManageSitePilot.generateNavigationId(ManageSitePilot.PageId.home));
			this.linksPanel.add(adminLink);
			this.linksPanel.add(new Label(messages.delimiter()));
		}
		
		// user preferences page
		Hyperlink preference = new Hyperlink(messages.linkTitle2(), HomePilot.generateNavigationId(HomePilot.PageId.preferences));
		this.linksPanel.add(preference);
		this.linksPanel.add(new Label(messages.delimiter()));
		
		// help page
//		Hyperlink help = new Hyperlink(messages.linkTitle3(), HomePilot.generateNavigationId(HomePilot.PageId.help));
		HTML helpLink = new HTML("<a href='"+ properties.getHelpLink()+"'>help</a>");
		this.linksPanel.add(helpLink);
		this.linksPanel.add(new Label(messages.delimiter()));
		
		// sign out
		Hyperlink signOut = new Hyperlink("sign out", HomePilot.generateNavigationId(HomePilot.PageId.logout));
		this.linksPanel.add(signOut);
		signOut.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				logInService.logOut(new AsyncCallback<Void>()
						{

							@Override
							public void onFailure(Throwable caught)
							{
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(Void result)
							{
								// TODO Auto-generated method stub
								
							}
					
						}
						
				);
				
			}
			
		}
		
		);
		
		this.linksPanel.setSpacing(3);
		this.linksPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		this.linksPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	}
}



