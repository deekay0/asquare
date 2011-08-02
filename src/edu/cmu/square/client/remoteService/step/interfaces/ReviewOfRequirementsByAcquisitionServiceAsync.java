package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtTerm;

public interface ReviewOfRequirementsByAcquisitionServiceAsync
{
	/**
	 * This returns all the terms in this project.
	 * @param gwtProject The project that the user selected.
	 * @return The categories associated with this project.
	 * @throws SquareException
	 */
	void getRequirements(GwtProject gwtProject, AsyncCallback<List<GwtRequirement>> callback);
	
	/**
	 * This add terms to the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	
	void addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement,  AsyncCallback<GwtRequirement> callback);
	
	/**
	 * This update an existing term in the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	void updateRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement,AsyncCallback<Void> callback);
	
	/**
	 * This remove the existing term from the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be removed from the project.
	 * @throws SquareException
	 */
	void removeRequirement(GwtRequirement GwtRequirement,AsyncCallback<Void> callback);

//Detail from elicit
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
	
//	void assignRequirementsToCategory(List<GwtRequirement> requirements,int categoryID, AsyncCallback<Void> callback);
	
	//void removeRequirementsFromCategory(List<GwtRequirement> requirements,int categoryID, AsyncCallback<Void> callback);

	void changeStatusToApproveRequirement(Integer projectId, GwtRequirement gwtRequirement, AsyncCallback<Void> asyncCallback);

	void changeStatusToRequestRevisionRequirement(Integer projectId, GwtRequirement gwtRequirement, AsyncCallback<Void> asyncCallback);
}
