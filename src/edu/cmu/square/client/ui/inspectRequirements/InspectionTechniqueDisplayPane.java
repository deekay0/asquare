package edu.cmu.square.client.ui.inspectRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueService;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot.PageId;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class InspectionTechniqueDisplayPane extends BasePane
{
	private final InspectRequirementsPaneMessages messages = GWT.create(InspectRequirementsPaneMessages.class);
	private ManageProjectServiceAsync serviceForProjectManagement = GWT.create(ManageProjectService.class); //to commit and update the project

	private GwtProject project;
	private GwtInspectionTechnique technique = null;
	private int selectedTechniqueID; 
	private InspectionTechniqueServiceAsync service = GWT.create(InspectionTechniqueService.class);
	private List<GwtInspectionTechnique> listOfRequirementsInspectionTechniques;
	private SelectedInspectionTechniqueRenderer inspectionTechniqueSummary;
	private VerticalPanel basePane;
	
	private final SquareHyperlink viewLink;
	
	private final 	SquareHyperlink changeTechniqueLink = new SquareHyperlink(messages.changeTechnique());
	
	public InspectionTechniqueDisplayPane(State stateInfo)
	{
		super(stateInfo);
		
		viewLink = new SquareHyperlink(messages.viewRequirements());
		
		this.showLoadingStatusBar();
		selectedTechniqueID = -1;
		
		listOfRequirementsInspectionTechniques = new ArrayList<GwtInspectionTechnique>();
		inspectionTechniqueSummary = new SelectedInspectionTechniqueRenderer(this);
	
		this.basePane = new VerticalPanel();
		this.basePane.setWidth("100%");
		this.basePane.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		this.showTechniquePane();
	}

	private void showTechniquePane()
	{
		this.showLoadingStatusBar();
		
		fetchProjectFromDatabase(); //to execute
	}

	private void fetchProjectFromDatabase()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) serviceForProjectManagement;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");
		
		this.serviceForProjectManagement.getProject(this.getCurrentState().getProjectID(), new AsyncCallback<GwtProject>()
		{
			public void onFailure(Throwable caught) 
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingProjectInformation());
				
			}

			public void onSuccess(GwtProject result) 
			{
				project = result;
				
				GwtInspectionTechnique tempTechniqueRef = project.getInspectionTechniqueID();
				if(tempTechniqueRef!=null)
				{
					selectedTechniqueID = tempTechniqueRef.getInspectionTechniqueId().intValue();
				}
				
				fetchInspectionTechnqiuesFromDataBase(inspectionTechniqueSummary);
			}
		});
	}
	
	private void fetchInspectionTechnqiuesFromDataBase(final Command cmd)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "inspectionTechnique.rpc");
		service.getInspectionTechniques(getCurrentState().getProjectID(), null,  new AsyncCallback<List<GwtInspectionTechnique>>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrieveInspectionsTechniques());
			}

			public void onSuccess(List<GwtInspectionTechnique> result)
			{
				for(int i=0; i<result.size();i++)
				{
					listOfRequirementsInspectionTechniques.add(result.get(i));
				
					if(selectedTechniqueID!=-1)
					{
						if(result.get(i).getInspectionTechniqueId().intValue() == selectedTechniqueID)
						{
							technique = result.get(i);  //the technique is stored
						}
					}
				}
				cmd.execute();
			}
			
		});
		
	}

	public void renderSelectedInspectionTechniqueTable()
	{	
		this.hideStatusBar();
		Label inspectionTitle;
		Label inspectionDescription;

		if (technique == null)
		{
			inspectionTitle = new Label(messages.noTechniqueSelected());
			inspectionDescription = new Label("");
		}
		else
		{
			inspectionTitle = new Label(project.getInspectionTechniqueID().getTitle());
			inspectionDescription= new Label(project.getInspectionTechniqueID().getDescription());
		}
		
		Label userAssistance = new Label(messages.userAssistance());
		inspectionTitle.setStyleName("section-heading");
		Button inspectionSubmissionButton = new Button(messages.saveInspectionStatus());
		inspectionSubmissionButton.setWidth("200px");
		
		final ListBox inspectionCompletionStatusOption = new ListBox();
		inspectionCompletionStatusOption.addItem(messages.inspectionInProgress());
		inspectionCompletionStatusOption.addItem(messages.issuesFoundRedo());
		inspectionCompletionStatusOption.addItem(messages.issuesFoundNoRedo());
		inspectionCompletionStatusOption.addItem(messages.issuesCleared());
		
		if (this.getCurrentState().getMode() != GwtModesType.ReadWrite)
		{
			inspectionSubmissionButton.setEnabled(false);
			inspectionCompletionStatusOption.setEnabled(false);
			changeTechniqueLink.setVisible(false);
		}
		
		for (int i = 0; i < inspectionCompletionStatusOption.getItemCount(); i++)
		{
			if (this.project.getInspectionStatus() == null)
			{
				inspectionCompletionStatusOption.setItemSelected(0, true);
				break;
			}
			
			if (inspectionCompletionStatusOption.getItemText(i).trim().equals(this.project.getInspectionStatus().getLabel()))
			{
				inspectionCompletionStatusOption.setItemSelected(i, true);
				break;
			}
			
		}
		inspectionSubmissionButton.addClickHandler(
				
				new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						String x=inspectionCompletionStatusOption.getItemText(inspectionCompletionStatusOption.getSelectedIndex());
						saveHandler(x);
					}

				}
		);
		HorizontalPanel inspectionTitlePanel = new HorizontalPanel();
		
		//setPanelSettings(inspectionTitlePanel,inspectionTitle);
		inspectionTitlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		
		FlexTable titleAndLink = new FlexTable();	
		titleAndLink.setWidget(0, 0, inspectionTitle);
		titleAndLink.setWidget(0, 1, changeTechniqueLink);
	
		

		setPanelSettings(inspectionTitlePanel,titleAndLink);
		
		
		HorizontalPanel inspectionDescriptionPanel = new HorizontalPanel();
		if(technique!=null)
		{
			setPanelSettings(inspectionDescriptionPanel,inspectionDescription);
		}
		
		HorizontalPanel userAssistancePanel = new HorizontalPanel();
		setPanelSettings(userAssistancePanel,userAssistance);
		
		changeTechniqueLink.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				boolean response = Window.confirm(messages.techniqueModConfirm());
				if(response)
				{					
					serviceForProjectManagement.updateProjectInspectionStatus (currentState.getProjectID(), null, new AsyncCallback<Void>()
					{

						@Override
						public void onFailure(Throwable caught)
						{
							ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.restartInspection());		
						}

						@Override
						public void onSuccess(Void result)
						{
							
							currentState.setProjectInspectionStatus(null);
							History.newItem(InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.inspectionTechniqueSelection));

						}

								
					});
				}
			}
			
		 });
 
		
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, inspectionSubmissionButton);
		table.setWidget(0, 1, inspectionCompletionStatusOption);
		table.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_BOTTOM);
		table.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_BOTTOM);
		
		HorizontalPanel inspectionStatusPanel = new HorizontalPanel();
		setPanelSettings(inspectionStatusPanel,table);
		
		HorizontalPanel navigationPanel = new HorizontalPanel();
		SquareHyperlink navigateToChooseStepLink = new SquareHyperlink(messages.navigateStepSelection());
		setPanelSettings(navigationPanel, navigateToChooseStepLink);
		navigateToChooseStepLink.addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				History.newItem(ChooseStepPilot.generateNavigationId(PageId.home));
			}
			
		});
		
		
		viewLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				History.newItem(InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.Traceability));
			}
		});
		

		
		this.basePane.add(inspectionTitlePanel);
		this.basePane.add(inspectionDescriptionPanel);
		this.basePane.add(this.viewLink);
		createExportPanel(basePane, currentState.getProjectID());
		this.basePane.add(userAssistancePanel);
		this.basePane.add(inspectionStatusPanel);
		this.basePane .add(navigationPanel);
		this.basePane.setSpacing(15);
		this.getContent().clear();
		this.getContent().add(this.basePane);

		
	}
	

	
	private void setPanelSettings(HorizontalPanel tempPanel,Widget widget)
	{
		tempPanel.setWidth("85%");
		tempPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		tempPanel.add(widget);
		tempPanel.setHeight("25px");
	}

	//select the inspection status based on the string
	private void saveHandler(final String inspectionTechniqueStatus)
	{
		
		/*if(inspectionTechniqueStatus.trim().equalsIgnoreCase("Inspection in Progress"))
		{
			return;
		}*/
		
		if(currentState.getProjectInspectionStatus()!=null)
		{
			if(!currentState.getProjectInspectionStatus().trim().equalsIgnoreCase(messages.inspectionInProgress()))
			{
				if(!inspectionTechniqueStatus.trim().equalsIgnoreCase(currentState.getProjectInspectionStatus().trim()))
				{
					boolean response =Window.confirm(messages.inspectionChangeConfirm());
					if(!response) return;
				}
			}
		}
	
		
		serviceForProjectManagement.updateProjectInspectionStatus
		(currentState.getProjectID(), inspectionTechniqueStatus, 
				new AsyncCallback<Void>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.unpdatingInspectionStatus());
					}

					@Override
					public void onSuccess(Void result)
					{
						
						 currentState.setProjectInspectionStatus(inspectionTechniqueStatus);
						showStatusBarWithoutAnimation(messages.savedInspectionStatus());
					}
			
				}
				
		);
		
	} 
}

/**
 * COMMAND CLASS for rendering the flextable for the selected inspection technique
 */
class SelectedInspectionTechniqueRenderer implements Command
{
	private InspectionTechniqueDisplayPane pane;
	
	
	public SelectedInspectionTechniqueRenderer(InspectionTechniqueDisplayPane inspectionTechniqueSelectionPane)
	{
			this.pane = inspectionTechniqueSelectionPane;
	}

	public void execute()
	{
		
		pane.renderSelectedInspectionTechniqueTable();
	}
	
}
