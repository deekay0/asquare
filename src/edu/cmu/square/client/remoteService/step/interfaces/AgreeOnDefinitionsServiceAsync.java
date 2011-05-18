/**
 * 
 */
package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTerm;


/**
 * @author yirul
 *
 */
public interface AgreeOnDefinitionsServiceAsync {

	/**
	 * This returns all the terms in this project.
	 * @param gwtProject The project that the user selected.
	 * @return The categories associated with this project.
	 * @throws SquareException
	 */
	void getTerms(GwtProject gwtProject, AsyncCallback<List<GwtTerm>> callback);
	
	/**
	 * This add terms to the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	
	void addTerm(GwtProject gwtProject, GwtTerm gwtTerm,  AsyncCallback<GwtTerm> callback);
	
	/**
	 * This update an existing term in the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	void updateTerm(GwtProject gwtProject, GwtTerm gwtTerm,AsyncCallback<Void> callback);
	
	/**
	 * This remove the existing term from the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be removed from the project.
	 * @throws SquareException
	 */
	void removeTerm(GwtTerm GwtTerm,AsyncCallback<Void> callback);
	
}
