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
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.StepStatus;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.CategorizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

/**
 * 
 * Purpose: This is the grid used within Manage Project for holding categories
 *
 */
public class CategoryGrid extends Composite
{
	private FlexTable categoryTable = new FlexTable();
	private GwtProject currentProject = null;
	private StepStatus stepStatus = StepStatus.NotStarted; 
	private BasePane caller = null;
	
	private List<GwtCategory> categoryList = new ArrayList<GwtCategory>();
	private int currentCategoryID = -1;
	private int lastRowClicked = -1;
	
	private ManageProjectPaneMessages messages = (ManageProjectPaneMessages)GWT.create(ManageProjectPaneMessages.class);
	
	private CategorizeRequirementsServiceAsync service = GWT.create(CategorizeRequirementsService.class);

	/**
	 * Creates a new categoryGrid and prepares it for use.
	 * @param project The current project to which this category belongs
	 */
	public CategoryGrid(GwtProject project, BasePane caller)
	{	
		this.currentProject = project;
		this.caller = caller;
		
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "categorizeRequirements.rpc");
		
		//Get the users from the database and list them in the table
		getCategoriesForProject();
		
		this.initWidget(this.categoryTable);
	}
	
	/***
	 * Retrieves all the category currently in the DB for this project and puts them in the grid
	 */
	private void getCategoriesForProject()
	{
		this.service.getCategories(this.currentProject, new AsyncCallback<List<GwtCategory>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(messages.categoryRetrievalError());
			}

			public void onSuccess(List<GwtCategory> result)
			{
				categoryList = result;
				initGrid();
			}
		});
	}
	
	
	
	
	/**
	 * Adds the given category to the table and updates the DB appropriately.
	 * @param newcategory
	 */
	public void addCategoryToTable(final GwtCategory newcategory)
	{
		caller.showStatusBar(messages.updating());
		this.service.addCategory(this.currentProject, newcategory, new AsyncCallback<GwtCategory>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch(se.getType())
					{
						case authorization:
							Window.alert(messages.errorAuthorization());
							break;
						default:
							Window.alert(messages.generalCategoryAddError());	
							break;
					}
				}
				else
				{
					Window.alert(messages.generalCategoryAddError());
				}
				
				caller.hideStatusBar();
			}

			
			public void onSuccess(GwtCategory result)
			{
				addRow(result);
				caller.yellowFadeHandler.add(categoryTable, categoryTable.getRowCount() - 1);
				categoryList.add(result);
				
				caller.hideStatusBar();
			}
		});
	}


	/**
	 * Updates the given category in the table and updates the DB appropriately.
	 * @param category
	 * @param event 
	 */
	public void updateCategoryInTable(final GwtCategory category)
	{
		caller.showStatusBar(messages.updating());
		this.service.updateCategory(this.currentProject,category, new AsyncCallback<Void>()
		{			
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch(se.getType())
					{
						case authorization:
							Window.alert(messages.errorAuthorization());
							break;
						default:
							Window.alert(messages.generalCategoryUpdateError());
							break;
					}	
				}
				else
				{
					Window.alert(messages.generalCategoryUpdateError());
				}
				
				caller.hideStatusBar();
			}

			public void onSuccess(Void result)
			{
				categoryTable.setWidget(lastRowClicked, 0, new Label(category.getCategoryName()));
				caller.yellowFadeHandler.add(categoryTable, lastRowClicked);
				
				categoryList.set(lastRowClicked - 1, category);
				lastRowClicked = -1;
				
				caller.hideStatusBar();
			}
		});
	}
	
	/**
	 * Updates the given category in the table and updates the DB appropriately.
	 * @param category
	 * @param event 
	 */
	public void mergeCategoryInTable(int categoryFromId, int categoryToId)
	{
		caller.showStatusBar(messages.updating());
		this.service.mergeCategories(categoryFromId, categoryToId, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException)
				{
					SquareException se = (SquareException) caught;
					switch (se.getType())
					{
						case authorization:
							Window.alert(messages.errorAuthorization());
							break;
						default:
							Window.alert(messages.generalCategoryUpdateError());
							break;
					}	
				}
				else
				{
					Window.alert(messages.generalCategoryUpdateError());
				}
				
				caller.hideStatusBar();
			}

			
			public void onSuccess(Void result)
			{
				categoryTable.removeRow(lastRowClicked);
				categoryList.remove(lastRowClicked -1);
				lastRowClicked = -1;
				
				caller.hideStatusBar();
			}
		});
	}
	
	// ------------------ Private Stuff -------------------------

	

	
	/**
	 * Deletes the category from the DB and if it works, the UI.
	 * @param categoryToRemove
	 * @param clickedRow 
	 */
	private void removeCategory(final GwtCategory categoryToRemove, final int rowToRemove)
	{
		boolean response = Window.confirm(messages.deleteConfirmation(categoryToRemove.getCategoryName()));
		if (response)
		{
			caller.showStatusBar(messages.removing());
			this.service.removeCategory(categoryToRemove, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException)
					{
						SquareException se = (SquareException) caught;
						switch(se.getType())
						{
							case authorization:
								Window.alert(messages.errorDeleteAuthorization());
								break;

							default:
								Window.alert(messages.generalCategoryRemovalError());
								break;
						}
					}
					else
					{
						Window.alert(messages.generalCategoryRemovalError());
					}
					
					caller.hideStatusBar();
				}

				public void onSuccess(Void result)
				{				
					categoryTable.removeRow(rowToRemove);
					categoryList.remove(lastRowClicked -1);
					lastRowClicked = -1;
					
					caller.hideStatusBar();
				}
			});
		}
	}


	
	
	
	/**
	 * Initializes the category grid, putting the button at the top and inputting the initial
	 * set of categories from the database.
	 */
	private void initGrid()
	{
		this.categoryTable.clear();
		
		FlexCellFormatter formatter = this.categoryTable.getFlexCellFormatter();
		this.categoryTable.setWidth("100%");
		this.categoryTable.setCellSpacing(0);
		this.categoryTable.setStyleName("square-flex");
		
		//populate the flex table with default values
		Button createUserButton = new Button(messages.createCategoryButton());
		createUserButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				createAndShowcategoryDialog();			
			}	
		});
		
		formatter.setStyleName(0, 0, "square-TableHeader");
		formatter.setStyleName(0, 1, "square-TableHeader");
		
		this.categoryTable.setWidget(0, 0, createUserButton);
		formatter.setColSpan(0, 1, 2);
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setWidth(0, 0, "180px");
		
		for(final GwtCategory category: this.categoryList)
		{
			addRow(category);
		}
	}
	
	
	
	
	private void addRow(final GwtCategory newCategory)
	{
		HorizontalPanel links = new HorizontalPanel();
		links.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		links.setStyleName("flex-link-bar");
		
		int row = categoryTable.getRowCount();
        this.categoryTable.getFlexCellFormatter().setHorizontalAlignment(row, 2, HasHorizontalAlignment.ALIGN_RIGHT);
		
		this.categoryTable.setWidget(row, 0, new Label(newCategory.getCategoryName()));
		this.categoryTable.setWidget(row, 1, new Label(" "));
		this.categoryTable.setWidget(row, 2, links);
	
		if (this.stepStatus != StepStatus.Complete)
		{
			DataElementSquareHyperlink changecategoryLink = new DataElementSquareHyperlink(messages.change());
			changecategoryLink.setCategoryId(newCategory.getId());
			changecategoryLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = categoryTable.getCellForEvent(event).getRowIndex();
					currentCategoryID = newCategory.getId();
					createAndShowcategoryDialog(newCategory);	
				}
			});
		
			links.add(changecategoryLink);
			
			DataElementSquareHyperlink removecategoryLink = new DataElementSquareHyperlink(messages.remove());
			removecategoryLink.setCategoryId(newCategory.getId());
			links.add(removecategoryLink);

			removecategoryLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = categoryTable.getCellForEvent(event).getRowIndex();
					currentCategoryID = newCategory.getId();
					removeCategory(newCategory, lastRowClicked);	
				}
			});	
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Shows the categoryDialog, used for getting information from the user to add a new category.
	 */
	private void createAndShowcategoryDialog()
	{
		final CreateCategoryDialog dialogBox = new CreateCategoryDialog(this);
		dialogBox.setText(messages.createCategoryButton());
	    dialogBox.setAnimationEnabled(true);

	    dialogBox.center();
	    dialogBox.show();
	}
	
	
	/**
	 * Shows the categoryDialog, used for editing a category.
	 * @param category The category to edit.
	 */
	private void createAndShowcategoryDialog(GwtCategory category)
	{
		final EditOrMergeCategoryDialog dialogBox = new EditOrMergeCategoryDialog(this,category);
	
		dialogBox.setText(messages.editCategoryTitle());
	    dialogBox.setAnimationEnabled(true);

	    dialogBox.center();
	    dialogBox.show();
	}
	
	

	
	




	public void setCategoryList(List<GwtCategory> categoryList)
	{
		this.categoryList = categoryList;
	}



	public List<GwtCategory> getCategoryList()
	{
		return categoryList;
	}



	public void setCurrentCategoryID(int currentCategoryID)
	{
		this.currentCategoryID = currentCategoryID;
	}



	public int getCurrentCategoryID()
	{
		return currentCategoryID;
	}
	
	class DataElementSquareHyperlink extends SquareHyperlink
	{

		public DataElementSquareHyperlink(String text)
			{
				super(text);
				
			}
		
		public void setCategoryId(int categoryId)
		{
			this.categoryId = categoryId;
		}

		public int getCategoryId()
		{
			return categoryId;
		}

		private int categoryId;
		
	}
	
	
}
