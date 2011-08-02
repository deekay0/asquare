package edu.cmu.square.server.business.interfaces;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;

public interface StepBusiness extends BaseBusinessInterface
{

	boolean isStepClosed(String description, Integer projectId) throws SquareException;

	void updateStepStatus(int projectId, int stepId, String status) throws SquareException;

	void createStepsForProject(GwtProject project);

	List<GwtStepVerficationResult> verifyAllSteps(Integer id, WebApplicationContext ctx) throws SquareException;
	
}
