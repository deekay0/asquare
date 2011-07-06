package edu.cmu.square.server.remoteService.step.implementations;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtRationale;
import edu.cmu.square.client.model.GwtRequirementRating;
import edu.cmu.square.client.model.GwtTradeoffReason;
import edu.cmu.square.client.remoteService.step.interfaces.FinalProductSelectionService;
import edu.cmu.square.server.business.step.interfaces.FinalProductSelectionBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;

@Service
public class FinalProductSelectionServiceImpl extends SquareRemoteServiceServlet implements FinalProductSelectionService
{

	private static final long serialVersionUID = 3901084962447192221L;
	@Resource
	private FinalProductSelectionBusiness performTradeoffBusiness;


	@Override
	public List<GwtRequirementRating> getRequirementRateValues(int projectID) throws SquareException
	{
		return performTradeoffBusiness.getRequirementRateValues(projectID);
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
	
	public void setRationale(GwtRationale rationale) throws SquareException
	{
		performTradeoffBusiness.setRationale(rationale);
	}
	
	public GwtRationale getRationale(GwtProject projectID) throws SquareException
	{
		return performTradeoffBusiness.getRationale(projectID);
	}
}
