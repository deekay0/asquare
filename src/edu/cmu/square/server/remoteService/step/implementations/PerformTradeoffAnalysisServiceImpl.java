package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtTradeoffReason;

import edu.cmu.square.client.remoteService.step.interfaces.PerformTradeoffAnalysisService;

import edu.cmu.square.server.business.step.interfaces.PerformTradeoffAnalysisBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class PerformTradeoffAnalysisServiceImpl extends SquareRemoteServiceServlet implements PerformTradeoffAnalysisService
{

	private static final long serialVersionUID = 3901084962447192221L;
	@Resource
	private PerformTradeoffAnalysisBusiness performTradeoffBusiness;


	@Override
	public List<GwtRequirementRating> getRequirementRateValues(int projectID) throws SquareException
	{
		return performTradeoffBusiness.getRequirementRateValues(projectID);
	}

	@Override
	public void setRequirementRateValue(int projectID, int packageID, int requirementID, int value) throws SquareException
	{
		// TODO Auto-generated method stub
		performTradeoffBusiness.setRequirementRateValue(projectID, packageID, requirementID, value);
	}

	@Override
	public void setValuesForAuthorization()
	{
		// TODO Auto-generated method stub
		setValuesForAuthorization(performTradeoffBusiness);
	}

	@Override
	public List<GwtTradeoffReason> getTradeoffReasons(int projectID) throws SquareException
	{
		// TODO Auto-generated method stub
		return performTradeoffBusiness.getTradeoffReasons(projectID);
	}

	@Override
	public void setTradeoffReason(int projectID, int packageID, String tradeoffreason) throws SquareException
	{
		// TODO Auto-generated method stub
		performTradeoffBusiness.updateTradeoffReason(projectID, packageID, tradeoffreason);
	}

	@Override
	public void setPriority(int projectID, int packageID, int priority) throws SquareException
	{
		// TODO Auto-generated method stub
		performTradeoffBusiness.updatePriority(projectID, packageID, priority);
	}

}
