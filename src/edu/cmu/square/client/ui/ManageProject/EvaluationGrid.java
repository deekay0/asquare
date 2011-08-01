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
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueService;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitationTechniqueServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

/**
 * This is the grid that holds the current evaluation criteria.
 * 
 */
public class EvaluationGrid extends Composite
{
	private ElicitationTechniqueServiceAsync service = GWT.create(ElicitationTechniqueService.class);

	private ManageProjectPaneMessages messages = (ManageProjectPaneMessages) GWT.create(ManageProjectPaneMessages.class);

	private FlexTable evaluationTable = null;
	private GwtProject currentProject;
	private StepStatus stepStatus = StepStatus.NotStarted; // initialized when the grid gets all the techniques
	
	private BasePane caller = null;
	
	private List<GwtEvaluation> evaluationList = new ArrayList<GwtEvaluation>();
	private int lastRowClicked = -1;

	
	
	public EvaluationGrid(GwtProject gwtProject, BasePane caller)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitationTechniques.rpc");

		this.caller = caller;
		this.currentProject = gwtProject;
		
		initializeGrid();
		initWidget(this.evaluationTable);
	}

	
	/**
	 * Adds an evaluation row to the table.
	 * 
	 * @param newEvaluation
	 */
	public void addEvaluationToTable(final GwtEvaluation newEvaluation)
	{
		this.service.addEvaluation(this.currentProject, newEvaluation, new AsyncCallback<GwtEvaluation>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case duplicateName :
							Window.alert(messages.duplicateEvaluation());
							break;
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalEvaluationAddError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalEvaluationAddError());
				}
			}

			
			public void onSuccess(GwtEvaluation result)
			{
				addRow(result);
				caller.yellowFadeHandler.add(evaluationTable, evaluationTable.getRowCount() - 1);
				evaluationList.add(result);
			}
		});
	}

	/**
	 * Updates the evaluation table with the given evaluation.
	 * 
	 * @param evaluation
	 * @param rowToUpdate
	 */
	public void updateEvaluationInTable(final GwtEvaluation evaluation)
	{
		this.service.updateEvaluation(evaluation, currentProject, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case duplicateName :
							Window.alert(messages.duplicateEvaluation());
							break;
						case authorization :
							Window.alert(messages.errorAuthorization());
							break;
						default :
							Window.alert(messages.generalEvaluationUpdateError());
							break;
					}
				}
				else
				{
					Window.alert(messages.generalEvaluationUpdateError());
				}
			}

			
			public void onSuccess(Void result)
			{
				evaluationTable.setWidget(lastRowClicked, 0, new Label(evaluation.getTitle()));
				evaluationTable.setWidget(lastRowClicked, 1, new Label(evaluation.getDescription()));
				caller.yellowFadeHandler.add(evaluationTable, lastRowClicked);
				
				evaluationList.set(lastRowClicked - 1, evaluation);
				lastRowClicked = -1;
			}
		});
	}

	// ------------------------------------------------------
	// -------------- Private stuff -------------------------
	// ------------------------------------------------------

	/**
	 * Initializes the <code>evaluationTable</code>. Sets up layouts that are
	 * specific to this grid.
	 */
	private void initializeGrid()
	{
		this.evaluationTable = new FlexTable();
		this.evaluationTable.setStyleName("square-flex");
		this.evaluationTable.setWidth("100%");
		this.evaluationTable.setCellSpacing(0);

		Button createEvaluationButton = new Button(messages.createEvaluationButton());
		evaluationTable.setWidget(0, 0, createEvaluationButton);
		evaluationTable.setWidget(0, 1, new Label(messages.description()));

		FlexCellFormatter formatter = evaluationTable.getFlexCellFormatter();
		
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		formatter.setStyleName(0, 2, "square-TableHeader");
		
		this.evaluationTable.getCellFormatter().setHeight(0, 0, "30px");

		formatter.setWidth(0, 0, "140px");
		formatter.setColSpan(0, 1, 2);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);

		initializeTableData();

		createEvaluationButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				createAndShowEvaluationDialog();
			}
		});
	}

	/**
	 * Creates the evaluation dialog for adding new evaluations
	 */
	private void createAndShowEvaluationDialog()
	{
		ElicitationEvaluationDialog evaluationDialog = new ElicitationEvaluationDialog(this, evaluationList);
		evaluationDialog.setText(messages.addEvaluationTitle());
		evaluationDialog.setAnimationEnabled(true);
		evaluationDialog.center();
		evaluationDialog.show();
	}

	/**
	 * Creates the evaluation dialog for editing new evaluations.
	 * 
	 * @param gwtEvaluation
	 */
	private void createAndShowEvaluationDialog(GwtEvaluation gwtEvaluation)
	{
		ElicitationEvaluationDialog evaluationDialog = new ElicitationEvaluationDialog(this, gwtEvaluation, evaluationList);
		evaluationDialog.setText(messages.editEvaluationTitle());
		evaluationDialog.setAnimationEnabled(true);
		evaluationDialog.center();
		evaluationDialog.show();
	}

	/**
	 * Initializes table with the evaluation information from the database.
	 */
	private void initializeTableData()
	{
		this.service.getEvaluations(currentProject, stepStatus, new AsyncCallback<List<GwtEvaluation>>()
		{		
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.evaluationError());
			}

			public void onSuccess(List<GwtEvaluation> result)
			{
				evaluationList = result;
				
				for (GwtEvaluation currentEvaluation : result)
				{
					addRow(currentEvaluation);
				}
			}
		});
	}



	private void removeEvaluation(final GwtEvaluation evaluationToRemove, final Integer rowToRemove)
	{
		boolean response = Window.confirm(messages.deleteEvaluationConfirmation(evaluationToRemove.getTitle()));
		if (response)
		{
			this.service.removeEvaluation(evaluationToRemove, new AsyncCallback<Void>()
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
								Window.alert(messages.generalEvaluationRemovalError());
								break;
						}
					}
					else
					{
						Window.alert(messages.generalEvaluationRemovalError());
					}
				}

				
				public void onSuccess(Void result)
				{
					evaluationTable.removeRow(rowToRemove);
					evaluationList.remove(lastRowClicked -1);
					lastRowClicked = -1;
				}
			});
		}
	}

	private void addRow(final GwtEvaluation newEvaluation)
	{
		HorizontalPanel linkBar = new HorizontalPanel();
		linkBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		linkBar.setStyleName("flex-link-bar");

		if (this.stepStatus != StepStatus.Complete)
		{
			SquareHyperlink changeEvaluationLink = new SquareHyperlink(messages.change());
			changeEvaluationLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = evaluationTable.getCellForEvent(event).getRowIndex();
					createAndShowEvaluationDialog(newEvaluation);
				}
			});

			linkBar.add(changeEvaluationLink);

			SquareHyperlink removeEvaluationLink = new SquareHyperlink(messages.remove());
			linkBar.add(removeEvaluationLink);

			removeEvaluationLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = evaluationTable.getCellForEvent(event).getRowIndex();
					removeEvaluation(newEvaluation, lastRowClicked);
					
				}
			});
		}
		
		int row = evaluationTable.getRowCount();
		evaluationTable.setWidget(row, 0, new Label(newEvaluation.getTitle()));
		evaluationTable.setWidget(row, 1, new Label(newEvaluation.getDescription()));
		evaluationTable.setWidget(row, 2, linkBar);
		evaluationTable.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_RIGHT);
	}
}
