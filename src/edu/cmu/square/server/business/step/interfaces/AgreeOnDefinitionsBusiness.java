/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTerm;

/**
 * @author yirul
 *
 */
public interface AgreeOnDefinitionsBusiness extends StepBusinessInterface{
	/**
	 * This returns all the terms in this project.
	 * @param gwtProject The project that the user selected.
	 * @return The categories associated with this project.
	 * @throws SquareException
	 */
	List<GwtTerm> getTerms(GwtProject gwtProject)throws SquareException;
	
	/**
	 * This add terms to the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	
	GwtTerm addTerm(GwtProject gwtProject, GwtTerm gwtTerm)throws SquareException;
	
	/**
	 * This update an existing term in the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be updated to the project
	 * @throws SquareException
	 */
	void updateTerm(GwtProject gwtProject, GwtTerm gwtTerm) throws SquareException;
	
	/**
	 * This remove the existing term from the project.
	 * @param gwtProject The project that the user selected.
	 * @param GwtTerm The term to be removed from the project.
	 * @throws SquareException
	 */
	void removeTerm(GwtTerm GwtTerm)throws SquareException;
	
	void loadDefaultTerms(int projectId, List<GwtTerm> terms) throws SquareException;
}
