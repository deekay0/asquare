package edu.cmu.square.client.ui.categorizeRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.SelectSecurityTechnique.SelectSecurityElicitationTechniquePilot.PageId;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;


public class CategorizeRequirementsPilot extends Pilot
{
	
	public static class PageId
	{
		public static final String home = "teach";
		public static final String start = "categorize";
	}


	public CategorizeRequirementsPilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Step 7: Categorize Requirements";
	}
	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#navigateTo(java.lang.String, edu.cmu.square.client.entryPoint.State)
	 */
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		//If user settings indicates to skip teach step, the target page will be the start page not the teach step 
		if(currentStateInformation.getSkipTeachSetp() && pageId.equals(PageId.home))
		 {
			 pageId=PageId.start;
		 }
		
		if (pageId.equals(PageId.home)) 
		{
			return new TeachStepPane(HistoryManager.ViewId.categorizeRequirements, generateNavigationId(PageId.start));
		}
		
		if (pageId.equals(PageId.start))
		{
			return new CategorizeRequirementsPane(currentStateInformation);
		}
		return null;
	}

	public void determineAccessRights(String page, State currentState)
	{
		if (currentState.getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Requirements_Engineer)
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
	}
	

	public String getBreadCrumb()
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		return messages.categorizeRequirements();
	}
	
	
	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.categorizeRequirements + "/" + pageId;
	}

	
}
