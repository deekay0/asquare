package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.StepStatus;

@RemoteServiceRelativePath("reviewPackages.rpc")
public interface ReviewPackagesService extends RemoteService {
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
	 * This returns all the technique evaluation values for this project.
	 * @param projectID The project that the user selected.
	 * @return a list of all the ratings by technique and evaluation criteria for a project..
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	List<GwtRating> getRateValues(int projectID) throws SquareException;
	
	
	/**
	 * Set the rate value (1,2,3) for a given technique and evaluation criteria in a project.
	 * @param projectID The project that the user selected.
	 * @param packageID This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @param attributeID This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @param value This parameter is for returning the status of "Select Elicitation Techniques" step from the project.
	 * @return The Evaluation Criteria associated with this project.
	 * @throws SquareException This may indicate database errors, and/or business logic errors
	 * (for example user already removed)
	 */
	void setRateValue(int projectID, int packageID, int attributeID, int value) throws SquareException;
	
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
	 * @param gwtSoftwarePackage The technique to be added to the project.
	 * @throws SquareException
	 */
	GwtSoftwarePackage addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtSoftwarePackage) throws SquareException;
	

	/**
	 * This add a new evaluation criteria to the selected project.
	 * @param gwtProject The project that the user selected.
	 * @param gwtElicitEvaluation The evaluation to be added to the project.
	 * @return 
	 * @throws SquareException
	 */
	GwtQualityAttribute addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtQualityAttribute) throws SquareException;
	
	/**
	 * This update an exist elicitation technique to the selected project.
	 * @param gwtSoftwarePackage The technique which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateSoftwarePackage(GwtSoftwarePackage gwtSoftwarePackage, GwtProject gwtProject) throws SquareException;
	
	/**
	 * This update an exist evaluation criteria to the selected project.
	 * @param gwtElicitEvaluation The evaluation which has been updated.
	 * @param gwtProject TODO
	 * @throws SquareException
	 */
	void updateQualityAttribute(GwtQualityAttribute gwtQualityAttribute, GwtProject gwtProject) throws SquareException;
	/**
	 * This remove a selected elicitation technique from the selected project.
	 * @param gwtElicitTechnique The technique that wanted to be removed.
	 * @param projectId TODO
	 * @throws SquareException
	 */
	void removeSoftwarePackage(GwtSoftwarePackage gwtSoftwarePackage, Integer projectId) throws SquareException;
	/**
	 * This remove a selected evaluation criteria from the selected project.
	 * @param gwtElicitEvaluation  The evaluation that wanted to be removed.
	 * @throws SquareException
	 */
	void removeQualityAttribute(GwtQualityAttribute gwtQualityAttribute) throws SquareException;
	
	
	

}
