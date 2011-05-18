/**
 * 
 */
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.client.SliderBar;

import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;

/**
 * @author kaalpurush
 * 
 */
public class PRCompareRequirementsPane extends BasePane
{
//	final PrioritizeMessages paneMessages = (PrioritizeMessages) GWT.create(PrioritizeMessages.class);

	final PrioritizePaneMessages paneMessages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	PrioritizeRequirementsServiceAsync prioritizeService = GWT.create(PrioritizeRequirementsService.class);
	private Label countLabel;
	private List<GwtPrioritization> prioritizationsLeft;
	private GwtPrioritization currentPrioritization;
	private int totalPrioritizations;
	private int completedPrioritizations;
	public PRCompareRequirementsPane(State stateInfo)
		{
			super(stateInfo);
			ServiceDefTarget endpoint = (ServiceDefTarget) prioritizeService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "prioritizeRequirements.rpc");
			this.showLoadingStatusBar();
			initializePage();

		}

	protected void initializePage()
	{
		this.getContent().clear();
		// get comparisons
		GwtUser user = new GwtUser();
		user.setUserId(this.currentState.getUserId());
		Integer projectId = this.currentState.getProjectID();
		
		prioritizeService.getRequirementsToCompare(user, projectId, new AsyncCallback<List<GwtPrioritization>>()
			{

				@Override
				public void onFailure(Throwable caught)
				{
					
				}

				@Override
				public void onSuccess(List<GwtPrioritization> result)
				{
					prioritizationsLeft = result;
					totalPrioritizations = currentState.getTotalPrioritizations();
					completedPrioritizations = totalPrioritizations - prioritizationsLeft.size();
					initializeTopPane();
					if (!prioritizationsLeft.isEmpty())
					{
						currentPrioritization = prioritizationsLeft.get(0);
						initializeRequirementsPane(currentPrioritization);
					}
					else
					{
						Window.alert("No more prioritizations left");
					}
					
					
				}

			}

		);
		
	}

	

	private void initializeRequirementsPane(GwtPrioritization prioritization)
	{
		this.hideStatusBar();
		HorizontalPanel requirementsPanel = new HorizontalPanel();
		requirementsPanel.setWidth("100%");
		requirementsPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		//the requirement A
		requirementsPanel.add(createRequirementsVerticalPanel(prioritization.getRequirementA()));
		
		VerticalPanel sliderPanel = new VerticalPanel();
		sliderPanel.setSpacing(3);
		
		Label costLabel = new Label(paneMessages.costLabel());
		
		SliderBar costSlider = new SliderBar(-5, 5);
		costSlider.setStepSize(1.0);
		costSlider.setCurrentValue(0.0);
		costSlider.setNumTicks(10);
		costSlider.setNumLabels(5);
		costSlider.setWidth("300px");
		
		
		Label valueLabel = new Label(paneMessages.valueLabel());
		
		SliderBar valueSlider = new SliderBar(-5, 5);
		valueSlider.setStepSize(1.0);
		valueSlider.setCurrentValue(0.0);
		valueSlider.setNumTicks(10);
		valueSlider.setNumLabels(5);
		valueSlider.setWidth("300px");

		sliderPanel.add(costLabel);
		sliderPanel.add(costSlider);
		sliderPanel.add(valueLabel);
		sliderPanel.add(valueSlider);

		requirementsPanel.add(sliderPanel);
		
		//requirementsB panel
		requirementsPanel.add(createRequirementsVerticalPanel(prioritization.getRequirementB()));		
		//end requirements panel
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
	
		buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		buttonPanel.setSpacing(10);
		
		Button saveAndQuit = new Button(paneMessages.saveAndQuit());
		saveAndQuit.setWidth("150px");
		saveAndQuit.addClickHandler(new SaveClickHandler(true, costSlider, valueSlider, 
				prioritization, currentState, this));
		
		Button saveAndContinue = new Button(paneMessages.saveAndContinue());
		saveAndContinue.setWidth("150px");
		saveAndContinue.addClickHandler(new SaveClickHandler(false, costSlider, valueSlider, 
				prioritization, currentState, this));
		
		buttonPanel.add(saveAndQuit);
		buttonPanel.add(new HTML("<div class=\"prioritization-step-spacer\"></div>"));
		buttonPanel.add(saveAndContinue);
		
		this.getContent().add(requirementsPanel);
		this.getContent().add(buttonPanel);
	}

	private VerticalPanel createRequirementsVerticalPanel(final GwtRequirement requirement)
	{
		VerticalPanel requirementsVerticalPanel = new VerticalPanel();
		requirementsVerticalPanel.addStyleName("square-login");
		
		Label requirementATitle = new Label(requirement.getTitle());
		requirementATitle.setStyleName("page-title-heading");

		final String categoriesString = formatCategories(requirement.getCategories());
		Label requirementACategories = new Label(categoriesString);
		SquareHyperlink viewDetails = new SquareHyperlink(paneMessages.view());

		viewDetails.addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event)
					{
						ViewRequirementDialog requirementsDetails =  new ViewRequirementDialog(requirement, categoriesString);
						requirementsDetails.center();
						requirementsDetails.show();
						
					}
					
				}
		);
		requirementsVerticalPanel.add(requirementATitle);
		requirementsVerticalPanel.add(requirementACategories);
		requirementsVerticalPanel.add(viewDetails);
		requirementsVerticalPanel.setWidth("300px");
		return requirementsVerticalPanel; 
	}

	private String formatCategories(List<GwtCategory> categories)
	{
		String categoriesString = "";
		int i = 0;
		for (GwtCategory gc : categories)
		{
			if (i < categories.size() - 1)
			{
				categoriesString += gc.getCategoryName() + ", ";
			}
			else
			{
				categoriesString += gc.getCategoryName();
			}
			i++;
		}
		return categoriesString;
	}

	private void initializeTopPane()
	{
		HorizontalPanel countPanel = new HorizontalPanel();
		countPanel.setSpacing(10);
		Label countHeader = new Label(paneMessages.countHeader());

		countLabel = new Label(paneMessages.comparisonsCount(completedPrioritizations + "", totalPrioritizations + ""));
		countLabel.setStyleName("square-title");
		countLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		countPanel.add(countHeader);
		countPanel.add(countLabel);

		this.getContent().add(countPanel);

	}

	protected void getNextRequirement()
	{
		this.getContent().clear();
		
		completedPrioritizations++;
		prioritizationsLeft.remove(currentPrioritization);
		if (prioritizationsLeft.isEmpty()) 
		{
			History.newItem(PrioritizeRequirementsPilot.generateNavigationId(PrioritizeRequirementsPilot.PageId.requirementsSummaryGroup));
			return;
		}
		currentPrioritization = prioritizationsLeft.get(0);
		
		initializeTopPane();
		initializeRequirementsPane(currentPrioritization);
	}

}
