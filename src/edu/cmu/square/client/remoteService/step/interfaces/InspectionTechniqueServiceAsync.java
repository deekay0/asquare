package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.StepStatus;

public interface InspectionTechniqueServiceAsync
{
	void getInspectionTechniques(Integer projectId, StepStatus stepStatus, AsyncCallback<List<GwtInspectionTechnique>> callback);
	
	void addInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection, AsyncCallback<GwtInspectionTechnique> callback);
	
	void updateInspectionTechnique(Integer projectId, GwtInspectionTechnique inspection, AsyncCallback<Void> callback);
	
	void removeInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection, AsyncCallback<Void> callback);	
}
