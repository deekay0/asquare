package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtTradeoffReason;

@RemoteServiceRelativePath("finalProductSelection.rpc")
public interface FinalProductSelectionService extends RemoteService {
	
	List<GwtRequirementRating> getRequirementRateValues(int projectID) throws SquareException;
	
	List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException;
	
	void setRationale(GwtRationale rationale) throws SquareException;
	GwtRationale getRationale(GwtProject projectID) throws SquareException;
}
