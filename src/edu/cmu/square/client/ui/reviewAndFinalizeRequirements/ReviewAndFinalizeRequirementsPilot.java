package edu.cmu.square.client.ui.reviewAndFinalizeRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementPane;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementsPilot.PageId;

public class ReviewAndFinalizeRequirementsPilot extends Pilot
{

	public static class PageId
	{
		public static final String home = "teach";
		public static final String start = "summary";
		public static final String requirementDetail = "requirementDetail";
	}

	
	public ReviewAndFinalizeRequirementsPilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Case 1 - Step 4: Review Of Requirements By Acquisition Organization";
	}
	

	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		
		String step = HistoryManager.ViewId.reviewOfRequirementsByAcquisitionOrganization;

		//If user settings indicates to skip teach step, the target page will be the start page not the teach step 
		if(currentStateInformation.getSkipTeachSetp() && pageId.equals(PageId.home))
		 {
			 pageId=PageId.start;
		 }
	
		if (pageId.equals(PageId.home))
		{
			return new TeachStepPane(step, generateNavigationId(PageId.start));
			                               
		}
		/*else if (pageId.equals(PageId.start))
		{
			return new AgreeOnDefinitionsPane(currentStateInformation);
		}*/

		else if (pageId.equals(PageId.start))
		{
			return new ReviewAndFinalizeRequirementsPane(currentStateInformation);
		}
		else if(pageId.equals(PageId.requirementDetail))
		{
			return  new ReviewAndFinalizeRequirementsDetailPane(currentStateInformation);
		}
		
		return null;
	}

	public void determineAccessRights(String page, State currentState)
	{
		//SQUARE
		if (currentState.getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Stakeholder)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		//ASQUARE
		else if (currentState.getUserProjectRole() == ProjectRole.Acquisition_Organization_Engineer)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Contractor)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.None)
		{
			
			currentState.setMode(GwtModesType.ReadWrite);
			//currentState.setMode(GwtModesType.NoAccess);
		}
		else 
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		
	}

	@Override
	public String getBreadCrumb()
	{
		final BreadCrumbMessages messages = (BreadCrumbMessages) GWT.create(BreadCrumbMessages.class);
		return messages.reviewOfRequirementsByAcquisitionOrganization();
	}
	
	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.reviewOfRequirementsByAcquisitionOrganization+ "/" + pageId;
	}	
}
