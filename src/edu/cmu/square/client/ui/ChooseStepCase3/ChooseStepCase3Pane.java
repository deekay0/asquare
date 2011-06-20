/**
 * 
 */
package edu.cmu.square.client.ui.ChooseStepCase3;

import java.util.List;

import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.ui.ManageProject.ManageProjectPilot;
import edu.cmu.square.client.ui.agreeOnDefinitions.AgreeOnDefinitionsPilot;
import edu.cmu.square.client.ui.assetsAndGoals.AssetsAndGoalsPilot;
import edu.cmu.square.client.ui.categorizeRequirements.CategorizeRequirementsPilot;
import edu.cmu.square.client.ui.collectArtifacts.CollectArtifactsPilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.elicitSecurityRequirements.ElicitSecurityRequirementsPilot;
import edu.cmu.square.client.ui.reviewAndFinalizeRequirements.ReviewAndFinalizeRequirementsPilot;
import edu.cmu.square.client.ui.reviewPackages.ReviewPackagesPilot;

public class ChooseStepCase3Pane extends BasePane
{
	private State currentState;

	private List<GwtStep> gwtProjectStepList;
	GwtProject gwtProject = null;
	private FlexTable projectStepTable = new FlexTable();
	FlexCellFormatter formatter = this.projectStepTable.getFlexCellFormatter();

	// use the getSteps from the manage project
	private ManageProjectServiceAsync mpsa = GWT.create(ManageProjectService.class);

	final ChooseStepCase3PaneMessages messages = (ChooseStepCase3PaneMessages) GWT.create(ChooseStepCase3PaneMessages.class);

	public ChooseStepCase3Pane(State stateInfo)

	{
		super(stateInfo);
		currentState = stateInfo;
		VerticalPanel mainLayout = new VerticalPanel();
		this.gwtProject = new GwtProject();
		this.gwtProject.setId(this.currentState.getProjectID());
		
		mainLayout.add(this.projectStepTable);
		getStepsForProject(gwtProject);
		this.showLoadingStatusBar();
		this.getContent().add(mainLayout);
	}
	
