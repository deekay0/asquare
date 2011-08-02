package edu.cmu.square.client.ui.prioritizeRequirements;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class PRRequirementsSummaryGroupPane extends BasePane
{
	private static final String TOP_IMAGE_LOCATION = "images//top.jpg";
	private static final String UP_ARROW_IMAGE_LOCATION = "images//up.jpg";
	private static final String DOWN_ARROW_IMAGE_LOCATION = "images//down.jpg";
	private int completedUsers;
	private int totalUsers;
	final PrioritizePaneMessages messages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	private PrioritizeRequirementsServiceAsync prioritizeService = GWT.create(PrioritizeRequirementsService.class);
	private List<GwtPrioritizedRequirement> prioritizedRequirements;
	public PRRequirementsSummaryGroupPane(State stateInfo)
		{
			super(stateInfo);
			ServiceDefTarget endpoint = (ServiceDefTarget) prioritizeService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "prioritizeRequirements.rpc");
			initializePage();

		}
	private void initializePage()
	{
		showLoadingStatusBar();
		GwtUser user = new GwtUser();
		user.setUserId(getCurrentState().getUserId());
		Integer projectId = getCurrentState().getProjectID();

		prioritizeService.getPrioritizedRequirements(user, projectId,

		new AsyncCallback<List<GwtPrioritizedRequirement>>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					try
					{
						throw caught;
					}
					catch (SquareException se)
					{
						switch (se.getType())
						{
							case tooFewToPrioritize :
								showNoPrioritizations(messages.tooFewRequirements());
								break;
							case noUsersPrioritized :
								showNoPrioritizations(messages.groupStatusEmpty());
								break;	
							default :
						}
					}
					catch (Throwable t)
					{
						Window.alert(messages.generalPrioritizationError());
					}
				}

				@Override
				public void onSuccess(List<GwtPrioritizedRequirement> result)
				{
					prioritizedRequirements = result;
					getCounts();
				}

			}

		);

	}

	private void getCounts()
	{
		Integer projectId = getCurrentState().getProjectID();

		prioritizeService.getUserPrioritizations(projectId,

		new AsyncCallback<GwtPrioritizationStatus>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					try
					{
						throw caught;
					}
					catch (SquareException se)
					{
						switch (se.getType())
						{
							case noUsersPrioritized :
								
								showNoPrioritizations(messages.groupStatusEmpty());
								
								break;
							case tooFewToPrioritize:
								
								showNoPrioritizations(messages.tooFewRequirements());
								
								break;
						}
					}
					catch (Throwable t)
					{
						Window.alert(messages.generalPrioritizationError());
					}
				}

				@Override
				public void onSuccess(GwtPrioritizationStatus result)
				{
					completedUsers = result.getCompletedUsers();
					totalUsers = result.getTotalUsers();
					setupPage();
				}

			}

		);

	}

	private void showNoPrioritizations(String message) 
	{
		hideStatusBar();
		Label pageTitle = new Label(messages.requirementPriorities());
		pageTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		pageTitle.addStyleName("square-title");
		Label groupStatusLabel = new Label(messages.groupStatus(completedUsers, totalUsers));
		groupStatusLabel.setText(message);
		groupStatusLabel.addStyleName("square-login");
		groupStatusLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setSpacing(10);
		
		Button done = new Button(messages.done());
		
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
		buttonPanel.setWidth("70%");
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		

		
		getContent().add(pageTitle);
		getContent().add(groupStatusLabel);
		getContent().add(buttonPanel);
	}
	private void setupPage()
	{

		Label pageTitle = new Label(messages.requirementPriorities());
		pageTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		pageTitle.addStyleName("square-title");
		Label groupStatusLabel = new Label(messages.groupStatus(completedUsers, totalUsers));
		

		if (prioritizedRequirements != null && !prioritizedRequirements.isEmpty())
		{
			groupStatusLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			VerticalPanel rankPanel = new VerticalPanel();
			rankPanel.setWidth("70%");
			
			rankPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			final HorizontalPanel linkPanel = new HorizontalPanel();
			linkPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			linkPanel.setWidth("100%");

			final FlexTable rankTable = new FlexTable();
			rankTable.setWidth("100%");
			rankTable.setCellSpacing(0);
			rankTable.addStyleName("square-flex");
			
			
			FlexCellFormatter rankTableFormatter = rankTable.getFlexCellFormatter();
			Label finalRankLabel = new Label(messages.finalRank());
			Label requirementLabel = new Label(messages.requirement());
			requirementLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			Label calculatedRankLabel = new Label(messages.ahpRank());

			rankTable.setWidget(0, 0, finalRankLabel);
			rankTable.setWidget(0, 1, requirementLabel);
			rankTable.setWidget(0, 2, calculatedRankLabel);

			rankTableFormatter.addStyleName(0, 0,  "square-TableHeader");
			rankTableFormatter.addStyleName(0, 1,  "square-TableHeader");
			rankTableFormatter.addStyleName(0, 2,  "square-TableHeader");
			// start creating the rows
			int rowToAdd = 1;

			for (GwtPrioritizedRequirement gpr : prioritizedRequirements)
			{
				Label finalRank = new Label(gpr.getRequirement().getPriority() + "");
				Label requirementTitle = new Label(gpr.getRequirement().getTitle());
				requirementTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				Label calculatedRank = new Label(gpr.getGroupRank() + "");
	
				rankTable.setWidget(rowToAdd, 0, finalRank);
				rankTable.setWidget(rowToAdd, 1, requirementTitle);
				rankTable.setWidget(rowToAdd, 2, calculatedRank);
				rowToAdd++;
			}

			final Label disclaimerLabel = new Label(messages.disclaimer());

			rankPanel.add(linkPanel);
			rankPanel.add(rankTable);
			rankPanel.add(disclaimerLabel);
			rankPanel.setSpacing(5);

			final FlexTable buttonPanel = new FlexTable();
			
		
			

			if (currentState.getMode() == GwtModesType.ReadWrite)
			{
				Button resetButton = new Button(messages.resetComparisons());
				resetButton.addClickHandler(new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event)
						{
							boolean response = Window.confirm(messages.deleteConfirmation());
							if (response)
							{
								resetComparisons();
							}
						}

					});
				resetButton.setWidth("150px");
				buttonPanel.setWidget(0, 0, resetButton);
				buttonPanel.getCellFormatter().setHorizontalAlignment(0,0,HasHorizontalAlignment.ALIGN_LEFT);
			}
			Button doneButton = new Button(messages.done());
			doneButton.addStyleName("square-button");
			doneButton.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));

					}

				}

			);

			doneButton.setWidth("150px");

		
			buttonPanel.setWidget(0, 1, doneButton);
			buttonPanel.getCellFormatter().setHorizontalAlignment(0,1,HasHorizontalAlignment.ALIGN_RIGHT);
			buttonPanel.setWidth("70%");
			// FIXME: Hide this link till all the users have completed.
			if (((getCurrentState().getUserProjectRole() == ProjectRole.Requirements_Engineer) || (getCurrentState().getUserProjectRole() == ProjectRole.Lead_Requirements_Engineer))
					&& completedUsers == totalUsers)
			{
				SquareHyperlink updateLink = new SquareHyperlink(messages.updatePriorities());
				updateLink.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				updateLink.addClickHandler(new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event)
						{
							updateTable(rankTable);
							linkPanel.clear();

							buttonPanel.clear();
							updateButtons(buttonPanel);
							disclaimerLabel.setText(messages.updateText());
						}

					});
				linkPanel.add(updateLink);
			}
			if (currentState.getMode() == GwtModesType.ReadWrite)
			{
				Hyperlink viewIndividualLink = new Hyperlink(messages.viewIndividualPriorities(), PrioritizeRequirementsPilot
						.generateNavigationId(PrioritizeRequirementsPilot.PageId.requirementsSummaryIndv));
				linkPanel.add(viewIndividualLink);

			}
			getContent().add(pageTitle);
			getContent().add(groupStatusLabel);
			getContent().add(rankPanel);
			getContent().add(buttonPanel);
		}

		this.hideStatusBar();
	}

	protected void updateButtons(FlexTable buttonPanel)
	{
		Button saveButton = new Button(messages.save());
		saveButton.setWidth("150px");
		Button cancelButton = new Button(messages.cancel());
		cancelButton.setWidth("150px");
		Button resetButton = new Button(messages.reset());
		resetButton.setWidth("150px");

		buttonPanel.setWidget(0, 0, saveButton);
		buttonPanel.getCellFormatter().setHorizontalAlignment(0,0,HasHorizontalAlignment.ALIGN_LEFT);
		buttonPanel.setWidget(0, 1, cancelButton);
		buttonPanel.getCellFormatter().setHorizontalAlignment(0,1,HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setWidget(0, 2, resetButton);
		buttonPanel.getCellFormatter().setHorizontalAlignment(0,2,HasHorizontalAlignment.ALIGN_RIGHT);
	
		cancelButton.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					getContent().clear();
					initializePage();
				}
			}

		);
		saveButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					saveFinalRanks();
				}

			}

		);

		resetButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					resetFinalRanks();
				}

			}

		);
	}
	private void resetFinalRanks()
	{
		// loop through the list and reset to the ahp rank
		for (GwtPrioritizedRequirement gpr : prioritizedRequirements)
		{
			gpr.getRequirement().setPriority(0);
		}
		prioritizeService.batchUpdatePrioritizations(prioritizedRequirements, new AsyncCallback<Void>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Void result)
				{
					getContent().clear();
					initializePage();
				}

			});
	}

	protected void saveFinalRanks()
	{
		// create a gwtPrioritizedRequirement list with the updated priorities
		prioritizeService.batchUpdatePrioritizations(prioritizedRequirements, new AsyncCallback<Void>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Void result)
				{
					getContent().clear();
					initializePage();
				}

			});

	}
	protected void updateTable(final FlexTable rankTable)
	{
		rankTable.removeStyleName("summary-Table-thick");
		for (int i = 1; i < rankTable.getRowCount(); i++)
		{
			HorizontalPanel rankingPanel = new HorizontalPanel();
			rankingPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			rankingPanel.setWidth("70px");
			rankingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			// replace the label with a textbox
			VerticalPanel arrows = new VerticalPanel();
			arrows.setStyleName("prioritization-arrows");

			// arrows.setHeight("30px");

			Image upArrowImage = new Image(UP_ARROW_IMAGE_LOCATION);
			upArrowImage.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						int rowClicked = rankTable.getCellForEvent(event).getRowIndex();

						GwtPrioritizedRequirement currentRequirement = prioritizedRequirements.get(rowClicked - 1);
						GwtPrioritizedRequirement previousRequirement = prioritizedRequirements.get(rowClicked - 2);
						previousRequirement.getRequirement().setPriority(rowClicked);
						currentRequirement.getRequirement().setPriority(rowClicked - 1);
						prioritizedRequirements.set(rowClicked - 1, previousRequirement);
						prioritizedRequirements.set(rowClicked - 2, currentRequirement);

						// swap table rows
						swapTableRows(rankTable, rowClicked, rowClicked - 1);
					}

				});
			Image downArrowImage = new Image(DOWN_ARROW_IMAGE_LOCATION);

			downArrowImage.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						int rowClicked = rankTable.getCellForEvent(event).getRowIndex();

						GwtPrioritizedRequirement currentRequirement = prioritizedRequirements.get(rowClicked - 1);
						GwtPrioritizedRequirement previousRequirement = prioritizedRequirements.get(rowClicked);
						previousRequirement.getRequirement().setPriority(rowClicked);
						currentRequirement.getRequirement().setPriority(rowClicked + 1);
						prioritizedRequirements.set(rowClicked - 1, previousRequirement);
						prioritizedRequirements.set(rowClicked, currentRequirement);

						// swap table rows
						swapTableRows(rankTable, rowClicked, rowClicked + 1);
					}

				});
			if (i > 1)
			{
				arrows.add(upArrowImage);
				arrows.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			}
			if (i < rankTable.getRowCount() - 1)
			{
				arrows.add(downArrowImage);
				arrows.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			}

			Image topImage = new Image(TOP_IMAGE_LOCATION);
			topImage.addClickHandler(new ClickHandler()
				{

					@Override
					public void onClick(ClickEvent event)
					{
						int rowClicked = rankTable.getCellForEvent(event).getRowIndex();

						GwtPrioritizedRequirement currentRequirement = prioritizedRequirements.remove(rowClicked - 1);
						prioritizedRequirements.add(0, currentRequirement);
						for (int i = 0; i < prioritizedRequirements.size(); i++)
						{
							prioritizedRequirements.get(i).getRequirement().setPriority(i + 1);
						}
						Widget clickedReq = rankTable.getWidget(rowClicked, 1);
						Widget clickedReq2 = rankTable.getWidget(rowClicked, 2);

						Widget newReq = null;
						Widget oldReq = rankTable.getWidget(1, 1);

						Widget newReq2 = null;
						Widget oldReq2 = rankTable.getWidget(1, 2);

						for (int i = 2; i <= rowClicked; i++)
						{
							newReq = rankTable.getWidget(i, 1);
							rankTable.setWidget(i, 1, oldReq);
							oldReq = newReq;

							newReq2 = rankTable.getWidget(i, 2);
							rankTable.setWidget(i, 2, oldReq2);
							oldReq2 = newReq2;
						}
						rankTable.setWidget(1, 1, clickedReq);
						rankTable.setWidget(1, 2, clickedReq2);
					}

				});
			rankingPanel.add(arrows);
			if (i > 1)
			{
				rankingPanel.add(topImage);
			}
			rankTable.setWidget(i, 0, rankingPanel);
		}

	}
	protected void swapTableRows(FlexTable rankTable, int x, int y)
	{
		Widget current = rankTable.getWidget(x, 1);
		Widget previous = rankTable.getWidget(y, 1);
		rankTable.setWidget(x, 1, previous);
		rankTable.setWidget(y, 1, current);

		current = rankTable.getWidget(x, 2);
		previous = rankTable.getWidget(y, 2);
		rankTable.setWidget(x, 2, previous);
		rankTable.setWidget(y, 2, current);

	}
	private void resetComparisons()
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
					// go back to the comparison status page
					History.newItem(PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.start));
				}

			});

	}

}
