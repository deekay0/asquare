package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirement;

@RemoteServiceRelativePath("elicitRequirement.rpc")
public interface ElicitRequirementService extends RemoteService {
	/**
	 * Adds a Requirement to a project
	 * @param projectId The id of the project.
	 * @param Requirement The Requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot add the Requirement.
	 */
	Integer addRequirementToProject(Integer projectId, GwtRequirement Requirement) throws SquareException;
	/**
	 * Update Requirement of   a project
	 * @param Requirement The Requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot update the Requirement.
	 */
	void updateRequirement(GwtRequirement Requirement) throws SquareException;
	
	/**
	 * Remove a Requirement from the project
	 * @param RequirementId the Requirement to be removed.
	 * @throws SquareException Exception thrown if the database cannot remove the Requirement.
	 */
	void deleteRequirement(Integer requirementId, Integer projectId) throws SquareException;


	/**
	 * Removes the artifact from the Requirement
	 * @param artifactId The id of the project
	 * @param RequirementId the RequirementId to be associated.
	 * @throws SquareException Exception thrown if the database cannot remove the Requirement.
	 */
	List<GwtRequirement> getRequirementsFromProject(Integer projectID) throws SquareException;
	
	void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID)throws SquareException;

	
	void removeRequirementsFromCategory(List<GwtRequirement> requirements,int categoryID)throws SquareException;
	

}
