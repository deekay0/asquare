/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRisk;


/**
 * @author kaalpurush
 *
 */
public interface RiskAssessmentBusiness extends StepBusinessInterface {
	/**
	 * Adds a risk to a project
	 * @param projectId The id of the project.
	 * @param risk The risk to be added.
	 * @throws SquareException Exception thrown if the database cannot add the risk.
	 */
	int addRisksToProject(Integer projectId, GwtRisk risk) throws SquareException;
	
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


	List<GwtRisk> getRisksFromProject(int projectID) throws SquareException;

}
