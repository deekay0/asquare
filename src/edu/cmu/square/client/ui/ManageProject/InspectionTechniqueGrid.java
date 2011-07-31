package edu.cmu.square.client.ui.ManageProject;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueService;
import edu.cmu.square.client.remoteService.step.interfaces.InspectionTechniqueServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;


/**
 * This is the grid that holds the current inspection criteria.
 * 
 */
public class InspectionTechniqueGrid extends Composite
{
	private InspectionTechniqueServiceAsync service = GWT.create(InspectionTechniqueService.class);

	private ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	private FlexTable inspectionTable = null;
	private Integer projectId;
	private StepStatus stepStatus = StepStatus.NotStarted; 
	
	private BasePane caller = null;

	private List<GwtInspectionTechnique> inspections = new ArrayList<GwtInspectionTechnique>();
	private int lastRowClicked = -1;

	
	
	public InspectionTechniqueGrid(Integer projectId, BasePane caller)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "inspectionTechniques.rpc");

		this.projectId = projectId;
		this.caller = caller;
		initializeGrid();
		initWidget(this.inspectionTable);
	}



	public void addInspectionToTable(final GwtInspectionTechnique newInspection)
	{
		this.service.addInspectionTechnique(this.projectId, newInspection, new AsyncCallback<GwtInspectionTechnique>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case duplicateName :
							Window.alert(messages.inspectionDuplicatedName());
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalInspectionAddError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalInspectionAddError());
				}
			}
				
			public void onSuccess(GwtInspectionTechnique result)
			{
				addRow(result);
				caller.yellowFadeHandler.add(inspectionTable, inspectionTable.getRowCount() - 1);
				
				//inspections.add(newInspection);
				inspections.add(result);
			}
		});
	}



	public void updateInspectionInTable(final GwtInspectionTechnique inspection)
	{
		this.service.updateInspectionTechnique(this.projectId, inspection, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case duplicateName :
							Window.alert(messages.inspectionDuplicatedName());
							break;
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalInspectionUpdateError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalInspectionUpdateError());
				}
			}
			
			public void onSuccess(Void result)
			{
				inspectionTable.setWidget(lastRowClicked, 0, new Label(inspection.getTitle()));
				inspectionTable.setWidget(lastRowClicked, 1, new Label(inspection.getDescription()));
				caller.yellowFadeHandler.add(inspectionTable, lastRowClicked);
				
				inspections.set(lastRowClicked - 1, inspection);
				//lastRowClicked = -1;
			}
		});
	}

	// ------------------------------------------------------
	// -------------- Private stuff -------------------------
	// ------------------------------------------------------

	/**
	 * Initializes the <code>inspectionTable</code>. Sets up layouts that are
	 * specific to this grid.
	 */
	private void initializeGrid()
	{
		this.inspectionTable = new FlexTable();
		this.inspectionTable.setStyleName("square-flex");
		this.inspectionTable.setWidth("100%");
		this.inspectionTable.setCellSpacing(0);

		Button createInspectionButton = new Button(messages.createInspectionButton());
		inspectionTable.setWidget(0, 0, createInspectionButton);
		inspectionTable.setWidget(0, 1, new Label(messages.description()));

		FlexCellFormatter formatter = inspectionTable.getFlexCellFormatter();

		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		formatter.setStyleName(0, 2, "square-TableHeader");
		
		this.inspectionTable.getCellFormatter().setHeight(0, 0, "30px");
		
		formatter.setWidth(0, 0, "140px");
		formatter.setColSpan(0, 1, 2);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);

		initializeTableData();

		createInspectionButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				createAndShowInspectionDialog();
			}
		});
	}

	/**
	 * Creates the inspection dialog for adding new inspections
	 */
	private void createAndShowInspectionDialog()
	{
		InspectionTechniqueDialog inspectionDialog = new InspectionTechniqueDialog(this, inspections);
		inspectionDialog.setText(messages.addInspectionTitle());
		inspectionDialog.setAnimationEnabled(true);
		inspectionDialog.center();
		inspectionDialog.show();
	}

	/**
	 * Creates the inspection dialog for editing new inspections.
	 * 
	 * @param gwtInspection
	 */
	private void createAndShowInspectionDialog(GwtInspectionTechnique technique)
	{
		InspectionTechniqueDialog inspectionDialog = new InspectionTechniqueDialog(this, technique, inspections);
		inspectionDialog.setText(messages.editInspectionTitle());
		inspectionDialog.setAnimationEnabled(true);
		inspectionDialog.center();
		inspectionDialog.show();
	}

	/**
	 * Initializes table with the inspection information from the database.
	 */
	private void initializeTableData()
	{
		this.service.getInspectionTechniques(this.projectId, stepStatus, new AsyncCallback<List<GwtInspectionTechnique>>()
		{		
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.generalInspectionError());
			}
			
			public void onSuccess(List<GwtInspectionTechnique> result)
			{
				inspections = result;
				
				for (GwtInspectionTechnique currentInspection : inspections)
				{
					addRow(currentInspection);
				}	
			}
		});
	}

	/**
	 * Removes an inspection row from the table.
	 * 
	 * @param rowToRemove
	 * 
	 * @param inspection
	 *            The inspection to be removed.
	 */
	private void removeInspection(final GwtInspectionTechnique inspectionToRemove, final Integer rowToRemove)
	{
		boolean response = Window.confirm(messages.deleteInspectionConfirmation(inspectionToRemove.getTitle()));
		if (response)
		{
			this.service.removeInspectionTechnique(this.projectId, inspectionToRemove, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException)
					{
						SquareException se = (SquareException) caught;
						switch (se.getType())
						{
							case authorization :
								Window.alert(messages.errorDeleteAuthorization());
								break;
							default :
								Window.alert(messages.generalInspectionRemovalError());
								break;
						}
					}
					else
					{
						Window.alert(messages.generalInspectionRemovalError());
					}
				}
				
				public void onSuccess(Void result)
				{
					inspectionTable.removeRow(rowToRemove);
					inspections.remove(rowToRemove - 1);
					//lastRowClicked = -1;
				}
			});
		}
	}

	private void addRow(final GwtInspectionTechnique newInspection)
	{
		HorizontalPanel linkBar = new HorizontalPanel();
		linkBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		linkBar.setStyleName("flex-link-bar");

		if (this.stepStatus != StepStatus.Complete)
		{
			SquareHyperlink changeInspectionLink = new SquareHyperlink(messages.change());
			changeInspectionLink.addClickHandler(new ClickHandler()
			{	
				public void onClick(ClickEvent event)
				{
					lastRowClicked = inspectionTable.getCellForEvent(event).getRowIndex();
					createAndShowInspectionDialog(newInspection);
				}
			});

			linkBar.add(changeInspectionLink);
			
			SquareHyperlink removeInspectionLink = new SquareHyperlink(messages.remove());
			linkBar.add(removeInspectionLink);

			removeInspectionLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					Integer rowToRemove = inspectionTable.getCellForEvent(event).getRowIndex();
					removeInspection(newInspection, rowToRemove);
				}
			});
		}

		int row = inspectionTable.getRowCount();
		inspectionTable.setWidget(row, 0, new Label(newInspection.getTitle()));
		inspectionTable.setWidget(row, 1, new Label(newInspection.getDescription()));
		inspectionTable.setWidget(row, 2, linkBar);
		inspectionTable.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_RIGHT);
	}
}

