package edu.cmu.square.client.ui.assetsAndGoals;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

/**
 * This class is used to render the page for identifying/updating assets and goals
 */
public class IdentifyAssetsAndGoalsSubGoalsPane extends BasePane
{

	private IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
	private GwtBusinessGoal businessGoal;
	private FlexTable subGoalsTable;
	private FlexTable assetsTable;
	private IdentifyAssetsAndGoalsPaneMessages messages = (IdentifyAssetsAndGoalsPaneMessages)GWT.create(IdentifyAssetsAndGoalsPaneMessages.class);

	public IdentifyAssetsAndGoalsSubGoalsPane(State stateInfo) 
	{
		super(stateInfo);
		
		VerticalPanel layout = new VerticalPanel();
		layout.setWidth("100%");
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.setSpacing(5);

		//Get up to date data first thing
		this.updateBusinessGoalInformation(new SubGoalsTableInitializer());
		this.updateBusinessGoalInformation(new AssetTableInitializer());
		
		//Then add the data to our flex tables
		this.subGoalsTable = new FlexTable();
		layout.add(addToPanel(messages.securitySubGoalsLabel(), this.subGoalsTable, false));

		//And do the same thing for the assets
		this.assetsTable = new FlexTable();
		layout.add(addToPanel(messages.assetsLabel(), this.assetsTable, true));
		layout.add(NavigationButtons(layout));

		this.getContent().clear();
		this.getContent().add(layout);
	}
	/************************************************************************/
	/*************** UI Rendering functions ********************************/ 
	/***********************************************************************/
	



