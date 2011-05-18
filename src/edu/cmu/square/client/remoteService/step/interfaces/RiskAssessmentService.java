package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRisk;
@RemoteServiceRelativePath("riskAssessment.rpc")
public interface RiskAssessmentService extends RemoteService {
	/**
	 * Adds a risk to a project
	 * @param projectId The id of the project.
	 * @param risk The risk to be added.
	 * @throws SquareException Exception thrown if the database cannot add the risk.
	 */
	Integer addRisksToProject(Integer projectId, GwtRisk risk) throws SquareException;
	
	/**
	 * Update risks a risk to a project
	 * @param risk The risk to be added.
	 * @throws SquareException Exception thrown if the database cannot update the risk.
	 */
	void updateRisk(GwtRisk risk) throws SquareException;
	
	/**
	 * Remove a risk from the project
	 * @param riskId the risk to be removed.
	 * @throws SquareException Exception thrown if the database cannot remove the risk.
	 */
	void deleteRisk(Integer riskId) throws SquareException;

	//associate risks with assets
	
	
	/**
	 * Removes the artifact from the risk
	 * @param artifactId The id of the project
	 * @param riskId the riskId to be associated.
	 * @throws SquareException Exception thrown if the database cannot remove the risk.
	 */
	
	List<GwtRisk> getRisksFromProject(Integer projectID) throws SquareException;
}
