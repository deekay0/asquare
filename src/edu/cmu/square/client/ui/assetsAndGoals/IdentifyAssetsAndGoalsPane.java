package edu.cmu.square.client.ui.assetsAndGoals;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtBusinessGoal;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsService;
import edu.cmu.square.client.remoteService.step.interfaces.IdentifyGoalsAssetsServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;

/**
 * This is used to set up the assets and goals panel
 */
public class IdentifyAssetsAndGoalsPane extends BasePane
{
	private IdentifyAssetsAndGoalsPaneMessages messages = (IdentifyAssetsAndGoalsPaneMessages)GWT.create(IdentifyAssetsAndGoalsPaneMessages.class);
	private IdentifyGoalsAssetsServiceAsync service = GWT.create(IdentifyGoalsAssetsService.class);
	private GwtBusinessGoal businessGoal = new GwtBusinessGoal();
	
	public IdentifyAssetsAndGoalsPane(final State stateInfo)
	{
		super(stateInfo);
		this.showLoadingStatusBar();
		this.showStepHome();
	}
	
	/**
	 * Teach Step information
	 */
	public void showStepHome()
	{
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
				showBusinessGoalPage();
		
			}
		 });
	}

/***********************************************************************************************/
/***************This section is for rendering the panel for the GUI*****************************/
/***********************************************************************************************/
	
	/**
	 * Display the Business Goal page
	 */
	private void showBusinessGoalPage()
	{
		this.hideStatusBar();
		Label business_goal_label = new Label(messages.businessGoalPage());
		business_goal_label.setStyleName("square-title");
		this.getContent().clear();
		VerticalPanel layout = new VerticalPanel();
		layout.setSpacing(15);
		layout.add(business_goal_label);
		
		final TextArea goal = new TextArea();
		goal.setText(this.businessGoal.getDescription());
		goal.setWidth("500px");
		goal.setHeight("100px");
		layout.add(goal);
		
		HorizontalPanel textLayout = new HorizontalPanel();
		textLayout.setWidth("100%");
		textLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layout.add(textLayout);
		
		final Button saveAndNextButton = new Button(messages.subGoalsButton());
	     
		if(goal.getText().trim().isEmpty())
		{
			saveAndNextButton.setEnabled(false); 
		}
		
		validateBusinessGoalNotEmpty(goal, saveAndNextButton);
		
		saveAndNextButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				ServiceDefTarget endpoint = (ServiceDefTarget) service;  //save the goal to the database
				endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "identifyGoalAssetService.rpc");	
				businessGoal.setDescription(goal.getText());
				service.setBusinessGoalInfo(getCurrentState().getProjectID(), businessGoal, new AsyncCallback<Void>()
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
								Window.alert(messages.errorSettingGoal());
								break;
							}

						} else {
							Window.alert(messages.errorSettingGoal());
						}
		            }
					public void onSuccess(Void result)
					{
						History.newItem(AssetsAndGoalsPilot.generateNavigationId(AssetsAndGoalsPilot.PageId.addSubGoalsAndAssets));
					}
				 });
			}
		});
		
		HorizontalPanel buttonLayout = new HorizontalPanel();
		buttonLayout.setWidth("100%");
		buttonLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonLayout.add(saveAndNextButton);
		layout.add(buttonLayout);
		
		this.getContent().add(layout);
	}
	
	/**
	 * Grey out the button until the business goal is not blank
	 * @param goal the text area which will contain the goal
	 * @param saveAndNextButton save button
	 */
	private void validateBusinessGoalNotEmpty(final TextArea goal, final Button saveAndNextButton) 
	{
		goal.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				toggleSaveAndNextButton(goal, saveAndNextButton);
			}
		});
		
		goal.addKeyDownHandler(new KeyDownHandler()
		{
			public void onKeyDown(KeyDownEvent event)
			{
				toggleSaveAndNextButton(goal, saveAndNextButton);
			}
		});
		
		goal.addKeyUpHandler(new KeyUpHandler()
		{
			public void onKeyUp(KeyUpEvent event) 
			{
				toggleSaveAndNextButton(goal, saveAndNextButton);
			}
		});
	}
	
	private void toggleSaveAndNextButton(final TextArea goal, final Button saveAndNextButton)
	{
		if(goal.getText().trim().isEmpty())
		{
			saveAndNextButton.setEnabled(false);
		}
		else
		{
			saveAndNextButton.setEnabled(true);
		}
		
	}

}




