package edu.cmu.square.client.ui.ChooseStepCase3;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;

public class ChooseStepCase3Pilot extends Pilot
{
	public static class PageId
	{
		public static final String home = "choose-step-case3";
		public static final String member = "members";
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#determineAccessRights(java.lang.String, edu.cmu.square.client.model.ProjectRole, boolean)
	 */
	
	public void determineAccessRights(String page, State currentState)
	{
		if (currentState.getUserProjectRole() == ProjectRole.Acquisition_Organization_Engineer)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Contractor)
		{
			currentState.setMode(GwtModesType.NoAccess);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Security_Specialist)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.COTS_Vendor)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		
		else if (currentState.getUserProjectRole() == ProjectRole.None)
		{
			currentState.setMode(GwtModesType.NoAccess);
		}
		else{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		/*
		if (currentState.getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.None)
		{
			currentState.setMode(GwtModesType.NoAccess);
		}
		else{
		currentState.setMode(GwtModesType.ReadOnly);
		}
		*/
	}


	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#getBreadCrumb()
	 */
	
	public String getBreadCrumb()
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		return messages.chooseStep();
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#navigateTo(java.lang.String, edu.cmu.square.client.entryPoint.State)
	 */
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		if (pageId.equals(PageId.home)) 
		{
			return new ChooseStepCase3Pane(currentStateInformation);
		} 
		else if (pageId.equals(PageId.member))
		{
			return new ViewMembersCase3Pane(currentStateInformation);
		}

		return null;
	}
	
	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.chooseStepCase3 + "/" + pageId;
	}

}
