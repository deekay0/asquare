/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.StepStatus;


public interface PerformTradeoffAnalysisBusiness extends StepBusinessInterface
{
	//List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	//List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	
	void setRequirementRateValue(int projectID,int packageID, int requirementID, int value) throws SquareException;
	
	List<GwtRequirementRating>  getRequirementRateValues(int projectID) throws SquareException;
	
	int getRequirementRateValue(int projectID,int packageID, int requirementID) throws SquareException; 
}