	private void getStepsForProject(final GwtProject gwtProject)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		mpsa.getSteps(gwtProject, new AsyncCallback<List<GwtStep>>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.errorRetrieveSteps());
				}

				public void onSuccess(List<GwtStep> result)
				{
					gwtProjectStepList = result;
					getProjectType();
				}
			});
	}
	private void getProjectType()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) mpsa;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		mpsa.getProject(gwtProject.getId(), new AsyncCallback<GwtProject>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.errorRetrieveProject());
				}

				public void onSuccess(GwtProject result)
				{
					gwtProject = result;
					hideStatusBar();
					loadStepsToTable();
					//execute();
				}

			});
	}

	private void loadStepsToTable()
	{
		formatter.setWidth(0, 0, "450px");
		this.projectStepTable.clear();
		this.projectStepTable.setCellSpacing(2);

		// Edit setting links and View Project Members link
		HorizontalPanel links = new HorizontalPanel();
		links.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		links.setStyleName("flex-link-bar");
		Hyperlink editProjectSettingLink = new Hyperlink(messages.editSettingsLink(), ManageProjectPilot
				.generateNavigationId(ManageProjectPilot.PageId.home));
		Hyperlink viewMembersLink = new Hyperlink(messages.viewMembersLink(), ChooseStepCase3Pilot.generateNavigationId(ChooseStepCase3Pilot.PageId.member));

		if (GwtModesType.ReadWrite == this.currentState.getMode())
		{
			links.add(editProjectSettingLink);
			links.add(viewMembersLink);
		}
		else
		{
			links.add(viewMembersLink);
		}

		this.projectStepTable.setWidget(0, 0, links);
		formatter.setColSpan(0, 0, 2);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);

		// Draw Determine Context
		drawDetermineContext();
		// Retrieve the project type

	}

	/*public void execute()
	{
		int rowID = 0;
		// there is something wrong here of the isSecurity and isPrivacy
		if (gwtProject.isSecurity() && gwtProject.isPrivacy())
		{
			// Draw Gather Security Requirements
			drawSecurityRequirements(4);
			// Draw Gather Privacy Requirements
			drawPrivacyRequirements(8);
			rowID = 12;
		}
		else if (gwtProject.isSecurity())
		{
			// Draw Gather Security Requirements
			drawSecurityRequirements(4);
			rowID = 8;
		}
		else if (gwtProject.isPrivacy())
		{
			// Draw Gather Privacy Requirements
			drawPrivacyRequirements(4);
			rowID = 8;
		}
		else
		{
			// Window.alert("I'm false!");
			drawSecurityRequirements(4);
			rowID = 8;
		}

		drawAnalyzeRequirements(rowID);

	}

	private void drawAnalyzeRequirements(int rowID)
	{
		rowID++;
		this.projectStepTable.setWidget(rowID, 0, new Label(messages.analyzeRequirementsLabel()));
		formatter.setColSpan(rowID, 0, 2);
		formatter.setHorizontalAlignment(rowID, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setStyleName(rowID, 0, "square-choose-step-title");

		for (final GwtStep s : gwtProjectStepList)
		{
			if (s.getDescription().equals(messages.step7()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(rowID + 1, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(rowID + 1, 0, new Hyperlink(s.getDescription(), CategorizeRequirementsPilot
							.generateNavigationId(CategorizeRequirementsPilot.PageId.home)));
				}

				this.projectStepTable.setWidget(rowID + 1, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(rowID + 1, 0, "square-choose-step");
				formatter.setStyleName(rowID + 1, 1, "square-choose-step");
				formatter.setHorizontalAlignment(rowID + 1, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(rowID + 1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step8()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(rowID + 2, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(rowID + 2, 0, new Hyperlink(s.getDescription(), PrioritizeRequirementsPilot
							.generateNavigationId(PrioritizeRequirementsPilot.PageId.home)));
				}

				this.projectStepTable.setWidget(rowID + 2, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(rowID + 2, 0, "square-choose-step");
				formatter.setStyleName(rowID + 2, 1, "square-choose-step");
				formatter.setHorizontalAlignment(rowID + 2, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(rowID + 2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step9()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(rowID + 3, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(rowID + 3, 0, new Hyperlink(s.getDescription(), InspectRequirementsPilot
							.generateNavigationId(InspectRequirementsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(rowID + 3, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(rowID + 3, 0, "square-choose-step");
				formatter.setStyleName(rowID + 3, 1, "square-choose-step");
				formatter.setHorizontalAlignment(rowID + 3, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(rowID + 3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
		}
		this.projectStepTable.setWidget(rowID + 4, 0, new Label(" "));
		formatter.setColSpan(rowID + 4, 0, 2);
		formatter.setHorizontalAlignment(rowID + 4, 0, HasHorizontalAlignment.ALIGN_LEFT);
	}*/

	
	private void drawDetermineContext()
	{
		this.projectStepTable.setWidget(1, 0, new Label(messages.determineContextLabel()));
		formatter.setColSpan(1, 0, 2);
		formatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setStyleName(1, 0, "square-choose-step-title");

		for (final GwtStep s : gwtProjectStepList)
		{
			System.out.println("case3...description"+s.getDescription());
			if (s.getDescription().equals(messages.step1()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(2, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(2, 0, new Hyperlink(s.getDescription(), AgreeOnDefinitionsPilot
							.generateNavigationId(AgreeOnDefinitionsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(2, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(2, 0, "square-choose-step");
				formatter.setStyleName(2, 1, "square-choose-step");
				formatter.setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step2()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(3, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(3, 0, new Hyperlink(s.getDescription(), AssetsAndGoalsPilot
							.generateNavigationId(AssetsAndGoalsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(3, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(3, 0, "square-choose-step");
				formatter.setStyleName(3, 1, "square-choose-step");
				formatter.setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step3()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(4, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(4, 0, new Hyperlink(s.getDescription(), ElicitSecurityRequirementsPilot
							.generateNavigationId(ElicitSecurityRequirementsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(4, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(4, 0, "square-choose-step");
				formatter.setStyleName(4, 1, "square-choose-step");
				formatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step4()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(5, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(5, 0, new Hyperlink(s.getDescription(), ReviewPackagesPilot
							.generateNavigationId(ReviewPackagesPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(5, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(5, 0, "square-choose-step");
				formatter.setStyleName(5, 1, "square-choose-step");
				formatter.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step5()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(6, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(6, 0, new Hyperlink(s.getDescription(), ReviewAndFinalizeRequirementsPilot
							.generateNavigationId(ReviewAndFinalizeRequirementsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(6, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(6, 0, "square-choose-step");
				formatter.setStyleName(6, 1, "square-choose-step");
				formatter.setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step6()))
			{
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(7, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(7, 0, new Hyperlink(s.getDescription(), CollectArtifactsPilot
							.generateNavigationId(CollectArtifactsPilot.PageId.home)));
				}
				this.projectStepTable.setWidget(7, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(7, 0, "square-choose-step");
				formatter.setStyleName(7, 1, "square-choose-step");
				formatter.setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			else if (s.getDescription().equals(messages.step7()))
			{
				System.out.println("case 3...............step7");
				if (s.getStatus() == StepStatus.NotStarted)
				{
					this.projectStepTable.setWidget(8, 0, new Label(s.getDescription()));
				}
				else
				{
					this.projectStepTable.setWidget(8, 0, new Hyperlink(s.getDescription(), CategorizeRequirementsPilot
							.generateNavigationId(CategorizeRequirementsPilot.PageId.home)));
				}

				this.projectStepTable.setWidget(8, 1, new Label(s.getStatus().getLabel()));
				formatter.setStyleName(8, 0, "square-choose-step");
				formatter.setStyleName(8, 1, "square-choose-step");
				formatter.setHorizontalAlignment(8, 0, HasHorizontalAlignment.ALIGN_LEFT);
				formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
			}
			
			this.projectStepTable.setWidget(9, 0, new Label(" "));
			formatter.setColSpan(9, 0, 2);
			formatter.setHorizontalAlignment(9, 0, HasHorizontalAlignment.ALIGN_LEFT);
			
			
		}

	}

}
