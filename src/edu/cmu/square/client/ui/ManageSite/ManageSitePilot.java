package edu.cmu.square.client.ui.ManageSite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;

/**
 * 
 */
public class ManageSitePilot extends Pilot
{
	
	public static class PageId
	{
		public static final String home = "settings"; 
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#navigateTo(java.lang.String, edu.cmu.square.client.entryPoint.State)
	 */
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		if (pageId.equals(PageId.home)) 
		{
			return new ManageSitePane(currentStateInformation);
		} 

		return null;
	}

	
	public void determineAccessRights(String page, State currentState)
	{
		if (currentState.isSiteAdministrator())
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Acquisition_Organization_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		
		else{
			currentState.setMode(GwtModesType.NoAccess);
		}
	}
	
	
	public String getBreadCrumb()
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		return messages.manageSite();
	}
	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.manageSite + "/" + pageId;
	}



	

}
