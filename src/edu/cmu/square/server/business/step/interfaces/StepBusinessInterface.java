package edu.cmu.square.server.business.step.interfaces;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.business.interfaces.BaseBusinessInterface;
import edu.cmu.square.server.dao.model.Project;

public interface StepBusinessInterface extends BaseBusinessInterface
{
	GwtStepVerficationResult verifyStep(Project projectId) throws SquareException;
	
	StepEnum getStepDescription() throws SquareException;
}
