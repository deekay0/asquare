package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTechnique;
import edu.cmu.square.client.model.GwtTechniqueEvaluationValue;
import edu.cmu.square.client.model.StepStatus;

public interface ElicitationTechniqueServiceAsync {
	/**
	 * This returns all the elicitation techniques for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the step status of "Select Elicitation Techniques" step from the project.
	 * @return The Elicitation Techniques associated with this project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void getTechniques(GwtProject gwtProject, StepStatus stepStatus, AsyncCallback<List<GwtTechnique>> callback);
	
	/**
	 * This returns all the evaluation criteria for this project.
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @return The Evaluation Criteria associated with this project.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void getEvaluations(GwtProject gwtProject, StepStatus stepStatus, AsyncCallback<List<GwtEvaluation>> callback);
	
	/**
	 * Set the rate value (1,2,3) for a given technique and evaluation criteria in a project.
	 * @param projectID The project that the user selected.
	 * @param techniqueID This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @param evaluationID This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @param value This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @return The Evaluation Criteria associated with this project.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void setRateValue(int projectID, int techniqueID, int evaluationID, int value,  AsyncCallback<Void> callback) ;
	
	/**
	 * This returns all the technique evaluation values for this project.
	 * @param projectID The project that the user selected.
	 * @return a list of all the ratings by technique and evaluation criteria for a project..
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void getRateValues(int projectID, AsyncCallback<List<GwtTechniqueEvaluationValue>> callback) ;
	
	/**
	 * This add a new elicitation techniques to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitTechnique The technique to be added to the project.
	 * @throws SquareException
	 */
	void addTechnique(GwtProject gwtProject, GwtTechnique gwtElicitTechnique, AsyncCallback<GwtTechnique> callback);
	

	/**
	 * This add a new evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation to be added to the project.
	 * @throws SquareException
	 */
	void addEvaluation(GwtProject gwtProject, GwtEvaluation gwtElicitEvaluation, AsyncCallback<GwtEvaluation> asyncCallback);
	
	/**
	 * This update an exist elicitation technique to the selected project.
	 * @param gwtElicitTechnique The technique which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateTechnique(GwtTechnique gwtElicitTechnique, GwtProject gwtProject, AsyncCallback<Void> callback);
	
	/**
	 * This update an exist evaluation criteria to the selected project.
	 * @param gwtElicitEvaluation The evaluation which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateEvaluation(GwtEvaluation gwtElicitEvaluation, GwtProject gwtProject, AsyncCallback<Void> callback) ;
	/**
	 * This remove a selected elicitation technique from the selected project.
	 * @param gwtElicitTechnique The technique that wanted to be removed.
	 * @throws SquareException
	 */
	void removeTechnique(GwtTechnique gwtElicitTechnique, Integer projectId, AsyncCallback<Void> callback);
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	void removeEvaluation(GwtEvaluation gwtElicitEvaluation, AsyncCallback<Void> callback);
	

}
