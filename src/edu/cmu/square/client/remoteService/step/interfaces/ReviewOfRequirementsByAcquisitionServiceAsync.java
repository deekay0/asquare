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
	
}
