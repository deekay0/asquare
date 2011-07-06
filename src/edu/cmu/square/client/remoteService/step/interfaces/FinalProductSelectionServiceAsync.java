package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtTradeoffReason;

public interface FinalProductSelectionServiceAsync 
{	
	void getRequirementRateValues(int projectID, AsyncCallback<List<GwtRequirementRating>> callback) ;
	void getTradeoffReasons(int projectID, AsyncCallback<List<GwtTradeoffReason>> callback);
	void setRationale(GwtRationale rationale, AsyncCallback<Void> callback);
	void getRationale(GwtProject projectID, AsyncCallback<GwtRationale> callback);
//	void updateRationale(int projectID, int packageId, String tradeoffreason, AsyncCallback<Void> callback);
}
