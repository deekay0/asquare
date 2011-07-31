package edu.cmu.square.client.remoteService.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtStepVerficationResult;



public interface StepServiceAsync
{
	void isStepClosed(String description, Integer projectId, AsyncCallback<Boolean> callback);
	
	void updateStepStatus(int projectId, int stepId, String status, AsyncCallback<Void> callback);
	
	void getStepsWithVerification(GwtProject project, AsyncCallback<List<GwtStepVerficationResult>> asyncCallback);
}
