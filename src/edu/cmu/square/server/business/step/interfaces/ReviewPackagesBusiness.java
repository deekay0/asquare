/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtPackage;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.StepStatus;


public interface ReviewPackagesBusiness extends StepBusinessInterface
{
	
	
	/**
	 * This returns all the elicitation techniques for this project.	 * 
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the step status of "Select Elicitation Techniques" step from the project.
	 * @return The Elicitation Techniques associated with this project.
	 * @throws SquareException  This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	
	
	/**
	 * This returns all the evaluation criteria for this project.
	 * @param gwtProject The project that the user selected.
	 * @param stepStatus This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @return The Evaluation Criteria associated with this project.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	

	/**
	 * This add a new elicitation techniques to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitTechnique The technique to be added to the project.
	 * @throws SquareException
	 */
	void addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtElicitTechnique) throws SquareException;
	

	/**
	 * This add a new evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation to be added to the project.
	 * @throws SquareException
	 */
	void addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtElicitEvaluation) throws SquareException;
	
	
	/**
	 * This update an exist elicitation technique to the selected project.
	 * @param gwtElicitTechnique The technique which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateSoftwarePackage(GwtSoftwarePackage gwtElicitTechnique, GwtProject gwtProject) throws SquareException;
	
	/**
	 * This update an exist evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation which has been updated.
	 * @param gwtProject 
	 * @throws SquareException
	 */
	
  void updateQualityAttribute(GwtQualityAttribute gwtElicitEvaluation, GwtProject gwtProject) throws SquareException;
	/**
	 * This remove a selected elicitation technique from the selected project.
	 * @param gwtElicitTechnique The technique that wanted to be removed.
	 * @param projectId TODO
	 * @param gwtProject The project that the user selected.
	 * @throws SquareException
	 */
  

	
	void removeSoftwarePackage(GwtSoftwarePackage gwtElicitTechnique, Integer projectId) throws SquareException;
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	void removeQualityAttribute(GwtQualityAttribute gwtElicitEvaluation) throws SquareException;
	
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
	
	 List<GwtRating>  getRateValues(int projectID) throws SquareException;
	

	  int getRateValue(int projectID, int techniqueID, int evaluationCriteriaID) throws SquareException; 
	  
	   void loadDefaultTechniques(int projectId, List<GwtSoftwarePackage> techniques) throws SquareException;
	   
	   void loadDefaultEvaluations(int projectId, List<GwtQualityAttribute> evaluations) throws SquareException;
}
