package edu.cmu.square.client.ui.core;

import edu.cmu.square.client.model.GwtStep;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.client.ui.chooseCase.ChooseCasePilot;

public class StepRouter
{
	
	/**
	 * Retrieve the steps from the database.  Steps are going to be returned
	 * in numeric order with 
	 * @return
	 */
	public static String CreateStepLink(GwtStep step)
	{
		String stepDescription = step.getDescription();
		
		for (StepEnum se: StepEnum.values())
		{
			if (se.getDescription().equals(stepDescription))
			{
				return se.getLink();
			}
		}
		return ChooseCasePilot.generateNavigationId(ChooseCasePilot.PageId.FourOhFour);
	}
	
	
}
