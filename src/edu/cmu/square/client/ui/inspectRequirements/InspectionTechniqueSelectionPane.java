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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueService;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;

/**
 * Display the inspection technique pane to the user. 
 * Available only to the lead requirements engineer.
 */
public class InspectionTechniqueSelectionPane extends BasePane
{
	private final InspectRequirementsPaneMessages inspectonMessages = GWT.create(InspectRequirementsPaneMessages.class);
	
	private List<GwtInspectionTechnique> listOfRequirementsInspectionTechniques;
	private InspectionTechniqueFlexTableRenderer inspectionTechniqueGrid;
	private InspectionTechniqueServiceAsync service = GWT.create(InspectionTechniqueService.class);
	
	private ManageProjectServiceAsync serviceForProjectManagement = GWT.create(ManageProjectService.class); //to commit and update the project
	
	private InspectionTechniqueSelectionPane selfReference;
	private GwtProject project;
	private int selectedTechniqueID; 
	
	
	public InspectionTechniqueSelectionPane(State stateInfo)
	{
		super(stateInfo);
		listOfRequirementsInspectionTechniques = new ArrayList<GwtInspectionTechnique>();
		this.inspectionTechniqueGrid = new InspectionTechniqueFlexTableRenderer(this);
		this.selectedTechniqueID = -1;
		this.showLoadingStatusBar();
	
		
		selfReference = this; //for clearing the pane when a message is loaded
		this.showListOfInspectionTechniquePane();
	}

	private void showListOfInspectionTechniquePane()
	{		
		selfReference.getContent().clear();
		fetchProjectFromDatabase(); //to execute
	}

	private void fetchInspectionTechnqiuesFromDataBase(final Command cmd)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "inspectionTechnique.rpc");
		service.getInspectionTechniques(getCurrentState().getProjectID(), null,  new AsyncCallback<List<GwtInspectionTechnique>>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, inspectonMessages.inspectionRetrievalError());
			}

			public void onSuccess(List<GwtInspectionTechnique> result)
			{
				for(int i=0; i<result.size();i++)
				{
					listOfRequirementsInspectionTechniques.add(result.get(i));
				}
				cmd.execute();
			}
			
		});
	}
	
	
	private void fetchProjectFromDatabase()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) serviceForProjectManagement;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");
		
		this.serviceForProjectManagement.getProject(this.getCurrentState().getProjectID(), new AsyncCallback<GwtProject>()
		{
			public void onFailure(Throwable caught) 
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, inspectonMessages.retrievingProjectInformation());
			}

			public void onSuccess(GwtProject result) 
			{
				project = result;
				
				GwtInspectionTechnique tempTechniqueRef = project.getInspectionTechniqueID();
				if(tempTechniqueRef!=null)
				{
					selectedTechniqueID = tempTechniqueRef.getInspectionTechniqueId().intValue();
				}
				fetchInspectionTechnqiuesFromDataBase(inspectionTechniqueGrid);
			}
		});
	}
	
	
	/**
	 * To commit the selection to the database and proceed to the next page
	 */
	private void SaveSelectionAndContinue()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) serviceForProjectManagement;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");
		
