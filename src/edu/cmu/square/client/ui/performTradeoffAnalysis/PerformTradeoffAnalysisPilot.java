package edu.cmu.square.client.ui.performTradeoffAnalysis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;


public class PerformTradeoffAnalysisPilot extends Pilot
{

	public static class PageId
	{
		public static final String home = "teach";
		public static final String matrix = "matrix-tecniques"; 
		public static final String performTradeoffAnalysis = "perform-tradeoff-analysis";
		public static final String requirementDetail = "requirementDetail";
	}


	public PerformTradeoffAnalysisPilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Step 6: Perform Tradeoff Analysis";
	}
	
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		//If user settings indicates to skip teach step, the target page will be the start page not the teach step 
		if(currentStateInformation.getSkipTeachSetp() && pageId.equals(PageId.home))
		 {
			 pageId=PageId.matrix;
		 }		
		if (pageId.equals(PageId.home))
		{
			return new TeachStepPane(HistoryManager.ViewId.performTradeoffAnalysis, generateNavigationId(PageId.matrix));
		}
		else if (pageId.equals(PageId.matrix))
		{
			return new PerformTradeoffAnalysisPane(currentStateInformation); //new Label("Summary Page!");
		}	
		else if(pageId.equals(PageId.requirementDetail))
		{
			return  new RequirementsDetailPane(currentStateInformation);
		}
		return null;
	}

	
	
	public void determineAccessRights(String page, State currentState)
	{
		if (currentState.getUserProjectRole() == ProjectRole.Acquisition_Organization_Engineer)
		{
			currentState.setMode(GwtModesType.NoAccess);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Contractor)
		{
			currentState.setMode(GwtModesType.NoAccess);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.Security_Specialist)
		{
			currentState.setMode(GwtModesType.ReadWrite);
		}
		else if (currentState.getUserProjectRole() == ProjectRole.COTS_Vendor)
		{
			currentState.setMode(GwtModesType.ReadOnly);
		}
		
//SQUARE delete this later!!
		else if (currentState.getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer)
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
		return messages.performTradeoffAnalysis();
	}
	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.performTradeoffAnalysis + "/" + pageId;
	}
	
}
