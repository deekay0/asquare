package edu.cmu.square.client.ui.chooseProject;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.AsquareCase;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ChooseProjectService;
import edu.cmu.square.client.remoteService.interfaces.ChooseProjectServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.ChooseStepCase3.ChooseStepCase3Pilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.client.utils.SquareUtil;

public class HomePane extends BasePane
{
	private HomePaneMessages messages = GWT.create(HomePaneMessages.class);
	private ChooseProjectServiceAsync chooseService = GWT.create(ChooseProjectService.class);

	
	private List<GwtProject>[] projects;
	
	
	public HomePane(final State stateInfo)
		{
			super(stateInfo);
		
			super.cleanProjectContext();
			super.showLoadingStatusBar();
			
			initializePane();

		}

	public void initializePane()
	{
		chooseService.getProjectsForUser(currentState.getUserId(), currentState.getCaseID(),
				new AsyncCallback<List<GwtProject>[]>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "getting projects fo user");
				}

				@Override
				public void onSuccess(List<GwtProject>[] result)
				{
					projects = result;
					setupProjectsTable();
				}

			}

		);

	}

	private void setupProjectsTable()
	{
		VerticalPanel projectPanel = new VerticalPanel();
		projectPanel.setSpacing(10);
		projectPanel.setWidth("100%");
		projectPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		projectPanel.add(getProjectListTable(true));
		projectPanel.add(getProjectListTable(false));
		
		this.getContent().add(projectPanel);
		
		this.hideStatusBar();
	}

	/**
	 * This returns a Flextable with the list of projects.
	 * @param showOpenProjects True if the projects shown should be "in progress"
	 * @return returns a Flextable with list of projects.
	 */
	private FlexTable getProjectListTable(boolean showOpenProjects)
	{
		FlexTable projectsTable = new FlexTable();
		projectsTable.setWidth("450px");
		RowFormatter rowFormatter = projectsTable.getRowFormatter();
		rowFormatter.setStyleName(0, "square-choose-step-title");

		FlexCellFormatter cellFormatter = projectsTable.getFlexCellFormatter();
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		Label projectsTableHeader = new Label(messages.openProjects());
		projectsTableHeader.setStyleName("page-title-heading");
		
		if (!showOpenProjects)
		{
			projectsTableHeader.setText(messages.completedProjects());
		}
		
		projectsTable.setWidget(0, 0, projectsTableHeader);
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setStyleName(0, 0, "square-choose-step-title");
		
		List<GwtProject> projectList = null;
		if (showOpenProjects)
		{
			projectList = projects[0];
		}
		else
		{
			projectList = projects[1];
		}
		
		int currentRow = 1;
		for (GwtProject p : projectList)
		{
			
			cellFormatter.setVerticalAlignment(currentRow, 0, HasVerticalAlignment.ALIGN_TOP);
			cellFormatter.setHorizontalAlignment(currentRow, 0, HasHorizontalAlignment.ALIGN_LEFT);
			cellFormatter.setHorizontalAlignment(currentRow, 1, HasHorizontalAlignment.ALIGN_LEFT);
			
			cellFormatter.setStyleName(currentRow, 0, "square-select-project");
			cellFormatter.setStyleName(currentRow, 1, "square-select-project");
		
			
			cellFormatter.setWidth(currentRow, 0, "180px");
			
			final ChooseProjectLink projectLink = new ChooseProjectLink(p);
		
			projectLink.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						currentState.setProjectID(projectLink.getProject().getId());

						loadRoles(projectLink.getProject(), currentState.getUserName());
						

					}

				}

			);
			VerticalPanel statusPanel = new VerticalPanel();
			String projectFocus = messages.projectFocus()+" "+ super.getProjectFocus(p);
			
			String projectType = null;//SQUARE or SQUARE Lite
			
			
			
			if(p.isLite())
			{
				projectType = messages.squareLiteType();
			}
			else
			{
				projectType = messages.squareType();
			}

		
			
			HTML projecDetail = new HTML(messages.projectDetails());
			statusPanel.setSpacing(3);
			statusPanel.add(projecDetail);
			List<String> projectInfoList =new ArrayList<String>();
			projectInfoList.add(projectType);
			projectInfoList.add(projectFocus);
			projectInfoList.add(messages.currentRole(p.getCurrentRole().getName()));
	
			statusPanel.add(new HTML(SquareUtil.createHTMLList(projectInfoList)));
		
			if (!showOpenProjects)
			{
				createExportPanel(statusPanel, p.getId());
			} 
			
		
		
		
			projectsTable.setWidget(currentRow, 0, projectLink);
			projectsTable.setWidget(currentRow, 1, statusPanel);
			
			
			currentRow++;
		}
		
		if(projectsTable.getRowCount()==1)
		{
			Label noProject = new Label(messages.noProjectsFound());
			DisclosurePanel notFoundDisclousure = new DisclosurePanel();
			
			noProject.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			notFoundDisclousure.add(noProject);        
			notFoundDisclousure.setAnimationEnabled(true);
			notFoundDisclousure.setOpen(true);
			
			projectsTable.setWidget(1, 0, notFoundDisclousure);
			cellFormatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
			
		}
		
	
		// fill in the flextable rows with project data
		return projectsTable;
	}

	/**
	 * This function is temporary here. It should be placed in the chose project
	 * step.
	 * 
	 * @param projectID
	 * @param userName
	 */
	public void loadRoles(final GwtProject project, final String userName)
	{
		this.showLoadingStatusBar();

		
		currentState.setProjectName(project.getName());
		
		for(AsquareCase ac: AsquareCase.values()){
			if(ac.getLabel().equals(project.getClass().getName()))
			{
				currentState.setCaseName(ac);
			}
		}
		
		currentState.setProjectInspectionStatus(null);
		
		
		if (project.getInspectionStatus() != null)
		{
			currentState.setProjectInspectionStatus(project.getInspectionStatus().getLabel());
		}
		for (ProjectRole pr: ProjectRole.values())
		{
			if (pr.getLabel().equals(project.getCurrentRole().getName()))
			{
				currentState.setUserProjectRole(pr);
			}
		}
		System.out.println("homepane before choose steps........."+project.getCases().getId());
		if(project.getCases().getId()==1)
		{
			System.out.println("case id ...............1");
			History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
		}
		else if(project.getCases().getId()==3)
		{
			System.out.println("case id ...............3");
			History.newItem(ChooseStepCase3Pilot.generateNavigationId(ChooseStepCase3Pilot.PageId.home));
		}
	}

	class ChooseProjectLink extends SquareHyperlink
	{
		private GwtProject project;

		public ChooseProjectLink(GwtProject project)
			{
				super(project.getName());
				this.project = project;
			}

		/**
		 * @return the projectId
		 */
		public GwtProject getProject()
		{
			return project;
		}
	}
}