	/**
	 * Configures a flex table and corresponding titles to add to the overall layout. 
	 */
	private VerticalPanel addToPanel(String sectionText, final FlexTable table, final boolean isAsset)
	{
		VerticalPanel layout = new VerticalPanel();
		layout.setWidth("60%");
		layout.setStyleName("asset-step-layout");
		layout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		table.setWidth("100%");
		FlexCellFormatter formatter = table.getFlexCellFormatter();
		formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
			
		//Adding the section headings for the table
		Label sectionLabel = new Label(sectionText);
		sectionLabel.setStyleName("square-title");
		table.setWidget(0, 0, sectionLabel);
		

		if (table.getRowCount() == 0)
		{
			this.addRowToTable("", null, -1, table, isAsset);  // initially blank
		}
		
		Button newRowButton = null;
		if (isAsset)
		{
			newRowButton = new Button(messages.addAssetButton());
		}
		else
		{
			newRowButton = new Button(messages.addSecuritySubGoal());
		}

		newRowButton.setWidth("180px");
		
		newRowButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				addRowToTable("", null, new Integer(-1), table, isAsset);
			}
		});
		
		layout.add(table);
		layout.add(newRowButton);
		
		return layout;
	}
	

	
	// ------------------------------------------------------
	//                 Business Goal Methods 
	// ------------------------------------------------------

	/**
	 * Update the Business goal data structure from the database
	 * @param populateSubGoalsTable
	 */
	private void updateBusinessGoalInformation(final Command command)
	{   this.showLoadingStatusBar();
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.loadBusinessGoalInfo(getCurrentState().getProjectID(), new AsyncCallback<GwtBusinessGoal>()
		{
			
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			
			public void onSuccess(GwtBusinessGoal result)
			{
				
				businessGoal = result;
				if (command != null)
				{
					command.execute();
				}
			}
		});
	}
	
	
	
	
	// ------------------------------------------------------
	//                 Assets Methods 
	// ------------------------------------------------------

	
	/**
	 * Commit a new asset to the database
	 * 
	 * @param asset
	 *            the new asset
	 * @param tableId
	 *            the row number in the flex table
	 * @param textBox
	 *            the text box itself
	 */
	private void addAssetToDatabase(final GwtAsset asset, final int tableId, final ScrollBarTextBox textBox)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.addAsset(getCurrentState().getProjectID(), asset, new AsyncCallback<Integer>()
		{
			
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException) {
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.errorAssetAuthorization());
						break;
				
					default:
						Window.alert(messages.errorAddingAsset());
						break;
					}

				} else {
					Window.alert(messages.errorAddingAsset());
				}
			}

			
			public void onSuccess(Integer result)
			{
				textBox.setKey(result.toString());
			}
		});
	}

	
	/**
	 * Adds and asset to the asset table and sets up all the click handlers for the given links.
	 * @param assetText
	 * @param assetLink
	 */
	private void addAssetToTable(final ScrollBarTextBox assetText, SquareHyperlink assetLink)
	{
		
		// add the click handler for removing the link from the textbox.
		assetLink.addClickHandler(new ClickHandler()
		{
			boolean commitChanges = false;
			
			public void onClick(ClickEvent event)
			{
				if(assetText.getText().toString().length()!=0)
				{
					boolean isAssetToBeDeleted = Window.confirm(messages.confirmAssetDelete());
					if(isAssetToBeDeleted)
					{
						commitChanges = true;
					}	
				}
				else
				{
					commitChanges = true;
				}	
				if(commitChanges)
				{
					// this should also delete the asset from the DB.
					Cell cellForEvent = assetsTable.getCellForEvent(event);
					int row = cellForEvent.getRowIndex();
					removeRowInAssetsTable(assetsTable, row, assetText.getKey(), assetText);
				}
			}
		});

		assetText.addClickHandler(new ClickHandler()
		{
			
			public void onClick(ClickEvent event)
			{
				Cell cellForEvent = assetsTable.getCellForEvent((ClickEvent) event);
				int row = cellForEvent.getRowIndex();
				assetText.setName(row + "");
			}
		});

		// Add the blur handler to save the information from the textbox to the
		// DB
		assetText.addBlurHandler(new BlurHandler()
		{
			public void onBlur(BlurEvent event)
			{
				// don't save anything if the textbox is empty
				if (assetText.getText().equals(""))
				{
					return;
				}
				
				int index = Integer.parseInt(assetText.getName());

				GwtAsset newAsset = new GwtAsset();
				newAsset.setDescription(assetText.getText());

				if (assetText.getKey() == null)
				{
					addAssetToDatabase(newAsset, index, assetText);
				}
				else
				{
					newAsset.setId(Integer.parseInt(assetText.getKey()));
					updateAssetInDatabase(newAsset, index, Integer.parseInt(assetText.getKey()), assetText);
				}
			}
		});
	}

	
	/**
	 * Update the asset in the database
	 * 
	 * @param asset
	 *            the modified asset
	 * @param tableId
	 *            the current row number in the flex table
	 * @param primaryKey
	 *            the primary key of the asset to be modified
	 * @param textBox
	 *            contains the description of the asset
	 */
	private void updateAssetInDatabase(final GwtAsset asset, final int tableId, final int primaryKey, final ScrollBarTextBox textBox)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.updateAsset(this.getCurrentState().getProjectID(), asset, new AsyncCallback<Void>()
		{
			
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException) {
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.errorAssetAuthorization());
						break;
				
					default:
						Window.alert(messages.errorUpdatingAsset());
						break;
					}

				} else {
					Window.alert(messages.errorUpdatingAsset());
				}
			}

			
			public void onSuccess(Void result)
			{
				// update the object with the most up to date stuff from
				// the server.
					
			}
		});
	}
	
	private void removeRowInAssetsTable(FlexTable table, int id, String keyString, final ScrollBarTextBox textBox)
	{
		table.removeRow(id);

		if (keyString == null) // the asset was not saved in the db in the first place
		{
			return;
		}
		
		int primaryKey;
		try
		{
			primaryKey = Integer.parseInt(keyString);
		}
		catch (Exception e)
		{
			return;
		}

		// remove the item from the id mapping.
		GwtAsset assetToBeDeleted = new GwtAsset();
		assetToBeDeleted.setId(primaryKey);

		// Get the asset from the mapping and make the asynch call to the DB to
		// remove the asset permanently
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.removeAsset(this.getCurrentState().getProjectID(), assetToBeDeleted, new AsyncCallback<Void>()
		{
			
			public void onFailure(Throwable caught) 
			{
				if (caught instanceof SquareException) {
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.errorAssetAuthorization());
						break;
				
					default:
						Window.alert(messages.errorRemovingAsset());
						break;
					}

				} else {
					Window.alert(messages.errorRemovingAsset());
				}
			}

			
			public void onSuccess(Void result)
			{
			
			}

		});
	}
	
	
	class AssetTableInitializer implements Command
	{
		
		public void execute()
		{
			List<GwtAsset> assetInDB = businessGoal.getAssets();
			for (int i = 0; i < assetInDB.size(); i++)
			{
				addRowToTable(assetInDB.get(i).getDescription(), assetInDB.get(i).getId().toString(), -1, assetsTable, true);
			}
		}
	}
	
	// ------------------------------------------------------
	//                 Sub Goal Methods 
	// ------------------------------------------------------

	
	/**
	 * Command to populate sub-goals flex-table with values from the database
	 */
	private class SubGoalsTableInitializer implements Command
	{
		
		public void execute()
		{
			hideStatusBar();
			List<GwtSubGoal> subGoalInDB = businessGoal.getSubGoals();
			for (int i = 0; i < subGoalInDB.size(); i++)
			{
				addRowToTable(subGoalInDB.get(i).getDescription(), subGoalInDB.get(i).getId().toString(), subGoalInDB.get(i).getPriority(), subGoalsTable, false);
			}
		}
	}
	
	/**
	 * Adds and goal to the sub goals table and sets up all the click handlers for the given links.
	 * @param assetText
	 * @param assetLink
	 */
	private void addGoalToTable(final ScrollBarTextBox userInput, SquareHyperlink subGoalLink)
	{
		// add the click handler for removing the link from the textbox.			
		subGoalLink.addClickHandler(new ClickHandler()
		{
			boolean commitChanges = false;
			
			public void onClick(ClickEvent event)
			{
				if(userInput.getText().toString().length()!=0)
				{
					boolean isSubGoalToBeDeleted = Window.confirm(messages.confirmSubGoalDelete());
					if(isSubGoalToBeDeleted)
					{
						commitChanges = true;
					}	
				}
				else
				{
					commitChanges = true;
				}
				if(commitChanges)
				{
					Cell cellForEvent = subGoalsTable.getCellForEvent(event);
					int row = cellForEvent.getRowIndex();
					removeRowInSubGoalsTable(subGoalsTable, row, userInput.getKey(), userInput);
				}
			}
		});

		userInput.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Cell cellForEvent = subGoalsTable.getCellForEvent((ClickEvent) event);
				int row = cellForEvent.getRowIndex();
				userInput.setName(row + "");
			}
		});

		// Add the blur handler to save the information from the textbox to the DB
		userInput.addBlurHandler(new BlurHandler()
		{
			public void onBlur(BlurEvent event)
			{
				// don't save anything if the textbox is empty
				if (userInput.getText().equals(""))
				{
					return;
				}
				
				int index = Integer.parseInt(userInput.getName());

				GwtSubGoal newSubGoal = new GwtSubGoal();
				newSubGoal.setDescription(userInput.getText());

				if (userInput.getKey() == null)
				{
					newSubGoal.setPriority(new Integer(-1));
					addSubGoalToDatabase(newSubGoal, index, userInput);
				}
				else
				{
					newSubGoal.setId(Integer.parseInt(userInput.getKey()));
					newSubGoal.setPriority(userInput.getPriority());
					updateSubGoalInDatabase(newSubGoal, index, Integer.parseInt(userInput.getKey()), userInput);
				}
			}
		});
	}

	
	
	/**
	 * Commit a new sub goal to the database
	 * 
	 * @param subGoal
	 *            the new sub goal
	 * @param tableId
	 *            the row number in the flex table
	 * @param textBox
	 *            the text box itself
	 */
	private void addSubGoalToDatabase(final GwtSubGoal subGoal, final int tableId, final ScrollBarTextBox textBox)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.addSubGoal(getCurrentState().getProjectID(), subGoal, new AsyncCallback<Integer>()
		{
			
			public void onFailure(Throwable caught)
			{
				if (caught instanceof SquareException) {
					SquareException se = (SquareException) caught;
					switch (se.getType()) {
					case authorization:
						Window.alert(messages.errorAuthorization());
						break;
				
					default:
						Window.alert(messages.errorAddingSubgoal());
						break;
					}

				} else {
					Window.alert(messages.errorAddingSubgoal());
				}
			}

			
			public void onSuccess(Integer result)
			{
				textBox.setKey(result.toString());
			}
		});
	}

	
	/**
	 * Update the sub goal in the database
	 * 
	 * @param subGoal
	 *            the modified sub goal
	 * @param tableId
	 *            the current row number in the flex table
	 * @param primaryKey
	 *            the primary key of the sub goal to be modified
	 * @param textBox
	 *            contains the description of the sub goal
	 */
	private void updateSubGoalInDatabase(final GwtSubGoal subGoal, final int tableId, final int primaryKey, final ScrollBarTextBox textBox)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.updateSubGoal(this.getCurrentState().getProjectID(), subGoal, new AsyncCallback<Void>()
		{
			
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			
			public void onSuccess(Void result)
			{
				// update the object with the most up to date stuff from
				// the server.
			}
		});
	}
	
	
	private void removeRowInSubGoalsTable(FlexTable table, int id, String keyString, final ScrollBarTextBox textBox)
	{
		table.removeRow(id);

		if (keyString == null) // the sub goal was not saved in the db in the first place
		{
			return;
		}
		
		int primaryKey;
		try
		{
			primaryKey = Integer.parseInt(keyString);
		}
		catch (Exception e)
		{
			return;
		}

		// remove the item from the id mapping.
		GwtSubGoal goalToBeDeleted = new GwtSubGoal();
		goalToBeDeleted.setId(primaryKey);
		goalToBeDeleted.setPriority(-1);

		// Get the asset from the mapping and make the asynchronous call to the
		// DB to remove the asset permanently
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");

		service.removeSubGoal(this.getCurrentState().getProjectID(), goalToBeDeleted, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(Void result)
			{
				
			}
		});
	}

	
	
	// ------------------------------------------------------
	//            Shared Methods and inner classes 
	// ------------------------------------------------------
	
	
	/**
	 * Add a new row to the sub goals table
	 * @param integer 
	 * 
	 * @param subGoalsTable
	 *            the flex table for the sub-goals
	 */
	private void addRowToTable(String info, String keyInfo, Integer priority, final FlexTable table, boolean isAsset)
	{
		// get the current number of rows so we know where to add the new text box
		int currentNumberOfRows = table.getRowCount();

		// Create and add the textbox and supporting links for the mechanics of the UI
		final ScrollBarTextBox userInput = new ScrollBarTextBox();
		userInput.setWidth("100%");
		SquareHyperlink removeLink = new SquareHyperlink(messages.remove());

		userInput.setName(String.valueOf(currentNumberOfRows));
		userInput.setKey(keyInfo);  // For tracking db id
		userInput.setText(info);
		userInput.setPriority(priority);

		table.setWidget(currentNumberOfRows, 0, userInput);
		table.setWidget(currentNumberOfRows, 1, removeLink);
		FlexCellFormatter formatter = table.getFlexCellFormatter();
		formatter.setHorizontalAlignment(currentNumberOfRows, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatter.setHorizontalAlignment(currentNumberOfRows, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setWidth(currentNumberOfRows, 1, "10%");

		if (isAsset)
		{
			this.addAssetToTable(userInput, removeLink);
		}
		else
		{
			this.addGoalToTable(userInput, removeLink);
		}
	}

	

	/**
	 * A specialized TextBox to hold additional information
	 */
	private class ScrollBarTextBox extends TextBox
	{
		String primaryKey = null;
		Integer subGoalPriority = -1;

		void setKey(String key)
		{
			this.primaryKey = key;
		}

		String getKey()
		{
			return this.primaryKey;
		}

		void setPriority(Integer priority) 
		{
			subGoalPriority = priority;
		}
		
		Integer getPriority() 
		{
			return subGoalPriority;
		}
	}


	
	/**
	 * Navigation Buttons to browse other pages
	 * @param layout 
	 * 
	 * @return a table containing navigation buttons
	 */
	private FlexTable NavigationButtons(VerticalPanel layout)
	{
		FlexTable navigatorLayout = new FlexTable();
		FlexCellFormatter formatterNavigator = navigatorLayout.getFlexCellFormatter();
		navigatorLayout.setWidth("60%");

		Button businessGoalButton = new Button(messages.businessGoalButtonLabel());
		Button associateAssetsAndGoalsButton = new Button(messages.associateAssetsAndGoalsButtonLabel());
		businessGoalButton.setWidth("180px");
		associateAssetsAndGoalsButton.setWidth("180px");

		navigatorLayout.setWidget(0, 0, businessGoalButton);
		navigatorLayout.setWidget(0, 1, associateAssetsAndGoalsButton);
		formatterNavigator.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatterNavigator.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		businessGoalButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				updateBusinessGoalInformation(null);
				History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.start));
			}
		});
		
		associateAssetsAndGoalsButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				updateBusinessGoalInformation(new AssociateAssetsNavigation());
				
				
			}
		});
		return navigatorLayout;
	}
	private class AssociateAssetsNavigation implements Command
	{

		@Override
		public void execute()
		{
			if (businessGoal.getAssets().isEmpty())
			{
				History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.summary));	
			}
			else 
			{
				History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.assetGoalAssociation));
			}
			
			
		}
		
	}
}
