package edu.cmu.square.client.ui.assetsAndGoals;

import java.util.Collections;
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
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.ChooseStepCase3.ChooseStepCase3Pilot;
import edu.cmu.square.client.ui.core.BasePane;

public class IdentifyAssetsAndGoalsSummaryPane extends BasePane
{
	private GwtBusinessGoal businessGoal;
	private VerticalPanel baseLayout;
	private IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
	private RenderSummaryPage summaryPageCommand = new RenderSummaryPage();
	private IdentifyAssetsAndGoalsPaneMessages messages = (IdentifyAssetsAndGoalsPaneMessages)GWT.create(IdentifyAssetsAndGoalsPaneMessages.class);
	
	public IdentifyAssetsAndGoalsSummaryPane(State stateInfo)
	{
		super(stateInfo);
		fetchBusinessGoalFromDB();
	}
	
	
	private void showSummaryPage()
	{
		baseLayout = new VerticalPanel();
		baseLayout.setWidth("100%");
		baseLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		//baseLayout.add(renderPageHeading());
		baseLayout.add(renderSummaryFlexTable());
		baseLayout.add(NavigationButtons(baseLayout));
		this.getContent().add(baseLayout);
	}
	
	/**
	 * Navigation Buttons to browse other pages from the summary page
	 * @param layout 
	 * 
	 * @return a table containing navigation buttons
	 */
	private FlexTable NavigationButtons(VerticalPanel layout)
	{
		FlexTable navigatorLayout = new FlexTable();
		FlexCellFormatter formatterNavigator = navigatorLayout.getFlexCellFormatter();
		navigatorLayout.setWidth("60%");

		Button assetAssociationButton = new Button(messages.assetAssociationButton());
		assetAssociationButton.addStyleName("square-button");
		if (businessGoal.getAssets().isEmpty())
		{
			assetAssociationButton.setText(messages.reviewSubgoals());
		}
		Button summaryButton = new Button(messages.done());
		
		summaryButton.addStyleName("square-button");
	
		//summaryButton.addStyleName("square-button");
		
        if(getCurrentState().getMode().toString().equalsIgnoreCase("ReadWrite"))
        {
        	navigatorLayout.setWidget(0, 0, assetAssociationButton);
        }
        
		navigatorLayout.setWidget(0, 1, summaryButton);
		formatterNavigator.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		formatterNavigator.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);

		assetAssociationButton.addClickHandler(new ClickHandler() 
		{
			public void onClick(ClickEvent event) 
			{
				if (businessGoal.getAssets().isEmpty())
				{
					History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.addSubGoalsAndAssets));
				}
				else 
				{
					History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.assetGoalAssociation));
				}
				
			}
		});
		
		final int caseid = this.getCurrentState().getCaseID();
		
		summaryButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if(caseid==1)
					History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
				if(caseid==3)
					History.newItem(ChooseStepCase3Pilot.generateNavigationId(ChooseStepCase3Pilot.PageId.home));
			}
		});
		
		return navigatorLayout;
	}
	/**
	 * Render the summary page table
	 * @return the panel encapsulating the summary table
	 */
	private VerticalPanel renderSummaryFlexTable()
	{
		VerticalPanel summaryPanel= new VerticalPanel();
		summaryPanel.setWidth("60%");
		
		FlexTable summaryTable = new FlexTable();
		summaryTable.setWidth("100%");
		summaryTable.setStyleName("square-flex");
		summaryTable.setCellSpacing(0);

		FlexCellFormatter summaryTableFormatter = summaryTable.getFlexCellFormatter();
		
		Label businessDescription = new Label(businessGoal.getDescription());
		businessDescription.setStyleName("square-title");
		summaryTable.setWidget(0, 0, businessDescription);
		summaryTableFormatter.setWidth(0, 0, "100%");
		summaryTableFormatter.setColSpan(0, 0, 3);
		
		Label subgoalHeader = new Label(messages.subgoal());
		Label assetsHeader = new Label(messages.assets());
		Label priorityHeader = new Label(messages.priority());
		
		summaryTable.setWidget(1, 0, priorityHeader);
		summaryTable.setWidget(1, 1, subgoalHeader);
		summaryTable.setWidget(1, 2, assetsHeader);
		summaryTableFormatter.setStyleName(1, 0, "square-TableHeader");
		summaryTableFormatter.setStyleName(1, 1, "square-TableHeader");
		summaryTableFormatter.setStyleName(1, 2, "square-TableHeader");
		
		List<GwtSubGoal> subgoals = businessGoal.getSubGoals();
		Collections.sort(subgoals);
		
		int nextRow = 2; //Start populating the table from row 1
		for (int i = 0; i < subgoals.size(); i++)
		{	
			Label subGoalLabel = new Label(subgoals.get(i).getDescription());
			
			VerticalPanel assetGrid = new VerticalPanel();
			assetGrid.setWidth("100%");
			assetGrid.setStyleName("inner-table");
			
			Collections.sort(subgoals.get(i).getAssets());
			for(int j = 0; j < subgoals.get(i).getAssets().size(); j++)
			{
				Label assetsLabel = new Label(subgoals.get(i).getAssets().get(j).getDescription());	
				assetGrid.add(assetsLabel);
			}
			
			summaryTable.setWidget(nextRow, 0, new Label(subgoals.get(i).getPriority().toString()));
			summaryTableFormatter.setAlignment(nextRow, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
			summaryTable.setWidget(nextRow, 1, subGoalLabel);
			summaryTableFormatter.setAlignment(nextRow, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP);
			summaryTable.setWidget(nextRow, 2, assetGrid);
			
			nextRow += 1;
		}
		summaryPanel.add(summaryTable);
		
		if (subgoals.size() == 0)
		{

			DisclosurePanel diclosure = new DisclosurePanel();

			Label noRequirement = new Label(messages.noelementsFound());
			noRequirement.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			diclosure.add(noRequirement);
			summaryPanel.add(diclosure);
			diclosure.setAnimationEnabled(true);
			diclosure.setOpen(true);
		}
		
		return summaryPanel;
	}



	/**
	 * Render the summary page only once the business goal has been fetched
	 */
	
	private class RenderSummaryPage implements Command
	 {		
		
		public void execute() 
		 {
			hideStatusBar();
			showSummaryPage();
		} 
	 }
	
	/**
	 * Retrieve the business goal from the database
	 * @param cmd
	 */
	private void fetchBusinessGoalFromDB() 
	{
		this.showLoadingStatusBar();
		ServiceDefTarget endpoint = (ServiceDefTarget) service; 		// get the business goal and associated subgoals and assets
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
					summaryPageCommand.execute();
			}
		});
	}
}

