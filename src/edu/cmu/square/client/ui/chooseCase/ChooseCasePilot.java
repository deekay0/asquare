package edu.cmu.square.client.ui.chooseCase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.chooseProject.HomePane;
import edu.cmu.square.client.ui.core.LoginPane;
import edu.cmu.square.client.ui.core.SignOutPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;
import edu.cmu.square.client.ui.editUserProfile.EditUserProfilePane;


public class ChooseCasePilot extends Pilot
{
	private String breadcrumb = "";

	public static class PageId
	{
		public static final String login = "sign-in"; 
		public static final String logout = "sign-out";
		public static final String home = "choose-case";
		public static final String preferences = "preferences";
		public static final String help = "help";
		public static final String FourOhFour = "404";
	}
	
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		
		if (pageId.equals(PageId.login)) 
		{
			if(!currentStateInformation.isAuthenticated())
			{
				this.breadcrumb = "";
				return new LoginPane(currentStateInformation);
			}
			else
			{
				this.breadcrumb = messages.welcome(currentStateInformation.getFullName());
				return new ChooseCasePane(currentStateInformation);
			}
			
		} 
		else if (pageId.equals(PageId.home)) 
		{
			this.breadcrumb = messages.welcome(currentStateInformation.getFullName());
			System.out.println("choose case pilot.....       "+currentStateInformation);
			return new ChooseCasePane(currentStateInformation);
		}
		else if (pageId.equals(PageId.logout))
		{
			this.breadcrumb = "";
			return new SignOutPane(currentStateInformation);
		}
		else if (pageId.equals(PageId.preferences))
		{
			this.breadcrumb = messages.preferences();
			return new EditUserProfilePane(currentStateInformation);
		}
		else if (pageId.equals(PageId.help))
		{
			this.breadcrumb = "";
			return new Label("Help manual, coming soon!");
		}
		return null;
	}

	
	public void determineAccessRights(String page, State currentState)
	{
	//	currentState.setMode(GwtModesType.ReadWrite); // no matter what, the home pages are always read only.
		currentState.setMode(GwtModesType.ReadOnly);
	}
	
	
	public String getBreadCrumb()
	{
		return this.breadcrumb;
	}
	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.chooseCase + "/" + pageId;
	}



}
