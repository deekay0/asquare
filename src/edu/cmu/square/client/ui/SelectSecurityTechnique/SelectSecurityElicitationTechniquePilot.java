package edu.cmu.square.client.ui.SelectSecurityTechnique;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementsPilot.PageId;


public class SelectSecurityElicitationTechniquePilot extends Pilot
{

	public static class PageId
	{
		public static final String home = "teach";
		public static final String matrix = "matrix-tecniques"; 
		public static final String selectTechnique = "edit-techniques";
	}


	public SelectSecurityElicitationTechniquePilot()
	{
		this.isStep=true;
		this.STEP_DESCRIPTION="Step 5: Select Elicitation Technique";
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
			return new TeachStepPane(HistoryManager.ViewId.selectSecurityElicitationTechnique, generateNavigationId(PageId.matrix));
		}
		else if (pageId.equals(PageId.matrix))
		{
			return new SelectSecurityTechniquePane(currentStateInformation); //new Label("Summary Page!");
		}
		else if(pageId.equals(PageId.selectTechnique))
		{
			return new SelectTopRatedTechniquePane(currentStateInformation);
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
		return messages.selectSecurityElicitationTechnique();
	}
	

	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.selectSecurityElicitationTechnique + "/" + pageId;
	}
	
}
