package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;

@RemoteServiceRelativePath("stepService.rpc")
public interface StepService extends RemoteService
{
	boolean isStepClosed(String description, Integer projectId) throws SquareException;

	void updateStepStatus(int projectId, int stepId, String status)throws SquareException;
	
	/**
	 * Retrieves all the steps for a given project, verifies, and then returns the steps and verification 
	 * @param project the given project
	 * @return Steps with verification data
	 * @throws SquareException 
	 */
	List<GwtStepVerficationResult> getStepsWithVerification(GwtProject project) throws SquareException;

}
