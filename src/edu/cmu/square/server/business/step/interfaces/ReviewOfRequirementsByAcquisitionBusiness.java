package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirement;

public interface ReviewOfRequirementsByAcquisitionBusiness extends StepBusinessInterface
{
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
	
	void loadDefaultRequirements(int projectId, List<GwtRequirement> requirements) throws SquareException;

}
