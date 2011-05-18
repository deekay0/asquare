/**
 * 
 */
package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.remoteService.step.interfaces.RiskAssessmentService;
import edu.cmu.square.server.business.step.interfaces.RiskAssessmentBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

/**
 * @author kaalpurush
 * 
 */
@Service
public class RiskAssessmentServiceImpl extends SquareRemoteServiceServlet implements RiskAssessmentService
{

	private static final long serialVersionUID = -862162205855546182L;

	@Resource
	private RiskAssessmentBusiness riskBusiness;

	public Integer addRisksToProject(Integer projectId, GwtRisk risk) throws SquareException
	{
		super.setValuesForAuthorization(riskBusiness);
		int riskID = riskBusiness.addRisksToProject(projectId, risk);
		Integer integer = new Integer(riskID);
		return integer;

	}


	public void deleteRisk(Integer riskId) throws SquareException
	{
		super.setValuesForAuthorization(riskBusiness);
		riskBusiness.deleteRisk(riskId);
	}



	public void updateRisk(GwtRisk risk) throws SquareException
	{
		super.setValuesForAuthorization(riskBusiness);
		riskBusiness.updateRisk(risk);

	}


	public List<GwtRisk> getRisksFromProject(Integer projectID) throws SquareException
	{
		return riskBusiness.getRisksFromProject(projectID);

	}


	public void setValuesForAuthorization()
	{
		setValuesForAuthorization(riskBusiness);

	}

}
