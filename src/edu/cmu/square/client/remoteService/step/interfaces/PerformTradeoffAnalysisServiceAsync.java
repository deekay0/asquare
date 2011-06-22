package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.StepStatus;

public interface PerformTradeoffAnalysisServiceAsync {
	

	void setRequirementRateValue(int projectID, int packageID, int requirementID, int value,  AsyncCallback<Void> callback) ;
	
	void getRequirementRateValues(int projectID, AsyncCallback<List<GwtRequirementRating>> callback) ;
	



}
