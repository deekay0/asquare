package edu.cmu.square.client.ui.inspectRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.HistoryManager;
import edu.cmu.square.client.navigation.Pilot;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.ui.core.TeachStepPane;
import edu.cmu.square.client.ui.core.content.BreadCrumbMessages;


public class InspectRequirementsPilot extends Pilot
{
	public static class PageId
	{
		public static final String home = "teach";
		public static final String inspectionTechniqueSelection = "summary";
		public static final String inspectionTechniqueDisplay = "inspectionTechniqueSelected";
		public static final String Associations = "associations";
		protected static final String Traceability = "traceability";
	}

	private String projectInspectionStatus=null;
	private String pageId;
	//private State currentStateInformation;
	
	
	public InspectRequirementsPilot()
	{
		this.isStep = true;
		this.STEP_DESCRIPTION = "Step 9: Inspect Requirements";
	}
	/* (non-Javadoc)
	 * @see edu.cmu.square.client.entryPoint.Pilot#navigateTo(java.lang.String, edu.cmu.square.client.entryPoint.State)
	 */
	
	public Widget navigateTo(String pageId, State currentStateInformation)
	{
		this.pageId = pageId;
		this.projectInspectionStatus = currentStateInformation.getProjectInspectionStatus();
		String step = HistoryManager.ViewId.inspectRequirements;
		
		if (this.pageId.equals(PageId.home)) 
		{
			String targetId = "";
			
			if (projectInspectionStatus == null)
			{
				
				if (currentStateInformation.getMode() == GwtModesType.ReadWrite)
				{
					targetId = generateNavigationId(PageId.inspectionTechniqueSelection);
					pageId=PageId.inspectionTechniqueSelection;
				}
				else if (currentStateInformation.getMode() == GwtModesType.ReadOnly)
				{
					targetId = generateNavigationId(PageId.inspectionTechniqueDisplay);
					pageId=PageId.inspectionTechniqueDisplay;
				}	
			}
			else 
			{
					targetId = generateNavigationId(PageId.inspectionTechniqueDisplay);
					pageId=PageId.inspectionTechniqueDisplay;
			}
			
			if (!currentStateInformation.getSkipTeachSetp())
			{
				return new TeachStepPane(step, targetId);
			}
		}
		
		if (pageId.equals(PageId.inspectionTechniqueSelection))
		{
			return new InspectionTechniqueSelectionPane(currentStateInformation);
		}
		
		if (pageId.equals(PageId.inspectionTechniqueDisplay))
		{
			return new InspectionTechniqueDisplayPane(currentStateInformation);
		}
		if (pageId.equals(PageId.Traceability))
		{
			return new TraceabilityPane(currentStateInformation);
		}
		return null;
	
	}
	
	
	public void loadProject(int projectId)
	{
		ManageProjectServiceAsync projectService = GWT.create(ManageProjectService.class);
		projectService.getProject(projectId, new AsyncCallback<GwtProject>()
		{	
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Loading project");
			}

			public void onSuccess(GwtProject result)
			{
				
			   if(result.getInspectionStatus()!=null)
				{ 
				   projectInspectionStatus=result.getInspectionStatus().getLabel();
				   innerNavigate();
				}
			}
		});
	}
	
	
	public void innerNavigate()
	{
		Window.alert(projectInspectionStatus);
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
		return messages.inspectRequirements();
	}
	
	public static String generateNavigationId(String pageId)
	{
		return HistoryManager.ViewId.inspectRequirements + "/" + pageId;
	}

	
}
