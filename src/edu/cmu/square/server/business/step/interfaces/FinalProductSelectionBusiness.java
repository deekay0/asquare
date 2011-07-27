/**
 * 
 */
package edu.cmu.square.server.business.step.interfaces;

import java.util.List;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.dao.model.Project;

public interface FinalProductSelectionBusiness extends StepBusinessInterface
{
	public StepEnum getStepDescription() throws SquareException;
	public GwtRationale getRationale(GwtProject project) throws SquareException;
	public void setRationale(GwtRationale rationale) throws SquareException;
	public void updateTradeoffReason(int projectID, int packageId, String tradeoffReason) throws SquareException;
	public List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException;
	public int getRequirementRateValue(int projectID,int packageID, int requirementID) throws SquareException; 
	public List<GwtRequirementRating>  getRequirementRateValues(int projectID) throws SquareException;
}
