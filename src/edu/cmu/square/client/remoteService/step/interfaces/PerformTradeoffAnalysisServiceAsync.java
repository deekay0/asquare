package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.model.StepStatus;

public interface PerformTradeoffAnalysisServiceAsync {
	

	void setRequirementRateValue(int projectID, int packageID, int requirementID, int value,  AsyncCallback<Void> callback) ;
	
	void getRequirementRateValues(int projectID, AsyncCallback<List<GwtRequirementRating>> callback) ;
	
	void getTradeoffReasons(int projectID, AsyncCallback<List<GwtTradeoffReason>> callback);
	
	void setTradeoffReason(int projectID,int packageID, String tradeoffreason, AsyncCallback<Void> callback);
	
	void setPriority(int projectID, int packageID, int priority, AsyncCallback<Void> callback);



}
