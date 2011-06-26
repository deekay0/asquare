package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.StepStatus;

@RemoteServiceRelativePath("performTradeoffAnalysis.rpc")
public interface PerformTradeoffAnalysisService extends RemoteService {
	
	List<GwtRequirementRating> getRequirementRateValues(int projectID) throws SquareException;
	
	void setRequirementRateValue(int projectID,int packageID, int requirementID, int value) throws SquareException;
	
}
