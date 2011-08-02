package edu.cmu.square.client.ui.categorizeRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsServiceAsync;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitRequirementService;
import edu.cmu.square.client.remoteService.step.interfaces.ElicitRequirementServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.categorizeRequirements.CategoryChangedEvent.Action;
import edu.cmu.square.client.ui.core.BasePane;

public class CategorizeRequirementsPane extends BasePane
{
	private int projectID;
	private List<GwtCategory> listOfCategories = new ArrayList<GwtCategory>();
	private List<GwtRequirement> listOfRequirements = new ArrayList<GwtRequirement>();
	private CategoriesInbox categoriesInbox;
	private CategorizeRequirementsTable categorizeRequirementTable;

	private GwtModesType currentMode;
	private int categoryId = -1;
	FlexTable innerLayout = new FlexTable();
	
	final CategorizePaneMessages messages = (CategorizePaneMessages) GWT.create(CategorizePaneMessages.class);


	public CategorizeRequirementsPane(State stateInfo)
		{
			super(stateInfo);
			currentMode = stateInfo.getMode();

			this.projectID = this.getCurrentState().getProjectID();

		
			this.getContent().add(innerLayout);
			//mainLayout.add(innerLayout);
			this.showLoadingStatusBar();
			addDoneButton();
			

			categoriesInbox = new CategoriesInbox();
			categorizeRequirementTable = new CategorizeRequirementsTable();
			categoriesInbox.addHandler(CategoryChangedEvent.getType(), new CategoryChangedEvent.IMyHandler()
				{

					public void onLoad(CategoryChangedEvent event)
					{
						renderCategorizeRequirementsPane();

					}
				});

			categorizeRequirementTable.addHandler(CategoryChangedEvent.getType(), new CategoryChangedEvent.IMyHandler()
				{

					public void onLoad(CategoryChangedEvent event)
					{
						if (event.getCommand().equals(Action.assign))
						{
							categoryId = event.getCategoryId();
							assignRequirementsToCategory(event.getRequirementsToProcess(), event.getCategoryId());

						}
						if (event.getCommand().equals(Action.remove))
						{
							categoryId = event.getCategoryId();
							removeRequirementsFromCategory(event.getRequirementsToProcess(), event.getCategoryId());
						}
						if (event.getCommand().equals(Action.create))
						{
							assignRequirementsToNewCategory(event.getNewCategory(), event.getRequirementsToProcess());

						}

					}
				});

			loadCategories();

		}

	public void loadCategories()
	{

		

		CategorizeRequirementsServiceAsync service1 = GWT.create(CategorizeRequirementsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "categorizeRequirements.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.getCurrentState().getProjectID());

		service1.getCategories(project, new AsyncCallback<List<GwtCategory>>()
			{
				
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingCategoriesRPCAction());

				}

				
				public void onSuccess(List<GwtCategory> result)
				{
					listOfCategories = result;
					loadProjectRequirements();

				}
			});

	}

	public void loadProjectRequirements()
	{

		ElicitRequirementServiceAsync service = GWT.create(ElicitRequirementService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitRequirement.rpc");
		service.getRequirementsFromProject(this.getCurrentState().getProjectID(), new AsyncCallback<List<GwtRequirement>>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingRequirementsRPCAction());

				}
				public void onSuccess(List<GwtRequirement> result)
				{
					listOfRequirements = result;
					renderCategorizeRequirementsPane();
				}

			});
	}

	public void assignRequirementsToCategory(List<GwtRequirement> requirements, final int categoryId)
	{
		this.showStatusBar(messages.assigningStatus());

		ElicitRequirementServiceAsync service = GWT.create(ElicitRequirementService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitRequirement.rpc");
		service.assignRequirementsToCategory(requirements, categoryId, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					// TODO: Handle Authorization Error
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.categorizingRequirementRPCAction());

				}
				public void onSuccess(Void result)
				{

					loadCategories();
				}

			});
	}

	

	public void removeRequirementsFromCategory(final List<GwtRequirement> requirements, int categoryId)
	{

		this.showStatusBar(messages.removingStatus());

		ElicitRequirementServiceAsync service = GWT.create(ElicitRequirementService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "elicitRequirement.rpc");
		service.removeRequirementsFromCategory(requirements, categoryId, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.removingCategoryRPCAction());
				}
				public void onSuccess(Void result)
				{
					loadProjectRequirements();
				}

			});
	}

	/**
	 * Adds the given category to the table and updates the DB appropriately.
	 * 
	 * @param newcategory
	 */
	public void assignRequirementsToNewCategory(final GwtCategory newcategory, final List<GwtRequirement> requirements)
	{
		this.showStatusBar(messages.creatingStatus());
		CategorizeRequirementsServiceAsync service = GWT.create(CategorizeRequirementsService.class);
		GwtProject project = new GwtProject();
		project.setId(projectID);

		service.addCategory(project, newcategory, new AsyncCallback<GwtCategory>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.creatingCategoryRPCAction());
			}
				
			public void onSuccess(GwtCategory result)
			{
				assignRequirementsToCategory(requirements, result.getId());
			}
		});
	}

	private void renderCategorizeRequirementsPane()
	{
		this.hideStatusBar();
		
		categorizeRequirementTable.clearWidget();
		categoriesInbox.clearWidget();
	
		innerLayout.setWidget(0, 0, new Label(messages.category()));
		innerLayout.setWidget(1, 0,categoriesInbox.getInboxWidget(listOfCategories, listOfRequirements, currentMode));
		innerLayout.setWidget(1, 1,categorizeRequirementTable.getRequirementsCategoryWidget(listOfRequirements, listOfCategories, categoriesInbox.getSelectedCategory(), currentMode));
		
		innerLayout.getCellFormatter().setVerticalAlignment(0, 0,HasVerticalAlignment.ALIGN_BOTTOM);
		innerLayout.getCellFormatter().setHorizontalAlignment(0, 0,HasHorizontalAlignment.ALIGN_LEFT);
		innerLayout.getCellFormatter().setVerticalAlignment(1, 0,HasVerticalAlignment.ALIGN_TOP);
		innerLayout.getCellFormatter().setVerticalAlignment(1, 1,HasVerticalAlignment.ALIGN_TOP);
		innerLayout.getCellFormatter().setStyleName(1, 1,"square-Categorize-mainlayout");
	
	

	}
	private void addDoneButton()
	{
		FlexTable buttonPanel = new FlexTable();
		
		Button done =new Button(messages.done());
		done.addStyleName("square-button");
		done.addClickHandler(new ClickHandler(){

			
			public void onClick(ClickEvent event)
			{
				History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
				
			}});
		buttonPanel.setWidget(0,0,new Label(" "));
		buttonPanel.setWidget(1, 0, done);
		buttonPanel.setWidth("810px");
		buttonPanel.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_RIGHT);
		this.getContent().add(buttonPanel);
	}

}
