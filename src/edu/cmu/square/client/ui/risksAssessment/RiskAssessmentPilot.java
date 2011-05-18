package edu.cmu.square.client.ui.risksAssessment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.agreeOnDefinitions.AgreeOnDefinitionsPilot.PageId;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;


public class RiskAssessmentPilot extends Pilot
{

	public static class PageId
	{
		public static final String home = "teach";
		public static final String riskAssessmentsummary = "summary-summary"; 
		public static final String riskAssessment = "risk-detail";
	
	}


	public RiskAssessmentPilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Step 4: Perform Risk Assessment";
	}
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		
		//If user settings indicates to skip teach step, the target page will be the start page not the teach step 
		if(currentStateInformation.getSkipTeachSetp() && pageId.equals(PageId.home))
		 {
			 pageId=PageId.riskAssessmentsummary;
		 }
		
		if (pageId.equals(PageId.home))
		{
			return new TeachStepPane(HistoryManager.ViewId.riskAssessment, generateNavigationId(PageId.riskAssessmentsummary));
		}
		else if (pageId.equals(PageId.riskAssessment))
		{
			return new RiskAssessmentPane(currentStateInformation);
		}
		else if(pageId.equals(PageId.riskAssessmentsummary))
		{
			return  new RiskAssessmentSummaryPane(currentStateInformation);
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
		return messages.securityRiskAssessment();
	}
	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.riskAssessment + "/" + pageId;
	}
	
}
