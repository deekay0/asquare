package edu.cmu.square.client.remoteService.step.interfaces;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.StepStatus;

@RemoteServiceRelativePath("inspectionTechnique.rpc")
public interface InspectionTechniqueService extends RemoteService
{

	List<GwtInspectionTechnique> getInspectionTechniques(Integer projectId, StepStatus stepStatus) throws SquareException;

	GwtInspectionTechnique addInspectionTechnique(Integer projectId, GwtInspectionTechnique inspection) throws SquareException;

	void updateInspectionTechnique(Integer projectId, GwtInspectionTechnique inspection) throws SquareException;
	
	void removeInspectionTechnique(Integer projectId, GwtInspectionTechnique gwtInspection) throws SquareException;
	
}
