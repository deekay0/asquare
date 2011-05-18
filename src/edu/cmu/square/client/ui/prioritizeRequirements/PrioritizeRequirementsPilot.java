package edu.cmu.square.client.ui.prioritizeRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.categorizeRequirements.CategorizeRequirementsPilot.PageId;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;


/**
 *
 */
public class PrioritizeRequirementsPilot extends Pilot
{
	
	public static class PageId
	{
		public static final String home = "teach";
		public static final String start = "summary";
		public static final String compareRequirements = "compare-requirements";
		public static final String requirementsSummaryGroup = "requirements-summary-group";
		public static final String requirementsSummaryIndv = "requirements-summary-indv";
		public static final String requirementsSummaryLead = "requirements-summary-lead";
		
	}

	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#navigateTo(java.lang.String, edu.cmu.square.client.entryPoint.State)
	 */
	public PrioritizeRequirementsPilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Step 8: Prioritize Requirements";
	}
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		String step = HistoryManager.ViewId.prioritizeRequirements;
		//If user settings indicates to skip teach step, the target page will be the start page not the teach step 
		if(currentStateInformation.getSkipTeachSetp() && pageId.equals(PageId.home))
		 {
			 pageId=PageId.start;
		 }
		
		
		if (pageId.equals(PageId.home)) 
		{
			return new TeachStepPane(step, generateNavigationId(PageId.start));
		}
		
		if (pageId.equals(PageId.start )) 
		{
			if (currentStateInformation.getMode()==GwtModesType.ReadWrite)
			{
				return new PRCurrentStatusPane(currentStateInformation);
			} else 
			{
				return new PRRequirementsSummaryGroupPane(currentStateInformation);
			}
			
		}
		
		if (pageId.equals(PageId.compareRequirements )) 
		{
			return new PRCompareRequirementsPane(currentStateInformation);
		}
		
		if (pageId.equals(PageId.requirementsSummaryGroup )) 
		{
			return new PRRequirementsSummaryGroupPane(currentStateInformation);
		}
		
		if (pageId.equals(PageId.requirementsSummaryIndv )) 
		{
			return new PRRequirementsSummaryIndvPane(currentStateInformation);
		}
		
		if (pageId.equals(PageId.requirementsSummaryLead )) 
		{
			return new PRRequirementsSummaryLeadPane(currentStateInformation);
		}
		return null;
	}

	
	public void determineAccessRights(String page, State currentState)
	{
		if (currentState.getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Requirements_Engineer)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Stakeholder)
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
		return messages.prioritizeRequirements();
	}
	
	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.prioritizeRequirements + "/" + pageId;
	}

	
}
