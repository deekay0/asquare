package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;

@RemoteServiceRelativePath("reviewOfRequirementsByAcquisitionService.rpc") //<----!!
public interface ReviewOfRequirementsByAcquisitionService extends RemoteService {
		
		/**
		 * This returns all the terms in this project.
		 * @param gwtProject The project that the user selected.
		 * @return The categories associated with this project.
		 * @throws SquareException
		 */
		List<GwtRequirement> getRequirements(GwtProject gwtProject)throws SquareException;
		
		/**
		 * This add terms to the project.
		 * @param gwtProject The project that the user selected.
		 * @param GwtTerm The term to be updated to the project
		 * @throws SquareException
		 */
		
		GwtRequirement addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement)throws SquareException;
		
		/**
		 * This update an existing term in the project.
		 * @param gwtProject The project that the user selected.
		 * @param GwtTerm The term to be updated to the project
		 * @throws SquareException
		 */
		void updateRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement) throws SquareException;
		
		/**
		 * This remove the existing term from the project.
		 * @param gwtProject The project that the user selected.
		 * @param GwtTerm The term to be removed from the project.
		 * @throws SquareException
		 */
		void removeRequirement(GwtRequirement GwtRequirement)throws SquareException;

		
		//Detail from elicit
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

		void changeStatusToApproveRequirement(Integer projectId, GwtRequirement gwtRequirement)throws SquareException;

		void changeStatusToRequestRevisionRequirement(Integer projectId, GwtRequirement gwtRequirement)throws SquareException;


		
}
