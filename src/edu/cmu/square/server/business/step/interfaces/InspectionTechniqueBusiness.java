package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtInspectionTechnique;
import edu.cmu.square.client.model.StepStatus;


public interface InspectionTechniqueBusiness extends StepBusinessInterface
{	
	List<GwtInspectionTechnique> getInspectionTechniques(int projectId, StepStatus stepStatus) throws SquareException;

	GwtInspectionTechnique addInspectionTechnique(int projectId, GwtInspectionTechnique gwtInspection) throws SquareException;

	void updateInspectionTechnique(GwtInspectionTechnique gwtInspection, int projectId) throws SquareException;

	void removeInspectionTechnique(GwtInspectionTechnique gwtInspection) throws SquareException;

	void chooseInspectionTechnique(int projectId, GwtInspectionTechnique gwtInspection) throws SquareException;
	
	void loadDefaultInspections(int projectId, List<GwtInspectionTechnique> inspection) throws SquareException;
}
