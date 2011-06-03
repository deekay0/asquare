package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

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
		
		//GwtRequirement addRequirement(GwtProject gwtProject, GwtRequirement gwtRequirement)throws SquareException;
		
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
		//void removeRequirement(GwtRequirement GwtRequirement)throws SquareException;
		
}
