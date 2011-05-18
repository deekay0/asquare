package edu.cmu.square.client.ui.prioritizeRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.widgetideas.client.SliderBar;

import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsService;
import edu.cmu.square.client.remoteService.step.interfaces.PrioritizeRequirementsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;

public class SaveClickHandler implements ClickHandler
{
	private SliderBar costSlider;
	private SliderBar valueSlider;
	private boolean quit;
	private State currentState;
	private GwtPrioritization currentPrioritization;
	private PRCompareRequirementsPane compareRequirementsPane; 
	final PrioritizePaneMessages messages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	PrioritizeRequirementsServiceAsync prioritizeService = GWT.create(PrioritizeRequirementsService.class);
	public SaveClickHandler(boolean quit, SliderBar costSlider, SliderBar valueSlider, GwtPrioritization prioritization, State currentState, PRCompareRequirementsPane compareRequirementsPane)
		{
			this.quit = quit;
			this.costSlider = costSlider;
			this.valueSlider = valueSlider;
			this.currentPrioritization = prioritization;
			this.currentState = currentState;
			this.compareRequirementsPane = compareRequirementsPane;
			ServiceDefTarget endpoint = (ServiceDefTarget) prioritizeService;
			endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "prioritizeRequirements.rpc");
		}

	@Override
	public void onClick(ClickEvent event)
	{
		compareRequirementsPane.showStatusBar(messages.saving());
		double cost = costSlider.getCurrentValue();
		double value = valueSlider.getCurrentValue();
		
		if (cost < 0 ) 
		{
			double costA = cost * -1;
			currentPrioritization.setCostA(costA);
			currentPrioritization.setCostB(1/costA);
			
		} 
		else if (cost > 0) 
		{
			double costB = cost;
			currentPrioritization.setCostB(costB);
			currentPrioritization.setCostA(1/costB);
		}
		
		if (value < 0 ) 
		{
			double valueA = value * -1;
			currentPrioritization.setValueA(valueA);
			currentPrioritization.setValueB(1/valueA);
			
		} 
		else if (value > 0) 
		{
			double valueB = value;
			currentPrioritization.setValueB(valueB);
			currentPrioritization.setValueA(1/valueB);
		}
		if (cost == 0 || value == 0) 
		{
			Window.alert(messages.zeroCostOrValueError());
			return;
		}
		//async call to save 
		GwtUser user = new GwtUser();
		user.setUserId(currentState.getUserId());
		prioritizeService.updateRequirementComparison(currentPrioritization, user, currentState.getProjectID(), 
				new AsyncCallback<Void>() 
				{

					@Override
					public void onFailure(Throwable caught)
					{
						compareRequirementsPane.hideStatusBar();
						
					}

					@Override
					public void onSuccess(Void result)
					{
						compareRequirementsPane.hideStatusBar();
						if (quit) 
						{
							History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
						} else 
						{
							compareRequirementsPane.getNextRequirement();
							
						}
						
					}
			
				}
				
		);

	}

}
