/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.StepStatus;


public interface PerformTradeoffAnalysisBusiness extends StepBusinessInterface
{
	
	void setRequirementRateValue(int projectID,int packageID, int requirementID, int value) throws SquareException;
	
	List<GwtRequirementRating>  getRequirementRateValues(int projectID) throws SquareException;
	
	int getRequirementRateValue(int projectID,int packageID, int requirementID) throws SquareException; 
	
	
	List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException;
		
	void addTradeoffReason(int projectID, int packageID, String tradeoffReason) throws SquareException;
	
	void updateTradeoffReason(int projectID, int packageID, String tradeoffReason) throws SquareException;
	
	void updatePriority(int projectID, int packageID, int priority) throws SquareException;
}
