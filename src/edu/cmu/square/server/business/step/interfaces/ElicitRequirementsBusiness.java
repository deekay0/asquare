/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirement;


public interface ElicitRequirementsBusiness extends StepBusinessInterface
{
	/**
	 * Adds a requirement to a project
	 * @param projectId The id of the project.
	 * @param requirement The requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot add the requirement.
	 */
	int addRequirementToProject(Integer projectId, GwtRequirement gwtRequirement) throws SquareException;
	
	/**
	 * Update requirements a requirement to a project
	 * @param requirement The requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot update the requirement.
	 */
	void updateRequirement(GwtRequirement gwtRequirement) throws SquareException;
	
	/**
	 * Remove a requirement from the project
	 * @param requirementId the requirement to be removed.
	 * @param projectId 
	 * @throws SquareException Exception thrown if the database cannot remove the requirement.
	 */
	void deleteRequirement(Integer requirementId, Integer projectId) throws SquareException;


	List<GwtRequirement> getRequirementsFromProject(int projectID) throws SquareException;
	
	void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID) throws SquareException;
	
	void removeRequirementsFromCategory(List<GwtRequirement> requirements,int categoryID) throws SquareException;
	
}
