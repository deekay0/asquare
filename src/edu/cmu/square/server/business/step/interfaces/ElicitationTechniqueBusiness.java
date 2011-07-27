/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTechniqueEvaluationValue;
import edu.cmu.square.client.model.StepStatus;


public interface ElicitationTechniqueBusiness extends StepBusinessInterface
{
	
	/**
	 * This returns all the elicitation techniques for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the step status of "Select Elicitation Techniques" step from the project.
	 * @return The Elicitation Techniques associated with this project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtTechnique> getTechniques(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	
	/**
	 * This returns all the evaluation criteria for this project.
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @return The Evaluation Criteria associated with this project.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtEvaluation> getEvaluations(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	

	/**
	 * This add a new elicitation techniques to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitTechnique The technique to be added to the project.
	 * @throws SquareException
	 */
	void addTechnique(GwtProject gwtProject, GwtTechnique gwtElicitTechnique) throws SquareException;
	

	/**
	 * This add a new evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation to be added to the project.
	 * @throws SquareException
	 */
	void addEvaluation(GwtProject gwtProject, GwtEvaluation gwtElicitEvaluation) throws SquareException;
	
	/**
	 * This update an exist elicitation technique to the selected project.
	 * @param gwtElicitTechnique The technique which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateTechnique(GwtTechnique gwtElicitTechnique, GwtProject gwtProject) throws SquareException;
	
	/**
	 * This update an exist evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation which has been updated.
	 * @param gwtProject 
	 * @throws SquareException
	 */
	void updateEvaluation(GwtEvaluation gwtElicitEvaluation, GwtProject gwtProject) throws SquareException;
	/**
	 * This remove a selected elicitation technique from the selected project.
	 * @param gwtElicitTechnique The technique that wanted to be removed.
	 * @param projectId TODO
	 * @param gwtProject The project that the user selected.
	 * @throws SquareException
	 */
	void removeTechnique(GwtTechnique gwtElicitTechnique, Integer projectId) throws SquareException;
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	void removeEvaluation(GwtEvaluation gwtElicitEvaluation) throws SquareException;
	
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	void setRateValue(int projectID,int techniqueID, int evaluationCriteriaID, int value) throws SquareException;
	
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	 List<GwtTechniqueEvaluationValue>  getRateValues(int projectID) throws SquareException;
	

	  int getRateValue(int projectID, int techniqueID, int evaluationCriteriaID) throws SquareException; 
	  
	   void loadDefaultTechniques(int projectId, List<GwtTechnique> techniques) throws SquareException;
	   
	   void loadDefaultEvaluations(int projectId, List<GwtEvaluation> evaluations) throws SquareException;
}
