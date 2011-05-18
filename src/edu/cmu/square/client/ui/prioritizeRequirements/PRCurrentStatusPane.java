package edu.cmu.square.client.ui.prioritizeRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.core.BasePane;

public class PRCurrentStatusPane extends BasePane
{
	final PrioritizePaneMessages messages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	private int countOfCompleted;
	private int totalUsers;
	PrioritizeRequirementsServiceAsync prioritizeService = GWT.create(PrioritizeRequirementsService.class);
	public PRCurrentStatusPane(State stateInfo)
		{
			super(stateInfo);
			getPrioritizationCounts();

		}
	private void getPrioritizationCounts()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) prioritizeService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "prioritizeRequirements.rpc");
		GwtUser gwtUser = new GwtUser();
		gwtUser.setUserId(this.currentState.getUserId());
		Integer projectId = this.currentState.getProjectID();
		prioritizeService.getPriotizationStatus(gwtUser, projectId,

		new AsyncCallback<GwtPrioritizationStatus>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					if (caught instanceof SquareException) 
					{
						SquareException se = (SquareException)caught;
						switch (se.getType())
						{
							case tooFewToPrioritize:
								//set up a simple error message.
								showTooFewPrioritizations();
								break;
								default:
									Window.alert(messages.generalPrioritizationError());
								
						}
					} else 
					{
						Window.alert(messages.generalPrioritizationError());
					}


				}

				@Override
				public void onSuccess(GwtPrioritizationStatus result)
				{
					currentState.setTotalPrioritizations(result.getTotalComparisons());
					countOfCompleted = result.getPrioritizationsDone();
					totalUsers = result.getTotalComparisons();
					initializePane();
				}

			}

		);
		
	}
	protected void showTooFewPrioritizations()
	{
		this.hideStatusBar();
		
		Label tooFewRequirementsLabel = new Label (messages.tooFewRequirements());
		tooFewRequirementsLabel.addStyleName("square-login");
		tooFewRequirementsLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonPanel.setSpacing(10);
		buttonPanel.setWidth("500px");
		
		Button done = new Button(messages.done());
		done.addStyleName("square-button");
		done.addClickHandler(
				new ClickHandler() 
				{

					@Override
					public void onClick(ClickEvent event)
					{
						History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
					}
					
				}
		);
		buttonPanel.add(done);
		
		this.getContent().add(tooFewRequirementsLabel);
		this.getContent().add(buttonPanel);
	}
	private void initializePane()
	{
		// set a box with the message and the buttons
		VerticalPanel centerPanel = new VerticalPanel();
		centerPanel.setWidth("500px");
		centerPanel.setStyleName("square-login");

		HTML prioritizationDirections = new HTML();

		prioritizationDirections.setHTML(messages.prioritizationInstructions());
		Label comparisonsCountHeader = new Label();
		comparisonsCountHeader.setText(messages.comparisonsCountHeader());

		final Label comparisonsCount = new Label();
		comparisonsCount.setText(messages.comparisonsCount(countOfCompleted + "",  totalUsers + ""));
		comparisonsCount.setStyleName("square-title");
		comparisonsCount.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		Button resetButton = new Button(messages.resetComparisons());
		Button startButton = new Button(messages.startComparisons());

		resetButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					boolean response = Window.confirm(messages.deleteConfirmation());
					if (response) {
						resetPrioritizations(comparisonsCount, totalUsers);
						
					}
					
				}

				

			}

		);

		startButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					if (countOfCompleted == totalUsers) 
					{
						History.newItem(PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.requirementsSummaryGroup));
					}
					else 
					{
						History.newItem(PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.compareRequirements));
					}
				}

			});

		buttonPanel.setStyleName("square-horizontalPanel");
		buttonPanel.add(resetButton);
		buttonPanel.add(new HTML("<div class=\"prioritization-step-spacer\"></div>"));
		buttonPanel.add(startButton);
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setWidth("100%");

		centerPanel.add(prioritizationDirections);
		centerPanel.add(comparisonsCountHeader);
		centerPanel.add(comparisonsCount);
		centerPanel.add(buttonPanel);

		this.getContent().add(centerPanel);
	}
	
	private void resetPrioritizations(final Label comparisonsCount, final int total)
	{
		
		GwtUser user = new GwtUser();
		user.setUserId(currentState.getUserId());
		Integer projectId = currentState.getProjectID();
		prioritizeService.resetPrioritizationStatus(user, projectId, 
				
				new AsyncCallback<Void>() 
				{

					@Override
					public void onFailure(Throwable caught)
					{
						
						
					}

					@Override
					public void onSuccess(Void result)
					{
						comparisonsCount.setText(messages.comparisonsCount("0", total + ""));
						countOfCompleted = 0;
					}
			
		}
		);

		
	}
}
