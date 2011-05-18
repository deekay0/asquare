package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRequirement;




public interface ElicitRequirementServiceAsync
{

	/**
	 * Adds a Requirement to a project
	 * @param projectId The id of the project.
	 * @param Requirement The Requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot add the Requirement.
	 */
	void addRequirementToProject(Integer projectId, GwtRequirement requirement, AsyncCallback<Integer> callback); 
	
	/**
	 * Update Requirements a Requirement to a project
	 * @param Requirement The Requirement to be added.
	 * @throws SquareException Exception thrown if the database cannot update the Requirement.
	 */
	void updateRequirement(GwtRequirement requirement, AsyncCallback<Void> callback); 
	
	/**
	 * Remove a Requirement from the project
	 * @param RequirementId the Requirement to be removed.
	 * @param projectId 
	 * @throws SquareException Exception thrown if the database cannot remove the Requirement.
	 */
	void deleteRequirement(Integer RequirementId, Integer projectId, AsyncCallback<Void> callback); 

	void getRequirementsFromProject(Integer projectID, AsyncCallback<List<GwtRequirement>> callback); 
	
	void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID, AsyncCallback<Void> callback);
	
	void removeRequirementsFromCategory(List<GwtRequirement> requirements,int categoryID, AsyncCallback<Void> callback);
	
}