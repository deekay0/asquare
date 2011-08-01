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
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueService;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

/**
 * 
 * Purpose: This is the grid used within Manage Project for holding techniques
 * 
 */
public class TechniqueGrid extends Composite
{
	private FlexTable techniqueTable = new FlexTable();
	private GwtProject currentProject = null;
	private StepStatus stepStatus =  StepStatus.NotStarted;
	
	private BasePane caller = null;

	private List<GwtTechnique> listOfTechniques = new ArrayList<GwtTechnique>();
	int lastRowClicked = -1;
	
	private ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	private ElicitationTechniqueServiceAsync service = GWT.create(ElicitationTechniqueService.class);

	/**
	 * Creates a new TechniqueGrid and prepares it for use.
	 * 
	 * @param project
	 *            The current project to which this Techinque belongs
	 */
	public TechniqueGrid(GwtProject project, BasePane caller)
	{
		this.currentProject = project;
		this.caller = caller;

		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitationTechniques.rpc");

		this.initGrid();

		this.initWidget(this.techniqueTable);
	}

	/**
	 * Adds the given technqiue to the table and updates the DB appropriately.
	 * 
	 * @param newTechnique
	 */
	public void addTechniqueToTable(final GwtTechnique newTechnique)
	{
		this.service.addTechnique(this.currentProject, newTechnique, new AsyncCallback<GwtTechnique>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case constraintViolated :
							Window.alert(messages.techniqueSelectedError());
							break;
						case duplicateName :
							Window.alert(messages.duplicateTechnique());
							break;
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalTechniqueAddError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalTechniqueAddError());
				}
			}

			
			public void onSuccess(GwtTechnique result)
			{
				addRow(result);
			}
		});
	}

	/**
	 * Updates the given technique in the table and updates the DB
	 * appropriately.
	 * 
	 * @param technique
	 * @param event
	 */
	public void updateTechniqueInTable(final GwtTechnique technique)
	{
		this.service.updateTechnique(technique, currentProject, new AsyncCallback<Void>()
		{
			
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case constraintViolated :
							Window.alert(messages.techniqueSelectedError());
							break;
						case duplicateName :
							Window.alert(messages.duplicateTechnique());
							break;
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalTechniqueAddError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalTechniqueUpdateError());
				}
			}

			
			public void onSuccess(Void result)
			{
				techniqueTable.setWidget(lastRowClicked, 0, new Label(technique.getTitle()));
				techniqueTable.setWidget(lastRowClicked, 1, new Label(technique.getDescription()));
				caller.yellowFadeHandler.add(techniqueTable, lastRowClicked);
				
				listOfTechniques.set(lastRowClicked, technique);
			}
		});
	}

	// ------------------ Private Stuff -------------------------

	/**
	 * Initializes the technique grid, putting the button at the top and
	 * inputting the initial set of techniques from the database.
	 */
	private void initGrid()
	{
		FlexCellFormatter formatter = this.techniqueTable.getFlexCellFormatter();
		this.techniqueTable.setWidth("100%");
		this.techniqueTable.setCellSpacing(0);
		this.techniqueTable.setStyleName("square-flex");

		// populate the flex table with default values
		Button createUserButton = new Button(messages.createTechniqueButton());
		createUserButton.addClickHandler(new ClickHandler()
		{
			
			public void onClick(ClickEvent event)
			{
				createAndShowTechniqueDialog();
			}
		});

		this.techniqueTable.setWidget(0, 0, createUserButton);
		this.techniqueTable.setWidget(0, 1, new Label(this.messages.description()));
		formatter.setColSpan(0, 1, 2);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setWidth(0, 0, "140px");
		
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		formatter.setStyleName(0, 2, "square-TableHeader");
		
		this.techniqueTable.getCellFormatter().setHeight(0, 0, "30px");

		// Get the users from the database and list them in the table
		getTechniquesForProject();
	}

	
	private void addRow(final GwtTechnique newTechnique)
	{
		HorizontalPanel links = new HorizontalPanel();
		links.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		links.setStyleName("flex-link-bar");

		final int numberOfRows = this.techniqueTable.getRowCount();

		this.techniqueTable.getFlexCellFormatter().setHorizontalAlignment(numberOfRows, 2, HasHorizontalAlignment.ALIGN_RIGHT);

		this.techniqueTable.setWidget(numberOfRows, 0, new Label(newTechnique.getTitle()));
		this.techniqueTable.setWidget(numberOfRows, 1, new Label(newTechnique.getDescription()));
		this.techniqueTable.setWidget(numberOfRows, 2, links);
		this.techniqueTable.getFlexCellFormatter().setHorizontalAlignment(numberOfRows, 2, HasHorizontalAlignment.ALIGN_RIGHT);

		if (this.stepStatus != StepStatus.Complete)
		{
			SquareHyperlink changeTechniqueLink = new SquareHyperlink(messages.change());
			changeTechniqueLink.addClickHandler(new ClickHandler()
			{			
				public void onClick(ClickEvent event)
				{
					lastRowClicked = techniqueTable.getCellForEvent(event).getRowIndex();
					createAndShowTechniqueDialog(newTechnique);
				}
			});

			links.add(changeTechniqueLink);

			SquareHyperlink removeTechniqueLink = new SquareHyperlink(messages.remove());
			links.add(removeTechniqueLink);

			removeTechniqueLink.addClickHandler(new ClickHandler()
			{
				
				public void onClick(ClickEvent event)
				{
					lastRowClicked = techniqueTable.getCellForEvent(event).getRowIndex();
					removeTechnique(newTechnique, lastRowClicked);
				}
			});
		}
		
		caller.yellowFadeHandler.add(techniqueTable, numberOfRows);
	}

	/**
	 * Shows the TechniqueDialog, used for getting information from the user to
	 * add a new technique.
	 */
	private void createAndShowTechniqueDialog()
	{
		final ElicitationTechniqueDialog dialogBox = new ElicitationTechniqueDialog(this, listOfTechniques);
		dialogBox.setText(messages.addTechniqueTitle());
		dialogBox.setAnimationEnabled(true);

		dialogBox.center();
		dialogBox.show();
	}

	/**
	 * Shows the TechniqueDialog, used for editing a technique.
	 * 
	 * @param technique
	 *            The technique to edit.
	 */
	private void createAndShowTechniqueDialog(GwtTechnique technique)
	{
		final ElicitationTechniqueDialog dialogBox = new ElicitationTechniqueDialog(this, technique, listOfTechniques);
		dialogBox.setText(messages.editTechniqueTitle());
		dialogBox.setAnimationEnabled(true);

		dialogBox.center();
		dialogBox.show();
	}

	/***
	 * Retrieves all the techniques currently in the DB for this project and
	 * puts them in the grid
	 */
	private void getTechniquesForProject()
	{
		this.service.getTechniques(this.currentProject, this.stepStatus, new AsyncCallback<List<GwtTechnique>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.techniqueError());
			}

			public void onSuccess(List<GwtTechnique> result)
			{
				listOfTechniques = result;
				for (GwtTechnique technique : result)
				{
					addRow(technique);
				}
			}
		});
	}

	/**
	 * Deletes the technique from the DB and if it works, the UI.
	 * 
	 * @param techniqueToRemove
	 * @param clickedRow
	 */
	private void removeTechnique(final GwtTechnique techniqueToRemove, final Integer clickedRow)
	{
		boolean response = Window.confirm(messages.deleteConfirmation(techniqueToRemove.getTitle()));
		if (response)
		{
			this.service.removeTechnique(techniqueToRemove, currentProject.getId(), new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException)
					{
						SquareException se = (SquareException) caught;
						switch (se.getType())
						{
							case constraintViolated :
								Window.alert(messages.techniqueSelectedError());
								break;
							case authorization :
								Window.alert(messages.errorDeleteAuthorization());
								break;
							case onlyOneTechnique :
								Window.alert(messages.errorDeleteLastTechnique());
								break;
							default :
								Window.alert(messages.generalTechniqueRemovalError());
								break;
						}
					}
					else
					{
						Window.alert(messages.generalTechniqueRemovalError());
					}
				}

				
				public void onSuccess(Void result)
				{
					techniqueTable.removeRow(clickedRow);
					listOfTechniques.remove(clickedRow - 1);
				}
			});
		}
	}

}
