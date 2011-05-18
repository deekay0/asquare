package edu.cmu.square.client.entryPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GWTAppProperties;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.SquareStateChangedEvent;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.AppPropertiesService;
import edu.cmu.square.client.remoteService.interfaces.AppPropertiesServiceAsync;
import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.FooterPane;
import edu.cmu.square.client.ui.core.HeaderPane;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SquareGwt implements EntryPoint, SquareStateChangedEvent.ITakesMyEvent
{
	private HeaderPane headerPane;
	private HorizontalPanel breadCrumbPane = null;
	private VerticalPanel contentPane;
	private FooterPane footerPane;
	private HistoryManager historian = null;

	private State stateInfo;
	private boolean isTimeout = false;
	private GWTAppProperties appProperties;

	public void onModuleLoad()
	{
		// This asynchronous calls make sure to load any initially property resource on the server.
		AppPropertiesServiceAsync appPropertiesService = GWT.create(AppPropertiesService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) appPropertiesService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "appPropertiesService.rpc");

		appPropertiesService.loadProperties(new AsyncCallback<GWTAppProperties>()
			{

				public void onFailure(Throwable caught)
				{
					loadPage();
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "loadProperties");
				
				}

				public void onSuccess(GWTAppProperties result)
				{
					appProperties = result;

					if (isAuthenticated())
					{
						validateTimeOut();
					}
					else
					{
						loadPage();
					}

				}
			});
	}

	private void validateTimeOut()
	{
		AppPropertiesServiceAsync appPropertiesService = GWT.create(AppPropertiesService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) appPropertiesService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "appPropertiesService.rpc");
		appPropertiesService.isSessionTimeOut(new AsyncCallback<Boolean>()
			{
				public void onFailure(Throwable caught)
				{
					//ExceptionHelper.SquareRootRPCExceptionHandler(caught, "loadProperties");
					isTimeout = true;
					loadPage();
				}

				public void onSuccess(Boolean result)
				{
					if (result)// if there is timeout and the user was authenticated is thrown to loggin screen.
					{
						isTimeout = true;
					}
					
					loadPage();
				}
			});
	}

	public void loadPage()
	{
		DockPanel main = new DockPanel();
		main.setSize("100%", "100%"); // stretch to browser dimensions
		RootPanel.get().add(main);

		this.contentPane = new VerticalPanel();
		this.contentPane.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.contentPane.setSize("100%", "100%");

		this.breadCrumbPane = new HorizontalPanel();
		this.breadCrumbPane.setStyleName("square-bread-crumb");
		this.breadCrumbPane.setSpacing(0);
		DockPanel centerLayout = new DockPanel();

		centerLayout.setSize("100%", "100%");
		// centerLayout.setSpacing(2);
		centerLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerLayout.add(this.breadCrumbPane, DockPanel.NORTH);
		centerLayout.add(this.contentPane, DockPanel.CENTER);
		
		this.stateInfo = new State();
		this.stateInfo.register((SquareStateChangedEvent.ITakesMyEvent) this);
		this.historian = new HistoryManager(this.contentPane, this.breadCrumbPane, this.stateInfo);
		
		History.addValueChangeHandler(historian);
		this.headerPane = new HeaderPane(this.appProperties, stateInfo);

		// Identifies the first request,
		if (this.isFirtRequest())
		{
			this.stateInfo.setCurrentView(HomePilot.generateNavigationId(HomePilot.PageId.login)); // Default View
			this.setFirstRequest(false);
			History.newItem(this.stateInfo.getCurrentView());
		}
		else 		// request will be consider refresh actions from the user
		{
			
			this.stateInfo.loadStateFromCookies();
			
			if (isTimeout)
			{
				History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.logout) + "?timeout=1");
			}
			else
			{
				this.historian.forceNavigation(this.stateInfo.getCurrentView());
			}
		}
		
		this.headerPane = new HeaderPane(this.appProperties, stateInfo);
		this.footerPane = new FooterPane(this.appProperties);

		main.add(this.headerPane, DockPanel.NORTH);
		main.add(this.footerPane, DockPanel.SOUTH);
		main.add(centerLayout, DockPanel.CENTER);
		main.setCellHorizontalAlignment(this.breadCrumbPane, HasHorizontalAlignment.ALIGN_LEFT);
		main.setCellHeight(this.headerPane, "5%");
		main.setCellWidth(this.headerPane, "100%");
		main.setCellHeight(this.footerPane, "5%");
		main.setCellWidth(this.footerPane, "100%");
	}

	/**
	 * This getters returns whether this is the first request to the entry point
	 * class. This will indicate when the user had pressed the refresh button.
	 * 
	 * @return
	 */
	public boolean isFirtRequest()
	{
		if (Cookies.getCookie("firstRequest") == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * This getters returns whether this is the first request to the entry point
	 * class. This will indicate when the user had pressed the refresh button.
	 * 
	 * @return
	 */

	public boolean isAuthenticated()
	{
		if (Cookies.getCookie("isAuthenticated") == null)
		{
			return false;
		}
		else
		{
			return Boolean.parseBoolean(Cookies.getCookie("isAuthenticated"));
		}
	}

	/**
	 * This this setter help us to keep track of the first request using a
	 * cookie this allow us to know when we should recover state from the
	 * cookies.
	 * 
	 * @param isFirstRequest
	 */
	public void setFirstRequest(boolean isFirstRequest)
	{
		if (isFirstRequest)
		{
			Cookies.setCookie("firstRequest", null);
		}
		else
		{
			Cookies.setCookie("firstRequest", "1");
		}
	}

	public void onSquareStateChanged(SquareStateChangedEvent event)
	{
		// This event is triggered from the State class.
		// This even is trigger when the authentication field is set.
		// This helped us to communicate the Login Pane with the header
		// using the Entrypoint class handling the event.
		if (event.getKey().equalsIgnoreCase("authenticated"))
		{
			headerPane.refresh(this.stateInfo);
		}
		else if (event.getKey().equalsIgnoreCase("role"))
		{
			headerPane.refresh(this.stateInfo);
		}
		else if (event.getKey().equalsIgnoreCase("projectname"))
		{
			this.historian.setBreadCrumb(this.stateInfo.getCurrentView());
		}
	}

}