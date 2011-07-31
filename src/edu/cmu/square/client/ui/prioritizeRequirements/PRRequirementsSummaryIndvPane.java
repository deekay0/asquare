package edu.cmu.square.client.ui.prioritizeRequirements;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;


public class PRRequirementsSummaryIndvPane extends BasePane
{
	final PrioritizePaneMessages paneMessages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	private PrioritizeRequirementsServiceAsync prioritizeService = GWT.create(PrioritizeRequirementsService.class);
	private List<GwtPrioritizedRequirement> prioritizedRequirements;
	public PRRequirementsSummaryIndvPane(State stateInfo)
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
		prioritizeService.getPrioritizedRequirementsIndividual(user, projectId,

		new AsyncCallback<List<GwtPrioritizedRequirement>>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					

				}

				@Override
				public void onSuccess(List<GwtPrioritizedRequirement> result)
				{
					prioritizedRequirements = result;
					setupPage();
				}

			}

		);
	
		
		
		
	}
	private void setupPage()
	{

		Label pageTitle = new Label(paneMessages.requirementIndividualPriorities());
		pageTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		pageTitle.addStyleName("square-title");
		Label groupStatusLabel = new Label(paneMessages.assignedPriorities());
		groupStatusLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

		VerticalPanel rankPanel = new VerticalPanel();
		rankPanel.setWidth("70%");
		
		rankPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		final HorizontalPanel linkPanel = new HorizontalPanel();
		linkPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		linkPanel.setWidth("100%");

		final FlexTable rankTable = new FlexTable();
		rankTable.setWidth("100%");
		rankTable.addStyleName("square-flex");

		FlexCellFormatter rankTableFormatter = rankTable.getFlexCellFormatter();
		Label finalRankLabel = new Label(paneMessages.individualRank());
		Label requirementLabel = new Label(paneMessages.requirement());
		requirementLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		Label calculatedRankLabel = new Label(paneMessages.ahpRank());

		rankTable.setWidget(0, 0, finalRankLabel);
		rankTable.setWidget(0, 1, requirementLabel);
		rankTable.setWidget(0, 2, calculatedRankLabel);
		rankTable.setCellSpacing(0);
		
		rankTableFormatter.addStyleName(0, 0, "square-TableHeader");
		rankTableFormatter.addStyleName(0, 1, "square-TableHeader");
		rankTableFormatter.addStyleName(0, 2, "square-TableHeader");
		// start creating the rows
		int rowToAdd = 1;
		for (GwtPrioritizedRequirement gpr : prioritizedRequirements)
		{
			Label individualRank = new Label(gpr.getIndividualRank() + "");
			Label requirementTitle = new Label(gpr.getRequirement().getTitle());
			requirementTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			Label calculatedRank = new Label(gpr.getGroupRank() + "");
		
			rankTable.setWidget(rowToAdd, 0, individualRank);
			rankTable.setWidget(rowToAdd, 1, requirementTitle);
			rankTable.setWidget(rowToAdd, 2, calculatedRank);
			rowToAdd++;
		}

		final Label disclaimerLabel = new Label(paneMessages.individualDisclaimer());

		rankPanel.add(linkPanel);
		rankPanel.add(rankTable);
		rankPanel.add(disclaimerLabel);
		rankPanel.setSpacing(5);


		Hyperlink viewIndividualLink = new Hyperlink(paneMessages.viewGroupPriorities(), 
		PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.requirementsSummaryGroup));
		linkPanel.add(viewIndividualLink);

		getContent().add(pageTitle);
		getContent().add(groupStatusLabel);
		getContent().add(rankPanel);
		
		this.hideStatusBar();
	}

}
