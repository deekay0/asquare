/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtEvaluation;
import edu.cmu.square.client.model.GwtPackage;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtQualityAttribute;
import edu.cmu.square.client.model.GwtRating;
import edu.cmu.square.client.model.GwtSoftwarePackage;
import edu.cmu.square.client.model.StepStatus;


public interface ReviewPackagesBusiness extends StepBusinessInterface
{
	
	List<GwtSoftwarePackage> getSoftwarePackages(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	
	List<GwtQualityAttribute> getQualityAttributes(GwtProject gwtProject, StepStatus stepStatus) throws SquareException;
	
	void addSoftwarePackage(GwtProject gwtProject, GwtSoftwarePackage gwtElicitTechnique) throws SquareException;
	
	void addQualityAttribute(GwtProject gwtProject, GwtQualityAttribute gwtElicitEvaluation) throws SquareException;
	
	void updateSoftwarePackage(GwtSoftwarePackage gwtElicitTechnique, GwtProject gwtProject) throws SquareException;
	
	
  void updateQualityAttribute(GwtQualityAttribute gwtElicitEvaluation, GwtProject gwtProject) throws SquareException;


	
	void removeSoftwarePackage(GwtSoftwarePackage gwtElicitTechnique, Integer projectId) throws SquareException;

	void removeQualityAttribute(GwtQualityAttribute gwtElicitEvaluation) throws SquareException;

	
	void setRateValue(int projectID,int techniqueID, int evaluationCriteriaID, int value) throws SquareException;

	 List<GwtRating>  getRateValues(int projectID) throws SquareException;
	

	  int getRateValue(int projectID, int techniqueID, int evaluationCriteriaID) throws SquareException; 
	  
	   void loadDefaultTechniques(int projectId, List<GwtSoftwarePackage> techniques) throws SquareException;
	   
	   void loadDefaultEvaluations(int projectId, List<GwtQualityAttribute> evaluations) throws SquareException;
}
