package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRisk;



public interface RiskAssessmentServiceAsync
{

	/**
	 * Adds a risk to a project
	 * @param projectId The id of the project.
	 * @param risk The risk to be added.
	 * @throws SquareException Exception thrown if the database cannot add the risk.
	 */
	void addRisksToProject(Integer projectId, GwtRisk risk, AsyncCallback<Integer> callback); 
	
	/**
	 * Update risks a risk to a project
	 * @param risk The risk to be added.
	 * @throws SquareException Exception thrown if the database cannot update the risk.
	 */
	void updateRisk(GwtRisk risk, AsyncCallback<Void> callback); 
	
	/**
	 * Remove a risk from the project
	 * @param riskId the risk to be removed.
	 * @throws SquareException Exception thrown if the database cannot remove the risk.
	 */
	void deleteRisk(Integer riskId, AsyncCallback<Void> callback); 

	
	void getRisksFromProject(Integer projectID, AsyncCallback<List<GwtRisk>> callback); 
}