//		this.serviceForProjectManagement.updateProjectInspectionTechnique(project.getId(), project.getInspectionTechniqueID().getInspectionTechniqueId(), new AsyncCallback<Void>()
//		{
//			public void onFailure(Throwable caught)
//			{
//				ExceptionHelper.SquareRootRPCExceptionHandler(caught, inspectonMessages.savingInspectionInformation());
//			}
//
//			public void onSuccess(Void result)
//			{		
//				
//				History.newItem(InspectRequirementsPilot.generateNavigationId(InspectRequirementsPilot.PageId.inspectionTechniqueDisplay));
//			}
//			
//		});
	}

	public void renderInspectionTechniqueTable()
	{
		this.hideStatusBar();
		selfReference.getContent().clear(); //clear the loading message
		
		VerticalPanel techniqueListPanel = new VerticalPanel();
		techniqueListPanel.setWidth("100%");
		techniqueListPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		Label pageTitle = new Label(inspectonMessages.selectInspectionTechnique());
		pageTitle.setStyleName("section-heading");
		HorizontalPanel labelPanel = new HorizontalPanel();
		labelPanel.setWidth("85%");
		labelPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		labelPanel.add(pageTitle);
		labelPanel.setHeight("25px");
		
		Button saveSelection = new Button(inspectonMessages.saveSelection());
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setWidth("85%");
		buttonPanel.setHeight("45px");
		buttonPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonPanel.add(saveSelection);
		
		//STYLES TO BE SET FOR THE FLEX TABLE
		final FlexTable inspectionTechniqueTable = new FlexTable();
		inspectionTechniqueTable.setStyleName("square-flex");
		inspectionTechniqueTable.setWidth("85%");
		inspectionTechniqueTable.setCellPadding(10);
		inspectionTechniqueTable.setCellSpacing(0);
		
		inspectionTechniqueTable.getCellFormatter().setStyleName(0, 0, "summary-column-heading");
		inspectionTechniqueTable.getCellFormatter().setStyleName(0, 1, "summary-column-heading");
		inspectionTechniqueTable.getCellFormatter().setStyleName(0, 2, "summary-column-heading");
		inspectionTechniqueTable.getCellFormatter().setWidth(1, 0, "20%");
		inspectionTechniqueTable.getCellFormatter().setWidth(1, 1, "65%");
		inspectionTechniqueTable.getCellFormatter().setWidth(1, 2, "15%"); 
		inspectionTechniqueTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		inspectionTechniqueTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		
		inspectionTechniqueTable.setWidget(0, 0, new Label(inspectonMessages.technique()));
		inspectionTechniqueTable.setWidget(0, 1, new Label(inspectonMessages.description()));
		inspectionTechniqueTable.setWidget(0, 2, new Label(inspectonMessages.selection()));
		
		for(int i=0; i<listOfRequirementsInspectionTechniques.size();i++)
		{
			final InspectionTechniqueFlexTableRow row = new InspectionTechniqueFlexTableRow();
			row.setTitle(listOfRequirementsInspectionTechniques.get(i).getTitle());
			row.setTechnique_id(listOfRequirementsInspectionTechniques.get(i).getInspectionTechniqueId().intValue());
			row.setButton(new RadioButton(null),inspectonMessages.inspectionGroup());
			if(listOfRequirementsInspectionTechniques.get(i).getInspectionTechniqueId() == this.selectedTechniqueID)
			{
				row.setSelected(true); //DETERMINE IF THIS ROW WAS SELECTED based on GwtProject
				row.getButton().setValue(true);
			}
			else
			{
				row.getButton().setValue(false);
				row.setSelected(false);
			}
			row.setDescription(listOfRequirementsInspectionTechniques.get(i).getDescription());
			
			inspectionTechniqueTable.setWidget(i+1, 0, new Label(row.getTitle()));
			inspectionTechniqueTable.getCellFormatter().setHorizontalAlignment(i+1, 1, HasHorizontalAlignment.ALIGN_LEFT);
			inspectionTechniqueTable.setWidget(i+1, 1, new Label(row.getDescription()));
			RadioButton buttonReference = row.getButton();
			inspectionTechniqueTable.setWidget(i+1, 2, buttonReference);
			
			if(buttonReference.getValue()) //Called when loading the page
			{
				inspectionTechniqueTable.getCellFormatter().setStyleName(i+1, 0,"square-Table-SelectedRow"); 
				inspectionTechniqueTable.getCellFormatter().setStyleName(i+1, 1,"square-Table-SelectedRow"); 
				inspectionTechniqueTable.getCellFormatter().setStyleName(i+1, 2,"square-Table-SelectedRow"); 
			}
			
			final int tempRefForLoop = i;
			buttonReference.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					
					inspectionTechniqueTable.getCellForEvent(event).getRowIndex();	//get the row that is selected
					
					for(int j=0;j<inspectionTechniqueTable.getRowCount()-1;j++)
					{
						if(j==tempRefForLoop)
						{
							inspectionTechniqueTable.getCellFormatter().setStyleName(tempRefForLoop+1, 0,"square-Table-SelectedRow"); 
							inspectionTechniqueTable.getCellFormatter().setStyleName(tempRefForLoop+1, 1,"square-Table-SelectedRow"); 
							inspectionTechniqueTable.getCellFormatter().setStyleName(tempRefForLoop+1, 2,"square-Table-SelectedRow");
							row.setSelected(true);
							selectedTechniqueID = row.getTechnique_id();
						}
						else
						{
							row.setSelected(false);
							inspectionTechniqueTable.getCellFormatter().removeStyleName(j+1, 0,"square-Table-SelectedRow"); 
							inspectionTechniqueTable.getCellFormatter().removeStyleName(j+1, 1,"square-Table-SelectedRow"); 
							inspectionTechniqueTable.getCellFormatter().removeStyleName(j+1, 2,"square-Table-SelectedRow");
						}
					}
				}
			});
		}
		
		saveSelection.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if(selectedTechniqueID==-1)
				{
					Window.alert(inspectonMessages.selectBeforeSave());
					return;
				}
				for(int i=0; i<listOfRequirementsInspectionTechniques.size();i++)
				{
					if(listOfRequirementsInspectionTechniques.get(i).getInspectionTechniqueId() == selectedTechniqueID)
					{
						project.setInspectionTechniqueID(listOfRequirementsInspectionTechniques.get(i));
						SaveSelectionAndContinue();
					}
				}
			}
		});
		techniqueListPanel.add(labelPanel);
		techniqueListPanel.add(inspectionTechniqueTable);
		techniqueListPanel.add(buttonPanel);
		this.getContent().add(techniqueListPanel);
	}
	
	
	
	
	class InspectionTechniqueFlexTableRow
	{
		private int technique_id;
		private boolean selected;
		private String description;
		private String title;
		private RadioButton button;
		
		InspectionTechniqueFlexTableRow()
		{
			this.setSelected(false);
			this.setTechnique_id(-1);
		}

		public void setTechnique_id(int technique_id)
		{
			this.technique_id = technique_id;
		}

		public int getTechnique_id()
		{
			return technique_id;
		}

		public void setSelected(boolean selected)
		{
			this.selected = selected;
		}

		public boolean isSelected()
		{
			return selected;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getTitle()
		{
			return title;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public String getDescription()
		{
			return description;
		}

		public void setButton(RadioButton button, String name)
		{
			this.button = button;
			this.button.setName(name); //group naame to which this button belongs
		}

		public RadioButton getButton()
		{
			return button;
		}		
	}
}



/**
 * COMMAND CLASS for rendering the flextable for the list of inspection techniques
 */
class InspectionTechniqueFlexTableRenderer implements Command
{
	private InspectionTechniqueSelectionPane pane;
	
	
	public InspectionTechniqueFlexTableRenderer(InspectionTechniqueSelectionPane inspectionTechniqueSelectionPane)
	{
			this.pane = inspectionTechniqueSelectionPane;
	}

	public void execute()
	{
		pane.renderInspectionTechniqueTable();
	}
	
}
