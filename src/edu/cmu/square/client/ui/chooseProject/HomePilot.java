package edu.cmu.square.client.ui.chooseProject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.LoginPane;
import edu.cmu.square.client.ui.core.SignOutPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;
import edu.cmu.square.client.ui.editUserProfile.EditUserProfilePane;


public class HomePilot extends Pilot
{

	public static class PageId
	{
		public static final String home = "home";
	}
	
	public void determineAccessRights(String page, State currentState)
	{
		currentState.setMode(GwtModesType.ReadWrite); // no matter what, the home pages are always read only.
	}
	
	public String getBreadCrumb()
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		return messages.chooseProject();
	}
	
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		
		if (pageId.equals(PageId.home)) 
		{
			return new HomePane(currentStateInformation);
		}
		
		return null;
	}

	
	
	

	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.home + "/" + pageId;
	}



	
}
